package net.rosemarythmye.simplymore.config;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(
        name = "simplymore"
)
@Config.Gui.Background("cloth-config2:transparent")
public class WrapperConfig extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Category("uniques")
    @ConfigEntry.Gui.TransitiveObject
    public LootConfig loot = new LootConfig();
    public WeaponAttributesConfig weaponAttributes = new WeaponAttributesConfig();

    public WrapperConfig() {
    }
}