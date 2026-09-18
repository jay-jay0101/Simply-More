package net.rosemarythyme.simplymore.world.abilities;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.particle.ParticleTypes;
import net.rosemarythyme.simplymore.item.uniques.VipersCallItem;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.PredicateUtils;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class VipersCallAbilityType extends ActiveAbilityType {
    public VipersCallAbilityType() {
        super(32);
    }

    @Override
    public int tick(ActiveAbilityManager.ActiveAbility ability) {
        LivingEntity owner = ability.owner();

        AudioVisualUtils.particleAroundEntity(owner, ParticleTypes.SPORE_BLOSSOM_AIR, 3, 2f, 1f);
        TargetList nearby = AttackUtils.cylinderAttack(owner, owner.getPos(), VipersCallItem.SETTINGS.auraRange, 4, AttackUtils.AttackTarget.OTHERS_AND_USER_POSITIVELY);

        List<StatusEffectInstance> positive = new ArrayList<>();
        List<StatusEffectInstance> negative = new ArrayList<>();

        Predicate<StatusEffect> predicate = PredicateUtils.createForEffectBlacklist(VipersCallItem.SETTINGS.blacklist, VipersCallItem.SETTINGS.includeGlobalBlacklist);
        for (LivingEntity entity : nearby.targets()) {
            if(AttackUtils.canTarget(owner, entity, AttackUtils.AttackTarget.ENEMIES)) {
                for(StatusEffectInstance instance : List.copyOf(entity.getStatusEffects())) {
                    if(predicate.and(PredicateUtils.HARMFUL_EFFECT).test(instance.getEffectType().value())) {
                        negative.add(instance);
                    }
                }
            } else {
                for(StatusEffectInstance instance : List.copyOf(entity.getStatusEffects())) {
                    if(predicate.and(PredicateUtils.BENEFICIAL_EFFECT).test(instance.getEffectType().value())) {
                        positive.add(instance);
                    }
                }
            }
        }

        nearby.filterByTargetType(owner, AttackUtils.AttackTarget.ALLIES_AND_USER)
                .onEach((entity) -> positive.forEach(instance -> entity.addStatusEffect(new StatusEffectInstance(instance))));

        nearby.filterByTargetType(owner, AttackUtils.AttackTarget.ENEMIES)
                .onEach((entity) -> negative.forEach(instance -> entity.addStatusEffect(new StatusEffectInstance(instance))));

        return super.tick(ability);
    }
}
