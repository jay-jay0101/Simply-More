package net.rosemarythyme.simplymore.client;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.item.ItemPropertiesRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.FallingBlockEntityRenderer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.models.CrowEntityModel;
import net.rosemarythyme.simplymore.client.render.entity.CrowEntityRenderer;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.rosemarythyme.simplymore.util.MathUtils;

@Environment(EnvType.CLIENT)
public class SimplyMoreClientInit {

    @Environment(EnvType.CLIENT)
    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(EntityRegistry.CROW, CrowEntityRenderer::new);
        EntityModelLayerRegistry.register(CrowEntityModel.CROW_LAYER, CrowEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(EntityRegistry.GHOST_FALLING_BLOCK, FallingBlockEntityRenderer::new);
    }

    @Environment(EnvType.CLIENT)
    public static void registerModelPredicates() {
        final int[] randomSprite = {0};

        ItemPropertiesRegistry.register(ItemRegistry.TIMEKEEPER.get(), SimplyMore.identifier("sun"), (itemStack, clientWorld, livingEntity, a) -> {

            if (clientWorld == null) return 0f;

            long dayTime = Math.abs(clientWorld.getTimeOfDay() % 24000);
            boolean fixedTime = clientWorld.getDimension().hasFixedTime();

            if (fixedTime) {
                if (clientWorld.getRandom().nextBoolean()) {
                    randomSprite[0] = randomSprite[0] + 1;
                } else {
                    randomSprite[0] = randomSprite[0] - 1;
                }
                randomSprite[0] += 14;
                randomSprite[0] %= 14;

                return (float) randomSprite[0] / 100;
            }

            if (dayTime < 250) return 0.11f;
            if (dayTime < 750) return 0.12f;
            if (dayTime < 1250) return 0.13f;
            if (dayTime < 11250) return 0f;
            if (dayTime < 11750) return 0.01f;
            if (dayTime < 12250) return 0.02f;
            if (dayTime < 12750) return 0.03f;
            if (dayTime < 13250) return 0.04f;
            if (dayTime < 13750) return 0.05f;
            if (dayTime < 14250) return 0.06f;
            if (dayTime < 22250) return 0.07f;
            if (dayTime < 22750) return 0.08f;
            if (dayTime < 23250) return 0.09f;
            if (dayTime < 23750) return 0.10f;
            return 0.11f;
        });

        ColorHandlerRegistry.registerItemColors(((stack, tintIndex) -> {
            if(tintIndex == 0) return -1; // Do not dye "layer0"

            DyedColorComponent color = stack.get(DataComponentTypes.DYED_COLOR);
            if(color == null) {
                return 0xFFFF0000;
            }

            int rgb = color.rgb();
            int r = (rgb >> 16) & 0xFF;
            int g = (rgb >> 8) & 0xFF;
            int b = rgb & 0xFF;

            int lum = (r + b + g) / 3;
            int sat = Math.round(256 * 1.75f);

            r = Math.max(0, lum + (((r - lum) * sat) >> 8));
            g = Math.max(0, lum + (((g - lum) * sat) >> 8));
            b = Math.max(0, lum + (((b - lum) * sat) >> 8));

            int hueLum = Math.max(r, Math.max(b, g));
            if(hueLum > 255) {
                float lumMultiplier = 255f / hueLum;
                r = Math.round(r * lumMultiplier);
                g = Math.round(g * lumMultiplier);
                b = Math.round(b * lumMultiplier);
            }

            return 0xFF000000 | (r << 16) | (g << 8) | b;
        }), ItemRegistry.MATTERBANE);

        ItemPropertiesRegistry.register(ItemRegistry.BRASSTURN.get(), SimplyMore.identifier("oxidisation"), (itemStack, clientWorld, livingEntity, a) -> {

            int oxidisation = MathUtils.getCounterComponent(itemStack).value();
            if(oxidisation >= 16) {
                return 0.3f;
            } else if (oxidisation >= 11) {
                return 0.2f;
            } else if (oxidisation >= 6) {
                return 0.1f;
            }

            return 0f;
        });

        ItemPropertiesRegistry.register(ItemRegistry.DEATHS_EYRIE.get(), SimplyMore.identifier("crows"),
                (itemStack, clientWorld, livingEntity, a) -> MathUtils.getCounterComponent(itemStack).value() * 0.1f);

        ItemPropertiesRegistry.register(ItemRegistry.RUYI_JINGU_BANG.get(), SimplyMore.identifier("size"), (itemStack, clientWorld, livingEntity, a) -> {

            if (livingEntity == null)
                return 0f;
            if (livingEntity.getActiveItem() != itemStack)
                return 0f;

            int itemUseTime = livingEntity.getItemUseTime();

            if (itemUseTime < 20) return 0f;
            if (itemUseTime < 40) return 0.1f;
            if (itemUseTime < 60) return 0.2f;
            if (itemUseTime < 80) return 0.3f;
            if (itemUseTime < 100) return 0.4f;
            if (itemUseTime < 120) return 0.5f;
            if (itemUseTime < 140) return 0.6f;
            if (itemUseTime < 160) return 0.7f;
            if (itemUseTime < 180) return 0.8f;
            if (itemUseTime < 200) return 0.9f;
            return 1f;

        });

    }
}
