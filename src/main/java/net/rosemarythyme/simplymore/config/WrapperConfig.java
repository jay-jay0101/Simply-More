package net.rosemarythyme.simplymore.config;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(
        name = "simplymore"
)
@Config.Gui.Background("cloth-config2:transparent")
public class WrapperConfig extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Category("loot")
    @ConfigEntry.Gui.TransitiveObject
    public LootConfig loot = new LootConfig();

    @ConfigEntry.Category("attributes")
    @ConfigEntry.Gui.TransitiveObject
    public WeaponAttributesConfig weaponAttributes = new WeaponAttributesConfig();


    @ConfigEntry.Category("unique_effects")
    @ConfigEntry.Gui.TransitiveObject
    public UniqueEffectConfig uniqueEffects = new UniqueEffectConfig();

    public WrapperConfig() {
    }
}