package net.rosemarythyme.simplymore.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name="weapon_attributes")
public class WeaponAttributesConfig implements ConfigData {
    int ironWeaponDamageModifier = 0;
    int goldWeaponDamageModifier = 0;
    int diamondWeaponDamageModifier = 0;
    int netheriteWeaponDamageModifier = 0;
    int runicWeaponDamageModifier = 0;

    @Comment("Stick N Stone Compat")
    int woodenWeaponDamageModifier = 0;
    int stoneWeaponDamageModifier = 0;

    @Comment("Gobber Compat")
    int gobberWeaponDamageModifier = 0;
    int gobberNetherWeaponDamageModifier = 0;
    int gobberEndWeaponDamageModifier = 0;
    boolean gobberEndWeaponUnbreakable = true;

    @Comment("Mythic Metals Compat")
    int adamantiteWeaponDamageModifier = 0;
    int aquariumWeaponDamageModifier = 0;
    int banglumWeaponDamageModifier = 0;
    int carmotWeaponDamageModifier = 0;
    int kyberWeaponDamageModifier = 0;
    int mythrilWeaponDamageModifier = 0;
    int orichalcumWeaponDamageModifier = 0;
    int osmiumWeaponDamageModifier = 0;
    int prometheumWeaponDamageModifier = 0;
    int quadrillumWeaponDamageModifier = 0;
    int runiteWeaponDamageModifier = 0;
    int starPlatinumWeaponDamageModifier = 0;
    int bronzeWeaponDamageModifier = 0;
    int steelWeaponDamageModifier = 0;
    int palladiumWeaponDamageModifier = 0;
    int metallurgiumWeaponDamageModifier = 0;
    int celestiumWeaponDamageModifier = 0;
    int copperWeaponDamageModifier = 0;
    int durasteelWeaponDamageModifier = 0;
    int hallowedWeaponDamageModifier = 0;
    int legendaryBanglumWeaponDamageModifier = 0;
    int stormyxWeaponDamageModifier = 0;
    int tidesingerWeaponDamageModifier = 0;

    @Comment("Weapon Stats")
    int greatKatanaDamageModifier = 1;
    int grandswordDamageModifier = 6;
    int backhandBladeDamageModifier = -2;
    int lanceDamageModifier = 0;
    int khopeshDamageModifier = -1;
    int daggerDamageModifier = -2;
    int quarterstaffDamageModifier = -2;
    int pernachDamageModifier = 1;
    int greatSpearDamageModifier = 3;
    int deerHornsDamageModifier = -1;

    double greatKatanaSwingSpeed = 1.4;
    double grandswordSwingSpeed = 0.6;
    double backhandBladeSwingSpeed = 2.3;
    double lanceSwingSpeed = 1.0;
    double khopeshSwingSpeed = 1.9;
    double daggerSwingSpeed = 2.2;
    double quarterstaffSwingSpeed = 2.0;
    double pernachSwingSpeed = 1.3;
    double greatSpearSwingSpeed = 1.0;
    double deerHornsSwingSpeed = 2.1;

    int greatSlitherDamage = 11;
    double greatSlitherSwingSpeed = 1.4;
    int moltenFlareDamage = 13;
    double moltenFlareSwingSpeed = 0.6;
    int grandfrostDamage = 14;
    double grandfrostSwingSpeed = 0.6;
    int glimmerstepDamage = 9;
    double glimmerstepSwingSpeed = 1.0;
    int theBloodHarvesterDamage = 8;
    double theBloodHarvesterSwingSpeed = 1.6;
    int jesterPenetrateDamage = 6;
    double jesterPenetrateSwingSpeed = 1.0;
    int myrmedgeDamage = 8;
    double myrmedgeSwingSpeed = 1.9;

    int blackPearlDamage = 9;
    double blackPearlSwingSpeed = 2.0;

    int thePanDamage = 4;
    double thePanSwingSpeed = 1.5;

    int theVesselBreachDamage = 6;
    double theVesselBreachSwingSpeed = 2.2;

    int bladeOfTheGrotesqueDamage = 12;
    double bladeOfTheGrotesqueSwingSpeed = 1.2;

    int vipersCallDamage = 6;
    double vipersCallSwingSpeed = 1.0;

    int timekeeperDamage = 8;
    double timekeeperSwingSpeed = 2.0;

    int matterbaneDamage = 9;
    double matterbaneSwingSpeed = 1.6;

    int smoulderingRuinDamage = 7;
    double smoulderingRuinSwingSpeed = 2.3;

    int stasisDamage = 8;
    double stasisSwingSpeed = 2.0;
    int tidebreakerDamage = 9;
    double tidebreakerSwingSpeed = 2.1;
    int ruyiJinguBangDamage = 9;
    double ruyiJinguBangSwingSpeed = 2.0;
    int rupturedIdolDamage = 10;
    double rupturedIdolSwingSpeed = 1.3;

    int ascendedIdolDamage = 10;
    double ascendedIdolSwingSpeed = 1.3;

    int tarnishedIdolDamage = 10;
    double tarnishedIdolSwingSpeed = 1.3;

    int holylightDamage = 10;
    double holylightSwingSpeed = 1.3;

    int darksentDamage = 10;
    double darksentSwingSpeed = 1.3;

    int boasFangDamage = 7;
    double boasFangSwingSpeed = 1.8;
    int earthshatterDamage = 12;
    double earthshatterSwingSpeed = 0.6;
    int soulForeseerDamage = 9;
    double soulForeseerSwingSpeed = 1.4;
    int serpentineValourDamage = 11;
    double serpentineValourSwingSpeed = 0.7;
    int lustrousMoxieDamage = 10;
    double lustrousMoxieSwingspeed = 1.4;
    int brassturnDamage = 8;
    double brassturnMaxSwingSpeed = 3.8;
    int cindergorgeDamage = 11;
    double cindergorgeSwingSpeed = 1.3;
    int deathsEyrieDamage = 12;
    double deathsEyrieSwingSpeed = 0.8;
    int perforiscusDamage = 10;
    double perforiscusSwingSpeed = 0.7;
    int revvengineDamage = 8;
    double revvengineSwingSpeed = 2.0;
    int exedrillDamage = 9;
    double exedrillSwingSpeed = 1.0;
    int culterexDamage = 8;
    double culterexSwingSpeed = 2.2;

    public double getCulterexSwingSpeed() {
        return culterexSwingSpeed - 4.0;
    }

    public int getCulterexDamage() {
        return culterexDamage;
    }

    public double getExedrillSwingSpeed() {
        return exedrillSwingSpeed - 4.0;
    }

    public int getExedrillDamage() {
        return exedrillDamage;
    }

    public WeaponAttributesConfig() {
    }

    public double getRevvengineSwingSpeed() {
        return revvengineSwingSpeed - 4.0;
    }

    public int getRevvengineDamage() {
        return revvengineDamage;
    }

    public double getPerforiscusSwingSpeed() {
        return perforiscusSwingSpeed - 4.0;
    }

    public int getPerforiscusDamage() {
        return perforiscusDamage;
    }

    public double getDeathsEyrieSwingSpeed() {
        return deathsEyrieSwingSpeed - 4.0;
    }

    public int getDeathsEyrieDamage() {
        return deathsEyrieDamage;
    }

    public double getCindergorgeSwingSpeed() {
        return cindergorgeSwingSpeed - 4.0;
    }

    public int getCindergorgeDamage() {
        return cindergorgeDamage;
    }

    public double getBrassturnMaxSwingSpeed() {
        return brassturnMaxSwingSpeed - 4.0;
    }

    public int getBrassturnDamage() {
        return brassturnDamage;
    }

    public double getEarthshatterSwingSpeed() {
        return earthshatterSwingSpeed - 4.0;
    }

    public double getLustrousMoxieSwingspeed() {
        return lustrousMoxieSwingspeed - 4.0;
    }

    public double getSerpentineValourSwingSpeed() {
        return serpentineValourSwingSpeed - 4.0;
    }

    public double getSoulForeseerSwingSpeed() {
        return soulForeseerSwingSpeed - 4.0;
    }

    public int getEarthshatterDamage() {
        return earthshatterDamage;
    }

    public int getLustrousMoxieDamage() {
        return lustrousMoxieDamage;
    }

    public int getSerpentineValourDamage() {
        return serpentineValourDamage;
    }

    public int getSoulForeseerDamage() {
        return soulForeseerDamage;
    }

    public double getBoasFangSwingSpeed() {
        return boasFangSwingSpeed - 4.0;
    }

    public int getBoasFangDamage() {
        return boasFangDamage;
    }

    public double getDarksentSwingSpeed() {
        return darksentSwingSpeed - 4.0;
    }

    public int getDarksentDamage() {
        return darksentDamage;
    }

    public double getTarnishedIdolSwingSpeed() {
        return tarnishedIdolSwingSpeed - 4.0;
    }

    public int getTarnishedIdolDamage() {
        return tarnishedIdolDamage;
    }

    public double getHolylightSwingSpeed() {
        return holylightSwingSpeed - 4.0;
    }

    public int getHolylightDamage() {
        return holylightDamage;
    }

    public double getAscendedIdolSwingSpeed() {
        return ascendedIdolSwingSpeed - 4.0;
    }

    public int getAscendedIdolDamage() {
        return ascendedIdolDamage;
    }

    public double getRupturedIdolSwingSpeed() {
        return rupturedIdolSwingSpeed - 4.0;
    }

    public int getRupturedIdolDamage() {
        return rupturedIdolDamage;
    }

    public double getRuyiJinguBangSwingSpeed() {
        return ruyiJinguBangSwingSpeed - 4.0;
    }

    public int getRuyiJinguBangDamage() {
        return ruyiJinguBangDamage;
    }

    public double getTidebreakerSwingSpeed() {
        return tidebreakerSwingSpeed - 4.0;
    }

    public int getTidebreakerDamage() {
        return tidebreakerDamage;
    }

    public double getStasisSwingSpeed() {
        return stasisSwingSpeed - 4.0;
    }

    public int getStasisDamage() {
        return stasisDamage;
    }

    public double getSmoulderingRuinSwingSpeed() {
        return smoulderingRuinSwingSpeed - 4.0;
    }

    public int getSmoulderingRuinDamage() {
        return smoulderingRuinDamage;
    }

    public double getMatterbaneSwingSpeed() {
        return matterbaneSwingSpeed - 4.0;
    }

    public int getMatterbaneDamage() {
        return matterbaneDamage;
    }

    public double getTimekeeperSwingSpeed() {
        return timekeeperSwingSpeed - 4.0;
    }

    public int getTimekeeperDamage() {
        return timekeeperDamage;
    }

    public double getVipersCallSwingSpeed() {
        return vipersCallSwingSpeed - 4.0;
    }

    public int getVipersCallDamage() {
        return vipersCallDamage;
    }

    public double getBladeOfTheGrotesqueSwingSpeed() {
        return bladeOfTheGrotesqueSwingSpeed - 4.0;
    }

    public int getBladeOfTheGrotesqueDamage() {
        return bladeOfTheGrotesqueDamage;
    }

    public double getTheVesselBreachSwingSpeed() {
        return theVesselBreachSwingSpeed - 4.0;
    }

    public int getTheVesselBreachDamage() {
        return theVesselBreachDamage;
    }

    public double getThePanSwingSpeed() {
        return thePanSwingSpeed - 4.0;
    }

    public int getThePanDamage() {
        return thePanDamage;
    }

    public double getBlackPearlSwingSpeed() {
        return blackPearlSwingSpeed - 4.0;
    }

    public int getBlackPearlDamage() {
        return blackPearlDamage;
    }

    public double getBackhandBladeSwingSpeed() {
        return backhandBladeSwingSpeed - 4.0;
    }

    public double getGlimmerstepSwingSpeed() {
        return glimmerstepSwingSpeed - 4.0;
    }

    public double getGrandfrostSwingSpeed() {
        return grandfrostSwingSpeed - 4.0;
    }

    public double getGrandswordSwingSpeed() {
        return grandswordSwingSpeed - 4.0;
    }

    public double getGreatKatanaSwingSpeed() {
        return greatKatanaSwingSpeed - 4.0;
    }

    public double getGreatSlitherSwingSpeed() {
        return greatSlitherSwingSpeed - 4.0;
    }

    public double getJesterPenetrateSwingSpeed() {
        return jesterPenetrateSwingSpeed - 4.0;
    }

    public double getKhopeshSwingSpeed() {
        return khopeshSwingSpeed - 4.0;
    }

    public double getLanceSwingSpeed() {
        return lanceSwingSpeed - 4.0;
    }

    public double getMoltenFlareSwingSpeed() {
        return moltenFlareSwingSpeed - 4.0;
    }

    public double getMyrmedgeSwingSpeed() {
        return myrmedgeSwingSpeed - 4.0;
    }

    public double getTheBloodHarvesterSwingSpeed() {
        return theBloodHarvesterSwingSpeed - 4.0;
    }

    public int getBackhandBladeDamageModifier() {
        return backhandBladeDamageModifier;
    }

    public int getGlimmerstepDamage() {
        return glimmerstepDamage;
    }

    public int getGrandfrostDamage() {
        return grandfrostDamage;
    }

    public int getGrandswordDamageModifier() {
        return grandswordDamageModifier;
    }

    public int getGreatKatanaDamageModifier() {
        return greatKatanaDamageModifier;
    }

    public int getGreatSlitherDamage() {
        return greatSlitherDamage;
    }

    public int getJesterPenetrateDamage() {
        return jesterPenetrateDamage;
    }

    public int getKhopeshDamageModifier() {
        return khopeshDamageModifier;
    }

    public int getLanceDamageModifier() {
        return lanceDamageModifier;
    }

    public int getMoltenFlareDamage() {
        return moltenFlareDamage;
    }

    public int getMyrmedgeDamage() {
        return myrmedgeDamage;
    }

    public int getTheBloodHarvesterDamage() {
        return theBloodHarvesterDamage;
    }

    public double getDaggerSwingSpeed() {
        return daggerSwingSpeed - 4.0;
    }

    public int getDaggerDamageModifier() {
        return daggerDamageModifier;
    }

    public double getDeerHornsSwingSpeed() {
        return deerHornsSwingSpeed - 4.0;
    }

    public double getGreatSpearSwingSpeed() {
        return greatSpearSwingSpeed - 4.0;
    }

    public double getPernachSwingSpeed() {
        return pernachSwingSpeed - 4.0;
    }

    public double getQuarterstaffSwingSpeed() {
        return quarterstaffSwingSpeed - 4.0;
    }

    public int getDeerHornsDamageModifier() {
        return deerHornsDamageModifier;
    }

    public int getGreatSpearDamageModifier() {
        return greatSpearDamageModifier;
    }

    public int getPernachDamageModifier() {
        return pernachDamageModifier;
    }

    public int getQuarterstaffDamageModifier() {
        return quarterstaffDamageModifier;
    }

    public int getDiamondWeaponDamageModifier() {
        return diamondWeaponDamageModifier;
    }

    public int getGoldWeaponDamageModifier() {
        return goldWeaponDamageModifier;
    }

    public int getIronWeaponDamageModifier() {
        return ironWeaponDamageModifier;
    }

    public int getNetheriteWeaponDamageModifier() {
        return netheriteWeaponDamageModifier;
    }

    public int getRunicWeaponDamageModifier() {
        return runicWeaponDamageModifier;
    }

    public int getStoneWeaponDamageModifier() {
        return stoneWeaponDamageModifier;
    }

    public int getWoodenWeaponDamageModifier() {
        return woodenWeaponDamageModifier;
    }

    public int getGobberEndWeaponDamageModifier() {
        return gobberEndWeaponDamageModifier;
    }

    public int getGobberWeaponDamageModifier() {
        return gobberWeaponDamageModifier;
    }

    public int getGobberNetherWeaponDamageModifier() {
        return gobberNetherWeaponDamageModifier;
    }

    public boolean isGobberEndWeaponUnbreakable() {
        return gobberEndWeaponUnbreakable;
    }

    public int getAdamantiteWeaponDamageModifier() {
        return adamantiteWeaponDamageModifier;
    }

    public int getAquariumWeaponDamageModifier() {
        return aquariumWeaponDamageModifier;
    }

    public int getBanglumWeaponDamageModifier() {
        return banglumWeaponDamageModifier;
    }

    public int getBronzeWeaponDamageModifier() {
        return bronzeWeaponDamageModifier;
    }

    public int getCarmotWeaponDamageModifier() {
        return carmotWeaponDamageModifier;
    }

    public int getCelestiumWeaponDamageModifier() {
        return celestiumWeaponDamageModifier;
    }

    public int getCopperWeaponDamageModifier() {
        return copperWeaponDamageModifier;
    }

    public int getDurasteelWeaponDamageModifier() {
        return durasteelWeaponDamageModifier;
    }

    public int getHallowedWeaponDamageModifier() {
        return hallowedWeaponDamageModifier;
    }

    public int getKyberWeaponDamageModifier() {
        return kyberWeaponDamageModifier;
    }

    public int getLegendaryBanglumWeaponDamageModifier() {
        return legendaryBanglumWeaponDamageModifier;
    }

    public int getMetallurgiumWeaponDamageModifier() {
        return metallurgiumWeaponDamageModifier;
    }

    public int getMythrilWeaponDamageModifier() {
        return mythrilWeaponDamageModifier;
    }

    public int getOrichalcumWeaponDamageModifier() {
        return orichalcumWeaponDamageModifier;
    }

    public int getOsmiumWeaponDamageModifier() {
        return osmiumWeaponDamageModifier;
    }

    public int getPalladiumWeaponDamageModifier() {
        return palladiumWeaponDamageModifier;
    }

    public int getPrometheumWeaponDamageModifier() {
        return prometheumWeaponDamageModifier;
    }

    public int getQuadrillumWeaponDamageModifier() {
        return quadrillumWeaponDamageModifier;
    }

    public int getRuniteWeaponDamageModifier() {
        return runiteWeaponDamageModifier;
    }

    public int getStarPlatinumWeaponDamageModifier() {
        return starPlatinumWeaponDamageModifier;
    }

    public int getSteelWeaponDamageModifier() {
        return steelWeaponDamageModifier;
    }

    public int getTidesingerWeaponDamageModifier() {
        return tidesingerWeaponDamageModifier;
    }

    public int getStormyxWeaponDamageModifier() {
        return stormyxWeaponDamageModifier;
    }
}
