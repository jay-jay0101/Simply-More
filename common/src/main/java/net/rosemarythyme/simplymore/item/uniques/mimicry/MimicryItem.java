package net.rosemarythyme.simplymore.item.uniques.mimicry;

import dev.architectury.registry.registries.RegistrySupplier;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.config.MimicryConfig;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.TagRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemComponentRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.InventoryUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import net.sweenus.simplyswords.api.AwakeningApi;
import net.sweenus.simplyswords.api.WeaponAbilityContext;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.UniqueWeaponActiveAbility;
import net.sweenus.simplyswords.registry.ComponentTypeRegistry;
import net.sweenus.simplyswords.util.Styles;
import net.sweenus.simplyswords.world.WeaponAbilityCooldownManager;

import java.util.*;
import java.util.stream.Collectors;


public abstract class MimicryItem extends SimplyMoreUniqueSwordItem implements UniqueWeaponActiveAbility {
    public final static MimicryItem.EffectSettings SETTINGS = UNIQUE_CONFIG.mimicry;
    protected final static MimicryConfig MIMICRY_CONFIG = SETTINGS.config;

    public MimicryItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    protected Identifier getConfigPath() {
        return Identifier.of("simplymore.unique_effect.mimicry");
    }

    @Override
    public boolean canActivate(WeaponAbilityContext context) {
        return context.actor().isAlive() && !ActiveAbilityManager.SERVER.isInAbility(context.actor(), ActiveAbilityManager.Type.MIMICRY);
    }

    @Override
    public boolean activate(WeaponAbilityContext context) {
        beginAbilityByStack(context.actor().getStackInHand(context.hand()), context.actor());
        return true;
    }

    @Override
    public int getActivationCooldownTicks(ItemStack stack, WeaponAbilityContext context) {
        return 100;
    }

    @Override
    public TypedActionResult<ItemStack> startPlayerAbility(World world, PlayerEntity user, Hand hand) {
        if(!(world instanceof ServerWorld serverWorld)) return TypedActionResult.pass(user.getStackInHand(hand));
        return AttackUtils.holdToUse(serverWorld, user, hand);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return useFromDefaultInput(world, user, hand);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if(remainingUseTicks > 1) return;
        beginAbilityByStack(stack, user);
    }

    private void beginAbilityByStack(ItemStack stack, LivingEntity user) {
        Optional<MimicryForm> form = MimicryForm.getByItem(stack.getItem());
        if(form.isPresent()) {
            int uniqueDuration = MathUtils.PSEUDOINFINITE_DURATION - form.get().ordinal();
            ActiveAbilityManager.SERVER.start(user, ActiveAbilityManager.Type.MIMICRY, uniqueDuration);

            stack.set(ItemComponentRegistry.CHANGE.get(), true);
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return UNIQUE_CONFIG.mimicry.windup;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if(world.isClient) return;
        if(!(entity instanceof LivingEntity livingEntity)) return;

        Boolean shouldChange = stack.getOrDefault(ItemComponentRegistry.CHANGE.get(), Boolean.FALSE);
        if(shouldChange && !ActiveAbilityManager.SERVER.isInAbility(livingEntity, ActiveAbilityManager.Type.MIMICRY)) {
            swapToRandomForm(livingEntity, stack);
        }
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.ASH);
    }

    public void swapToRandomForm(LivingEntity entity, ItemStack stack) {
        EntityUtils.cooldown(entity, stack.getItem(), SETTINGS.typeCooldown, true);
        if(!(stack.getItem() instanceof MimicryItem currentForm)) return;

        MimicryItem newForm = getRandomForm(currentForm, entity);
        EntityUtils.cooldown(entity, newForm, SETTINGS.cooldown, false);

        ItemStack newStack = changeStack(stack, newForm);
        InventoryUtils.replaceStackInInventory(entity, stack, newStack);
    }

    public boolean isFormAvailable(LivingEntity entity, ItemStack stack) {
        if(!(stack.getItem() instanceof MimicryItem item)) return false;
        if(item.isFormDisabledInConfig()) return false;
        if(entity.getWorld() instanceof ServerWorld world && WeaponAbilityCooldownManager.isCoolingDown(world, entity, stack)) return false;
        if(entity instanceof PlayerEntity player) return !player.getItemCooldownManager().isCoolingDown(item);

        return true;
    }

    public abstract boolean isFormDisabledInConfig();

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        Optional<MimicryItem> type = getWeaponType(otherStack);
        if(type.isPresent() && isFormAvailable(player, new ItemStack(type.get())) && this.isFormAvailable(player, stack)) {
            InventoryUtils.replaceStackInInventory(player, stack, changeStack(stack, type.get()));
            player.giveItemStack(otherStack);
            return true;
        }

        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }

    private Set<MimicryItem> getAllowedForms(LivingEntity entity) {
        return Arrays.stream(MimicryForm.values())
                .map(form -> form.item.get())
                .filter(entry -> entry instanceof MimicryItem item && isFormAvailable(entity, new ItemStack(item)))
                .map(entry -> (MimicryItem) entry).collect(Collectors.toSet());
    }

    private MimicryItem getRandomForm(MimicryItem currentForm, LivingEntity entity) {
        Set<MimicryItem> allowedForms = getAllowedForms(entity);
        allowedForms.remove(currentForm);

        if(entity instanceof PlayerEntity player) {
            getFormFromInventory(allowedForms, player);
        }

        return allowedForms.isEmpty() ? currentForm : allowedForms.stream().toList().get(entity.getRandom().nextInt(allowedForms.size()));
    }

    private void getFormFromInventory(Set<MimicryItem> allowedForms, PlayerEntity player) {
        List<ItemStack> inventory = InventoryUtils.getEntireInventory(player);
        Set<MimicryItem> inventoryForms = new HashSet<>();

        for(ItemStack stack : inventory) {
            Optional<MimicryItem> type = getWeaponType(stack);
            if(type.isPresent()) inventoryForms.add(type.get());
        }

        if(!inventoryForms.isEmpty()) {
            float chance = UNIQUE_CONFIG.mimicry.baseCopyChance + (inventoryForms.size() * UNIQUE_CONFIG.mimicry.copyChancePerItem);
            chance = Math.min(chance, UNIQUE_CONFIG.mimicry.maximumCopyChance);

            inventoryForms.retainAll(allowedForms);
            if(!inventoryForms.isEmpty() && MathUtils.chance(player, chance)) {
                allowedForms.retainAll(inventoryForms);
            }
        }
    }

    private ItemStack changeStack(ItemStack stack, MimicryItem item) {
        ItemStack newStack = stack.copyComponentsToNewStack(item, 1);

        newStack.remove(ComponentTypeRegistry.WEAPON_IMPLICIT.get());
        newStack.remove(ItemComponentRegistry.CHANGE.get());
        newStack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, item.getDefaultStack().get(DataComponentTypes.ATTRIBUTE_MODIFIERS));
        AwakeningApi.rebuildAttributes(stack);

        return newStack;
    }

    public Optional<MimicryItem> getWeaponType(ItemStack stack) {
        if(!stack.isIn(TagRegistry.ALL)) return Optional.empty();

        for (MimicryForm form : MimicryForm.values()) {
            if(stack.isIn(form.tag)) return Optional.of((MimicryItem) form.item.get());
        }

        return Optional.empty();
    }

    public abstract boolean usageTimeline(LivingEntity entity, int ticksUsed);

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.mimicry.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.mimicry.tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.mimicry.tooltip3").setStyle(Styles.TEXT));
        appendAbilityCooldownTooltip(tooltip, itemStack, SETTINGS.cooldown);
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.mimicry.tooltip4").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(Styles.RIGHT_CLICK));
        appendSpecificTooltip(tooltip);
        appendAbilityCooldownTooltip(tooltip, itemStack, SETTINGS.typeCooldown);

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public abstract void appendSpecificTooltip(List<Text> tooltip);

    public record VisualData(float scale, float yawOffset, Vec2f holdPos) {
        public static final VisualData DEFAULT = new VisualData(1f, 0f, new Vec2f(0.25f, 0.25f));
        public static final VisualData MIDDLE_HOLD = new VisualData(1f, -90f, new Vec2f(0f, 0f));
    }

    public enum MimicryForm {
        TWINBLADE(TagRegistry.TWINBLADE, ItemRegistry.MIMICRY_TWINBLADE, VisualData.MIDDLE_HOLD),
        LONGSWORD(TagRegistry.LONGSWORD, ItemRegistry.MIMICRY_LONGSWORD),
        GRANDSWORD(TagRegistry.GRANDSWORD, ItemRegistry.MIMICRY_GRANDSWORD),
        DEER_HORNS(TagRegistry.DEER_HORNS, ItemRegistry.MIMICRY_DEER_HORNS, new VisualData(0.5f, VisualData.DEFAULT.yawOffset, VisualData.DEFAULT.holdPos())),
        BACKHAND_BLADE(TagRegistry.BACKHAND_BLADE, ItemRegistry.MIMICRY_BACKHAND_BLADE, new VisualData(0.5f, VisualData.DEFAULT.yawOffset, VisualData.DEFAULT.holdPos())),
        GLAIVE(TagRegistry.GLAIVE, ItemRegistry.MIMICRY_GLAIVE),
        DAGGER(TagRegistry.DAGGER, ItemRegistry.MIMICRY_DAGGER, new VisualData(0.5f, VisualData.DEFAULT.yawOffset, VisualData.DEFAULT.holdPos())),
        HALBERD(TagRegistry.HALBERD, ItemRegistry.MIMICRY_HALBERD, new VisualData(1.6f, VisualData.DEFAULT.yawOffset, VisualData.DEFAULT.holdPos())),
        WARGLAIVE(TagRegistry.WARGLAIVE, ItemRegistry.MIMICRY_WARGLAIVE, VisualData.MIDDLE_HOLD),
        LANCE(TagRegistry.LANCE, ItemRegistry.MIMICRY_LANCE),
        KHOPESH(TagRegistry.KHOPESH, ItemRegistry.MIMICRY_KHOPESH),
        RAPIER(TagRegistry.RAPIER, ItemRegistry.MIMICRY_RAPIER),
        CUTLASS(TagRegistry.CUTLASS, ItemRegistry.MIMICRY_CUTLASS, new VisualData(0.5f, VisualData.DEFAULT.yawOffset, VisualData.DEFAULT.holdPos())),
        CLAYMORE(TagRegistry.CLAYMORE, ItemRegistry.MIMICRY_CLAYMORE),
        KATANA(TagRegistry.KATANA, ItemRegistry.MIMICRY_KATANA),
        SCYTHE(TagRegistry.SCYTHE, ItemRegistry.MIMICRY_SCYTHE),
        SPEAR(TagRegistry.SPEAR, ItemRegistry.MIMICRY_SPEAR),
        GREATAXE(TagRegistry.GREATAXE, ItemRegistry.MIMICRY_GREATAXE),
        GREAT_KATANA(TagRegistry.GREAT_KATANA, ItemRegistry.MIMICRY_GREAT_KATANA,  new VisualData(1.6f, VisualData.DEFAULT.yawOffset, VisualData.DEFAULT.holdPos())),
        SAI(TagRegistry.SAI, ItemRegistry.MIMICRY_SAI, new VisualData(0.3f, VisualData.DEFAULT.yawOffset, VisualData.DEFAULT.holdPos())),
        QUARTERSTAFF(TagRegistry.QUARTERSTAFF, ItemRegistry.MIMICRY_QUARTERSTAFF),
        CHAKRAM(TagRegistry.CHAKRAM, ItemRegistry.MIMICRY_CHAKRAM, new VisualData(0.5f, VisualData.DEFAULT.yawOffset, VisualData.DEFAULT.holdPos())),
        GREATHAMMER(TagRegistry.GREATHAMMER, ItemRegistry.MIMICRY_GREATHAMMER),
        PERNACH(TagRegistry.PERNACH, ItemRegistry.MIMICRY_PERNACH, new VisualData(0.6f, VisualData.DEFAULT.yawOffset, VisualData.DEFAULT.holdPos())),
        GREAT_SPEAR(TagRegistry.GREAT_SPEAR, ItemRegistry.MIMICRY_GREAT_SPEAR, new VisualData(1.6f, VisualData.DEFAULT.yawOffset, VisualData.DEFAULT.holdPos()));

        public final TagKey<Item> tag;
        public final RegistrySupplier<Item> item;
        public final VisualData data;

        MimicryForm(TagKey<Item> tag, RegistrySupplier<Item> item, VisualData data) {
            this.tag = tag;
            this.item = item;
            this.data = data;
        }

        MimicryForm(TagKey<Item> tag, RegistrySupplier<Item> item) {
            this(tag, item, new VisualData(1, 0,  new Vec2f(0.25f, 0.25f)));
        }

        public static Optional<MimicryForm> getByOrdinal(int ordinal) {
            MimicryForm[] forms = values();
            return ordinal >= 0 && ordinal < forms.length ? Optional.of(forms[ordinal]) : Optional.empty();
        }

        public static Optional<MimicryForm> getByItem(Item item) {
            for (MimicryForm form : values()) {
                if (form.item.get().equals(item)) return Optional.of(form);
            }

            return Optional.empty();
        }
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_LONGSWORD));
        }

        @ValidatedInt.Restrict(min = 0)
        public int typeCooldown = 400;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 60;
        @ValidatedInt.Restrict(min = 0)
        public int windup = 10;
        @ValidatedFloat.Restrict(min=0, max = 1)
        public float maximumCopyChance = 0.5f;
        @ValidatedFloat.Restrict(min=0, max = 1)
        public float copyChancePerItem = 0.1f;
        @ValidatedFloat.Restrict(min=0, max = 1)
        public float baseCopyChance = 0.15f;

        public MimicryConfig config = new MimicryConfig();
    }
}
