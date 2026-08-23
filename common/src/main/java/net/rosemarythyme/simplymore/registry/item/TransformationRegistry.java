package net.rosemarythyme.simplymore.registry.item;

import net.minecraft.block.Blocks;
import net.sweenus.simplyswords.api.SimplySwordsAPI;

public class TransformationRegistry {

    public static void register() {
        SimplySwordsAPI.registerTransformation(Blocks.SLIME_BLOCK, ItemRegistry.GREAT_SLITHER.getId());
        SimplySwordsAPI.registerTransformation(Blocks.MAGMA_BLOCK, ItemRegistry.MAGMASEEP.getId());
        SimplySwordsAPI.registerTransformation(Blocks.BLUE_ICE, ItemRegistry.GRANDFROST.getId());
        SimplySwordsAPI.registerTransformation(Blocks.WEEPING_VINES, ItemRegistry.MIMICRY_LONGSWORD.getId());
        SimplySwordsAPI.registerTransformation(Blocks.WEEPING_VINES_PLANT, ItemRegistry.MIMICRY_LONGSWORD.getId());
        SimplySwordsAPI.registerTransformation(Blocks.GLOWSTONE, ItemRegistry.GLIMMERSTEP.getId());
        SimplySwordsAPI.registerTransformation(Blocks.NETHER_WART_BLOCK, ItemRegistry.THE_BLOOD_HARVESTER.getId());
        SimplySwordsAPI.registerTransformation(Blocks.SMOOTH_SANDSTONE, ItemRegistry.MYRMEDGE.getId());
        SimplySwordsAPI.registerTransformation(Blocks.GOLD_BLOCK, ItemRegistry.BLACK_PEARL.getId());
        SimplySwordsAPI.registerTransformation(Blocks.NETHER_WART_BLOCK, ItemRegistry.THE_VESSEL_BREACH.getId());
        SimplySwordsAPI.registerTransformation(Blocks.WITHER_ROSE, ItemRegistry.BLADE_OF_THE_GROTESQUE.getId());
        SimplySwordsAPI.registerTransformation(Blocks.POTTED_WITHER_ROSE, ItemRegistry.BLADE_OF_THE_GROTESQUE.getId());
        SimplySwordsAPI.registerTransformation(Blocks.SLIME_BLOCK, ItemRegistry.VIPERS_CALL.getId());
        SimplySwordsAPI.registerTransformation(Blocks.CHISELED_SANDSTONE, ItemRegistry.TIMEKEEPER.getId());
        SimplySwordsAPI.registerTransformation(Blocks.REDSTONE_BLOCK, ItemRegistry.MATTERBANE.getId());
        SimplySwordsAPI.registerTransformation(Blocks.ANCIENT_DEBRIS, ItemRegistry.SMOULDERING_RUIN.getId());
        SimplySwordsAPI.registerTransformation(Blocks.LIGHTNING_ROD, ItemRegistry.STASIS.getId());
        SimplySwordsAPI.registerTransformation(Blocks.DARK_PRISMARINE, ItemRegistry.TIDEBREAKER.getId());
        SimplySwordsAPI.registerTransformation(Blocks.QUARTZ_PILLAR, ItemRegistry.RUYI_JINGU_BANG.getId());
        SimplySwordsAPI.registerTransformation(Blocks.SUSPICIOUS_GRAVEL, ItemRegistry.RUPTURED_IDOL.getId());
        SimplySwordsAPI.registerTransformation(Blocks.SLIME_BLOCK, ItemRegistry.BOAS_FANG.getId());
        SimplySwordsAPI.registerTransformation(Blocks.ROOTED_DIRT, ItemRegistry.EARTHSHATTER.getId());
        SimplySwordsAPI.registerTransformation(Blocks.SOUL_LANTERN, ItemRegistry.SOULFRACTURE.getId());
        SimplySwordsAPI.registerTransformation(Blocks.EMERALD_BLOCK, ItemRegistry.SERPENTINE_VALOUR.getId());
        SimplySwordsAPI.registerTransformation(Blocks.OCHRE_FROGLIGHT, ItemRegistry.LUSTROUS_MOXIE.getId());
        SimplySwordsAPI.registerTransformation(Blocks.OXIDIZED_COPPER, ItemRegistry.BRASSTURN.getId());
        SimplySwordsAPI.registerTransformation(Blocks.WAXED_OXIDIZED_COPPER, ItemRegistry.BRASSTURN.getId());
        SimplySwordsAPI.registerTransformation(Blocks.FIRE, ItemRegistry.CINDERGORGE.getId());
        SimplySwordsAPI.registerTransformation(Blocks.BEDROCK, ItemRegistry.DEATHS_EYRIE.getId());
        SimplySwordsAPI.registerTransformation(Blocks.FLOWERING_AZALEA, ItemRegistry.PERFORISCUS.getId());
        SimplySwordsAPI.registerTransformation(Blocks.POTTED_FLOWERING_AZALEA_BUSH, ItemRegistry.PERFORISCUS.getId());
        SimplySwordsAPI.registerTransformation(Blocks.STONECUTTER, ItemRegistry.REVVENGINE.getId());
        SimplySwordsAPI.registerTransformation(Blocks.OBSIDIAN, ItemRegistry.MOUNDSHIFTER.getId());
        SimplySwordsAPI.registerTransformation(Blocks.BLACK_CANDLE, ItemRegistry.CULTEREX.getId());
    }
}
