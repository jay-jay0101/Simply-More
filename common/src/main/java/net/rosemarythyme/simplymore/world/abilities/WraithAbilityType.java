package net.rosemarythyme.simplymore.world.abilities;

import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.item.uniques.DesolateRuinItem;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import net.sweenus.simplyswords.registry.ParticlesRegistry;
import net.sweenus.simplyswords.registry.SoundRegistry;

public class WraithAbilityType extends ActiveAbilityType {
    public WraithAbilityType() {
        super(32);
    }

    @Override
    public boolean shouldContinue(ActiveAbilityManager.ActiveAbility ability) {
        return ability.owner().isHolding(ItemRegistry.DESOLATE_RUIN.get());
    }

    @Override
    public void onFinish(ActiveAbilityManager.ActiveAbility ability) {
        LivingEntity owner = ability.owner();

        owner.addVelocity(0, 0.5f, 0f);
        owner.velocityModified = true;

        AudioVisualUtils.particleAroundEntity(owner, ParticleTypes.CRIT, 100, 0, 1f);
        AudioVisualUtils.particleAroundEntity(owner, ParticlesRegistry.BLOOD_SPRAY.get(), 100, 0, 0.2f);
        AudioVisualUtils.playSound(owner.getWorld(), owner.getPos(), new Sound(SoundEvents.ENTITY_VEX_DEATH).setPitch(1.2f));
        AudioVisualUtils.playSound(owner.getWorld(), owner.getPos(), new Sound(SoundRegistry.DARK_SWORD_BREAKS.get()));
        AudioVisualUtils.applyScreenshake((ServerWorld) owner.getWorld(), owner.getPos(), owner, 6, 1, 10);

        owner.removeStatusEffect(StatusEffects.INVISIBILITY);

        if(ability.duration() <= 1) {
            EntityUtils.cooldown(owner, ItemRegistry.DESOLATE_RUIN.get(), DesolateRuinItem.SETTINGS.cooldownAfterBackstab, true);
        }
    }

    @Override
    public int tick(ActiveAbilityManager.ActiveAbility ability) {
        LivingEntity owner = ability.owner();

        if(ability.remainingDuration() % 5 == 0) {
            if(MathUtils.chance(owner, 0.4f)) {
                AudioVisualUtils.playSound(owner.getWorld(), owner.getPos(), new Sound(SoundEvents.ENTITY_VEX_AMBIENT).randomisePitch(0.4f, 1f, owner.getRandom()));
            }

            new TargetList(owner).applyEffect(StatusEffects.INVISIBILITY, ability.remainingDuration(), 0, true);
        }

        AudioVisualUtils.particleAroundEntity(owner, ParticleTypes.ASH, 1, 0.25f, 0.2f);

        return super.tick(ability);
    }

    @Override
    public Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> getModifiers(Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> map, LivingEntity entity) {
        map.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(SimplyMore.identifier("wraith_speed"), 0.4f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        return map;
    }
}
