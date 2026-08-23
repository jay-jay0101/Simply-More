package net.rosemarythyme.simplymore.registry.item;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.sweenus.simplyswords.api.*;

public class AwakeningProfileRegistry {
    public static final Identifier HOLYLIGHT = SimplyMore.identifier("holylight");
    public static final Identifier DARKSENT = SimplyMore.identifier("darksent");

    public static final AwakeningFormFamily RUPTURED_IDOL = AwakeningFormFamily.builder(ItemRegistry.RUPTURED_IDOL.get(), AwakeningProfile.DEFAULT, SimplyMore.identifier("ruptured_idol"))
            .basePresentation(
                    "item.simplymore.ruptured_idol",
                    AwakeningFormRarity.UNIQUE,
                    0f)
            .selectionLevel(4)
            .persistentProgression(true)
            .route(HOLYLIGHT,
                    new AwakeningFormStage(
                            SimplyMore.identifier("ascended_idol"),
                            4,
                            ItemRegistry.RUPTURED_IDOL.get(),
                            "item.simplymore.ascended_idol",
                            AwakeningFormRarity.UNIQUE,
                            1/4f
                    ),
                    new AwakeningFormStage(
                            SimplyMore.identifier("holylight"),
                            8,
                            ItemRegistry.RUPTURED_IDOL.get(),
                            "item.simplymore.holylight",
                            AwakeningFormRarity.UNIQUE,
                            3/4f
                    )
            )
            .route(DARKSENT,
                    new AwakeningFormStage(
                            SimplyMore.identifier("tarnished_idol"),
                            4,
                            ItemRegistry.RUPTURED_IDOL.get(),
                            "item.simplymore.tarnished_idol",
                            AwakeningFormRarity.UNIQUE,
                            2/4f
                    ),
                    new AwakeningFormStage(
                            SimplyMore.identifier("darksent"),
                            8,
                            ItemRegistry.RUPTURED_IDOL.get(),
                            "item.simplymore.darksent",
                            AwakeningFormRarity.UNIQUE,
                            4/4f
                    )
            )
            .routeHandler(context ->
                    ServerWorld.NETHER.equals(context.world().getRegistryKey()) ? DARKSENT : HOLYLIGHT
            )
            .build();

    public static void register() {
        SimplySwordsAPI.registerAwakeningFormFamily(RUPTURED_IDOL);
    }
}
