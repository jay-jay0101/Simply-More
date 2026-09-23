package net.rosemarythyme.simplymore.world.abilities;

import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Direction;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.item.uniques.TheVesselBreachItem;
import net.rosemarythyme.simplymore.registry.DamageTypeRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import net.sweenus.simplyswords.registry.ParticlesRegistry;
import net.sweenus.simplyswords.registry.SoundRegistry;

public class RageAbilityType extends ActiveAbilityType {
    public RageAbilityType() {
        super(0);
    }

    @Override
    public int tickOutro(ActiveAbilityManager.ActiveAbility ability) {
        int outro = (int)(ability.duration() / 10f);
        return Math.min(outro, ability.remainingDuration() - 1);
    }

    @Override
    public void onFinish(ActiveAbilityManager.ActiveAbility ability) {
        EntityUtils.cooldown(ability.owner(), ItemRegistry.THE_VESSEL_BREACH.get(), TheVesselBreachItem.SETTINGS.cooldown, true);
    }

    @Override
    public int tick(ActiveAbilityManager.ActiveAbility ability) {
        ServerWorld world = (ServerWorld) ability.owner().getWorld();

        LivingEntity owner = ability.owner();
        if(ability.remainingDuration() % 10 == 0) {
            owner.damage(DamageTypeRegistry.damageSourceOf(owner.getWorld(), DamageTypeRegistry.BLEED), 1f);
            AudioVisualUtils.particleAroundEntity(owner, ParticlesRegistry.BLOOD_SPRAY.get(), 100, 0f, 0.1f);
            AudioVisualUtils.playSound(world, owner.getPos(), new Sound(SoundRegistry.DARK_SWORD_ATTACK_WITH_BLOOD_03.get()));
        }

        AudioVisualUtils.applyScreenshake(owner, 1.5f, 10);
        AudioVisualUtils.particleCube(world, owner.getPos().offset(Direction.UP, owner.getHeight() + 0.1f), ParticleTypes.SMOKE, 3, 0.1f, 0);

        return super.tick(ability);
    }

    @Override
    public Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> getModifiers(Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> map, LivingEntity entity) {
        map.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(SimplyMore.identifier("rage_damage"), 2.5f, EntityAttributeModifier.Operation.ADD_VALUE));
        map.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(SimplyMore.identifier("rage_speed"), 0.4f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        return map;
    }
}
