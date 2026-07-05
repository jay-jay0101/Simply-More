package net.rosemarythyme.simplymore.item.uniques;

import dev.architectury.registry.registries.RegistrySupplier;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.config.MimicryConfig;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModComponentRegistry;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;
import net.rosemarythyme.simplymore.registry.ModTagRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.VisualEffectsUtils;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public abstract class MimicryItem extends SimplyMoreUniqueSwordItem {

    protected static MimicryConfig mimicryConfig = UNIQUE_CONFIG.mimicry.config;

    public MimicryItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, SwordTypes swordType, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, swordType, settings);
    }

    @Override
    protected Identifier getConfigPath() {
        return Identifier.of("simplymore.unique_effect.mimicry");
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if(isUsingAbility(user)) {
            return TypedActionResult.fail(itemStack);
        }

        user.setCurrentHand(hand);
        return itemStack.getDamage() >= itemStack.getMaxDamage() - 1
                ? TypedActionResult.fail(itemStack)
                : TypedActionResult.consume(itemStack);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (user.getWorld().isClient() || !(user instanceof PlayerEntity)) {
            super.usageTick(world, user, stack, remainingUseTicks);
            return;
        }

        if(remainingUseTicks == 1 && getAmplifier(stack.getItem()) != -1) {
            user.stopUsingItem();

            ((PlayerEntity) user).getItemCooldownManager().set(this, 1000);

            user.addStatusEffect(
                    new StatusEffectInstance(
                            ModEffectsRegistry.getReference(ModEffectsRegistry.MIMICRY_HAPPENING),
                            AttackUtils.INFINITE_DURATION,
                            getAmplifier(stack.getItem())
                    )
            );

            stack.set(ModComponentRegistry.CHANGE.get(), true);
        }

        super.usageTick(world, user, stack, remainingUseTicks);
    }


    public static int getAmplifier(Item item) {
        for (RegistrySupplier<Item> registry : ModItemsRegistry.MIMICRY_AMPLIFIERS) {
            if(registry.get() == item) return ModItemsRegistry.MIMICRY_AMPLIFIERS.indexOf(registry);
        }

        return -1;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return UNIQUE_CONFIG.mimicry.windup;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        Boolean shouldChange = stack.get(ModComponentRegistry.CHANGE.get());

        if(shouldChange == null) {
            stack.set(ModComponentRegistry.CHANGE.get(), false);
            shouldChange = false;
        }

        if (shouldChange
                && entity instanceof PlayerEntity player
                && !isUsingAbility(player)
                && !world.isClient()) {
            swapForm(player, stack);
        }

        VisualEffectsUtils.handleFootfalls(entity, stack, world, ParticleTypes.ASH);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    public void swapForm(PlayerEntity player, ItemStack stack) {
        player.getItemCooldownManager().set(stack.getItem(), UNIQUE_CONFIG.mimicry.typeCooldown);
        String currentForm = null;

        for (Map.Entry<String, RegistrySupplier<Item>> itemEntry : ModItemsRegistry.MIMICRY_ITEMS.entrySet()) {
            if(itemEntry.getValue().get() == stack.getItem()) {
                currentForm = itemEntry.getKey();
            }
        }

        String newForm = getWeightedRandomForm(currentForm, player);
        if(newForm == null) return;

        Item newItem = ModItemsRegistry.MIMICRY_ITEMS.get(newForm).get();
        player.getItemCooldownManager().set(newItem, UNIQUE_CONFIG.mimicry.cooldown);

        if(newItem instanceof MimicryItem mimicryItem) {
            ItemStack newItemStack = stack.copyComponentsToNewStack(mimicryItem, 1);
            newItemStack.set(ModComponentRegistry.CHANGE.get(), false);

            int slotIndex = player.getInventory().getSlotWithStack(stack);
            if(slotIndex != -1) {
                player.getInventory().setStack(slotIndex, newItemStack);
            } else {
                if(player.getOffHandStack() == stack) {
                    player.setStackInHand(Hand.OFF_HAND, newItemStack);
                }
            }
        }
    }

    public String getWeaponType(Item item) {
        for(Map.Entry<String, TagKey<Item>> tagKeyEntry : ModTagRegistry.MIMICRY_TAGS.entrySet()) {
            TagKey<Item> itemTagKey = tagKeyEntry.getValue();

            if (Registries.ITEM.getEntry(item).isIn(itemTagKey))
                return tagKeyEntry.getKey();
        }

        return null;
    }

    public boolean isFormEnabled(MimicryItem item, PlayerEntity user) {
        return !user.getItemCooldownManager().isCoolingDown(item) && !item.isFormDisabledInConfig();
    }

    public boolean isFormEnabled(String form, PlayerEntity user) {
        return isFormEnabled(((MimicryItem) ModItemsRegistry.MIMICRY_ITEMS.get(form).get()), user);
    }

    public abstract boolean isFormDisabledInConfig();

    public boolean isUsingAbility(PlayerEntity player) {
        return player.hasStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.MIMICRY_HAPPENING));
    }

    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if(otherStack.isIn(ModTagRegistry.ALL) && !isUsingAbility(player)) {
            String clickedItemType = getWeaponType(otherStack.getItem());
            Item newItem = ModItemsRegistry.MIMICRY_ITEMS.get(clickedItemType).get();

            if(newItem instanceof MimicryItem mimicryItem && isFormEnabled(mimicryItem, player)) {
                ItemStack newStack = stack.copyComponentsToNewStack(mimicryItem, 1);

                int slotIndex = player.getInventory().getSlotWithStack(stack);
                if(slotIndex != -1) {
                    player.getInventory().setStack(slotIndex, newStack);

                    player.giveItemStack(otherStack);
                    return true;
                }
            }
        }

        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }

    public String getWeightedRandomForm(String currentForm, PlayerEntity player) {
        List<String> availableForms = new ArrayList<>();

        List<ItemStack> inventoryStacks = new ArrayList<>(player.getInventory().main);
        inventoryStacks.addAll(player.getInventory().offHand);

        for (ItemStack stack : inventoryStacks) {
            if(stack.isIn(ModTagRegistry.ALL)) {
                String form = getWeaponType(stack.getItem());
                if(form != null && isFormEnabled(form, player)) {
                    if(!availableForms.contains(form)) {
                        availableForms.add(form);
                    }
                }
            }
        }

        float chance = UNIQUE_CONFIG.mimicry.baseCopyChance + (availableForms.size() * UNIQUE_CONFIG.mimicry.copyChancePerItem);
        chance = Math.min(chance, UNIQUE_CONFIG.mimicry.maximumCopyChance);

        if(!MathUtils.chance(player, chance)) {
            return getRandom(currentForm, player);
        }

        availableForms.remove(currentForm);

        if(availableForms.isEmpty()) {
            return getRandom(currentForm, player);
        }

        return availableForms.get(player.getRandom().nextInt(availableForms.size()));
    }

    public String getRandom(String currentForm, PlayerEntity player) {
        List<String> availableForms = new ArrayList<>();

        for(Map.Entry<String, RegistrySupplier<Item>> weaponType : ModItemsRegistry.MIMICRY_ITEMS.entrySet()) {
            String form =  weaponType.getKey();

            if(isFormEnabled(form, player) && !availableForms.contains(form) && !form.equals(currentForm)) {
                availableForms.add(form);
            }
        }

        return availableForms.isEmpty() ?
                null :
                availableForms.get(player.getRandom().nextInt(availableForms.size()));
    }

    public abstract void usageTimeline(PlayerEntity player, int ticksUsed);

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.mimicry.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.mimicry.tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.mimicry.tooltip4").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.mimicry.tooltip6").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(Styles.RIGHT_CLICK));
        appendSpecificTooltip(tooltip);

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public abstract void appendSpecificTooltip(List<Text> tooltip);

    public void knockback(PlayerEntity player, LivingEntity target, float strength) {
        Vec3d userPosition = player.getPos();
        Vec3d entityPosition = target.getPos();

        double deltaX = entityPosition.getX() - userPosition.getX();
        double deltaZ = entityPosition.getZ() - userPosition.getZ();
        double distance = Math.hypot(deltaX, deltaZ);

        if (distance == 0) {
            return;
        }

        double normalizedDeltaX = deltaX / distance;
        double normalizedDeltaZ = deltaZ / distance;

        target.setVelocity(normalizedDeltaX * strength, 0.4, normalizedDeltaZ * strength);
        target.velocityModified = true;
    }

    public void jump(LivingEntity target, float xzStrength, float yStrength) {
        Vector3d facingVector = MathUtils.getNormalised2dVector(target.getYaw()).mul(xzStrength);
        target.setVelocity(facingVector.x(), yStrength, facingVector.z());
        target.velocityModified = true;
    }

    public List<LivingEntity> sweepAttack(PlayerEntity player, float range) {
        return sweepAttack(player, range, 0);
    }

    public List<LivingEntity> sweepAttack(PlayerEntity player, float range, int angle) {
        Vec3d position = player.getEyePos();
        Vector3d normalisedVector = MathUtils.getNormalised2dVector(player.getYaw() + angle);

        Vec3d particlePos = new Vec3d(
                position.getX() + (normalisedVector.x() * range),
                position.getY(),
                position.getZ() + (normalisedVector.z() * range)
        );

        Box box = new Box(
                particlePos.getX() - range,
                particlePos.getY() - range,
                particlePos.getZ() - range,
                particlePos.getX() + range,
                particlePos.getY() + range,
                particlePos.getZ() + range
        );

        player.getWorld().playSound(null, particlePos.getX(), particlePos.getY(), particlePos.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, SoundCategory.PLAYERS, 1,0.5f);


        ((ServerWorld) player.getWorld()).spawnParticles(ParticleTypes.SWEEP_ATTACK, particlePos.getX(), particlePos.getY(), particlePos.getZ(), 1, 0, 0 , 0, 0);

        return AttackUtils.getTargets(player, box);
    }

    public List<LivingEntity> katanaAttack(PlayerEntity player, double x, double y, double z, float range) {

        Box box = new Box(
                x - range,
                y - range,
                z - range,
                x + range,
                y + range,
                z + range
        );

        ((ServerWorld) player.getWorld()).spawnParticles(ParticleTypes.SWEEP_ATTACK, x, y, z, (int)Math.pow(range, 2)*10, range, 2, range, 1f);
        player.getWorld().playSound(null, x, y, z, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 1f, 1.5f);

        return AttackUtils.getTargets(player, box);
    }

    public List<LivingEntity> spinAttack(PlayerEntity player, float range) {

        Box box = new Box(
                player.getX() - range,
                player.getY() - 1.5,
                player.getZ() - range,
                player.getX() + range,
                player.getY() + 1.5,
                player.getZ() + range
        );

        for(int i = 0; i < 10; i++) {
            float particleRange = range / 1.5f;

            Vector3d normalisedVector = MathUtils.getNormalised2dVector(i * 36);

            double xPos = player.getX() + (normalisedVector.x() * particleRange);
            double yPos = player.getEyeY();
            double zPos = player.getZ() + (normalisedVector.z() * particleRange);

            ((ServerWorld) player.getWorld()).spawnParticles(ParticleTypes.SWEEP_ATTACK, xPos, yPos, zPos, 1, 0, 0 , 0, 0);
            player.getWorld().playSound(null, xPos, yPos, zPos, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 1, player.getRandom().nextBetween(9,14)/10f);
        }

        return AttackUtils.getTargets(player, box);
    }

    public List<LivingEntity> slamAttack(PlayerEntity player, float range) {

        Box box = new Box(
                player.getX() - range,
                player.getY() - 1.5,
                player.getZ() - range,
                player.getX() + range,
                player.getY() + 1.5,
                player.getZ() + range
        );

        ((ServerWorld) player.getWorld()).spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.DIRT.getDefaultState()), player.getX(), player.getY(), player.getZ(), (int)Math.pow(range, 2)*10, range, 2, range, 1f);
        player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 0.5f, 1);


        return AttackUtils.getTargets(player, box);
    }

    public List<LivingEntity> stabAttack(PlayerEntity player, int range, float coverage) {
        List<LivingEntity> targets = new ArrayList<>();

        for (int j = 0; j < range/coverage; j++) {

            Vector3d normalisedVector = MathUtils.getNormalised3dVector(player);



            double dX = normalisedVector.x() * j * coverage * 1.2f;
            double dY = normalisedVector.y() * j * coverage * 1.2f;
            double dZ = normalisedVector.z() * j * coverage * 1.2f;

            double x = player.getX() + dX;
            double y = player.getEyeY() + dY;
            double z = player.getZ() + dZ;

            ((ServerWorld) player.getWorld()).spawnParticles(ParticleTypes.CRIT, x, y, z, 20, 0.1f, 0.1f , 0.1f, 0.2f);
            player.getWorld().playSound(null, x, y, z, SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, SoundCategory.PLAYERS, 0.5f, 1);

            Box box = new Box(
                    x - coverage,
                    y - coverage,
                    z - coverage,
                    x + coverage,
                    y + coverage,
                    z + coverage
            );

            List<LivingEntity> entities = AttackUtils.getTargets(player, box);
            targets.addAll(entities);
        }

        return targets;
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ModItemsRegistry.MIMICRY_LONGSWORD));
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
