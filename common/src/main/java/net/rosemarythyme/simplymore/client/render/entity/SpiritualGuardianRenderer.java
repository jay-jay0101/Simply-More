package net.rosemarythyme.simplymore.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.models.SpiritualGuardianModel;
import net.rosemarythyme.simplymore.entity.SpiritualGuardianEntity;

public class SpiritualGuardianRenderer extends AbstractSpiritualRenderer<SpiritualGuardianEntity, SpiritualGuardianModel> {
    public SpiritualGuardianRenderer(EntityRendererFactory.Context context) {
        super(context, new SpiritualGuardianModel(context.getPart(SpiritualGuardianModel.LAYER)));
    }

    @Override
    public Identifier getAuraTexture() {
        return SimplyMore.identifier("textures/entity/planes/holylight.png");
    }

    @Override
    public Identifier getTexture(SpiritualGuardianEntity entity) {
        return SimplyMore.identifier("textures/entity/spiritual_guardian.png");
    }
}