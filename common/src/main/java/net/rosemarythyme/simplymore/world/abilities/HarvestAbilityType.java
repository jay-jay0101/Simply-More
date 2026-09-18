package net.rosemarythyme.simplymore.world.abilities;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.SoundEvents;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
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

        new TargetList(owner)
                .applyEffect(StatusEffects.HASTE, 10, 3)
                .applyEffect(StatusEffects.SPEED, 10, 1);

        if(ability.remainingDuration() % 10 == 0) {
            AudioVisualUtils.playSound(owner.getWorld(), owner.getPos(), new Sound(SoundEvents.ENTITY_WARDEN_HEARTBEAT).setPitch(1.2f));
        }

        return super.tick(ability);
    }
}
