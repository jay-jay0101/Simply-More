package net.rosemarythyme.simplymore.client.render.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.entity.AbstractCollectableEntity;
import net.rosemarythyme.simplymore.entity.SoulFragmentEntity;
import net.rosemarythyme.simplymore.util.MathUtils;

import java.util.Optional;
import java.util.UUID;

public class SoulFragmentEntityRenderer extends AbstractCollectableEntityRenderer {

    public SoulFragmentEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(AbstractCollectableEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.translate(0, Math.sin(entity.getAge() * 0.1f) * 0.2f, 0);

        Optional<UUID> fragmentPersonUUID = ((SoulFragmentEntity) entity).getPerson();
        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        if(player != null && fragmentPersonUUID.isPresent()) {
            int collectionTime = entity.getCollectionTime();
            float scale = MathUtils.clampedLerp(collectionTime, 0, 10, 1f, 4f);
            matrices.scale(scale, scale, scale);
        }

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        matrices.pop();
    }

    @Override
    public Identifier getTexture(AbstractCollectableEntity entity) {
        int texture = ((SoulFragmentEntity) entity).texture;
        return SimplyMore.identifier("textures/entity/objects/soul_fragment/soul_fragment" + texture + ".png");
    }

    @Override
    int getOpacity(AbstractCollectableEntity entity) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        Optional<UUID> ownerUUID = entity.getOwnerUUID();
        Optional<UUID> fragmentPersonUUID = ((SoulFragmentEntity) entity).getPerson();


        if(player == null || ownerUUID.isEmpty() || fragmentPersonUUID.isEmpty()) return 0xFF;
        if(ownerUUID.get().equals(player.getUuid())) return 0xFF;

        if(fragmentPersonUUID.get().equals(player.getUuid())) return 0xFF;

        return 0x40;
    }
}
