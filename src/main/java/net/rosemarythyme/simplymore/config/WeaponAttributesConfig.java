package net.rosemarythyme.simplymore.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name="weapon_attributes")
public class WeaponAttributesConfig implements ConfigData {
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
    double deerHornsSwingSpeed = 2.0;

    int greatSlitherDamage = 11;
    double greatSlitherSwingSpeed = 1.4;
    int moltenFlareDamage = 13;
    double moltenFlareSwingSpeed = 0.6;
    int grandfrostDamage = 14;
    double grandfrostSwingSpeed = 0.6;
    int mimicryPurityDamage = 8;
    double mimicryPuritySwingSpeed = 2.0;
    int mimicryTwistedDamageModifier = 2;
    double mimicryTwistedSwingSpeedModifier = 3.4;
    int glimmerstepDamage = 9;
    double glimmerstepSwingSpeed = 1.0;
    int theBloodHarvesterDamage = 8;
    double theBloodHarvesterSwingSpeed = 1.6;
    int jesterPenetrateDamage = 6;
    double jesterPenetrateSwingSpeed = 1.0;
    int scarabRollerDamage = 8;
    double scarabRollerSwingSpeed = 1.9;

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
    double tidebreakerSwingSpeed = 2.0;
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
    int lustrousMoxieDamage = 11;
    double lustrousMoxieSwingspeed = 1.0;

    public WeaponAttributesConfig() {
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

    public double getMimicryPuritySwingSpeed() {
        return mimicryPuritySwingSpeed - 4.0;
    }

    public double getMimicryTwistedSwingSpeedModifier() {
        return mimicryTwistedSwingSpeedModifier - 4.0;
    }

    public double getMoltenFlareSwingSpeed() {
        return moltenFlareSwingSpeed - 4.0;
    }

    public double getScarabRollerSwingSpeed() {
        return scarabRollerSwingSpeed - 4.0;
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

    public int getMimicryPurityDamage() {
        return mimicryPurityDamage;
    }

    public int getMimicryTwistedDamageModifier() {
        return mimicryTwistedDamageModifier;
    }

    public int getMoltenFlareDamage() {
        return moltenFlareDamage;
    }

    public int getScarabRollerDamage() {
        return scarabRollerDamage;
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
}
