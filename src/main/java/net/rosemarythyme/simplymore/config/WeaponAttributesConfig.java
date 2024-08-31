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
    double greatKatanaSwingSpeed = -2.6;
    double grandswordSwingSpeed = -3.4;
    double backhandBladeSwingSpeed = -1.7;
    double lanceSwingSpeed = -3;
    double khopeshSwingSpeed = -2.1;
    double daggerSwingSpeed = -1.8;
    double quarterstaffSwingSpeed = -2;
    double pernachSwingSpeed = -2.7;
    double greatSpearSwingSpeed = -3;
    double deerHornsSwingSpeed = -2;

    int greatSlitherDamage = 11;
    double greatSlitherSwingSpeed = -2.6;
    int moltenFlareDamage = 13;
    double moltenFlareSwingSpeed = -3.6;
    int grandfrostDamage = 14;
    double grandfrostSwingSpeed = -3.4;
    int mimicryPurityDamage = 8;
    double mimicryPuritySwingSpeed = -2;
    int mimicryTwistedDamageModifier = 2;
    double mimicryTwistedSwingSpeedModifier = -0.6;
    int glimmerstepDamage = 9;
    double glimmerstepSwingSpeed = -3;
    int theBloodHarvesterDamage = 8;
    double theBloodHarvesterSwingSpeed = -2.4;
    int jesterPenetrateDamage = 6;
    double jesterPenetrateSwingSpeed = -3;
    int scarabRollerDamage = 8;
    double scarabRollerSwingSpeed = -2.1;

    int blackPearlDamage = 9;
    double blackPearlSwingSpeed = -2;

    int thePanDamage = 4;
    double thePanSwingSpeed = -2.5;

    int theVesselBreachDamage = 6;
    double theVesselBreachSwingSpeed = -1.8;

    int bladeOfTheGrotesqueDamage = 12;
    double bladeOfTheGrotesqueSwingSpeed = -2.8;

    int vipersCallDamage = 6;
    double vipersCallSwingSpeed = -3;

    int timekeeperDamage = 8;
    double timekeeperSwingSpeed = -2;

    int matterbaneDamage = 9;
    double matterbaneSwingSpeed = -2.4;
    int smoulderingRuinDamage = 7;
    double smoulderingRuinSwingSpeed = -1.7;

    int stasisDamage = 8;
    double stasisSwingSpeed = -2;
    int tidebreakerDamage = 9;
    double tidebreakerSwingSpeed = -2;
    int ruyiJinguBangDamage = 9;
    double ruyiJinguBangSwingSpeed = -2;
    int rupturedIdolDamage = 10;
    double rupturedIdolSwingSpeed = -2.7;

    int ascendedIdolDamage = 10;
    double ascendedIdolSwingSpeed = -2.7;

    int tarnishedIdolDamage = 10;
    double tarnishedIdolSwingSpeed = -2.7;

    int holylightDamage = 10;
    double holylightSwingSpeed = -2.7;

    int darksentDamage = 10;
    double darksentSwingSpeed = -2.7;

    int boasFangDamage = 7;

    double boasFangSwingSpeed = -2.2;
    int earthshatterDamage = 12;
    double earthshatterSwingSpeed = -3.5;
    int soulForeseerDamage = 9;
    double soulForeseerSwingSpeed = -2.6;
    int serpentineValourDamage = 11;
    double serpentineValourSwingSpeed = -3.3;
    int lustrousMoxieDamage = 11;
    double lustrousMoxieSwingspeed = -3;

    public WeaponAttributesConfig() {
    }

    public double getEarthshatterSwingSpeed() {
        return earthshatterSwingSpeed;
    }

    public double getLustrousMoxieSwingspeed() {
        return lustrousMoxieSwingspeed;
    }

    public double getSerpentineValourSwingSpeed() {
        return serpentineValourSwingSpeed;
    }

    public double getSoulForeseerSwingSpeed() {
        return soulForeseerSwingSpeed;
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
        return boasFangSwingSpeed;
    }

    public int getBoasFangDamage() {
        return boasFangDamage;
    }

    public double getDarksentSwingSpeed() {
        return darksentSwingSpeed;
    }

    public int getDarksentDamage() {
        return darksentDamage;
    }

    public double getTarnishedIdolSwingSpeed() {
        return tarnishedIdolSwingSpeed;
    }

    public int getTarnishedIdolDamage() {
        return tarnishedIdolDamage;
    }

    public double getHolylightSwingSpeed() {
        return holylightSwingSpeed;
    }

    public int getHolylightDamage() {
        return holylightDamage;
    }

    public double getAscendedIdolSwingSpeed() {
        return ascendedIdolSwingSpeed;
    }

    public int getAscendedIdolDamage() {
        return ascendedIdolDamage;
    }

    public double getRupturedIdolSwingSpeed() {
        return rupturedIdolSwingSpeed;
    }

    public int getRupturedIdolDamage() {
        return rupturedIdolDamage;
    }

    public double getRuyiJinguBangSwingSpeed() {
        return ruyiJinguBangSwingSpeed;
    }

    public int getRuyiJinguBangDamage() {
        return ruyiJinguBangDamage;
    }

    public double getTidebreakerSwingSpeed() {
        return tidebreakerSwingSpeed;
    }

    public int getTidebreakerDamage() {
        return tidebreakerDamage;
    }

    public double getStasisSwingSpeed() {
        return stasisSwingSpeed;
    }

    public int getStasisDamage() {
        return stasisDamage;
    }

    public double getSmoulderingRuinSwingSpeed() {
        return smoulderingRuinSwingSpeed;
    }

    public int getSmoulderingRuinDamage() {
        return smoulderingRuinDamage;
    }

    public double getMatterbaneSwingSpeed() {
        return matterbaneSwingSpeed;
    }

    public int getMatterbaneDamage() {
        return matterbaneDamage;
    }

    public double getTimekeeperSwingSpeed() {
        return timekeeperSwingSpeed;
    }

    public int getTimekeeperDamage() {
        return timekeeperDamage;
    }

    public double getVipersCallSwingSpeed() {
        return vipersCallSwingSpeed;
    }

    public int getVipersCallDamage() {
        return vipersCallDamage;
    }

    public double getBladeOfTheGrotesqueSwingSpeed() {
        return bladeOfTheGrotesqueSwingSpeed;
    }

    public int getBladeOfTheGrotesqueDamage() {
        return bladeOfTheGrotesqueDamage;
    }

    public double getTheVesselBreachSwingSpeed() {
        return theVesselBreachSwingSpeed;
    }

    public int getTheVesselBreachDamage() {
        return theVesselBreachDamage;
    }

    public double getThePanSwingSpeed() {
        return thePanSwingSpeed;
    }

    public int getThePanDamage() {
        return thePanDamage;
    }

    public double getBlackPearlSwingSpeed() {
        return blackPearlSwingSpeed;
    }

    public int getBlackPearlDamage() {
        return blackPearlDamage;
    }

    public double getBackhandBladeSwingSpeed() {
        return backhandBladeSwingSpeed;
    }

    public double getGlimmerstepSwingSpeed() {
        return glimmerstepSwingSpeed;
    }

    public double getGrandfrostSwingSpeed() {
        return grandfrostSwingSpeed;
    }

    public double getGrandswordSwingSpeed() {
        return grandswordSwingSpeed;
    }

    public double getGreatKatanaSwingSpeed() {
        return greatKatanaSwingSpeed;
    }

    public double getGreatSlitherSwingSpeed() {
        return greatSlitherSwingSpeed;
    }

    public double getJesterPenetrateSwingSpeed() {
        return jesterPenetrateSwingSpeed;
    }

    public double getKhopeshSwingSpeed() {
        return khopeshSwingSpeed;
    }

    public double getLanceSwingSpeed() {
        return lanceSwingSpeed;
    }

    public double getMimicryPuritySwingSpeed() {
        return mimicryPuritySwingSpeed;
    }

    public double getMimicryTwistedSwingSpeedModifier() {
        return mimicryTwistedSwingSpeedModifier;
    }

    public double getMoltenFlareSwingSpeed() {
        return moltenFlareSwingSpeed;
    }

    public double getScarabRollerSwingSpeed() {
        return scarabRollerSwingSpeed;
    }

    public double getTheBloodHarvesterSwingSpeed() {
        return theBloodHarvesterSwingSpeed;
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
        return daggerSwingSpeed;
    }

    public int getDaggerDamageModifier() {
        return daggerDamageModifier;
    }

    public double getDeerHornsSwingSpeed() {
        return deerHornsSwingSpeed;
    }

    public double getGreatSpearSwingSpeed() {
        return greatSpearSwingSpeed;
    }

    public double getPernachSwingSpeed() {
        return pernachSwingSpeed;
    }

    public double getQuarterstaffSwingSpeed() {
        return quarterstaffSwingSpeed;
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
