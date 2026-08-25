package net.rosemarythyme.simplymore.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.c2s.common.SyncedClientOptions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.StatHandler;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.BladeOfTheGrotesqueItem;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class StatueEntity extends AbstractVisibleAbilityEntity {
    public StatueData ownerSnapshot;
    public static final TrackedData<Float> YAW = DataTracker.registerData(StatueEntity.class, TrackedDataHandlerRegistry.FLOAT);

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(YAW, 0f);
    }

    public float getYawOverride() {
        return this.dataTracker.get(YAW);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.putFloat("yaw", this.getYaw());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        this.dataTracker.set(YAW, nbt.getFloat("yaw"));
    }


    public record StatueData(LivingEntity owner, EntityType<?> type, NbtCompound compound) {
        public LivingEntity snapshot(World world) {
            Class<? extends LivingEntity> clazz = owner.getClass();
            try {
                if(ServerPlayerEntity.class.isAssignableFrom(clazz)) {
                    ServerWorld serverWorld = (ServerWorld) world;
                    Entity player = clazz.getDeclaredConstructor(MinecraftServer.class, ServerWorld.class, GameProfile.class, SyncedClientOptions.class).newInstance(serverWorld.getServer(), serverWorld, ((ServerPlayerEntity) owner).getGameProfile(), ((ServerPlayerEntity) owner).getClientOptions());
                    return (ServerPlayerEntity) player;
                } else if (ClientPlayerEntity.class.isAssignableFrom(clazz)) {
                    Entity player = clazz.getDeclaredConstructor(MinecraftClient.class, ClientWorld.class, ClientPlayNetworkHandler.class, StatHandler.class, ClientRecipeBook.class, boolean.class, boolean.class).newInstance(MinecraftClient.getInstance(), ((ClientPlayerEntity) owner).clientWorld, MinecraftClient.getInstance().getNetworkHandler(), null, null, false, false);
                    return (ClientPlayerEntity) player;
                }

                Entity entity = clazz.getDeclaredConstructor(EntityType.class, World.class).newInstance(type, world);
                return entity instanceof LivingEntity livingEntity ? livingEntity : null;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
                return null;
            }
        }
    }

    public StatueEntity(EntityType<StatueEntity> entityType, World world) {
        super(entityType, world);
    }

    public StatueEntity(@NotNull LivingEntity owner, Vec3d position, float yaw) {
        super(owner, position, EntityRegistry.STATUE.get());
        this.dataTracker.set(YAW, yaw);
        ownerSnapshot = createSnapshot(owner);
    }

    @Override
    public void tick() {
        super.tick();

        if(ownerSnapshot == null) {
            Optional<UUID> uuid = getOwnerUUID();
            if(uuid.isEmpty()) return;

            List<LivingEntity> possibleOwners = this.getWorld().getNonSpectatingEntities(LivingEntity.class, MathUtils.createCubeBox(this.getPos(), 100))
                    .stream().filter(e -> uuid.get().equals(e.getUuid())).toList();

            if(possibleOwners.isEmpty()) return;

            ownerSnapshot = createSnapshot(possibleOwners.getFirst());
        }
    }

    private static StatueData createSnapshot(LivingEntity owner) {
        NbtCompound nbt = owner.writeNbt(new NbtCompound());
        nbt.putInt("HurtTime", 0);

        return new StatueData(owner, owner.getType(), nbt);
    }

    @Override
    public int getLifespan() {
        return BladeOfTheGrotesqueItem.SETTINGS.selfStunTime;
    }

    @Override
    protected void serverTick(LivingEntity owner) {
        if(!ActiveAbilityManager.SERVER.isStatue(owner)) {
            discard();
        }

        setPosition(owner.getPos());
        owner.setPose(EntityPose.STANDING);
    }

    @Override
    public int getOutroTicks() {
        return 0;
    }

    @Override
    public int getIntroTicks() {
        return 0;
    }
}
