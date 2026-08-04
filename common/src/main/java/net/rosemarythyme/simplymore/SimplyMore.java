package net.rosemarythyme.simplymore;

import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.client.registry.ClientEntityRendererRegistry;
import net.rosemarythyme.simplymore.client.registry.ClientItemPropertyRegistry;
import net.rosemarythyme.simplymore.client.registry.ClientTooltipRegistry;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.event.RemoveStatusOnJoin;
import net.rosemarythyme.simplymore.event.TickScreenshake;
import net.rosemarythyme.simplymore.item.LootRegistry;
import net.rosemarythyme.simplymore.registry.*;
import net.rosemarythyme.simplymore.registry.item.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class SimplyMore {
    public static final Logger LOGGER = LoggerFactory.getLogger("simplymore");
	public static final String ID = "simplymore";

	public static Identifier identifier(String path) {
		return Identifier.of(ID, path);
	}

	public static void init() {
		LifecycleEvent.SETUP.register(() -> {
			ConfigWrapper.register();

			StatusEffectRegistry.register();

			EntityRegistry.register();

			ItemRegistry.register();
			RecipeTypeRegistry.register();
			ImplicitRegistry.register();
			TransformationRegistry.register();
			AwakeningProfileRegistry.register();

			ItemComponentRegistry.register();
			TagRegistry.register();

			ItemRegistry.registerItemGroup();
			LootRegistry.register();

			SimplyMore.registerEvents();
			SoundEventRegistry.register();

			EnvExecutor.runInEnv(Env.SERVER, () -> SimplyMore::initServer);
			EnvExecutor.runInEnv(Env.CLIENT, () -> SimplyMore::initClient);
		});
	}

	@Environment(EnvType.SERVER)
	public static void initServer() {
		PacketRegistry.registerS2C();
	}

	@Environment(EnvType.CLIENT)
	public static void initClient() {
		ClientTooltipRegistry.register();
		ClientEntityRendererRegistry.register();
		ClientItemPropertyRegistry.register();

		PacketRegistry.registerS2CRecievers();
	}

	public static void registerEvents() {
		PlayerEvent.PLAYER_JOIN.register(new RemoveStatusOnJoin());
		ClientTickEvent.CLIENT_POST.register(new TickScreenshake());
	}
}