package net.rosemarythyme.simplymore.world.abilities.fire;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import net.rosemarythyme.simplymore.world.abilities.ActiveAbilityType;

public class FireTypeAbility extends ActiveAbilityType {
    public FireTypeAbility() {
        super(100);
    }

    @Override
    public boolean shouldContinue(ActiveAbilityManager.ActiveAbility ability) {
        return ability.owner().isOnFire() && !ability.owner().isDead();
    }

    @Override
    public void onFinish(ActiveAbilityManager.ActiveAbility ability) {
        ability.owner().extinguish();
        ability.owner().setOnFire(false);
    }

    @Override
    public int tick(ActiveAbilityManager.ActiveAbility ability) {
        LivingEntity owner = ability.owner();

        if (owner.getFireTicks() % 20 == 0 && !owner.isInLava()) {
            owner.damage(getSource(owner.getDamageSources()), getDamage());
        }

        return super.tick(ability);
    }

    protected float getDamage() {
        return 1f;
    }

    protected DamageSource getSource(DamageSources sources) {
        return sources.onFire();
    }
}
