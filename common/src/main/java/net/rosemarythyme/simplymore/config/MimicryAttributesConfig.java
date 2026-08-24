package net.rosemarythyme.simplymore.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name="mimicry_attributes")
public class MimicryAttributesConfig implements ConfigData {
    @Comment("For Generic Options, please look in the unique_effects.json5\n\nDamage")
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
    int longswordDamageModifier = 0;
    int twinbladeDamageModifier = 0;
    int rapierDamageModifier = -1;
    int katanaDamageModifier = 0;
    int saiDamageModifier = -3;
    int spearDamageModifier = 0;
    int glaiveDamageModifier = 0;
    int warglaiveDamageModifier = 0;
    int cutlassDamageModifier = 0;
    int claymoreDamageModifier = 2;
    int greathammerDamageModifier = 4;
    int greataxeDamageModifier = 3;
    int chakramDamageModifier = -1;
    int scytheDamageModifier = 1;
    int halberdDamageModifier = 3;

    @Comment("Swing Speeds")
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
    double longswordSwingSpeed = 1.6;
    double twinbladeSwingSpeed = 2;
    double rapierSwingSpeed = 2.2;
    double katanaSwingSpeed = 2;
    double saiSwingSpeed = 2.5;
    double spearSwingSpeed = 1.3;
    double glaiveSwingSpeed = 1.4;
    double warglaiveSwingSpeed = 1.8;
    double cutlassSwingSpeed = 2;
    double claymoreSwingSpeed = 1.2;
    double greathammerSwingSpeed = 0.8;
    double greataxeSwingSpeed = 0.9;
    double chakramSwingSpeed = 1;
    double scytheSwingSpeed = 1.3;
    double halberdSwingSpeed = 1.2;

    public double getBackhandBladeSwingSpeed() {
        return backhandBladeSwingSpeed - 4.0;
    }

    public double getGrandswordSwingSpeed() {
        return grandswordSwingSpeed - 4.0;
    }

    public double getGreatKatanaSwingSpeed() {
        return greatKatanaSwingSpeed - 4.0;
    }

    public double getKhopeshSwingSpeed() {
        return khopeshSwingSpeed - 4.0;
    }

    public double getLanceSwingSpeed() {
        return lanceSwingSpeed - 4.0;
    }

    public double getDaggerSwingSpeed() {
        return daggerSwingSpeed - 4.0;
    }

    public double getQuarterstaffSwingSpeed() {
        return quarterstaffSwingSpeed - 4.0;
    }

    public double getPernachSwingSpeed() {
        return pernachSwingSpeed - 4.0;
    }

    public double getGreatSpearSwingSpeed() {
        return greatSpearSwingSpeed - 4.0;
    }

    public double getDeerHornsSwingSpeed() {
        return deerHornsSwingSpeed - 4.0;
    }

    public double getLongswordSwingSpeed() {
        return longswordSwingSpeed - 4.0;
    }

    public double getTwinbladeSwingSpeed() {
        return twinbladeSwingSpeed - 4.0;
    }

    public double getRapierSwingSpeed() {
        return rapierSwingSpeed - 4.0;
    }

    public double getKatanaSwingSpeed() {
        return katanaSwingSpeed - 4.0;
    }

    public double getSaiSwingSpeed() {
        return saiSwingSpeed - 4.0;
    }

    public double getSpearSwingSpeed() {
        return spearSwingSpeed - 4.0;
    }

    public double getGlaiveSwingSpeed() {
        return glaiveSwingSpeed - 4.0;
    }

    public double getWarglaiveSwingSpeed() {
        return warglaiveSwingSpeed - 4.0;
    }

    public double getCutlassSwingSpeed() {
        return cutlassSwingSpeed - 4.0;
    }

    public double getClaymoreSwingSpeed() {
        return claymoreSwingSpeed - 4.0;
    }

    public double getGreathammerSwingSpeed() {
        return greathammerSwingSpeed - 4.0;
    }

    public double getGreataxeSwingSpeed() {
        return greataxeSwingSpeed - 4.0;
    }

    public double getChakramSwingSpeed() {
        return chakramSwingSpeed - 4.0;
    }

    public double getScytheSwingSpeed() {
        return scytheSwingSpeed - 4.0;
    }

    public double getHalberdSwingSpeed() {
        return halberdSwingSpeed - 4.0;
    }

    public int getBackhandBladeDamageModifier() {
        return backhandBladeDamageModifier;
    }

    public int getGrandswordDamageModifier() {
        return grandswordDamageModifier;
    }

    public int getGreatKatanaDamageModifier() {
        return greatKatanaDamageModifier;
    }

    public int getKhopeshDamageModifier() {
        return khopeshDamageModifier;
    }

    public int getLanceDamageModifier() {
        return lanceDamageModifier;
    }

    public int getDaggerDamageModifier() {
        return daggerDamageModifier;
    }

    public int getQuarterstaffDamageModifier() {
        return quarterstaffDamageModifier;
    }

    public int getPernachDamageModifier() {
        return pernachDamageModifier;
    }

    public int getGreatSpearDamageModifier() {
        return greatSpearDamageModifier;
    }

    public int getDeerHornsDamageModifier() {
        return deerHornsDamageModifier;
    }

    public int getLongswordDamageModifier() {
        return longswordDamageModifier;
    }

    public int getTwinbladeDamageModifier() {
        return twinbladeDamageModifier;
    }

    public int getRapierDamageModifier() {
        return rapierDamageModifier;
    }

    public int getKatanaDamageModifier() {
        return katanaDamageModifier;
    }

    public int getSaiDamageModifier() {
        return saiDamageModifier;
    }

    public int getSpearDamageModifier() {
        return spearDamageModifier;
    }

    public int getGlaiveDamageModifier() {
        return glaiveDamageModifier;
    }

    public int getWarglaiveDamageModifier() {
        return warglaiveDamageModifier;
    }

    public int getCutlassDamageModifier() {
        return cutlassDamageModifier;
    }

    public int getClaymoreDamageModifier() {
        return claymoreDamageModifier;
    }

    public int getGreathammerDamageModifier() {
        return greathammerDamageModifier;
    }

    public int getGreataxeDamageModifier() {
        return greataxeDamageModifier;
    }

    public int getChakramDamageModifier() {
        return chakramDamageModifier;
    }

    public int getScytheDamageModifier() {
        return scytheDamageModifier;
    }

    public int getHalberdDamageModifier() {
        return halberdDamageModifier;
    }

    @Comment("Longsword")
    boolean disableLongswordVariant = false;
    float longswordBaseDamage = 6f;
    float longswordExtraDamagePerTarget = 2f;
    float longswordKnockback = 1.2f;

    public float getLongswordBaseDamage() {
        return longswordBaseDamage;
    }

    public float getLongswordKnockback() {
        return longswordKnockback;
    }

    public float getLongswordExtraDamagePerTarget() {
        return longswordExtraDamagePerTarget;
    }

    public boolean isDisableLongswordVariant() {
        return disableLongswordVariant;
    }

    @Comment("Twinblade")
    boolean disableTwinbladeVariant = false;
    float twinbladeFirstDamage = 5f;
    float twinbladeSecondDamage = 8f;
    int twinbladeEffectTime = 80;
    float twinbladeKnockback = 1.5f;

    public boolean isDisableTwinbladeVariant() {
        return disableTwinbladeVariant;
    }

    public float getTwinbladeFirstDamage() {
        return twinbladeFirstDamage;
    }

    public float getTwinbladeSecondDamage() {
        return twinbladeSecondDamage;
    }

    public int getTwinbladeEffectTime() {
        return twinbladeEffectTime;
    }

    public float getTwinbladeKnockback() {
        return twinbladeKnockback;
    }

    @Comment("Rapier")
    boolean disableRapierVariant = false;
    float rapierDamage = 3f;
    int rapierEffectTime = 60;

    public float getRapierDamage() {
        return rapierDamage;
    }

    public int getRapierEffectTime() {
        return rapierEffectTime;
    }

    public boolean isDisableRapierVariant() {
        return disableRapierVariant;
    }

    @Comment("Sai")
    boolean disableSaiVariant = false;
    float saiDamage = 5.5f;
    int saiEffectTime = 80;

    public float getSaiDamage() {
        return saiDamage;
    }

    public int getSaiEffectTime() {
        return saiEffectTime;
    }

    public boolean isDisableSaiVariant() {
        return disableSaiVariant;
    }

    @Comment("Cutlass")
    boolean disableCutlassVariant = false;
    float cutlassDamage = 6f;
    float cutlassPull = 0.8f;

    public float getCutlassDamage() {
        return cutlassDamage;
    }

    public float getCutlassPull() {
        return cutlassPull;
    }

    public boolean isDisableCutlassVariant() {
        return disableCutlassVariant;
    }

    @Comment("Chakram")
    boolean disableChakramVariant = false;
    int chakramDuration = 60;

    public int getChakramDuration() {
        return chakramDuration;
    }

    public boolean isDisableChakramVariant() {
        return disableChakramVariant;
    }

    @Comment("Warglaive")
    boolean disableWarglaiveVariant = false;
    float warglaiveFirstDamage = 5f;
    float warglaiveSecondDamage = 6.75f;
    int warglaiveEffectTime = 80;

    public float getWarglaiveFirstDamage() {
        return warglaiveFirstDamage;
    }

    public float getWarglaiveSecondDamage() {
        return warglaiveSecondDamage;
    }

    public int getWarglaiveEffectTime() {
        return warglaiveEffectTime;
    }

    public boolean isDisableWarglaiveVariant() {
        return disableWarglaiveVariant;
    }

    @Comment("Spear")
    boolean disableSpearVariant = false;
    float spearDamage = 8f;
    float spearFinalStabDamage = 10f;
    int spearEffectTime = 80;

    public float getSpearDamage() {
        return spearDamage;
    }

    public float getSpearFinalStabDamage() {
        return spearFinalStabDamage;
    }

    public int getSpearEffectTime() {
        return spearEffectTime;
    }

    public boolean isDisableSpearVariant() {
        return disableSpearVariant;
    }

    @Comment("Glaive")
    boolean disableGlaiveVariant = false;
    float glaiveDamage = 6f;

    public boolean isDisableGlaiveVariant() {
        return disableGlaiveVariant;
    }

    public float getGlaiveDamage() {
        return glaiveDamage;
    }

    @Comment("Claymore")
    boolean disableClaymoreVariant = false;
    float claymoreDamage = 8f;
    int claymoreEffectTime = 120;
    float claymoreKnockback = 1.6f;

    public boolean isDisableClaymoreVariant() {
        return disableClaymoreVariant;
    }

    public float getClaymoreDamage() {
        return claymoreDamage;
    }

    public float getClaymoreKnockback() {
        return claymoreKnockback;
    }

    public int getClaymoreEffectTime() {
        return claymoreEffectTime;
    }

    @Comment("Scythe")
    boolean disableScytheVariant = false;
    float scytheDamage = 10f;
    int scytheEffectTime = 150;

    public float getScytheDamage() {
        return scytheDamage;
    }

    public int getScytheEffectTime() {
        return scytheEffectTime;
    }

    public boolean isDisableScytheVariant() {
        return disableScytheVariant;
    }

    @Comment("Greataxe")
    boolean disableGreataxeVariant = false;
    float greataxeDamage = 9f;
    float greataxeKnockback = 2f;

    public float getGreataxeDamage() {
        return greataxeDamage;
    }

    public float getGreataxeKnockback() {
        return greataxeKnockback;
    }

    public boolean isDisableGreataxeVariant() {
        return disableGreataxeVariant;
    }

    @Comment("Greathammer")
    boolean disableGreathammerVariant = false;
    float greathammerDamage = 7.5f;
    int greathammerEffectTime = 100;

    public float getGreathammerDamage() {
        return greathammerDamage;
    }

    public int getGreathammerEffectTime() {
        return greathammerEffectTime;
    }

    public boolean isDisableGreathammerVariant() {
        return disableGreathammerVariant;
    }

    @Comment("Dagger")
    boolean disableDaggerVariant = false;
    float daggerDamage = 6f;
    int daggerEffectTime = 120;

    public float getDaggerDamage() {
        return daggerDamage;
    }

    public int getDaggerEffectTime() {
        return daggerEffectTime;
    }

    public boolean isDisableDaggerVariant() {
        return disableDaggerVariant;
    }

    @Comment("Khopesh")
    boolean disableKhopeshVariant = false;
    float khopeshDamage = 8f;
    int khopeshEffectTime = 80;

    public float getKhopeshDamage() {
        return khopeshDamage;
    }

    public int getKhopeshEffectTime() {
        return khopeshEffectTime;
    }

    public boolean isDisableKhopeshVariant() {
        return disableKhopeshVariant;
    }

    @Comment("Katana")
    boolean disableKatanaVariant = false;
    float katanaDamage = 12f;

    public float getKatanaDamage() {
        return katanaDamage;
    }

    public boolean isDisableKatanaVariant() {
        return disableKatanaVariant;
    }

    @Comment("Grandsword")
    boolean disableGrandswordVariant = false;
    float grandswordDamage = 15f;
    int grandswordEffectTime = 120;
    float grandswordKnockback = 2.5f;

    public boolean isDisableGrandswordVariant() {
        return disableGrandswordVariant;
    }

    public float getGrandswordKnockback() {
        return grandswordKnockback;
    }

    public float getGrandswordDamage() {
        return grandswordDamage;
    }

    public int getGrandswordEffectTime() {
        return grandswordEffectTime;
    }

    @Comment("Pernach")
    boolean disablePernachVariant = false;
    float pernachDamage = 5.5f;
    int pernachEffectTime = 100;

    public boolean isDisablePernachVariant() {
        return disablePernachVariant;
    }

    public float getPernachDamage() {
        return pernachDamage;
    }

    public int getPernachEffectTime() {
        return pernachEffectTime;
    }
    @Comment("Lance")
    boolean disableLanceVariant = false;
    float lanceFirstDamage = 7f;
    float lanceSecondDamage = 9f;
    int lanceEffectTime = 80;
    float lanceKnockback = 1.5f;

    public boolean isDisableLanceVariant() {
        return disableLanceVariant;
    }

    public float getLanceFirstDamage() {
        return lanceFirstDamage;
    }

    public float getLanceSecondDamage() {
        return lanceSecondDamage;
    }

    public int getLanceEffectTime() {
        return lanceEffectTime;
    }

    public float getLanceKnockback() {
        return lanceKnockback;
    }

    @Comment("Great Spear")
    boolean disableGreatSpearVariant = false;
    float greatSpearSlamDamage = 6f;
    float greatSpearStabDamage = 10f;
    float greatSpearStabKnockback = 1.3f;
    int greatSpearEffectTime = 20;

    public boolean isDisableGreatSpearVariant() {
        return disableGreatSpearVariant;
    }

    public float getGreatSpearSlamDamage() {
        return greatSpearSlamDamage;
    }

    public float getGreatSpearStabDamage() {
        return greatSpearStabDamage;
    }

    public float getGreatSpearStabKnockback() {
        return greatSpearStabKnockback;
    }

    public int getGreatSpearEffectTime() {
        return greatSpearEffectTime;
    }

    @Comment("Quarterstaff")
    boolean disableQuarterstaffVariant = false;
    float quarterstaffDamage = 6f;

    public float getQuarterstaffDamage() {
        return quarterstaffDamage;
    }

    public boolean isDisableQuarterstaffVariant() {
        return disableQuarterstaffVariant;
    }

    @Comment("Halberd")
    boolean disableHalberdVariant = false;
    float halberdDamage = 4f;
    int halberdEffectTime = 180;

    public boolean isDisableHalberdVariant() {
        return disableHalberdVariant;
    }

    public float getHalberdDamage() {
        return halberdDamage;
    }

    public int getHalberdEffectTime() {
        return halberdEffectTime;
    }

    @Comment("Backhand Blade")
    boolean disableBackhandBladeVariant = false;
    float backhandBladeDamage = 8f;
    int backhandBladeEffectTime = 100;

    @Comment("Deer Horns")
    boolean disableDeerHornsVariant = false;
    float deerHornsDamage = 1.8f;
    int deerHornsEffectTime = 80;

    @Comment("Great Katana")
    boolean disableGreatKatanaVariant = false;
    float greatKatanaDamage = 14f;
    float greatKatanaAdditionalDamage = 4f;

    public int getDeerHornsEffectTime() {
        return deerHornsEffectTime;
    }

    public float getGreatKatanaDamage() {
        return greatKatanaDamage;
    }

    public float getGreatAdditionalKatanaDamage() {
        return greatKatanaAdditionalDamage;
    }

    public boolean isDisableGreatKatanaVariant() {
        return disableGreatKatanaVariant;
    }

    public float getDeerHornsDamage() {
        return deerHornsDamage;
    }

    public boolean isDisableDeerHornsVariant() {
        return disableDeerHornsVariant;
    }

    public int getBackhandBladeEffectTime() {
        return backhandBladeEffectTime;
    }

    public float getBackhandBladeDamage() {
        return backhandBladeDamage;
    }

    public boolean isDisableBackhandBladeVariant() {
        return disableBackhandBladeVariant;
    }
}