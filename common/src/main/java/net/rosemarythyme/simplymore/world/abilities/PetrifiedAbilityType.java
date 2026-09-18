package net.rosemarythyme.simplymore.world.abilities;

import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.item.uniques.BladeOfTheGrotesqueItem;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;

public class PetrifiedAbilityType extends ActiveAbilityType {
    public PetrifiedAbilityType() {
        super(100);
    }

    @Override
    public void onFinish(ActiveAbilityManager.ActiveAbility ability) {
        BladeOfTheGrotesqueItem.breakOutVisuals(ability.owner());
    }

    @Override
    public int tick(ActiveAbilityManager.ActiveAbility ability) {
        AudioVisualUtils.particleAroundEntity(ability.owner(), ParticleTypes.ASH, 5, 0.2, 1);
        EntityUtils.putAllItemsOnCooldown(ability.owner(), ability.remainingDuration() - 1);

        return super.tick(ability);
    }

    @Override
    public Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> getModifiers(Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> map, LivingEntity entity) {
        map.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(SimplyMore.identifier("statue_attack_speed"), -1, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        map.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(SimplyMore.identifier("statue_attack_damage"), -1, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        return map;
    }
}
