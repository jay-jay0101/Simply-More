package net.rosemarythyme.simplymore.world.abilities;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.rosemarythyme.simplymore.item.components.RotationComponent;
import net.rosemarythyme.simplymore.item.uniques.CindergorgeItem;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;

public class FlameFlingType extends ActiveAbilityType {
    public FlameFlingType() {
        super(0);
    }

    @Override
    public boolean shouldContinue(ActiveAbilityManager.ActiveAbility ability) {
        return ability.owner().isHolding(ItemRegistry.CINDERGORGE.get());
    }

    @Override
    public void onFinish(ActiveAbilityManager.ActiveAbility ability) {
        float delta = 1 - ((float) ability.remainingDuration() / ability.duration());
        float cooldown = MathUtils.clampedLerp(delta, 0f, 1f, 40f, CindergorgeItem.SETTINGS.cooldown);

        new TargetList(ability.owner()).applyEffect(StatusEffects.NAUSEA, Math.round(300f * delta), 0);
        EntityUtils.cooldown(ability.owner(), ItemRegistry.CINDERGORGE.get(), Math.round(cooldown), true);
    }

    @Override
    public int tick(ActiveAbilityManager.ActiveAbility ability) {
        LivingEntity owner = ability.owner();
        ServerWorld world = (ServerWorld) owner.getWorld();

        ItemStack stack = EntityUtils.getActiveItem(owner, ItemRegistry.CINDERGORGE.get());
        if(stack == null) return super.tick(ability);

        float durationDelta = (float) ability.remainingDuration() / ability.duration();
        if(durationDelta >= 0.8f) {
            float speed = MathUtils.clampedLerp(durationDelta, 0.8f, 1f, 25f, 0f);
            RotationComponent.update(stack, world, speed);
        }

        spewFlames(ability, stack, world, owner);
        move(owner, durationDelta);

        return super.tick(ability);
    }

    private void spewFlames(ActiveAbilityManager.ActiveAbility ability, ItemStack stack, ServerWorld world, LivingEntity owner) {
        float rot = RotationComponent.getRotation(stack, world.getTime()) + 90;
        AudioVisualUtils.playSound(world, owner.getEyePos(), new Sound(SoundEvents.ITEM_FIRECHARGE_USE).setVolume(0.25f).randomisePitch(0.2f, 1.8f, world.getRandom()));
        AudioVisualUtils.particleLine(world, owner.getEyePos().add(MathUtils.getDirectionalVector(rot, 0).multiply(0.5f)), rot, 0, CindergorgeItem.SETTINGS.range, ParticleTypes.FLAME, 0.75f, 20, 0.1f, 0.05f);

        if(ability.duration() % 5 == 0) {
            AttackUtils.lineAttack(owner, owner.getEyePos(), rot, 0, CindergorgeItem.SETTINGS.range, 0.75, AttackUtils.AttackTarget.ENEMIES)
                    .setOnFireFor(3)
                    .forceDamage(CindergorgeItem.SETTINGS.fireDamage, owner.getDamageSources().inFire());
        }
    }

    private void move(LivingEntity entity, float delta) {
        float maxSpeed = CindergorgeItem.SETTINGS.maxSpeed / (entity.isOnGround() ? 1f : 2f);
        float speed = MathUtils.clampedLerp(delta, 0.8f, 1f, maxSpeed, 0f);
        float acceleration = MathUtils.clampedLerp(delta, 0.8f, 1f, CindergorgeItem.SETTINGS.acceleration, 0f);

        Vec3d direction = MathUtils.getDirectionalVector(entity.getYaw(), 0);
        Vec3d idealVelocity = direction.multiply(speed);

        Vec3d newVelocity = addAcceleration(entity.getVelocity(), idealVelocity, acceleration);

        EntityUtils.StepUpResult result = EntityUtils.tryStepUp(entity, newVelocity, 1f);
        if(result != EntityUtils.StepUpResult.SUCCESS) {
            newVelocity = EntityUtils.bounceHorizontally(entity, newVelocity, entity.isOnGround() ? 4f : 2f);
        }

        entity.setVelocity(newVelocity);
        entity.velocityModified = true;
    }

    private Vec3d addAcceleration(Vec3d original, Vec3d idealVelocity, float acceleration) {
        double x = addAcceleration(original.getX(), idealVelocity.getX(), acceleration);
        double z = addAcceleration(original.getZ(), idealVelocity.getZ(), acceleration);

        return new Vec3d(x, original.getY(), z);
    }

    private double addAcceleration(double original, double ideal, float acceleration) {
        if (original < ideal) {
            return Math.min(original + acceleration, ideal);
        }

        return Math.max(original - acceleration, ideal);
    }
}
