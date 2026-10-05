package net.rosemarythyme.simplymore.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.rosemarythyme.simplymore.entity.CrowEntity;

public class CrowLookAtEntityGoal extends LookAtEntityGoal {
    private final CrowEntity entity;

    public CrowLookAtEntityGoal(CrowEntity entity, Class<? extends LivingEntity> targetType, float range) {
        super(entity, targetType, range);
        this.entity = entity;
    }

    @Override
    public boolean shouldContinue() {
        return !entity.isAttacking() && super.shouldContinue();
    }

    @Override
    public boolean canStart() {
        return !entity.isAttacking() && super.canStart();
    }

    @Override
    public boolean canStop() {
        return entity.isAttacking() || super.canStop();
    }
}
