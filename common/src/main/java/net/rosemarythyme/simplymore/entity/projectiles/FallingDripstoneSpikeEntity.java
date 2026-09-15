package net.rosemarythyme.simplymore.entity.projectiles;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.CrustspireItem;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.sweenus.simplyswords.registry.EffectRegistry;
import org.jetbrains.annotations.NotNull;

public class FallingDripstoneSpikeEntity extends AbstractAbilityProjectileEntity {
    @Override
    protected void updateRotation() {
        this.setRotation(0, 90);
    }

    public FallingDripstoneSpikeEntity(EntityType<FallingDripstoneSpikeEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    float getAirDrag() {
        return 0.95f;
    }

    @Override
    float getWaterDrag() {
        return 0.8f;
    }

    protected void serverTick(LivingEntity owner) {
        if(age % 10 == 0) {
            AudioVisualUtils.particleAroundEntity(this, new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.DRIPSTONE_BLOCK.getDefaultState()), 1, 0.05f, 0.005f);
        }
    }

    @Override
    protected void onHit(HitResult result) {
        AudioVisualUtils.playSound(getWorld(), result.getPos(), new Sound(SoundEvents.BLOCK_POINTED_DRIPSTONE_BREAK));
        AudioVisualUtils.particleAroundEntity(this, new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.DRIPSTONE_BLOCK.getDefaultState()), 40, 0.5f, 0.4f);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = getOwner();
        if(!(entity instanceof LivingEntity owner)) return;
        if(!(entityHitResult.getEntity() instanceof LivingEntity target)) return;

        new TargetList(target)
                .forceDamage(CrustspireItem.SETTINGS.dripstoneDamage, owner.getDamageSources().explosion(this, owner))
                .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.WOUNDED), CrustspireItem.SETTINGS.dripstoneEffectDuration, 0)
                .incrementEffect(EffectRegistry.getReference(EffectRegistry.SUNDERED_ARMOR), CrustspireItem.SETTINGS.dripstoneEffectDuration, CrustspireItem.SETTINGS.dripstoneSunderedArmor, CrustspireItem.SETTINGS.dripstoneSunderedArmorMax);
    }

    public FallingDripstoneSpikeEntity(@NotNull LivingEntity owner, Vec3d pos, Random random) {
        super(owner, pos, new Vec3d(0, random.nextFloat() * -0.5f, 0), EntityRegistry.FALLING_DRIPSTONE_SPIKE.get());
    }
}
