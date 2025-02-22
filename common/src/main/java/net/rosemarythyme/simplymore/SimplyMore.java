package net.rosemarythyme.simplymore;

import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import net.rosemarythyme.simplymore.client.SimplyMoreClientInit;
import net.rosemarythyme.simplymore.config.ModConfigs;
import net.rosemarythyme.simplymore.registry.*;
import net.rosemarythyme.simplymore.util.LootTableModifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class SimplyMore {
    public static final Logger LOGGER = LoggerFactory.getLogger("simplymore");
	public static final String ID = "simplymore";

	public static void init() {
		ModConfigs.registerConfigs();

		ModEffectsRegistry.registerModEffects();

		ModEntityRegistry.registerModEntities();
		EnvExecutor.runInEnv(Env.CLIENT, () -> SimplyMoreClientInit::registerEntityRenderers);

		ModItemsRegistry.registerModItems();

		ModTagRegistry.registerModTags();

		ModRecipesRegistry.registerModRecipes();

		LootTableModifier.registerLootTableChanges();

		LOGGER.info(ID + " Initialized Successfully!");
	}
}