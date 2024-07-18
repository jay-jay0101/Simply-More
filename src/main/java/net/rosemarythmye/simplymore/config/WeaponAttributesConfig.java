package net.rosemarythmye.simplymore.config;

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
    double greatKatanaSwingSpeed = -2.6;
    double grandswordSwingSpeed = -3.5;
    double backhandBladeSwingSpeed = -1.7;
    double lanceSwingSpeed = -3;
    double khopeshSwingSpeed = -2.25;
    double daggerSwingSpeed = -1.8;

    int greatSlitherDamage = 11;
    double greatSlitherSwingSpeed = -2.6;
    int moltenFlareDamage = 13;
    double moltenFlareSwingSpeed = -3.5;
    int grandfrostDamage = 14;
    double grandfrostSwingSpeed = -3.5;
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
    double scarabRollerSwingSpeed = -2.25;


    public WeaponAttributesConfig() {
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
}
