package net.rosemarythyme.simplymore.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.models.SpiritualTormentorModel;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.entity.SpiritualTormentorEntity;

public class SpiritualTormentorRenderer extends AbstractSpiritualRenderer<SpiritualTormentorEntity, SpiritualTormentorModel> {
    public SpiritualTormentorRenderer(EntityRendererFactory.Context context) {
        super(context, new SpiritualTormentorModel(context.getPart(SpiritualTormentorModel.LAYER)));
    }

    @Override
    public Identifier getAuraTexture() {
        return SimplyMore.identifier("textures/entity/planes/darksent.png");
    }

    @Override
    public Identifier getTexture(SpiritualTormentorEntity entity) {
        return SimplyMore.identifier("textures/entity/spiritual_tormentor.png");
    }
}