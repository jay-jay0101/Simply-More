package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.item.components.RotationComponent;
import net.rosemarythyme.simplymore.item.interfaces.StoppableAbilityItem;
import net.rosemarythyme.simplymore.registry.item.ItemComponentRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import net.sweenus.simplyswords.api.WeaponAbilityActivationSource;
import net.sweenus.simplyswords.api.WeaponAbilityContext;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.UniqueWeaponActiveAbility;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class CindergorgeItem extends SimplyMoreUniqueSwordItem implements UniqueWeaponActiveAbility, StoppableAbilityItem {
    public static final CindergorgeItem.EffectSettings SETTINGS = UNIQUE_CONFIG.cindergorge;

    public CindergorgeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return useFromDefaultInput(world, user, hand);
    }

    @Override
    public boolean canActivate(WeaponAbilityContext context) {
        return context.actor().isAlive() && !ActiveAbilityManager.SERVER.isInAbility(context.actor(), ActiveAbilityManager.Type.FLAME_FLINGER);
    }

    @Override
    public boolean activate(WeaponAbilityContext context) {
        if(context.activationSource() == WeaponAbilityActivationSource.PLAYER) return false;
        startAbility(context.actor(), context.actor().getStackInHand(context.hand()));

        return true;
    }

    private static void startAbility(LivingEntity user, ItemStack stack) {
        ActiveAbilityManager.SERVER.start(user, ActiveAbilityManager.Type.FLAME_FLINGER, SETTINGS.maxUseTime);
        stack.set(ItemComponentRegistry.ROTATION.get(), new RotationComponent(user.getYaw(), user.getWorld().getTime(), 0f));
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingTicks) {
        if(world.isClient) return;

        if (remainingTicks == getMaxUseTime(stack, user) - 1) {
            startAbility(user, stack);
        }
    }

    @Override
    public int getActivationCooldownTicks(ItemStack stack, WeaponAbilityContext context) {
        return SETTINGS.cooldown;
    }

    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return SETTINGS.maxUseTime;
    }

    @Override
    public TypedActionResult<ItemStack> startPlayerAbility(World world, PlayerEntity user, Hand hand) {
        if(!(world instanceof ServerWorld serverWorld)) return TypedActionResult.pass(user.getStackInHand(hand));
        return AttackUtils.holdToUse(serverWorld, user, hand);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.LAVA);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.cindergorge.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.cindergorge.tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.cindergorge.tooltip3").setStyle(Styles.TEXT));
        appendAbilityCooldownTooltip(tooltip, itemStack, SETTINGS.cooldown);

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    @Override
    public void stop(ItemStack stack, ServerWorld world, LivingEntity user, int ticksRemaining) {
        ActiveAbilityManager.SERVER.stop(user, ActiveAbilityManager.Type.FLAME_FLINGER);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.CINDERGORGE));
        }
        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.4f;
        @ValidatedFloat.Restrict(min = 0f)
        public float thornsDamage = 5f;
        @ValidatedInt.Restrict(min = 0)
        public int thornsFireDuration = 3;

        @ValidatedFloat.Restrict(min = 0f)
        public float maxSpeed = 1.75f;
        @ValidatedFloat.Restrict(min = 0f)
        public float acceleration = 0.15f;

        @ValidatedInt.Restrict(min = 0)
        public int maxUseTime = 200;
        @ValidatedInt.Restrict(min = 0)
        public int range = 5;
        @ValidatedFloat.Restrict(min = 0f)
        public float fireDamage = 3f;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 600;
    }
}
