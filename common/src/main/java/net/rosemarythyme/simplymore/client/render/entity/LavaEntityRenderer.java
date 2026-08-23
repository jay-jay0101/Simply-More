package net.rosemarythyme.simplymore.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.entity.AbstractLiquidEntity;

public class LavaEntityRenderer extends AbstractLiquidEntityRenderer {

    public LavaEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    int getLight(int light) {
        return 255;
    }

    @Override
    int getColor() {
        return 0xFFFFFFFF;
    }

    @Override
    public Identifier getTexture(AbstractLiquidEntity entity) {
        return Identifier.of("block/lava_still");
    }
}