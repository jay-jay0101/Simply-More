package net.rosemarythyme.simplymore.client.hud;

import net.minecraft.item.Item;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class HudOverlayItemRegistry {
    private static final Map<Item, HudOverlay<?>> OVERLAYS = new HashMap<>();

    public static void register() {
        OVERLAYS.put(ItemRegistry.SOULFRACTURE.get(), new SoulfractureHud());
        OVERLAYS.put(ItemRegistry.MOUNDSHIFTER.get(), new CounterBarHudOverlay("item.simplymore.moundshifter.overlay", 0xFF281B0D, 0x88895129, 0xFF895129, 0xFF654321));
        OVERLAYS.put(ItemRegistry.RUYI_JINGU_BANG.get(), new CounterBarHudOverlay("item.simplymore.ruyi_jingu_bang.overlay", 0xFFAA6C39, 0x88D3AF37, 0xFFD3AF37, 0xFFAE8625));
    }

    @Nullable
    public static HudOverlay<?> getOverlay(Item item) {
        return OVERLAYS.getOrDefault(item, null);
    }
}
