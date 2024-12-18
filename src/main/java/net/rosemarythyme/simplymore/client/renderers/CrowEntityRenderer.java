package net.rosemarythyme.simplymore.client.renderers;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.SimplyMoreClient;
import net.rosemarythyme.simplymore.client.models.CrowEntityModel;
import net.rosemarythyme.simplymore.entity.CrowEntity;

public class CrowEntityRenderer extends MobEntityRenderer<CrowEntity, CrowEntityModel> {

    public CrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new CrowEntityModel(context.getPart(SimplyMoreClient.MODEL_CROW_LAYER)), 0.1f);
    }

    @Override
    public boolean shouldRender(CrowEntity entity, Frustum frustum, double x, double y, double z) {
        return entity.squaredDistanceTo(x, y, z) < 1024 * 1024; // Adjust distance as needed
    }

    @Override
    public Identifier getTexture(CrowEntity entity) {
        return new Identifier(SimplyMore.ID, "textures/entity/crow.png");
    }
}