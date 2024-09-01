package net.rosemarythyme.simplymore.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name="loot")
public class LootConfig implements ConfigData {
    boolean enableLootInVillages = true;
    double goldLootWeight = 0.004500000189989805;
    double ironLootWeight = 0.004500000189989805;
    double diamondLootWeight = 0.003000000189989805;
    double netheriteLootWeight = 0;
    double runicLootWeight = 0;
    double uniqueLootWeight = 0.0010000000474974513;
    double witherUniqueWeight = 0.05;
    double enderDragonUniqueWeight = 0.5;
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
    boolean enableStasis = true;
    boolean enableTidebreaker = true;
    boolean enableRuyiJinguBang = true;
    boolean enableRupturedIdol = true;
    boolean enableBoasFang = true;

    boolean enableEarthshatter = true;
    boolean enableSoulForeseer = true;
    boolean enableSerpentineValour = true;
    boolean enableLustrousMoxie = true;


    public LootConfig() {
    }

    public boolean isEnableEarthshatter() {
        return enableEarthshatter;
    }

    public double getEnderDragonUniqueWeight() {
        return enderDragonUniqueWeight;
    }

    public double getWitherUniqueWeight() {
        return witherUniqueWeight;
    }

    public boolean isEnableLustrousMoxie() {
        return enableLustrousMoxie;
    }

    public boolean isEnableSerpentineValour() {
        return enableSerpentineValour;
    }

    public boolean isEnableSoulForeseer() {
        return enableSoulForeseer;
    }

    public boolean isEnableBoasFang() {
        return enableBoasFang;
    }

    public boolean isEnableRupturedIdol() {
        return enableRupturedIdol;
    }

    public boolean isEnableRuyiJinguBang() {
        return enableRuyiJinguBang;
    }

    public boolean isEnableTidebreaker() {
        return enableTidebreaker;
    }

    public boolean isEnableStasis() {
        return enableStasis;
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

    public boolean isEnableTheVesselBreach() {
        return enableTheVesselBreach;
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
