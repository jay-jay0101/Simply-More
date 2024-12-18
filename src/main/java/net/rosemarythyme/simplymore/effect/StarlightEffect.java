package net.rosemarythyme.simplymore.effect;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.config.WrapperConfig;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.sweenus.simplyswords.registry.SoundRegistry;

public class StarlightEffect extends StatusEffect {
    protected static WrapperConfig config = AutoConfig.getConfigHolder(WrapperConfig.class).getConfig();
    protected static UniqueEffectConfig effect = config.uniqueEffects;


    public StarlightEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }


    @Override
    public void applyUpdateEffect(LivingEntity affectedEntity, int amplifier) {
        int frequency = effect.getGlimmerstepBaseSpeedFrequency() - (effect.getGlimmerstepSpeedFrequencyPerStack() * (amplifier + 1));
        if(affectedEntity.getWorld().getTime() % frequency == 0) {
            affectedEntity.addStatusEffect(
                    new StatusEffectInstance(
                            StatusEffects.SPEED,
                            effect.getGlimmerstepSpeedTime(),
                            amplifier
                    )
            );
        }

        super.applyUpdateEffect(affectedEntity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
