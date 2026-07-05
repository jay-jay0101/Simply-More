package net.rosemarythyme.simplymore;

import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.client.SimplyMoreClientInit;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.event.RemoveStatusOnJoin;
import net.rosemarythyme.simplymore.registry.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class SimplyMore {
    public static final Logger LOGGER = LoggerFactory.getLogger("simplymore");
	public static final String ID = "simplymore";

	public static Identifier identifier(String path) {
		return Identifier.of(ID, path);
	}

	public static void init() {
		ConfigWrapper.registerModConfigs();

		ModEffectsRegistry.registerModEffects();

		ModEntityRegistry.registerModEntities();
		EnvExecutor.runInEnv(Env.CLIENT, () -> SimplyMoreClientInit::registerEntityRenderers);

		ModItemsRegistry.registerModItems();
		ModItemsRegistry.registerItemGroup();
		ModRecipeTypeRegistry.registerModRecipes();
		TransformationRegistry.registerTransformations();

		ModComponentRegistry.registerModComponents();
		ModTagRegistry.registerModTags();

		SimplyMore.registerEvents();
		TooltipMotifRegistry.registerModTooltipMotifs();

		LOGGER.info(ID + " Initialized Successfully!");
	}

	public static void registerEvents() {
		PlayerEvent.PLAYER_JOIN.register(new RemoveStatusOnJoin());
	}
}