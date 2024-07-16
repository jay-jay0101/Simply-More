package net.rosemarythmye.simplymore;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.util.Identifier;
import net.rosemarythmye.simplymore.effect.ModEffects;
import net.rosemarythmye.simplymore.item.ModItems;

@Environment(EnvType.CLIENT)
public class SimplymoreClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerModelPredicates();
    }

    private static void registerModelPredicates() {
        ModelPredicateProviderRegistry.register(ModItems.MIMICRY, new Identifier(Simplymore.ID, "mimicry_form"), (itemStack, clientWorld, livingEntity, a) -> {
            Object form = itemStack.getOrCreateNbt().get("simplymore:form");
            if (form==null) return 0f;
            return form.toString().equals("\"twisted\"")? 1f:0f;
        });

        ModelPredicateProviderRegistry.register(ModItems.THEBLOODHARVESTER, new Identifier(Simplymore.ID, "harvest"), (itemStack, clientWorld, livingEntity, a) -> {
            if (livingEntity == null) return 0f;
            return livingEntity.hasStatusEffect(ModEffects.HARVEST)? 1f:0f;
        });
    }
}
