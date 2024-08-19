package net.rosemarythyme.simplymore;

import net.fabricmc.api.ModInitializer;
import net.rosemarythyme.simplymore.config.ModConfigs;
import net.rosemarythyme.simplymore.recipes.ModRecipes;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;
import net.rosemarythyme.simplymore.util.LootTableModifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Simplymore implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("simplymore");
	public static final String ID = "simplymore";
	@Override
	public void onInitialize() {
		ModConfigs.registerConfigs();

		ModEffectsRegistry.registerModEffects();

		ModItemsRegistry.registerModItems();

		ModRecipes.registerModRecipes();

		LootTableModifier.registerLootTableChanges();
		LOGGER.info(ID + " Initialized Successfully!");
	}
}