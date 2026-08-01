package net.rosemarythyme.simplymore.registry;

import net.minecraft.block.Blocks;
import net.rosemarythyme.simplymore.SimplyMore;
import net.sweenus.simplyswords.api.SimplySwordsAPI;

public class TransformationRegistry {

    public static void register() {
        SimplySwordsAPI.registerTransformation(Blocks.SLIME_BLOCK, SimplyMore.identifier("great_slither"));
        SimplySwordsAPI.registerTransformation(Blocks.BASALT, SimplyMore.identifier("molten_flare"));
        SimplySwordsAPI.registerTransformation(Blocks.BLUE_ICE, SimplyMore.identifier("grandfrost"));
        SimplySwordsAPI.registerTransformation(Blocks.GLOW_LICHEN, SimplyMore.identifier("mimicry_longsword"));
        SimplySwordsAPI.registerTransformation(Blocks.GLOWSTONE, SimplyMore.identifier("glimmerstep"));
        SimplySwordsAPI.registerTransformation(Blocks.NETHER_WART_BLOCK, SimplyMore.identifier("the_blood_harvester"));
        SimplySwordsAPI.registerTransformation(Blocks.SMOOTH_SANDSTONE, SimplyMore.identifier("myrmedge"));
        SimplySwordsAPI.registerTransformation(Blocks.GOLD_BLOCK, SimplyMore.identifier("black_pearl"));
        SimplySwordsAPI.registerTransformation(Blocks.NETHER_WART, SimplyMore.identifier("the_vessel_breach"));
        SimplySwordsAPI.registerTransformation(Blocks.WITHER_ROSE, SimplyMore.identifier("blade_of_the_grotesque"));
        SimplySwordsAPI.registerTransformation(Blocks.POTTED_WITHER_ROSE, SimplyMore.identifier("blade_of_the_grotesque"));
        SimplySwordsAPI.registerTransformation(Blocks.SUNFLOWER, SimplyMore.identifier("vipers_call"));
        SimplySwordsAPI.registerTransformation(Blocks.DECORATED_POT, SimplyMore.identifier("timekeeper"));
        SimplySwordsAPI.registerTransformation(Blocks.REDSTONE_BLOCK, SimplyMore.identifier("matterbane"));
        SimplySwordsAPI.registerTransformation(Blocks.ANCIENT_DEBRIS, SimplyMore.identifier("smouldering_ruin"));
        SimplySwordsAPI.registerTransformation(Blocks.LIGHTNING_ROD, SimplyMore.identifier("stasis"));
        SimplySwordsAPI.registerTransformation(Blocks.SEA_LANTERN, SimplyMore.identifier("tidebreaker"));
        SimplySwordsAPI.registerTransformation(Blocks.QUARTZ_PILLAR, SimplyMore.identifier("ruyi_jingu_bang"));
        SimplySwordsAPI.registerTransformation(Blocks.SUSPICIOUS_GRAVEL, SimplyMore.identifier("ruptured_idol"));
        SimplySwordsAPI.registerTransformation(Blocks.POINTED_DRIPSTONE, SimplyMore.identifier("boas_fang"));
        SimplySwordsAPI.registerTransformation(Blocks.CRACKED_DEEPSLATE_BRICKS, SimplyMore.identifier("earthshatter"));
        SimplySwordsAPI.registerTransformation(Blocks.SOUL_TORCH, SimplyMore.identifier("soul_foreseer"));
        SimplySwordsAPI.registerTransformation(Blocks.SOUL_WALL_TORCH, SimplyMore.identifier("soul_foreseer"));
        SimplySwordsAPI.registerTransformation(Blocks.EMERALD_ORE, SimplyMore.identifier("serpentine_valour"));
        SimplySwordsAPI.registerTransformation(Blocks.DEEPSLATE_EMERALD_ORE, SimplyMore.identifier("serpentine_valour"));
        SimplySwordsAPI.registerTransformation(Blocks.OCHRE_FROGLIGHT, SimplyMore.identifier("lustrous_moxie"));
        SimplySwordsAPI.registerTransformation(Blocks.OXIDIZED_COPPER, SimplyMore.identifier("brassturn"));
        SimplySwordsAPI.registerTransformation(Blocks.WAXED_OXIDIZED_COPPER, SimplyMore.identifier("brassturn"));
        SimplySwordsAPI.registerTransformation(Blocks.LANTERN, SimplyMore.identifier("cindergorge"));
        SimplySwordsAPI.registerTransformation(Blocks.BEDROCK, SimplyMore.identifier("deaths_eyrie"));
        SimplySwordsAPI.registerTransformation(Blocks.FLOWERING_AZALEA, SimplyMore.identifier("perforiscus"));
        SimplySwordsAPI.registerTransformation(Blocks.POTTED_FLOWERING_AZALEA_BUSH, SimplyMore.identifier("perforiscus"));
        SimplySwordsAPI.registerTransformation(Blocks.COPPER_BULB, SimplyMore.identifier("revvengine"));
        SimplySwordsAPI.registerTransformation(Blocks.WAXED_COPPER_BULB, SimplyMore.identifier("revvengine"));
        SimplySwordsAPI.registerTransformation(Blocks.COPPER_BLOCK, SimplyMore.identifier("exedrill"));
        SimplySwordsAPI.registerTransformation(Blocks.WAXED_COPPER_BLOCK, SimplyMore.identifier("exedrill"));
        SimplySwordsAPI.registerTransformation(Blocks.LECTERN, SimplyMore.identifier("culterex"));
    }
}
