package net.rosemarythyme.simplymore.entity.ai;

import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

public class SpiritNavigation extends MobNavigation {
    public SpiritNavigation(MobEntity mobEntity, World world) {
        super(mobEntity, world);
    }

    @Override
    protected boolean isAtValidPosition() {
        return true;
    }

    @Override
    protected void continueFollowingPath() {
        super.continueFollowingPath();
    }
}
