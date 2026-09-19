package net.rosemarythyme.simplymore.world.abilities;

import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;

public class HarvestAbilityType extends ActiveAbilityType {
    public HarvestAbilityType() {
        super(32);
    }

    @Override
    public boolean shouldContinue(ActiveAbilityManager.ActiveAbility ability) {
        return EntityUtils.isHolding(ability.owner(), ItemRegistry.THE_BLOOD_HARVESTER.get());
    }

    @Override
    public int tickOutro(ActiveAbilityManager.ActiveAbility ability) {
        int outro = (int)(ability.duration() / 10f);
        return Math.min(outro, ability.remainingDuration() - 1);
    }

    @Override
    public int tick(ActiveAbilityManager.ActiveAbility ability) {
        LivingEntity owner = ability.owner();
        if(ability.remainingDuration() % 10 == 0) {
            AudioVisualUtils.playSound(owner.getWorld(), owner.getPos(), new Sound(SoundEvents.ENTITY_WARDEN_HEARTBEAT).setPitch(1.2f));
        }

        return super.tick(ability);
    }

    @Override
    public Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> getModifiers(Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> map, LivingEntity entity) {
        map.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(SimplyMore.identifier("harvest_attack_speed"), 0.4f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        map.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(SimplyMore.identifier("harvest_speed"), 0.4f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        return map;
    }
}
