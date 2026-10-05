package net.rosemarythyme.simplymore.entity.ai;

import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.rosemarythyme.simplymore.entity.CrowEntity;

public class CrowFollowOwnerGoal extends FollowOwnerGoal {
    private final CrowEntity entity;
    public CrowFollowOwnerGoal(CrowEntity entity, double speed, float minDistance, float maxDistance) {
        super(entity, speed, minDistance, maxDistance);
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
