package net.rosemarythyme.simplymore.client.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.rosemarythyme.simplymore.entity.SoulFragmentEntity;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.HudUtils;
import net.rosemarythyme.simplymore.util.MathUtils;

import java.util.*;
import java.util.stream.Collectors;

public class SoulfractureHud implements HudOverlay<Map<LivingEntity, Integer>> {
    @Override
    public void renderHudOverlay(DrawContext context, ItemStack stack, ClientPlayerEntity player, RenderTickCounter tickCounter) {
        final int DELTA_Y = 10;

        Map<LivingEntity, Integer> data = HudUtils.getCache(this, stack, player);
        MatrixStack matrices = context.getMatrices();
        TextRenderer renderer = MinecraftClient.getInstance().textRenderer;

        for(Map.Entry<LivingEntity, Integer> target : data.entrySet()) {

            matrices.push();
            matrices.translate(HudUtils.SQUARE_BORDER_SIZE * -4, 0, 0);
            HudUtils.renderSquareProgress(context, 4, target.getValue(), 0xFF3E5A64, 0xFF60FFED);
            matrices.pop();

            context.drawText(renderer, target.getKey().getName(), 2, (-DELTA_Y / 2) + 1, 0xFF60FFED, true);
            matrices.translate(0, DELTA_Y, 0);
        }
    }

    @Override
    public Map<LivingEntity, Integer> getHudData(ItemStack stack, ClientPlayerEntity player) {
        List<LivingEntity> entities = player.getWorld().getEntitiesByClass(LivingEntity.class, MathUtils.createCubeBox(player.getPos(), 40), ignored -> true);
        Map<UUID, LivingEntity> uuids = entities.stream().collect(Collectors.toMap(
                Entity::getUuid,
                entity -> entity
        ));

        List<SoulFragmentEntity> fragments = AttackUtils.getOwnedAbilities(player, SoulFragmentEntity.class);

        return fragments.stream()
                .collect(Collectors.groupingBy(SoulFragmentEntity::getPerson))
                .entrySet().stream()
                .filter(entry -> entry.getKey().isPresent())
                .sorted(Map.Entry.<Optional<UUID>, List<SoulFragmentEntity>>comparingByValue(Comparator.comparingInt(List::size)).reversed())
                .limit(3)
                .filter(entry -> uuids.containsKey(entry.getKey().get()))
                .collect(Collectors.toMap(
                        entry -> uuids.get(entry.getKey().get()),
                        entry -> entry.getValue().size()
                ));
    }
}
