package net.rosemarythyme.simplymore.client.registry;

import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.item.ItemPropertiesRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.util.RenderUtils;
import net.rosemarythyme.simplymore.item.components.CounterComponent;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.ItemStackUtils;
import net.sweenus.simplyswords.client.api.SimplySwordsClientAPI;

import java.util.Set;

public class ClientItemPropertyRegistry {
    public static void registerCounterItemProperty(Item item, Identifier id) {
        ItemPropertiesRegistry.register(item, id, (stack, client, entity, a) -> {
            CounterComponent counter = ItemStackUtils.getCounterComponent(stack);

            if (counter == null) return 0f;

            int range = counter.max() - counter.min();
            int value = counter.value() - counter.min();

            return range == 0 ? 0f : (float) value / range;
        });
    }

    public static void registerDyeableItemProperty(Item item, float saturationBoost, Integer... exemptIndexes) {
        Set<Integer> exemptLayers = Set.of(exemptIndexes);
        ColorHandlerRegistry.registerItemColors(((stack, tintIndex) -> {
            if(exemptLayers.contains(tintIndex)) return -1;

            int color = ItemStackUtils.getColorDec(stack, 0xFF0000);
            return 0xFF000000 | RenderUtils.changeSaturation(color, saturationBoost);
        }), item);
    }

    public static void registerTimekeeperItemProperty(Item item, Identifier id) {
        ItemPropertiesRegistry.register(item, id, (stack, client, entity, a) -> {
            ClientWorld world = MinecraftClient.getInstance().world;
            if(world == null) return 0f;

            int worldTime = (int) Math.abs(world.getTimeOfDay() % 24000);
            if(world.getDimension().hasFixedTime()) return 1f;

            if(worldTime <= 1000) return 0.7f;
            if(worldTime <= 8000) return 0f;
            if(worldTime <= 10000) return 0.1f;
            if(worldTime <= 12000) return 0.2f;
            if(worldTime <= 13000) return 0.3f;
            if(worldTime <= 20000) return 0.4f;
            if(worldTime <= 22000) return 0.5f;

            return 0.6f;
        });
    }

    @Environment(EnvType.CLIENT)
    public static void register() {
        registerDyeableItemProperty(ItemRegistry.MATTERBANE.get(), 1.75f, 0);

        registerCounterItemProperty(ItemRegistry.BRASSTURN.get(), SimplyMore.identifier("oxidisation"));
        registerCounterItemProperty(ItemRegistry.DEATHS_EYRIE.get(), SimplyMore.identifier("crows"));
        registerCounterItemProperty(ItemRegistry.MOUNDSHIFTER.get(), SimplyMore.identifier("pressure"));
        registerCounterItemProperty(ItemRegistry.RUYI_JINGU_BANG.get(), SimplyMore.identifier("size"));
        registerCounterItemProperty(ItemRegistry.REVVENGINE.get(), SimplyMore.identifier("revs"));

        registerTimekeeperItemProperty(ItemRegistry.TIMEKEEPER.get(), SimplyMore.identifier("time"));

        SimplySwordsClientAPI.registerAwakeningFormModelProperty(ItemRegistry.RUPTURED_IDOL.get(), SimplyMore.identifier("idol_path"));
    }
}
