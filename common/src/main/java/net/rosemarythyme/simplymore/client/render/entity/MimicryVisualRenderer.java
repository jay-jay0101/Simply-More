package net.rosemarythyme.simplymore.client.render.entity;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.rosemarythyme.simplymore.entity.MimicryVisualEntity;
import net.rosemarythyme.simplymore.item.uniques.mimicry.MimicryItem;
import net.rosemarythyme.simplymore.util.MathUtils;

import java.util.Optional;
import java.util.UUID;

public class MimicryVisualRenderer extends EntityRenderer<MimicryVisualEntity> {
    private final ItemRenderer itemRenderer;

    public MimicryVisualRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public Identifier getTexture(MimicryVisualEntity entity) {
        return Identifier.ofVanilla("air");
    }

    @Override
    public void render(MimicryVisualEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        Optional<UUID> uuid = entity.getOwnerUUID();
        if(uuid.isEmpty()) return;

        Optional<LivingEntity> ownerEntity = entity.getWorld().getNonSpectatingEntities(LivingEntity.class, MathUtils.createCubeBox(entity.getPos(), 25))
                        .stream().filter(e -> uuid.get().equals(e.getUuid())).findAny();

        if(ownerEntity.isEmpty()) return;
        LivingEntity owner = ownerEntity.get();

        MimicryVisualEntity.AnimationData animationData = getAnimation(entity, owner, tickDelta);
        if(animationData == null) return;

        matrices.push();
        Vec3d offset = owner.getLerpedPos(tickDelta).subtract(entity.getLerpedPos(tickDelta)).add(animationData.offset());
        matrices.translate(offset.getX(), offset.getY(), offset.getZ());

        MimicryItem.VisualData data = getVisualData(entity);

        matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(animationData.yaw() + data.yawOffset()));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(animationData.pitch()));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(animationData.roll()));

        float scale = 2 * data.scale();
        matrices.scale(scale, scale, scale);
        matrices.translate(data.holdPos().x, data.holdPos().y, 0);

        this.itemRenderer.renderItem(entity.getStack(), ModelTransformationMode.NONE, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 0);
        matrices.pop();
    }

    public MimicryItem.VisualData getVisualData(MimicryVisualEntity entity) {
        Optional<MimicryItem.MimicryForm> form = MimicryItem.MimicryForm.getByItem(entity.getStack().getItem());
        if(form.isEmpty()) return MimicryItem.VisualData.DEFAULT;
        return form.get().data;
    }

    public static MimicryVisualEntity.AnimationData getAnimation(MimicryVisualEntity entity, LivingEntity owner, float tickDelta) {
        if(entity.getDuration() == 0) return null;

        float sinceStart = entity.getAge() - entity.getStart() + tickDelta;
        if(sinceStart > entity.getDuration() || sinceStart < 0) return null;

        return entity.getAnimation().getData.apply(entity, owner, sinceStart / entity.getDuration());
    }

    @Override
    public boolean shouldRender(MimicryVisualEntity entity, Frustum frustum, double x, double y, double z) {
        return entity.shouldRender(x, y, z);
    }
}