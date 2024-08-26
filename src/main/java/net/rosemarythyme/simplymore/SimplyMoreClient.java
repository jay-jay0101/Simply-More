package net.rosemarythyme.simplymore;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.item.uniques.MatterbaneItem;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;

@Environment(EnvType.CLIENT)
public class SimplyMoreClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerModelPredicates();
    }

    private static void registerModelPredicates() {
        ModelPredicateProviderRegistry.register(ModItemsRegistry.MIMICRY, new Identifier(SimplyMore.ID, "mimicry_form"), (itemStack, clientWorld, livingEntity, a) -> {
            Object form = itemStack.getOrCreateNbt().get("simplymore:form");
            if (form == null) return 0f;
            return form.toString().equals("\"twisted\"") ? 1f : 0f;
        });


        final int[] randomSprite = {0};

        ModelPredicateProviderRegistry.register(ModItemsRegistry.TIMEKEEPER, new Identifier(SimplyMore.ID, "sun"), (itemStack, clientWorld, livingEntity, a) -> {

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

        ModelPredicateProviderRegistry.register(ModItemsRegistry.MATTERBANE, new Identifier(SimplyMore.ID, "color"), (itemStack, clientWorld, livingEntity, a) -> {

            Object color = itemStack.getOrCreateNbt().get("simplymore:color");
            color = MatterbaneItem.getMatterbaneColor(color);
            color = (float) ((int) color);
            return (float) color / 100f;
        });

        ModelPredicateProviderRegistry.register(ModItemsRegistry.RUYI_JINGU_BANG, new Identifier(SimplyMore.ID, "size"), (itemStack, clientWorld, livingEntity, a) -> {

            if(livingEntity==null) return 0f;
            if(livingEntity.getActiveItem()!=itemStack) return 0f;

            int itemUseTime = livingEntity.getItemUseTime();

            if(itemUseTime<20) return 0f;
            if(itemUseTime<40) return 0.1f;
            if(itemUseTime<60) return 0.2f;
            if(itemUseTime<80) return 0.3f;
            if(itemUseTime<100) return 0.4f;
            if(itemUseTime<120) return 0.5f;
            if(itemUseTime<140) return 0.6f;
            if(itemUseTime<160) return 0.7f;
            if(itemUseTime<180) return 0.8f;
            if(itemUseTime<200) return 0.9f;
            return 1f;

        });

    }
}