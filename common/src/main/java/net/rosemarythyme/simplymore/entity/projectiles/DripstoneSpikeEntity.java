package net.rosemarythyme.simplymore.entity.projectiles;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.EnumeratedEntity;
import net.rosemarythyme.simplymore.item.components.ActiveHitsComponent;
import net.rosemarythyme.simplymore.item.uniques.CrustspireItem;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemComponentRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.sweenus.simplyswords.registry.EffectRegistry;
import net.sweenus.simplyswords.registry.SoundRegistry;
import org.jetbrains.annotations.NotNull;

public class DripstoneSpikeEntity extends AbstractAbilityProjectileEntity implements EnumeratedEntity {
    public static final TrackedData<Integer> ORDINAL = DataTracker.registerData(DripstoneSpikeEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private boolean isFired = false;

    @Override
    public boolean shouldEnumerate() {
        return !isFired;
    }

    @Override
    protected void updateRotation() {
        if(isFired) {
            Pair<Float, Float> yawPitch = MathUtils.getYawAndPitch(this.getVelocity());
            this.setRotation(yawPitch.getLeft(), yawPitch.getRight());
        }
    }

    @Override
    public DataTracker getDataTrackerBridge() {
        return getDataTracker();
    }

    @Override
    public TrackedData<Integer> getDataType() {
        return ORDINAL;
    }

    public DripstoneSpikeEntity(EntityType<DripstoneSpikeEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        initEnumeratedDataTracker(builder);
    }

    @Override
    float getAirDrag() {
        return 0.95f;
    }

    @Override
    float getWaterDrag() {
        return 0.8f;
    }

    @Override
    protected double getGravity() {
        return isFired ? 0.08f : 0f;
    }

    public void fire() {
        if(!(this.getOwner() instanceof LivingEntity owner)) return;
        this.refreshPositionAfterTeleport(owner.getEyePos());
        isFired = true;

        float yaw = (owner.getRandom().nextFloat() * 40) - 20 + owner.getYaw();
        float pitch = (owner.getRandom().nextFloat() * 20) - 10 + owner.getPitch();
        this.setVelocity(MathUtils.getDirectionalVector(yaw, pitch).multiply(CrustspireItem.SETTINGS.dripstoneFireSpeed));
        this.velocityModified = true;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        writeEnumeratedCustomDataToNbt(nbt);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        readEnumeratedCustomDataFromNbt(nbt);
    }

    protected void serverTick(LivingEntity owner) {
        if(!isFired) {
            moveBehind(owner);

            if(!EntityUtils.isHolding(owner, ItemRegistry.CRUSTSPIRE.get())) {
                discard();
                return;
            }
        }

        if(age % 10 == 0) {
            AudioVisualUtils.particleAroundEntity(this, new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.DRIPSTONE_BLOCK.getDefaultState()), 1, 0.05f, 0.005f);
        }
    }

    private void moveBehind(LivingEntity owner) {
        this.setRotation(owner.getYaw(), -owner.getPitch());
        this.setPosition(MathUtils.getPosBehindEntity(owner, this, this.getOrdinal(), 0.5f, 0.6f));
        this.setRotation(owner.getYaw(), 0);
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

        attemptRefund(owner);
    }

    private void attemptRefund(LivingEntity owner) {
        ItemStack stack = EntityUtils.getItemInEitherHand(ItemRegistry.CRUSTSPIRE.get(), owner);
        if(!stack.isEmpty()) {
            long worldTime = owner.getWorld().getTime();
            ActiveHitsComponent component = stack.getOrDefault(ItemComponentRegistry.ACTIVE_HITS.get(), new ActiveHitsComponent(0, worldTime));
            component = component.increment(1, 4, worldTime);

            if(component.value() >= CrustspireItem.SETTINGS.dripstoneRefund) {
                MathUtils.addToCounterComponent(stack, CrustspireItem.SETTINGS.dripstoneRefundNum);
                component = new ActiveHitsComponent(0, worldTime);

                AudioVisualUtils.playSound(owner.getWorld(), owner.getPos(), new Sound(SoundRegistry.ELEMENTAL_SWORD_EARTH_ATTACK_03.get()).setPitch(0.8f));
                AudioVisualUtils.playSound(owner.getWorld(), owner.getPos(), new Sound(SoundRegistry.DARK_SWORD_UNFOLD.get()));
                AudioVisualUtils.applyScreenshake((ServerWorld) owner.getWorld(), this.getPos(), owner,  5, 0.5f, 5);
            }

            stack.set(ItemComponentRegistry.ACTIVE_HITS.get(), component);
        }
    }

    @Override
    protected boolean canCollide(HitResult result) {
        return isFired;
    }

    @SuppressWarnings("unused") // used via recursion in `AttackUtils#ensureEnumeratedEntities`
    public DripstoneSpikeEntity(@NotNull LivingEntity owner, int ordinal) {
        super(owner, owner.getPos(), new Vec3d(0, 0, 0), EntityRegistry.DRIPSTONE_SPIKE.get());
        this.setOrdinal(ordinal);
    }
}
