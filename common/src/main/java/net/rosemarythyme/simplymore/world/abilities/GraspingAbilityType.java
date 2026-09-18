package net.rosemarythyme.simplymore.world.abilities;

import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.item.uniques.MyrmedgeItem;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;

public class GraspingAbilityType extends ActiveAbilityType {
    public GraspingAbilityType() {
        super(0);
    }

    @Override
    public boolean shouldContinue(ActiveAbilityManager.ActiveAbility ability) {
        LivingEntity target = MyrmedgeItem.getActiveMyrmedgeTarget(ability.owner());
        return target != null;
    }

    @Override
    public void onFinish(ActiveAbilityManager.ActiveAbility ability) {
        MyrmedgeItem.stopAbility(ability.owner());
    }

    @Override
    public int tick(ActiveAbilityManager.ActiveAbility ability) {
        LivingEntity owner = ability.owner();
        LivingEntity target = MyrmedgeItem.getActiveMyrmedgeTarget(ability.owner());
        if(target == null) return super.tick(ability);

        target.dismountVehicle();
        target.addStatusEffect(new StatusEffectInstance(StatusEffectRegistry.getReference(StatusEffectRegistry.STUN), 5, 0));
        target.refreshPositionAfterTeleport(EntityUtils.rangeAroundPoint(owner.getEyePos(), target, owner.getYaw(), 1.5f));
        target.fallDistance = 0;

        if(ability.remainingDuration() % 20 == 0) {
            if(target instanceof PlayerEntity playerTarget) {
                HungerManager manager = playerTarget.getHungerManager();
                manager.setSaturationLevel(0);
                manager.setFoodLevel(manager.getFoodLevel() - 1);
            }

            target.damage(AttackUtils.getHitSource(owner), MyrmedgeItem.SETTINGS.grabDamage);
            owner.heal(1);

            if(owner instanceof PlayerEntity player) {
                player.getHungerManager().add(2, 0.1f);
            }
        }

        return super.tick(ability);
    }

    @Override
    public Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> getModifiers(Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> map, LivingEntity entity) {
        map.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(SimplyMore.identifier("grasping_attack_speed"), -1, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        map.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(SimplyMore.identifier("grasping_attack_damage"), -1, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        map.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(SimplyMore.identifier("grasping_move_speed"), -MyrmedgeItem.SETTINGS.grabSelfSlow, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        return map;
    }
}
