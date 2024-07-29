package net.rosemarythmye.simplymore.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name="loot")
public class LootConfig implements ConfigData {
    boolean enableLootInVillages = true;
    boolean enableWitherDropsUnique = true;
    boolean enableDragonDropsUnique = true;
    double goldLootWeight = 0.004500000189989805;
    double ironLootWeight = 0.004500000189989805;
    double diamondLootWeight = 0.003000000189989805;
    double netheriteLootWeight = 0;
    double runicLootWeight = 0;
    double uniqueLootWeight = 0.0010000000474974513;
    boolean enableJokeUniqueBossDrops = false;
    boolean enableJokeUniqueChestLoot = true;
    boolean enableGreatSlither = true;
    boolean enableMoltenFlare = true;
    boolean enableGrandfrost = true;
    boolean enableMimicry = true;
    boolean enableGlimmerstep = true;
    boolean enableTheBloodHarvester = true;
    boolean enableScarabRoller = true;
    boolean enableJesterPenetrate = true;
    boolean enableBlackPearl = true;
    boolean enableThePan = true;
    boolean enableTheVesselBreach = true;
    boolean enableBladeOfTheGrotesque = true;
    boolean enableVipersCall = true;
    boolean enableTimekeeper = true;
    boolean enableMatterbane = true;
    boolean enableSmoulderingRuin = true;

    public LootConfig() {
    }

    public boolean isEnableSmoulderingRuin() {
        return enableSmoulderingRuin;
    }

    public boolean isEnableMatterbane() {
        return enableMatterbane;
    }

    public boolean isEnableTimekeeper() {
        return enableTimekeeper;
    }

    public boolean isEnableVipersCall() {
        return enableVipersCall;
    }

    public boolean isEnableBladeOfTheGrotesque() {
        return enableBladeOfTheGrotesque;
    }

    public boolean isEnableDragonDropsUnique() {
        return enableDragonDropsUnique;
    }

    public boolean isEnableTheVesselBreach() {
        return enableTheVesselBreach;
    }

    public boolean isEnableWitherDropsUnique() {
        return enableWitherDropsUnique;
    }

    public boolean isEnableGlimmerstep() {
        return enableGlimmerstep;
    }

    public boolean isEnableThePan() {
        return enableThePan;
    }

    public boolean isEnableBlackPearl() {
        return enableBlackPearl;
    }

    public boolean isEnableGrandfrost() {
        return enableGrandfrost;
    }

    public boolean isEnableGreatSlither() {
        return enableGreatSlither;
    }

    public boolean isEnableJesterPenetrate() {
        return enableJesterPenetrate;
    }

    public boolean isEnableJokeUniqueBossDrops() {
        return enableJokeUniqueBossDrops;
    }

    public boolean isEnableJokeUniqueChestLoot() {
        return enableJokeUniqueChestLoot;
    }

    public boolean isEnableMimicry() {
        return enableMimicry;
    }

    public boolean isEnableMoltenFlare() {
        return enableMoltenFlare;
    }

    public boolean isEnableScarabRoller() {
        return enableScarabRoller;
    }

    public boolean isEnableTheBloodHarvester() {
        return enableTheBloodHarvester;
    }

    public double getDiamondLootWeight() {
        return diamondLootWeight;
    }

    public double getGoldLootWeight() {
        return goldLootWeight;
    }

    public double getIronLootWeight() {
        return ironLootWeight;
    }

    public double getNetheriteLootWeight() {
        return netheriteLootWeight;
    }

    public double getRunicLootWeight() {
        return runicLootWeight;
    }

    public double getUniqueLootWeight() {
        return uniqueLootWeight;
    }

    public boolean isEnableLootInVillages() {
        return enableLootInVillages;
    }
}
