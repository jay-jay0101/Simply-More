package net.rosemarythyme.simplymore.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name="unique_effects")
public class UniqueEffectConfig implements ConfigData {
    @Comment("Black Pearl")
    int blackPearlCannonballCooldown = 180;
    int blackPearlPlunderChance = 10;

    public int getBlackPearlCannonballCooldown() {
        return blackPearlCannonballCooldown;
    }

    public int getBlackPearlPlunderChance() {
        return blackPearlPlunderChance;
    }

    @Comment("Blade of the Grotesque")
    int grotesqueSolidifySelfStunTime = 50;
    int grotesqueSolidifyAuraStunTime = 90;
    int grotesqueAuraRange = 4;
    int grotesqueSolidifySelfStunnedArmorBuff = 10;
    int grotesqueSolidifyAttackerStunnedArmorBuff = 10;
    int grotesqueSolidifyCooldown = 500;
    float grotesqueSelfSlow = -0.02f;
    float grotesqueMaxAuraWard = 5;

    public float getGrotesqueMaxAuraWard() {
        return grotesqueMaxAuraWard;
    }

    public float getGrotesqueSelfSlow() {
        return grotesqueSelfSlow;
    }

    public int getGrotesqueSolidifyAuraStunTime() {
        return grotesqueSolidifyAuraStunTime;
    }

    public int getGrotesqueAuraRange() {
        return grotesqueAuraRange;
    }

    public int getGrotesqueSolidifySelfStunTime() {
        return grotesqueSolidifySelfStunTime;
    }

    public int getGrotesqueSolidifyCooldown() {
        return grotesqueSolidifyCooldown;
    }

    public int getGrotesqueSolidifyAttackerStunnedArmorBuff() {
        return grotesqueSolidifyAttackerStunnedArmorBuff;
    }

    public int getGrotesqueSolidifySelfStunnedArmorBuff() {
        return grotesqueSolidifySelfStunnedArmorBuff;
    }

    @Comment("Boa's Fang")
    int boasFangSuffocationChance = 50;
    int boasFangSuffocationTime = 50;
    int boasFangSpitSpeedTime = 80;
    int boasFangSpitCooldown = 600;
    int boasFangSpitPoisonTime = 80;
    float boasFangSpitDamage = 5f;
    float boasFangSpitSelfKnockback = 2f;

    public float getBoasFangSpitSelfKnockback() {
        return boasFangSpitSelfKnockback;
    }

    public int getBoasFangSpitCooldown() {
        return boasFangSpitCooldown;
    }

    public int getBoasFangSpitPoisonTime() {
        return boasFangSpitPoisonTime;
    }

    public int getBoasFangSpitSpeedTime() {
        return boasFangSpitSpeedTime;
    }

    public float getBoasFangSpitDamage() {
        return boasFangSpitDamage;
    }

    public int getBoasFangSuffocationChance() {
        return boasFangSuffocationChance;
    }

    public int getBoasFangSuffocationTime() {
        return boasFangSuffocationTime;
    }

    @Comment("Earthshatter")
    int earthshatterArmorCrunchChance = 15;
    int earthshatterSlamCooldown = 600;
    int earthshatterSlamEffectTime = 160;

    public int getEarthshatterArmorCrunchChance() {
        return earthshatterArmorCrunchChance;
    }

    public int getEarthshatterSlamCooldown() {
        return earthshatterSlamCooldown;
    }

    public int getEarthshatterSlamEffectTime() {
        return earthshatterSlamEffectTime;
    }

    @Comment("Glimmerstep")
    int glimmerstepMaxStarlight = 10;
    int glimmerstepStarlightChance = 25;
    int glimmerstepStarlightMountedChance = 40;
    float glimmerstepExplosionDamagePerStarlight = 3.2f;
    int glimmerstepBlindTime = 40;
    int glimmerstepBaseSpeedFrequency = 200;
    int glimmerstepSpeedFrequencyPerStack = 10;
    int glimmerstepSpeedTime = 40;
    int glimmerstepExplosionRange = 5;
    int glimmerstepExplosionCharge = 60;
    int glimmerstepExplosionCooldown = 800;
    int glimmerstepSelfAndAllyDamagePercentage = 80;
    int glimmerstepStarlightTime = 800;

    public float getGlimmerstepExplosionDamagePerStarlight() {
        return glimmerstepExplosionDamagePerStarlight;
    }

    public int getGlimmerstepStarlightTime() {
        return glimmerstepStarlightTime;
    }

    public int getGlimmerstepSelfAndAllyDamagePercentage() {
        return glimmerstepSelfAndAllyDamagePercentage;
    }

    public int getGlimmerstepBaseSpeedFrequency() {
        return glimmerstepBaseSpeedFrequency;
    }

    public int getGlimmerstepBlindTime() {
        return glimmerstepBlindTime;
    }

    public int getGlimmerstepExplosionCharge() {
        return glimmerstepExplosionCharge;
    }

    public int getGlimmerstepExplosionCooldown() {
        return glimmerstepExplosionCooldown;
    }

    public int getGlimmerstepExplosionRange() {
        return glimmerstepExplosionRange;
    }

    public int getGlimmerstepMaxStarlight() {
        return glimmerstepMaxStarlight;
    }

    public int getGlimmerstepSpeedFrequencyPerStack() {
        return glimmerstepSpeedFrequencyPerStack;
    }

    public int getGlimmerstepSpeedTime() {
        return glimmerstepSpeedTime;
    }

    public int getGlimmerstepStarlightChance() {
        return glimmerstepStarlightChance;
    }

    public int getGlimmerstepStarlightMountedChance() {
        return glimmerstepStarlightMountedChance;
    }

    @Comment("Grandfrost")
    int grandfrostChillingChance = 25;
    int grandfrostChillingTime = 140;
    int grandfrostBlizzardRange = 5;
    float grandfrostBlizzardStrength = 3.5f;
    int grandfrostBlizzardEffectTime = 200;
    int grandfrostBlizzardCooldown = 500;

    public float getGrandfrostBlizzardStrength() {
        return grandfrostBlizzardStrength;
    }

    public int getGrandfrostBlizzardCooldown() {
        return grandfrostBlizzardCooldown;
    }

    public int getGrandfrostBlizzardEffectTime() {
        return grandfrostBlizzardEffectTime;
    }

    public int getGrandfrostBlizzardRange() {
        return grandfrostBlizzardRange;
    }

    public int getGrandfrostChillingChance() {
        return grandfrostChillingChance;
    }

    public int getGrandfrostChillingTime() {
        return grandfrostChillingTime;
    }

    @Comment("Great Slither")
    int slitherPoisonTime = 90;
    int slitherVenomTime = 40;
    int slitherPoisonChance = 25;
    int slitherFangsRange = 7;
    float slitherFangsDamage = 4f;
    int slitherFangsVenomTime = 90;
    int slitherFangsSlowTime = 35;
    int slitherFangsCooldown = 300;

    public int getSlitherFangsRange() {
        return slitherFangsRange;
    }

    public float getSlitherFangsDamage() {
        return slitherFangsDamage;
    }

    public int getSlitherVenomTime() {
        return slitherVenomTime;
    }

    public int getSlitherFangsSlowTime() {
        return slitherFangsSlowTime;
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
    int lustrousRadiantMarkChance = 20;
    int lustrousRadiantTeleportCooldown = 400;
    int lustrousRadiantTeleportStunTime = 30;
    int lustrousRadiantTeleportRange = 20;
    int lustrousRadiantTeleportAOERange = 5;
    float lustrousRadiantTeleportAOEKnockback = 2f;
    float lustrousRadiantTeleportTargetDamage = 15f;
    float lustrousRadiantTeleportAOEDamage = 10f;

    public float getLustrousRadiantTeleportAOEDamage() {
        return lustrousRadiantTeleportAOEDamage;
    }

    public float getLustrousRadiantTeleportAOEKnockback() {
        return lustrousRadiantTeleportAOEKnockback;
    }


    public float getLustrousRadiantTeleportTargetDamage() {
        return lustrousRadiantTeleportTargetDamage;
    }

    public int getLustrousRadiantMarkChance() {
        return lustrousRadiantMarkChance;
    }

    public int getLustrousRadiantTeleportAOERange() {
        return lustrousRadiantTeleportAOERange;
    }

    public int getLustrousRadiantTeleportCooldown() {
        return lustrousRadiantTeleportCooldown;
    }

    public int getLustrousRadiantTeleportRange() {
        return lustrousRadiantTeleportRange;
    }

    public int getLustrousRadiantTeleportStunTime() {
        return lustrousRadiantTeleportStunTime;
    }

    @Comment("Matterbane")
    int matterbaneBeamChance = 20;
    int matterbaneBeamRange = 8;
    int matterbaneBeamDamage = 6;
    int matterbaneRiftCooldown = 1200;

    public int getMatterbaneBeamChance() {
        return matterbaneBeamChance;
    }

    public int getMatterbaneBeamDamage() {
        return matterbaneBeamDamage;
    }

    public int getMatterbaneBeamRange() {
        return matterbaneBeamRange;
    }

    public int getMatterbaneRiftCooldown() {
        return matterbaneRiftCooldown;
    }

    @Comment("Mimi-cry")
    int mimicryTypeCooldown = 400;
    int mimicryCooldownBetweenTypes = 60;
    int mimicryWindup = 10;
    int mimicryDamageModifierFromRunic = 1;

    public int getMimicryDamageModifierFromRunic() {
        return mimicryDamageModifierFromRunic;
    }

    public int getMimicryCooldownBetweenTypes() {
        return mimicryCooldownBetweenTypes;
    }

    public int getMimicryTypeCooldown() {
        return mimicryTypeCooldown;
    }

    public int getMimicryWindup() {
        return mimicryWindup;
    }

    @Comment("Molten Flare")
    int moltenFlareEruptionChance = 20;
    int moltenFlareEruptionRadius = 4;
    int moltenFlareEruptionRadiusEmpowered = 7;
    float moltenFlareExecutingSliceSwingSpeedBonus = 0.6f;
    int moltenFlareExecutingSliceCooldown = 300;

    public int getMoltenFlareExecutingSliceCooldown() {
        return moltenFlareExecutingSliceCooldown;
    }

    public float getMoltenFlareExecutingSliceSwingSpeedBonus() {
        return moltenFlareExecutingSliceSwingSpeedBonus;
    }

    public int getMoltenFlareEruptionChance() {
        return moltenFlareEruptionChance;
    }

    public int getMoltenFlareEruptionRadius() {
        return moltenFlareEruptionRadius;
    }

    public int getMoltenFlareEruptionRadiusEmpowered() {
        return moltenFlareEruptionRadiusEmpowered;
    }

    @Comment("Ruyi Jingu Bang")
    int ruyiJinguBangCooldown = 700;

    public int getRuyiJinguBangCooldown() {
        return ruyiJinguBangCooldown;
    }

    @Comment("Myrmedge")
    int myrmedgeMaxDamagePercentageBuff = 40;
    int myrmedgeMaxGrabTime = 100;
    int myrmedgeCooldown = 300;
    float myrmedgeThrowStrength = 1.6f;

    public int getMyrmedgeCooldown() {
        return myrmedgeCooldown;
    }

    public float getMyrmedgeThrowStrength() {
        return myrmedgeThrowStrength;
    }

    public int getMyrmedgeMaxDamagePercentageBuff() {
        return myrmedgeMaxDamagePercentageBuff;
    }

    public int getMyrmedgeMaxGrabTime() {
        return myrmedgeMaxGrabTime;
    }

    @Comment("Serpentine Valor")
    int serpentinePoisonBoltCooldown = 700;
    int serpentinePoisonBoltLifespan = 10;
    float serpentinePoisonBoltDamage = 2f;
    int serpentinePoisonVenomTime = 160;
    float serpentinePoisonedTargetDamageBuff = 4f;

    public float getSerpentinePoisonBoltDamage() {
        return serpentinePoisonBoltDamage;
    }

    public int getSerpentinePoisonBoltCooldown() {
        return serpentinePoisonBoltCooldown;
    }

    public float getSerpentinePoisonedTargetDamageBuff() {
        return serpentinePoisonedTargetDamageBuff;
    }

    public int getSerpentinePoisonBoltLifespan() {
        return serpentinePoisonBoltLifespan;
    }

    public int getSerpentinePoisonVenomTime() {
        return serpentinePoisonVenomTime;
    }

    @Comment("The Pan")
    int panBonkChance = 30;
    float panBonkStrength = 20f;

    public float getPanBonkStrength() {
        return panBonkStrength;
    }

    public int getPanBonkChance() {
        return panBonkChance;
    }

    @Comment("Idols")
    int idolSpreadAuraChance = 15;
    float idolCurseDamageMultiplier = 1.5f;
    int idolCurseNegativeAdditionsTime = 100;
    float idolBlessingHeal = 4f;
    int darksentCooldown = 800;
    int holylightCooldown = 800;


    public float getIdolCurseDamageMultiplier() {
        return idolCurseDamageMultiplier;
    }

    public int getDarksentCooldown() {
        return darksentCooldown;
    }

    public int getHolylightCooldown() {
        return holylightCooldown;
    }

    public float getIdolBlessingHeal() {
        return idolBlessingHeal;
    }

    public int getIdolCurseNegativeAdditionsTime() {
        return idolCurseNegativeAdditionsTime;
    }

    public int getIdolSpreadAuraChance() {
        return idolSpreadAuraChance;
    }

    @Comment("Smoldering Ruin")
    int smoulderingCooldown = 800;
    int smoulderingWitherChance = 25;
    int smoulderingWitherTime = 100;

    public int getSmoulderingCooldown() {
        return smoulderingCooldown;
    }

    public int getSmoulderingWitherChance() {
        return smoulderingWitherChance;
    }

    public int getSmoulderingWitherTime() {
        return smoulderingWitherTime;
    }
    @Comment("Soul Foreseer")
    int foreseerForseenTime = 160;
    int foreseerForeseenChance = 30;
    int foreseerJudgeTeleportRange = 20;
    int foreseerJudgeTeleportNegativeEffectTime = 80;

    public int getForeseerForeseenChance() {
        return foreseerForeseenChance;
    }

    public int getForeseerForseenTime() {
        return foreseerForseenTime;
    }

    public int getForeseerJudgeTeleportNegativeEffectTime() {
        return foreseerJudgeTeleportNegativeEffectTime;
    }

    public int getForeseerJudgeTeleportRange() {
        return foreseerJudgeTeleportRange;
    }

    @Comment("Stasis")
    int stasisLightningCooldown = 700;
    int stasisStagnationTime = 80;
    int stasisStagnationChance = 20;
    float stasisLightningDamage = 16;
    int stasisLightningWindup = 60;
    int stasisLightningRange = 4;

    public int getStasisLightningCooldown() {
        return stasisLightningCooldown;
    }

    public float getStasisLightningDamage() {
        return stasisLightningDamage;
    }

    public int getStasisLightningRange() {
        return stasisLightningRange;
    }

    public int getStasisLightningWindup() {
        return stasisLightningWindup;
    }

    public int getStasisStagnationChance() {
        return stasisStagnationChance;
    }

    public int getStasisStagnationTime() {
        return stasisStagnationTime;
    }

    @Comment("The Blood Harvester")
    int harvesterCooldown = 1800;
    int harvesterHarvestTime = 300;
    int harvesterBleedTime = 150;
    float harvesterLifesteal = 0.1f;
    float harvesterHarvestLifesteal = 0.2f;

    public float getHarvesterHarvestLifesteal() {
        return harvesterHarvestLifesteal;
    }

    public float getHarvesterLifesteal() {
        return harvesterLifesteal;
    }

    public int getHarvesterCooldown() {
        return harvesterCooldown;
    }

    public int getHarvesterHarvestTime() {
        return harvesterHarvestTime;
    }

    public int getHarvesterBleedTime() {
        return harvesterBleedTime;
    }

    @Comment("The Vessel Breach")
    int vesselRageCooldown = 1800;
    int vesselRageBleedTime = 100;
    float vesselLifesteal = 0.1f;
    float vesselRageLifesteal = 0.16f;
    int vesselRageTime = 200;
    float vesselRageStartupDamagePercentage = 0.3f;

    public float getVesselLifesteal() {
        return vesselLifesteal;
    }

    public int getVesselRageBleedTime() {
        return vesselRageBleedTime;
    }

    public float getVesselRageStartupDamagePercentage() {
        return vesselRageStartupDamagePercentage;
    }

    public float getVesselRageLifesteal() {
        return vesselRageLifesteal;
    }

    public int getVesselRageCooldown() {
        return vesselRageCooldown;
    }

    public int getVesselRageTime() {
        return vesselRageTime;
    }

    @Comment("Tidebreaker")
    int tidebreakerInsanityTeleportCooldown = 400;
    int tidebreakerInsanityCloudChance = 25;
    int tidebreakerInsanityCloudDuration = 300;
    int tidebreakerInsanityTeleportMaxDistance = 15;
    int tidebreakerInsanityTeleportMaxTime = 200;

    public int getTidebreakerInsanityCloudChance() {
        return tidebreakerInsanityCloudChance;
    }

    public int getTidebreakerInsanityTeleportCooldown() {
        return tidebreakerInsanityTeleportCooldown;
    }

    public int getTidebreakerInsanityCloudDuration() {
        return tidebreakerInsanityCloudDuration;
    }

    public int getTidebreakerInsanityTeleportMaxDistance() {
        return tidebreakerInsanityTeleportMaxDistance;
    }
    public int getTidebreakerInsanityTeleportMaxTime() {
        return tidebreakerInsanityTeleportMaxTime;
    }

    @Comment("Timekeeper")
    int timekeeperBaseCooldown = 400;
    int timekeeperNightActiveSlownessTime = 100;
    int timekeeperDayActiveBlindnessTime = 100;
    int timekeeperNightPassiveEffectTime = 70;
    int timekeeperDayPassiveEffectTime = 70;
    float timekeeperNightActiveDamage = 2f;
    float timekeeperDayActiveDamage = 6f;
    int timekeeperOnHitChance = 20;

    public int getTimekeeperBaseCooldown() {
        return timekeeperBaseCooldown;
    }

    public int getTimekeeperDayActiveBlindnessTime() {
        return timekeeperDayActiveBlindnessTime;
    }

    public int getTimekeeperNightActiveSlownessTime() {
        return timekeeperNightActiveSlownessTime;
    }

    public int getTimekeeperNightPassiveEffectTime() {
        return timekeeperNightPassiveEffectTime;
    }

    public int getTimekeeperDayPassiveEffectTime() {
        return timekeeperDayPassiveEffectTime;
    }

    public int getTimekeeperOnHitChance() {
        return timekeeperOnHitChance;
    }

    public float getTimekeeperNightActiveDamage() {
        return timekeeperNightActiveDamage;
    }

    public float getTimekeeperDayActiveDamage() {
        return timekeeperDayActiveDamage;
    }

    @Comment("Viper's Call")
    int vipersCallCooldown = 1200;

    public int getVipersCallCooldown() {
        return vipersCallCooldown;
    }

    @Comment("Brassturn")
    int brassturnJetChance = 15;
    int brassturnSparkStunDuration = 15;
    int brassturnScrapeTime = 5;
    int brassturnSparkChance = 25;

    public int getBrassturnScrapeTime() {
        return brassturnScrapeTime;
    }
    public int getBrassturnSparkChance() {
        return brassturnSparkChance;
    }

    public int getBrassturnJetChance() {
        return brassturnJetChance;
    }
    public int getBrassturnSparkStunDuration() {
        return brassturnSparkStunDuration;
    }

    @Comment("Cindergorge")
    int cindergorgeThornsChance = 40;
    float cindergorgeThornsDamage = 3f;
    float cindergorgeThornsFireDamage = 6f;
    int cindergorgeMaxDuration = 200;
    int cindergorgeFireRange = 5;
    float cindergorgeFireDamage = 5f;
    int cindergorgeMaxCooldown = 600;

    public int getCindergorgeThornsChance() {
        return cindergorgeThornsChance;
    }
    public float getCindergorgeThornsFireDamage() {
        return cindergorgeThornsFireDamage;
    }
    public float getCindergorgeThornsDamage() {
        return cindergorgeThornsDamage;
    }
    public int getCindergorgeMaxDuration() {
        return cindergorgeMaxDuration;
    }
    public int getCindergorgeFireRange() {
        return cindergorgeFireRange;
    }
    public float getCindergorgeFireDamage() {
        return cindergorgeFireDamage;
    }
    public int getCindergorgeMaxCooldown() {
        return cindergorgeMaxCooldown;
    }

    @Comment("Death's Eyrie")
    int deathsEyrieBleedChance = 25;
    int deathsEyrieBaseBleedTime = 80;
    int deathsEyrieCrowAdditionalBleedTime = 20;
    int deathsEyrieCooldown = 550;
    int deathsEyrieCrowAttackBleedTime = 120;
    int deathsEyrieCrowAttackBlindTime = 20;
    int deathsEyrieCrowAttackTimePerCrow = 30;
    float deathsEyrieCrowAttackDamage = 2.3f;
    float deathsEyrieCrowAttackHeal = 0.4f;

    public int getDeathsEyrieBleedChance() {
        return deathsEyrieBleedChance;
    }

    public float getDeathsEyrieCrowAttackDamage() {
        return deathsEyrieCrowAttackDamage;
    }

    public float getDeathsEyrieCrowAttackHeal() {
        return deathsEyrieCrowAttackHeal;
    }

    public int getDeathsEyrieCrowAttackBleedTime() {
        return deathsEyrieCrowAttackBleedTime;
    }

    public int getDeathsEyrieCrowAttackBlindTime() {
        return deathsEyrieCrowAttackBlindTime;
    }

    public int getDeathsEyrieCrowAttackTimePerCrow() {
        return deathsEyrieCrowAttackTimePerCrow;
    }

    public int getDeathsEyrieCooldown() {
        return deathsEyrieCooldown;
    }

    public int getDeathsEyrieBaseBleedTime() {
        return deathsEyrieBaseBleedTime;
    }

    public int getDeathsEyrieCrowAdditionalBleedTime() {
        return deathsEyrieCrowAdditionalBleedTime;
    }

    @Comment("Perforiscus")
    int perforiscusBloomTime = 500;
    int perforiscusCooldown = 800;

    public int getPerforiscusBloomTime() {
        return perforiscusBloomTime;
    }

    public int getPerforiscusCooldown() {
        return perforiscusCooldown;
    }

    @Comment("Revvengine")
    int revvenginePhase1Cooldown = 240;
    int revvenginePhase2Cooldown = 360;
    int revvenginePhase3Cooldown = 440;
    int revvenginePhase1MinimumCharge = 10;
    int revvenginePhase2MinimumCharge = 40;
    int revvenginePhase3MinimumCharge = 80;
    int revvengineBleedChance = 20;
    int revvengineBleedTime = 80;
    int revvenginePhase1Damage = 7;
    int revvenginePhase2Damage = 12;
    int revvenginePhase2EffectTime = 120;
    int revvenginePhase3Damage = 15;
    int revvenginePhase3EffectTime = 100;
    int revvenginePhase3ExplosionWaitTime = 80;
    int revvenginePhase3ExplosionDamage = 10;
    int revvengineMaxDamagePercentageBuff = 60;
    int revvengineMaxRangePercentageBuff = 50;

    public int getRevvenginePhase1Cooldown() {
        return revvenginePhase1Cooldown;
    }

    public int getRevvenginePhase2Cooldown() {
        return revvenginePhase2Cooldown;
    }

    public int getRevvenginePhase3Cooldown() {
        return revvenginePhase3Cooldown;
    }

    public int getRevvenginePhase1MinimumCharge() {
        return revvenginePhase1MinimumCharge;
    }

    public int getRevvenginePhase2MinimumCharge() {
        return revvenginePhase2MinimumCharge;
    }

    public int getRevvenginePhase3MinimumCharge() {
        return revvenginePhase3MinimumCharge;
    }

    public int getRevvengineBleedChance() {
        return revvengineBleedChance;
    }

    public int getRevvengineBleedTime() {
        return revvengineBleedTime;
    }

    public int getRevvenginePhase1Damage() {
        return revvenginePhase1Damage;
    }

    public int getRevvenginePhase2Damage() {
        return revvenginePhase2Damage;
    }

    public int getRevvenginePhase2EffectTime() {
        return revvenginePhase2EffectTime;
    }

    public int getRevvenginePhase3Damage() {
        return revvenginePhase3Damage;
    }

    public int getRevvenginePhase3EffectTime() {
        return revvenginePhase3EffectTime;
    }

    public int getRevvenginePhase3ExplosionWaitTime() {
        return revvenginePhase3ExplosionWaitTime;
    }

    public int getRevvenginePhase3ExplosionDamage() {
        return revvenginePhase3ExplosionDamage;
    }

    public int getRevvengineMaxDamagePercentageBuff() {
        return revvengineMaxDamagePercentageBuff;
    }

    public int getRevvengineMaxRangePercentageBuff() {
        return revvengineMaxRangePercentageBuff;
    }

    @Comment("Exedrill")
    int exedrillCooldown = 250;
    int exedrillTrembleChance = 15;
    int exedrillTrembleEffectTime = 80;
    int exedrillTrembleMountedChance = 30;
    int exedrillTrembleHeatAmount = 6;
    int exedrillHitHeatAmount = 1;
    int exedrillMaxHeat = 25;
    float exedrillExplosionDamage = 12f;
    float exedrillRockDamage = 6f;
    float exedrillRockSpeed = 0.4f;
    int exedrillRockStunTime = 14;
    int exedrillRocksAmount = 5;
    float exedrillEarthquakePushStrength = 1.8f;

    public int getExedrillTrembleEffectTime() {
        return exedrillTrembleEffectTime;
    }

    public float getExedrillRockSpeed() {
        return exedrillRockSpeed;
    }

    public int getExedrillRocksAmount() {
        return exedrillRocksAmount;
    }

    public float getExedrillRockDamage() {
        return exedrillRockDamage;
    }

    public int getExedrillRockStunTime() {
        return exedrillRockStunTime;
    }

    public int getExedrillCooldown() {
        return exedrillCooldown;
    }
    public int getExedrillTrembleChance() {
        return exedrillTrembleChance;
    }

    public int getExedrillTrembleMountedChance() {
        return exedrillTrembleMountedChance;
    }

    public int getExedrillTrembleHeatAmount() {
        return exedrillTrembleHeatAmount;
    }

    public int getExedrillHitHeatAmount() {
        return exedrillHitHeatAmount;
    }

    public int getExedrillMaxHeat() {
        return exedrillMaxHeat;
    }

    public float getExedrillExplosionDamage() {
        return exedrillExplosionDamage;
    }

    public float getExedrillEarthquakePushStrength() {
        return exedrillEarthquakePushStrength;
    }

    @Comment("Culterex")
    int culterexCooldown = 400;
    int culterexBaseDuration = 180;
    int culterexExtraDuration = 60;
    int culterexExtraDurationChance = 35;
    int culterexDurationPerEffectLevel = 60;
    int culterexRightClickRange = 30;

    public int getCulterexCooldown() {
        return culterexCooldown;
    }

    public int getCulterexBaseDuration() {
        return culterexBaseDuration;
    }

    public int getCulterexExtraDuration() {
        return culterexExtraDuration;
    }

    public int getCulterexExtraDurationChance() {
        return culterexExtraDurationChance;
    }

    public int getCulterexDurationPerEffectLevel() {
        return culterexDurationPerEffectLevel;
    }

    public int getCulterexRightClickRange() {
        return culterexRightClickRange;
    }

}
