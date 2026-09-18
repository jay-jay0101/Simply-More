package net.rosemarythyme.simplymore.world.abilities;

import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.registry.entry.RegistryEntry;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;

public abstract class ActiveAbilityType {
    public final float syncRange;

    public ActiveAbilityType(float syncRange) {
        this.syncRange = syncRange;
    }

    public int tick(ActiveAbilityManager.ActiveAbility ability) {
        return ability.remainingDuration() - 1;
    }

    public boolean shouldContinue(ActiveAbilityManager.ActiveAbility ability) {
        return true;
    }

    public int tickOutro(ActiveAbilityManager.ActiveAbility ability) {
        return 0;
    }

    public void onFinish(ActiveAbilityManager.ActiveAbility ability) {}

    public Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> getModifiers(Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> map, LivingEntity entity) {
        return map;
    }
}
