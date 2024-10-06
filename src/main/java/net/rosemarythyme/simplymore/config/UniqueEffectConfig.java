package net.rosemarythyme.simplymore.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name="unique_effects")
public class UniqueEffectConfig implements ConfigData {
    @Comment("Black Pearl")
    int cannonballCooldown = 180;
    int plunderChance = 10;

    public int getCannonballCooldown() {
        return cannonballCooldown;
    }

    public int getPlunderChance() {
        return plunderChance;
    }

    @Comment("Blade of the Grotesque")
    int solidifySelfStunTime = 50;
    int solidifyAttackerStunTime = 90;
    int grotesqueHeldArmorBuff = 3;
    int solidifySelfStunnedArmorBuff = 10;
    int solidifyAttackerStunnedArmorBuff = 10;
    int solidifyCooldown = 500;

    public int getSolidifyAttackerStunTime() {
        return solidifyAttackerStunTime;
    }

    public int getGrotesqueHeldArmorBuff() {
        return grotesqueHeldArmorBuff;
    }

    public int getSolidifySelfStunTime() {
        return solidifySelfStunTime;
    }

    public int getSolidifyCooldown() {
        return solidifyCooldown;
    }

    public int getSolidifyAttackerStunnedArmorBuff() {
        return solidifyAttackerStunnedArmorBuff;
    }

    public int getSolidifySelfStunnedArmorBuff() {
        return solidifySelfStunnedArmorBuff;
    }

    @Comment("Boa's Fang")
    int suffocationChance = 50;
    int suffocationTime = 50;
    int spitSpeedTime = 80;
    int spitCooldown = 600;
    int spitPoisonTime = 80;
    float spitDamage = 5f;
    float spitSelfKnockback = 2f;

    public float getSpitSelfKnockback() {
        return spitSelfKnockback;
    }

    public int getSpitCooldown() {
        return spitCooldown;
    }

    public int getSpitPoisonTime() {
        return spitPoisonTime;
    }

    public int getSpitSpeedTime() {
        return spitSpeedTime;
    }

    public float getSpitDamage() {
        return spitDamage;
    }

    public int getSuffocationChance() {
        return suffocationChance;
    }

    public int getSuffocationTime() {
        return suffocationTime;
    }

    @Comment("Earthshatter")
    int armorCrunchChance = 15;
    int slamCooldown = 600;
    int slamEffectTime = 160;

    public int getArmorCrunchChance() {
        return armorCrunchChance;
    }

    public int getSlamCooldown() {
        return slamCooldown;
    }

    public int getSlamEffectTime() {
        return slamEffectTime;
    }

    @Comment("Glimmerstep")
    int mountedBlindnessChance = 30;
    int unmountedBlindnessChance = 5;
    int blindnessTime = 5;
    int teleportWindUpTime = 60;
    int teleportDistance = 30;
    int teleportCooldown = 1200;

    public int getBlindnessTime() {
        return blindnessTime;
    }

    public int getTeleportCooldown() {
        return teleportCooldown;
    }

    public int getMountedBlindnessChance() {
        return mountedBlindnessChance;
    }

    public int getTeleportDistance() {
        return teleportDistance;
    }

    public int getTeleportWindUpTime() {
        return teleportWindUpTime;
    }

    public int getUnmountedBlindnessChance() {
        return unmountedBlindnessChance;
    }

    @Comment("Grandfrost")
    int chillingChance = 25;
    int chillingTime = 140;
    int blizzardRange = 5;
    float blizzardStrength = 3.5f;
    int blizzardEffectTime = 200;
    int blizzardCooldown = 500;

    public float getBlizzardStrength() {
        return blizzardStrength;
    }

    public int getBlizzardCooldown() {
        return blizzardCooldown;
    }

    public int getBlizzardEffectTime() {
        return blizzardEffectTime;
    }

    public int getBlizzardRange() {
        return blizzardRange;
    }

    public int getChillingChance() {
        return chillingChance;
    }

    public int getChillingTime() {
        return chillingTime;
    }

    @Comment("Great Slither")
    int slitherPoisonTime = 90;
    int slitherPoisonChance = 25;
    int slitherFangsRange = 7;
    float slitherFangsDamage = 4f;
    int slitherFangsVenomTime = 90;
    int slitherFangsCooldown = 200;

    public int getSlitherFangsRange() {
        return slitherFangsRange;
    }

    public float getSlitherFangsDamage() {
        return slitherFangsDamage;
    }

    public int getSlitherFangsCooldown() {
        return slitherFangsCooldown;
    }

    public int getSlitherFangsVenomTime() {
        return slitherFangsVenomTime;
    }

    public int getSlitherPoisonChance() {
        return slitherPoisonChance;
    }

    public int getSlitherPoisonTime() {
        return slitherPoisonTime;
    }

    @Comment("Lustrous Moxie")
    int radiantMarkChance = 20;
    int radiantTeleportCooldown = 400;
    int radiantTeleportStunTime = 30;
    int radiantTeleportRange = 20;
    int radiantTeleportAOERange = 5;
    float radiantTeleportAOEKnockback = 2f;
    float radiantTeleportTargetDamage = 15f;
    float radiantTeleportAOEDamage = 10f;

    public float getRadiantTeleportAOEDamage() {
        return radiantTeleportAOEDamage;
    }

    public float getRadiantTeleportAOEKnockback() {
        return radiantTeleportAOEKnockback;
    }

    public float getRadiantTeleportTargetDamage() {
        return radiantTeleportTargetDamage;
    }

    public int getRadiantMarkChance() {
        return radiantMarkChance;
    }

    public int getRadiantTeleportAOERange() {
        return radiantTeleportAOERange;
    }

    public int getRadiantTeleportCooldown() {
        return radiantTeleportCooldown;
    }

    public int getRadiantTeleportRange() {
        return radiantTeleportRange;
    }

    public int getRadiantTeleportStunTime() {
        return radiantTeleportStunTime;
    }

    @Comment("Matterbane")
    int levitationChance = 15;
    int levitationTime = 20;
    int riftCooldown = 1200;

    public int getLevitationChance() {
        return levitationChance;
    }

    public int getLevitationTime() {
        return levitationTime;
    }

    public int getRiftCooldown() {
        return riftCooldown;
    }

    @Comment("Mimi-cry")
    int purityHitsNeeded = 5;
    int twistedHitsNeeded = 4;
    int purityEffectTime = 250;
    int twistedEffectTime = 250;

    public int getPurityHitsNeeded() {
        return purityHitsNeeded;
    }

    public int getTwistedHitsNeeded() {
        return twistedHitsNeeded;
    }

    public int getPurityEffectTime() {
        return purityEffectTime;
    }

    public int getTwistedEffectTime() {
        return twistedEffectTime;
    }

    @Comment("Molten Flare")
    int eruptionChance = 20;
    int eruptionRadius = 4;
    int eruptionRadiusEmpowered = 7;
    float executingSliceSwingSpeedBonus = 0.6f;
    int executingSliceCooldown = 300;

    public int getExecutingSliceCooldown() {
        return executingSliceCooldown;
    }

    public float getExecutingSliceSwingSpeedBonus() {
        return executingSliceSwingSpeedBonus;
    }

    public int getEruptionChance() {
        return eruptionChance;
    }

    public int getEruptionRadius() {
        return eruptionRadius;
    }

    public int getEruptionRadiusEmpowered() {
        return eruptionRadiusEmpowered;
    }

    @Comment("Ruyi Jingu Bang")
    int ruyiCooldown = 700;

    public int getRuyiCooldown() {
        return ruyiCooldown;
    }

    @Comment("Scarab Roller")
    int rollCooldown = 200;
    float rollSpeed = 1f;
    float rollDamage = 5f;
    int rollMaxDuration = 300;
    int resistanceChance = 15;
    int resistanceTime = 100;

    public float getRollSpeed() {
        return rollSpeed;
    }

    public float getRollDamage() {
        return rollDamage;
    }

    public int getRollMaxDuration() {
        return rollMaxDuration;
    }

    public int getResistanceChance() {
        return resistanceChance;
    }

    public int getResistanceTime() {
        return resistanceTime;
    }

    public int getRollCooldown() {
        return rollCooldown;
    }

    @Comment("Serpentine Valor")
    int poisonBoltCooldown = 700;
    int poisonBoltLifespan = 10;
    float poisonBoltDamage = 2f;
    int poisonVenomTime = 160;
    float poisonedTargetDamageBuff = 4f;

    public float getPoisonBoltDamage() {
        return poisonBoltDamage;
    }

    public int getPoisonBoltCooldown() {
        return poisonBoltCooldown;
    }

    public float getPoisonedTargetDamageBuff() {
        return poisonedTargetDamageBuff;
    }

    public int getPoisonBoltLifespan() {
        return poisonBoltLifespan;
    }

    public int getPoisonVenomTime() {
        return poisonVenomTime;
    }

    @Comment("The Pan")
    int bonkChance = 30;
    float bonkStrength = 20f;

    public float getBonkStrength() {
        return bonkStrength;
    }

    public int getBonkChance() {
        return bonkChance;
    }

    @Comment("Idols")
    int spreadAuraChance = 15;
    float curseDamageMultiplier = 1.5f;
    int curseNegativeAdditionsTime = 100;
    float blessingHeal = 4f;
    int darksentCooldown = 800;
    int holylightCooldown = 800;

    public float getCurseDamageMultiplier() {
        return curseDamageMultiplier;
    }

    public int getDarksentCooldown() {
        return darksentCooldown;
    }

    public int getHolylightCooldown() {
        return holylightCooldown;
    }

    public float getBlessingHeal() {
        return blessingHeal;
    }

    public int getCurseNegativeAdditionsTime() {
        return curseNegativeAdditionsTime;
    }

    public int getSpreadAuraChance() {
        return spreadAuraChance;
    }

    @Comment("Smoldering Ruin")
    int ruinCooldown = 800;
    int witherChance = 25;
    int witherTime = 100;

    public int getRuinCooldown() {
        return ruinCooldown;
    }

    public int getWitherChance() {
        return witherChance;
    }

    public int getWitherTime() {
        return witherTime;
    }
    @Comment("Soul Foreseer")
    int foreseenTime = 160;
    int foreseenChance = 30;
    int judgeTeleportRange = 20;
    int judgeTeleportNegativeEffectTime = 80;

    public int getForeseenChance() {
        return foreseenChance;
    }

    public int getForeseenTime() {
        return foreseenTime;
    }

    public int getJudgeTeleportNegativeEffectTime() {
        return judgeTeleportNegativeEffectTime;
    }

    public int getJudgeTeleportRange() {
        return judgeTeleportRange;
    }

    @Comment("Stasis")
    int lightningCooldown = 700;
    int stagnationTime = 80;
    int stagnationChance = 20;
    float lightningDamage = 16;
    int lightningWindup = 60;
    int lightningRange = 4;

    public int getLightningCooldown() {
        return lightningCooldown;
    }

    public float getLightningDamage() {
        return lightningDamage;
    }

    public int getLightningRange() {
        return lightningRange;
    }

    public int getLightningWindup() {
        return lightningWindup;
    }

    public int getStagnationChance() {
        return stagnationChance;
    }

    public int getStagnationTime() {
        return stagnationTime;
    }

    @Comment("The Blood Harvester")
    int harvestCooldown = 1800;
    int harvestTime = 300;
    int harvestWitherTime = 80;
    float harvesterLifesteal = 0.1f;
    float harvesterHarvestLifesteal = 0.2f;

    public float getHarvesterHarvestLifesteal() {
        return harvesterHarvestLifesteal;
    }

    public float getHarvesterLifesteal() {
        return harvesterLifesteal;
    }

    public int getHarvestCooldown() {
        return harvestCooldown;
    }

    public int getHarvestTime() {
        return harvestTime;
    }

    public int getHarvestWitherTime() {
        return harvestWitherTime;
    }

    @Comment("The Vessel Breach")
    int rageCooldown = 1800;
    float vesselLifesteal = 0.1f;
    float vesselRageLifesteal = 0.28f;
    int rageTime = 200;
    float rageStartupDamagePercentage = 0.3f;

    public float getVesselLifesteal() {
        return vesselLifesteal;
    }

    public float getRageStartupDamagePercentage() {
        return rageStartupDamagePercentage;
    }

    public float getVesselRageLifesteal() {
        return vesselRageLifesteal;
    }

    public int getRageCooldown() {
        return rageCooldown;
    }

    public int getRageTime() {
        return rageTime;
    }

    @Comment("Tidebreaker")
    int insanityTeleportCooldown = 400;
    int insanityCloudChance = 25;
    int insanityCloudDuration = 300;
    int insanityTeleportMaxDistance = 15;
    int insanityTeleportMaxTime = 200;

    public int getInsanityCloudChance() {
        return insanityCloudChance;
    }

    public int getInsanityTeleportCooldown() {
        return insanityTeleportCooldown;
    }

    public int getInsanityCloudDuration() {
        return insanityCloudDuration;
    }

    public int getInsanityTeleportMaxDistance() {
        return insanityTeleportMaxDistance;
    }
    public int getInsanityTeleportMaxTime() {
        return insanityTeleportMaxTime;
    }

    @Comment("Timekeeper")
    int timekeeperBaseCooldown = 400;
    int nightActiveSlownessTime = 100;
    int dayActiveBlindnessTime = 100;
    int nightPassiveEffectTime = 70;
    int dayPassiveEffectTime = 70;
    float nightDamage = 2f;
    float dayDamage = 6f;
    int timekeeperOnHitChance = 20;

    public int getTimekeeperBaseCooldown() {
        return timekeeperBaseCooldown;
    }

    public int getDayActiveBlindnessTime() {
        return dayActiveBlindnessTime;
    }

    public int getNightActiveSlownessTime() {
        return nightActiveSlownessTime;
    }

    public int getNightPassiveEffectTime() {
        return nightPassiveEffectTime;
    }

    public int getDayPassiveEffectTime() {
        return dayPassiveEffectTime;
    }

    public int getTimekeeperOnHitChance() {
        return timekeeperOnHitChance;
    }

    public float getNightDamage() {
        return nightDamage;
    }

    public float getDayDamage() {
        return dayDamage;
    }

    @Comment("Viper's Call")
    int chakramCooldown = 1200;

    public int getChakramCooldown() {
        return chakramCooldown;
    }

}
