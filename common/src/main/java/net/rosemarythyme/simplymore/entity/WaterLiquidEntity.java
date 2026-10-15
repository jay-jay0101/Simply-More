package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.TidebreakerItem;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.SummonUtils;
import net.rosemarythyme.simplymore.util.TargetUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
import org.jetbrains.annotations.NotNull;

public class WaterLiquidEntity extends AbstractLiquidEntity {
    private final Vec3d origin;

    public WaterLiquidEntity(EntityType<WaterLiquidEntity> entityType, World world) {
        super(entityType, world);
        origin = Vec3d.ZERO;
    }

    public WaterLiquidEntity(@NotNull LivingEntity owner, Vec3d position, Vec3d origin, float maxHeight) {
        super(owner, position, maxHeight, EntityRegistry.WATER.get());
        this.origin = origin;
    }

    public WaterLiquidEntity(@NotNull LivingEntity owner, Vec3d position, float maxHeight) {
        this(owner, position, position, maxHeight);
    }

    @Override
    protected void serverTick(LivingEntity owner) {
        if(this.getAge() != this.getLifespan()) return;

        AudioVisualUtils.particleAroundEntity(this, ParticleTypes.RAIN, 5, 0.1f, 0.1f);

        for(int i = 0; i < 4; i++) {
            int x = 0, z = 0;

            if(i % 2 == 0) {
                x = (i / 2) == 0 ? -1 : 1;
            } else {
                z = (i / 2) == 0 ? -1 : 1;
            }

            Vec3d newPos = this.getPos().add(x, 0, z);
            double distance = this.getPos().distanceTo(origin);
            if(distance < newPos.distanceTo(origin)) {
                spreadWater(owner, newPos);
            }
        }
    }

    private void spreadWater(LivingEntity owner, Vec3d newPos) {
        float newHeight = getMaxHeight() - (1f / TidebreakerItem.SETTINGS.waterRange);
        if(newHeight <= 0f) return;

        Box box = MathUtils.createCuboidBox(newPos, 0.45f, 3, 0.45f);
        if(!this.getWorld().getNonSpectatingEntities(this.getClass(), box).isEmpty()) return;

        SummonUtils.spawnAbility(new WaterLiquidEntity(owner, newPos.offset(Direction.UP, 1), origin, newHeight), owner, true);
        if(MathUtils.chance(this, 0.2f)) {
            AudioVisualUtils.playSound(this.getWorld(), newPos, new Sound(SoundEvents.ENTITY_PLAYER_SPLASH).setVolume(0.1f).randomisePitch(0.6f, 1.4f, this.getRandom()));
        }
    }

    @Override
    public int getLifespan() {
        return 5;
    }

    @Override
    public int getOutroTicks() {
        return 4;
    }

    @Override
    public int getIntroTicks() {
        return this.getPos().equals(origin) ? 4 : 0;
    }

    @Override
    public void hit(LivingEntity owner, TargetList hitList) {
        hitList.filterByTargetType(owner, TargetUtils.TargetType.ENEMIES)
                .damage(TidebreakerItem.SETTINGS.waterDamage, this.getDamageSources().indirectMagic(this, owner))
                .knockback(this.origin, TidebreakerItem.SETTINGS.waterKnockback)
                .incrementEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.LUNACY), TidebreakerItem.SETTINGS.lunacyDuration, 1, 9);
    }
}