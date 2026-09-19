package net.rosemarythyme.simplymore.world.abilities;

import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.entity.MimicryVisualEntity;
import net.rosemarythyme.simplymore.item.uniques.mimicry.MimicryItem;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;

import java.util.List;
import java.util.Optional;

public class MimicryAbilityType extends ActiveAbilityType {

    public MimicryAbilityType() {
        super(0);
    }

    @Override
    public boolean shouldContinue(ActiveAbilityManager.ActiveAbility ability) {
        Optional<MimicryItem.MimicryForm> form = getForm(ability);
        return form.isPresent();
    }

    @Override
    public int tick(ActiveAbilityManager.ActiveAbility ability) {
        LivingEntity owner = ability.owner();
        Optional<MimicryItem.MimicryForm> form = getForm(ability);

        if(form.isPresent()) {
            MimicryItem item = (MimicryItem) form.get().item.get();

            int ticksUsed = AttackUtils.getUseTicksFromInfiniteDuration(ability.remainingDuration() + form.get().ordinal());
            if(item.usageTimeline(owner, ticksUsed)) return 0;

            List<MimicryVisualEntity> entities = AttackUtils.getOwnedEntities(owner, MimicryVisualEntity.class);
            if(entities.isEmpty()) {
                AttackUtils.spawnAbility(new MimicryVisualEntity(owner, new ItemStack(item)), owner);
            }
        }

        return super.tick(ability);
    }

    @Override
    public Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> getModifiers(Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> map, LivingEntity entity) {
        map.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(SimplyMore.identifier("harvest_attack_speed"), -1, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        map.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(SimplyMore.identifier("harvest_speed"), -0.4f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        return map;
    }

    public Optional<MimicryItem.MimicryForm> getForm(ActiveAbilityManager.ActiveAbility ability) {
        int ordinal = AttackUtils.getUseTicksFromInfiniteDuration(ability.duration());
        return MimicryItem.MimicryForm.getByOrdinal(ordinal);
    }
}
