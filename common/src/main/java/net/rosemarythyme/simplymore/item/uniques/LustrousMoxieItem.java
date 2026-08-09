package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class LustrousMoxieItem extends SimplyMoreUniqueSwordItem implements TwoHandedWeapon {
    int skillCooldown = UNIQUE_CONFIG.lustrous_moxie.cooldown;

    public LustrousMoxieItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, ServerWorld world, int consecutiveHits, boolean isFirstInTick) {
        if (!attacker.getWorld().isClient()) {
            StatusEffectInstance radiantMarkEffect = target.getStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.RADIANT_MARK));
            if (target.hasStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.RADIANT_MARK)) && radiantMarkEffect != null) {
                target.damage(attacker.getDamageSources().magic(),radiantMarkEffect.getAmplifier() + 1);
            }
            if (MathUtils.chance(attacker, UNIQUE_CONFIG.lustrous_moxie.chance)) {
                if (target.hasStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.RADIANT_MARK)) && radiantMarkEffect != null) {
                    int amplifier = radiantMarkEffect.getAmplifier() + 1;
                    int duration = 240 - (amplifier * 40);
                    target.addStatusEffect(new StatusEffectInstance(StatusEffectRegistry.getReference(StatusEffectRegistry.RADIANT_MARK), duration, amplifier), attacker);
                } else {
                    target.addStatusEffect(new StatusEffectInstance(StatusEffectRegistry.getReference(StatusEffectRegistry.RADIANT_MARK), 200, 0), attacker);
                }
            }
        }
    }

    public void attack(PlayerEntity user) {
        if (user.getWorld().isClient()) {
            return;
        }

        LivingEntity target = locateRadiantMarkedTarget(user);

        if(target != null) {
            damageAndKnockbackAndTeleportToRadiantMarkedTarget(target, user);
            damageAndKnockbackNearbyNonRadiantMarkedEntities(target, user);

            user.addStatusEffect(new StatusEffectInstance(StatusEffectRegistry.getReference(StatusEffectRegistry.STUNNED_MOXIE), UNIQUE_CONFIG.lustrous_moxie.stunTime, 0));
            user.getWorld().playSound(null, user.getBlockPos(), SoundRegistry.ELEMENTAL_SWORD_ICE_ATTACK_01.get(), SoundCategory.PLAYERS);
            user.getItemCooldownManager().set(this, skillCooldown);
        }
    }

    private LivingEntity locateRadiantMarkedTarget(PlayerEntity user) {
        int boxRange = UNIQUE_CONFIG.lustrous_moxie.range;
        Box box = new Box(user.getX() - boxRange,user.getY() - boxRange,user.getZ() - boxRange,user.getX() + boxRange,user.getY() + boxRange,user.getZ() + boxRange);
        List<LivingEntity> potentiallyMarkedLivingEntities = AttackUtils.cuboidAttack(user, box);

        return potentiallyMarkedLivingEntities.stream().filter(livingEntity -> livingEntity.hasStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.RADIANT_MARK))).findAny().orElse(null);
    }

    private void damageAndKnockbackAndTeleportToRadiantMarkedTarget(LivingEntity targetEntity, PlayerEntity user) {
        if (targetEntity == user.getAttacking()) {
            user.teleport(targetEntity.getX(), targetEntity.getY(), targetEntity.getZ(), false);
            ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.WAX_OFF, user.getX(), user.getY() + 2, user.getZ(), 500, 3, 3, 3, 0);
            targetEntity.removeStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.RADIANT_MARK));
            knockbackAndDamageEntity(targetEntity, user, UNIQUE_CONFIG.lustrous_moxie.targetDamage);
        }
    }

    private void damageAndKnockbackNearbyNonRadiantMarkedEntities(LivingEntity targetEntity, PlayerEntity user) {
        int boxRange = UNIQUE_CONFIG.lustrous_moxie.aoe;
        Box box = new Box(user.getX() - boxRange,user.getY() - boxRange,user.getZ() - boxRange,user.getX() + boxRange,user.getY() + boxRange,user.getZ() + boxRange);
        List<LivingEntity> nearbyLivingEntities = user.getWorld().getNonSpectatingEntities(LivingEntity.class, box);
        nearbyLivingEntities.remove(targetEntity);
        for (LivingEntity livingEntity : nearbyLivingEntities) {
            knockbackAndDamageEntity(livingEntity, user, UNIQUE_CONFIG.lustrous_moxie.aoeDamage);
        }
    }

    private void knockbackAndDamageEntity(LivingEntity targetEntity, PlayerEntity user, float damage) {
        if(!AttackUtils.canTarget(targetEntity, user, AttackUtils.AttackTarget.ENEMIES)) return;

        targetEntity.damage(user.getDamageSources().playerAttack(user), damage);

        Vec3d userPosition = user.getPos();
        Vec3d entityPosition = targetEntity.getPos();

        double deltaX = entityPosition.getX() - userPosition.getX();
        double deltaZ = entityPosition.getZ() - userPosition.getZ();
        double distance = Math.hypot(deltaX, deltaZ);

        if (distance == 0) {
            return;
        }

        double normalizedDeltaX = deltaX / distance;
        double normalizedDeltaZ = deltaZ / distance;

        targetEntity.setVelocity(normalizedDeltaX * UNIQUE_CONFIG.lustrous_moxie.knockbackStrength, 0.2, normalizedDeltaZ * UNIQUE_CONFIG.lustrous_moxie.knockbackStrength);
        targetEntity.velocityModified = true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);

        return itemStack.getDamage() >= itemStack.getMaxDamage() - 1
                ? TypedActionResult.fail(itemStack)
                : TypedActionResult.consume(itemStack);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!user.getWorld().isClient && user instanceof PlayerEntity player) {
             if (remainingUseTicks == 1)
                 attack(player);
        }
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 15;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.WAX_OFF);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip5").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip7").setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.LUSTROUS_MOXIE));
        }

        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.2f;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 400;
        @ValidatedInt.Restrict(min = 0)
        public int stunTime = 20;
        @ValidatedInt.Restrict(min = 0)
        public int range = 20;
        @ValidatedInt.Restrict(min = 0)
        public int aoe = 5;
        @ValidatedFloat.Restrict(min = 0f)
        public float knockbackStrength = 2f;
        @ValidatedFloat.Restrict(min = 0f)
        public float targetDamage = 15f;
        @ValidatedFloat.Restrict(min = 0f)
        public float aoeDamage = 10f;
    }
}
