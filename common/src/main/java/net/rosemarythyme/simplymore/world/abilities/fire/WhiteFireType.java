package net.rosemarythyme.simplymore.world.abilities.fire;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;

public class WhiteFireType extends FireTypeAbility {
    public WhiteFireType() {}

    protected DamageSource getSource(DamageSources sources) {
        return sources.wither();
    }
}
