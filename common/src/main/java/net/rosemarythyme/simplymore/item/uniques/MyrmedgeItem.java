package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.item.components.GrabbedComponent;
import net.rosemarythyme.simplymore.item.interfaces.StackModifierItem;
import net.rosemarythyme.simplymore.registry.item.ItemComponentRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import net.sweenus.simplyswords.api.WeaponAbilityActivationSource;
import net.sweenus.simplyswords.api.WeaponAbilityContext;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.UniqueWeaponActiveAbility;
import net.sweenus.simplyswords.util.HelperMethods;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class MyrmedgeItem extends SimplyMoreUniqueSwordItem implements UniqueWeaponActiveAbility, StackModifierItem {
    public static final MyrmedgeItem.EffectSettings SETTINGS = UNIQUE_CONFIG.myrmedge;

    public MyrmedgeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    public static float getHungerPercentage(LivingEntity entity) {
        if(!(entity instanceof PlayerEntity player)) return 1f;
        return player.getHungerManager().getFoodLevel() / 20f;
    }

    @Override
    public TypedActionResult<ItemStack> startPlayerAbility(World world, PlayerEntity user, Hand hand) {
        return UniqueWeaponActiveAbility.super.startPlayerAbility(world, user, hand);
    }

    @Override
    public boolean activate(WeaponAbilityContext context) {
        if(ActiveAbilityManager.SERVER.isInAbility(context.actor(), ActiveAbilityManager.Type.GRASPING)) {
            ActiveAbilityManager.SERVER.stop(context.actor(), ActiveAbilityManager.Type.GRASPING);
            stopAbility(context.actor());
            return true;
        }

        Entity target = HelperMethods.getTargetedEntity(context.actor(), 2);
        if(!(target instanceof LivingEntity livingEntity)) return false;
        if(!AttackUtils.canTarget(context.actor(), livingEntity, AttackUtils.AttackTarget.ENEMIES)) return false;

        ItemStack stack = context.actor().getStackInHand(context.hand());
        stack.set(ItemComponentRegistry.GRABBED.get(), new GrabbedComponent(target.getUuid(), context.world().getTime()));

        AudioVisualUtils.playSound(context.world(), context.origin(), new Sound(SoundEvents.ENTITY_EVOKER_FANGS_ATTACK).setPitch(1.5f));

        ActiveAbilityManager.SERVER.start(context.actor(), ActiveAbilityManager.Type.GRASPING, SETTINGS.grabTime);
        return context.activationSource() != WeaponAbilityActivationSource.PLAYER;
    }

    public static void stopAbility(LivingEntity entity) {
        EntityUtils.cooldown(entity, ItemRegistry.MYRMEDGE.get(), SETTINGS.cooldown, true);

        LivingEntity target = getActiveMyrmedgeTarget(entity);
        if(target == null) return;

        Vec3d direction = MathUtils.getNormalised3dVector(entity).multiply(SETTINGS.throwStrength);
        target.setVelocity(direction);
        target.velocityModified = true;
    }

    public static LivingEntity getActiveMyrmedgeTarget(LivingEntity entity) {
        if(!(entity.getWorld() instanceof ServerWorld world)) return null;
        LivingEntity target = getStackTarget(entity.getStackInHand(Hand.MAIN_HAND), world);

        return target == null ?
                getStackTarget(entity.getStackInHand(Hand.OFF_HAND), world) :
                target;
    }

    private static LivingEntity getStackTarget(ItemStack stack, ServerWorld world) {
        if(!(stack.getItem() instanceof MyrmedgeItem)) return null;

        GrabbedComponent component = stack.get(ItemComponentRegistry.GRABBED.get());
        if(component == null) return null;
        if(component.time() < world.getTime() - SETTINGS.grabTime) return null;

        Entity entity = world.getEntity(component.entityId());
        if(!(entity instanceof LivingEntity target)) return null;

        if(target.isDead()) return null;
        return target;
    }

    @Override
    public boolean canActivate(WeaponAbilityContext context) {
        return context.actor().isAlive();
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return useFromDefaultInput(world, user, hand);
    }

    @Override
    public int getActivationCooldownTicks(ItemStack stack, WeaponAbilityContext context) {
        return SETTINGS.cooldown;
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.ASH);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.myrmedge.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.myrmedge.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.myrmedge.tooltip3").setStyle(textStyle));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplymore.myrmedge.tooltip4", MathUtils.translateTicks(
                UNIQUE_CONFIG.myrmedge.grabTime
        )).setStyle(textStyle));
        appendAbilityCooldownTooltip(tooltip, itemStack, SETTINGS.cooldown);

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    @Override
    public AttributeModifiersComponent getModifier(LivingEntity entity, ItemStack stack, AttributeModifiersComponent base) {
        float bonus = 1 - getHungerPercentage(entity);
        return base.with(
                EntityAttributes.GENERIC_ATTACK_DAMAGE,
                new EntityAttributeModifier(
                        SimplyMore.identifier("hunger_damage"),
                        MathHelper.lerp(bonus, 0, SETTINGS.maxDamageBonus),
                        EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                ),
                AttributeModifierSlot.MAINHAND
        ).with(
                EntityAttributes.GENERIC_MOVEMENT_SPEED,
                new EntityAttributeModifier(
                        SimplyMore.identifier("hunger_speed"),
                        MathHelper.lerp(bonus, 0, SETTINGS.maxSpeed),
                        EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                ),
                AttributeModifierSlot.MAINHAND
        );
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MYRMEDGE));
        }

        @ValidatedFloat.Restrict(min = 0f)
        public float maxDamageBonus = 0.4f;
        @ValidatedFloat.Restrict(min = 0f)
        public float maxSpeed = 1f;
        @ValidatedFloat.Restrict(min = 0f)
        public float grabSelfSlow = 0.4f;
        @ValidatedFloat.Restrict(min = 0f)
        public float grabDamage = 3f;
        @ValidatedInt.Restrict(min = 0)
        public int grabTime = 100;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 300;
        @ValidatedFloat.Restrict(min = 0f)
        public float throwStrength = 1.6f;
    }
}
