package net.rosemarythyme.simplymore.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.entity.AbstractLiquidEntity;

public class WaterEntityRenderer extends AbstractLiquidEntityRenderer {

    public WaterEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    int getLight(int light) {
        return light;
    }

    @Override
    int getColor() {
        return 0xAA337096;
    }

    @Override
    public Identifier getTexture(AbstractLiquidEntity entity) {
        return Identifier.of("block/water_still");
    }
}