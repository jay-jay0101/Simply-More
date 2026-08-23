package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.GrandfrostItem;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import org.jetbrains.annotations.NotNull;

public class BlizzardEntity extends AbstractAbilityPlacementEntity {
    public BlizzardEntity(EntityType<BlizzardEntity> entityType, World world) {
        super(entityType, world);
    }

    public BlizzardEntity(@NotNull LivingEntity owner, Vec3d position) {
        super(owner, position, EntityRegistry.BLIZZARD.get());
    }

    @Override
    public int getLifespan() {
        return GrandfrostItem.SETTINGS.wallDuration;
    }

    @Override
    protected void serverTick(LivingEntity owner) {
        AudioVisualUtils.rainParticlesAboveEntity(this, ParticleTypes.SNOWFLAKE, 60, (float) GrandfrostItem.SETTINGS.radius, 7, 0.2f);

        if(this.age == this.getLifespan() && owner instanceof PlayerEntity player) {
            player.getItemCooldownManager().set(ItemRegistry.GRANDFROST.get(), GrandfrostItem.SETTINGS.cooldown);
        }

        AttackUtils.cylinderAttack(owner, this.getPos().offset(Direction.UP, 2), GrandfrostItem.SETTINGS.radius, 5, AttackUtils.AttackTarget.ENEMIES)
                .damage(GrandfrostItem.SETTINGS.blizzardDamage, owner.getDamageSources().freeze())
                .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.CHILL), GrandfrostItem.SETTINGS.chillTime, 1);
    }

    @Override
    public int getOutroTicks() {
        return 1;
    }

    @Override
    public int getIntroTicks() {
        return 8;
    }
}
