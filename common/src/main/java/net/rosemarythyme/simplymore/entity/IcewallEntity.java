package net.rosemarythyme.simplymore.entity;

import dev.architectury.networking.NetworkManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.GrandfrostItem;
import net.rosemarythyme.simplymore.networking.s2c.S2CChangeAbilityAgePacket;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IcewallEntity extends AbstractCollidableAbilityEntity {
    public IcewallEntity(EntityType<IcewallEntity> entityType, World world) {
        super(entityType, world);
    }

    public IcewallEntity(@NotNull LivingEntity owner, Vec3d position) {
        super(owner, position, EntityRegistry.ICEWALL.get());
    }

    public void lower() {
        setAge(this.getLifespan());
    }

    @Override
    public int getLifespan() {
        return GrandfrostItem.SETTINGS.wallDuration;
    }

    @Override
    protected void serverTick(LivingEntity owner) {
        BlockHitResult floor = EntityUtils.raycastDown(this, this.getPos().offset(Direction.UP, 0.2f), getServerWorld(), 5);
        if(floor.getType() == HitResult.Type.MISS) {
            this.discard();
            return;
        }


        this.setPosition(floor.getPos());

        AudioVisualUtils.particleCube(getServerWorld(), this.getPos().offset(Direction.UP, 1), ParticleTypes.SNOWFLAKE, 10, 0.2f, 0.2f);

        if(age < 0) {
            AttackUtils.boxAttack(owner, this.getBoundingBox(), AttackUtils.AttackTarget.ENEMIES)
                    .damage(GrandfrostItem.SETTINGS.knockUpDamage, owner.getDamageSources().explosion(this, owner))
                    .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.CHILL), GrandfrostItem.SETTINGS.chillTime, 0)
                    .addVelocity(0, GrandfrostItem.SETTINGS.knockUp, 0);
        }
    }

    @Override
    public int getOutroTicks() {
        return 10;
    }

    @Override
    public int getIntroTicks() {
        return 4;
    }
}
