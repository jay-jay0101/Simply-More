package net.rosemarythyme.simplymore.world.abilities;

import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.item.uniques.MoundshifterItem;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import net.rosemarythyme.simplymore.world.PlayerItemUseManager;

public class DrillAbilityType extends ActiveAbilityType {
    public DrillAbilityType() {
        super(100);
    }

    @Override
    public boolean shouldContinue(ActiveAbilityManager.ActiveAbility ability) {
        LivingEntity owner = ability.owner();

        if (owner.getVehicle() instanceof LivingEntity vehicle) {
            return shouldContinue(ability.onOtherEntity(vehicle));
        }

        if (!owner.isOnGround()) {
            BlockHitResult hit = EntityUtils.raycastDown(owner, owner.getPos(), owner.getWorld(), 2);
            if (hit.getType() == HitResult.Type.MISS) return false;

            owner.setVelocity(new Vec3d(0, -2, 0));
            owner.velocityModified = true;
        }

        return true;
    }

    @Override
    public void onFinish(ActiveAbilityManager.ActiveAbility ability) {
        LivingEntity owner = ability.owner();
        ServerWorld world = (ServerWorld) owner.getWorld();

        if (owner instanceof PlayerEntity player) {
            PlayerItemUseManager.stop(player, player.getActiveItem(), false);
        } else {
            int useTime = ability.duration() - ability.remainingDuration();
            MoundshifterItem.emerge(world, owner, owner.isOnGround() && useTime >= 30);
        }
    }

    @Override
    public int tick(ActiveAbilityManager.ActiveAbility ability) {
        LivingEntity owner = ability.owner();
        ServerWorld world = (ServerWorld) owner.getWorld();

        if (owner.getVehicle() instanceof LivingEntity vehicle) {
            return tick(ability.onOtherEntity(vehicle));
        } else {
            owner.dismountVehicle();
            AudioVisualUtils.particleCuboid(world, owner.getPos(), new BlockStateParticleEffect(ParticleTypes.BLOCK, world.getBlockState(owner.getBlockPos().down())), 0.1f, 0.1f, 25, 0.2f);
        }

        return super.tick(ability);
    }

    @Override
    public Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> getModifiers(Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> map, LivingEntity entity) {
        map.put(EntityAttributes.GENERIC_STEP_HEIGHT, new EntityAttributeModifier(SimplyMore.identifier("drill_height"), 0.5f, EntityAttributeModifier.Operation.ADD_VALUE));
        map.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(SimplyMore.identifier("drill_attack_damage"), -1, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        if(entity instanceof PlayerEntity) {
            map.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(SimplyMore.identifier("drill_speed"), MoundshifterItem.SETTINGS.speedMultiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }

        return map;
    }
}
