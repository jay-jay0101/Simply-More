package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractVisibleAbilityEntity extends AbstractAbilityPlacementEntity {
    public AbstractVisibleAbilityEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    public AbstractVisibleAbilityEntity(@NotNull LivingEntity owner, Vec3d position, EntityType<? extends LivingEntity> entityType) {
        super(owner, position, entityType);
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket(EntityTrackerEntry entityTrackerEntry) {
        return new EntitySpawnS2CPacket(this, entityTrackerEntry);
    }
}