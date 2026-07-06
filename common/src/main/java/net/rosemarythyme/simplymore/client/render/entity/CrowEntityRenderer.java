package net.rosemarythyme.simplymore.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.models.CrowEntityModel;
import net.rosemarythyme.simplymore.entity.CrowEntity;

public class CrowEntityRenderer extends MobEntityRenderer<CrowEntity, CrowEntityModel> {

    public CrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new CrowEntityModel(context.getPart(CrowEntityModel.CROW_LAYER)), 0.1f);
    }

    @Override
    public Identifier getTexture(CrowEntity entity) {
        return SimplyMore.identifier("textures/entity/crow.png");
    }
}