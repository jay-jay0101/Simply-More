package net.rosemarythyme.simplymore.registry;

import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.sweenus.simplyswords.api.SimplySwordsAPI;

public class TransformationRegistry {

    public static void registerTransformations() {
        SimplySwordsAPI.registerTransformation(Blocks.SLIME_BLOCK, Identifier.of("simplymore", "great_slither"));
        SimplySwordsAPI.registerTransformation(Blocks.BASALT, Identifier.of("simplymore", "molten_flare"));
        SimplySwordsAPI.registerTransformation(Blocks.BLUE_ICE, Identifier.of("simplymore", "grandfrost"));
        SimplySwordsAPI.registerTransformation(Blocks.GLOW_LICHEN, Identifier.of("simplymore", "mimicry_longsword"));
        SimplySwordsAPI.registerTransformation(Blocks.GLOWSTONE, Identifier.of("simplymore", "glimmerstep"));
        SimplySwordsAPI.registerTransformation(Blocks.NETHER_WART_BLOCK, Identifier.of("simplymore", "the_blood_harvester"));
        SimplySwordsAPI.registerTransformation(Blocks.SMOOTH_SANDSTONE, Identifier.of("simplymore", "myrmedge"));
        SimplySwordsAPI.registerTransformation(Blocks.GOLD_BLOCK, Identifier.of("simplymore", "black_pearl"));
        SimplySwordsAPI.registerTransformation(Blocks.NETHER_WART, Identifier.of("simplymore", "the_vessel_breach"));
        SimplySwordsAPI.registerTransformation(Blocks.WITHER_ROSE, Identifier.of("simplymore", "blade_of_the_grotesque"));
        SimplySwordsAPI.registerTransformation(Blocks.POTTED_WITHER_ROSE, Identifier.of("simplymore", "blade_of_the_grotesque"));
        SimplySwordsAPI.registerTransformation(Blocks.SUNFLOWER, Identifier.of("simplymore", "vipers_call"));
        SimplySwordsAPI.registerTransformation(Blocks.DECORATED_POT, Identifier.of("simplymore", "timekeeper"));
        SimplySwordsAPI.registerTransformation(Blocks.REDSTONE_BLOCK, Identifier.of("simplymore", "matterbane"));
        SimplySwordsAPI.registerTransformation(Blocks.ANCIENT_DEBRIS, Identifier.of("simplymore", "smouldering_ruin"));
        SimplySwordsAPI.registerTransformation(Blocks.LIGHTNING_ROD, Identifier.of("simplymore", "stasis"));
        SimplySwordsAPI.registerTransformation(Blocks.SEA_LANTERN, Identifier.of("simplymore", "tidebreaker"));
        SimplySwordsAPI.registerTransformation(Blocks.QUARTZ_PILLAR, Identifier.of("simplymore", "ruyi_jingu_bang"));
        SimplySwordsAPI.registerTransformation(Blocks.SUSPICIOUS_GRAVEL, Identifier.of("simplymore", "ruptured_idol"));
        SimplySwordsAPI.registerTransformation(Blocks.POINTED_DRIPSTONE, Identifier.of("simplymore", "boas_fang"));
        SimplySwordsAPI.registerTransformation(Blocks.CRACKED_DEEPSLATE_BRICKS, Identifier.of("simplymore", "earthshatter"));
        SimplySwordsAPI.registerTransformation(Blocks.SOUL_TORCH, Identifier.of("simplymore", "soul_foreseer"));
        SimplySwordsAPI.registerTransformation(Blocks.SOUL_WALL_TORCH, Identifier.of("simplymore", "soul_foreseer"));
        SimplySwordsAPI.registerTransformation(Blocks.EMERALD_ORE, Identifier.of("simplymore", "serpentine_valour"));
        SimplySwordsAPI.registerTransformation(Blocks.DEEPSLATE_EMERALD_ORE, Identifier.of("simplymore", "serpentine_valour"));
        SimplySwordsAPI.registerTransformation(Blocks.OCHRE_FROGLIGHT, Identifier.of("simplymore", "lustrous_moxie"));
        SimplySwordsAPI.registerTransformation(Blocks.OXIDIZED_COPPER, Identifier.of("simplymore", "brassturn"));
        SimplySwordsAPI.registerTransformation(Blocks.WAXED_OXIDIZED_COPPER, Identifier.of("simplymore", "brassturn"));
        SimplySwordsAPI.registerTransformation(Blocks.LANTERN, Identifier.of("simplymore", "cindergorge"));
        SimplySwordsAPI.registerTransformation(Blocks.BEDROCK, Identifier.of("simplymore", "deaths_eyrie"));
        SimplySwordsAPI.registerTransformation(Blocks.FLOWERING_AZALEA, Identifier.of("simplymore", "perforiscus"));
        SimplySwordsAPI.registerTransformation(Blocks.POTTED_FLOWERING_AZALEA_BUSH, Identifier.of("simplymore", "perforiscus"));
        SimplySwordsAPI.registerTransformation(Blocks.COPPER_BULB, Identifier.of("simplymore", "revvengine"));
        SimplySwordsAPI.registerTransformation(Blocks.WAXED_COPPER_BULB, Identifier.of("simplymore", "revvengine"));
        SimplySwordsAPI.registerTransformation(Blocks.COPPER_BLOCK, Identifier.of("simplymore", "exedrill"));
        SimplySwordsAPI.registerTransformation(Blocks.WAXED_COPPER_BLOCK, Identifier.of("simplymore", "exedrill"));
        SimplySwordsAPI.registerTransformation(Blocks.LECTERN, Identifier.of("simplymore", "culterex"));
    }
}
