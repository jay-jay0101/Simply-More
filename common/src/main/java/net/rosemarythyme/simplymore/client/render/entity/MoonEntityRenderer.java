package net.rosemarythyme.simplymore.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.entity.AbstractPlanetaryEntity;

public class MoonEntityRenderer extends SunEntityRenderer {

    public MoonEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(AbstractPlanetaryEntity entity) {
        return SimplyMore.identifier("textures/entity/objects/moon.png");
    }
}