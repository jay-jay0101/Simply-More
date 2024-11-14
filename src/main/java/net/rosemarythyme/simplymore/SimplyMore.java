package net.rosemarythyme.simplymore;

import net.fabricmc.api.ModInitializer;
import net.rosemarythyme.simplymore.config.ModConfigs;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;
import net.rosemarythyme.simplymore.registry.ModRecipesRegistry;
import net.rosemarythyme.simplymore.util.LootTableModifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class SimplyMore implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("simplymore");
	public static final String ID = "simplymore";

	@Override
	public void onInitialize() {
		ModConfigs.registerConfigs();

		ModEffectsRegistry.registerModEffects();

		ModItemsRegistry.registerModItems();

		ModRecipesRegistry.registerModRecipes();

		LootTableModifier.registerLootTableChanges();

		LOGGER.info(ID + " Initialized Successfully!");
	}
}