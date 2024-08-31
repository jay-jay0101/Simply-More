package net.rosemarythyme.simplymore.registry;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.config.WeaponAttributesConfig;
import net.rosemarythyme.simplymore.config.WrapperConfig;
import net.rosemarythyme.simplymore.effect.*;

public class ModEffectsRegistry {
    static WrapperConfig config = AutoConfig.getConfigHolder(WrapperConfig.class).getConfig();
    static WeaponAttributesConfig attributes = config.weaponAttributes;

    static String genericAttackSpeedUUID            = "FA233E1C-4180-4865-B01B-BCCE9785ACA3";
    static String genericAttackDamageUUID           = "CB3F55D3-645C-4F38-A497-9C13A33DB5CF";
    static String genericMoveSpeedUUID              = "662A6B8D-DA3E-4C1C-8813-96EA6097278D";
    // I used the UUID for chestplate for the genericArmorUUID because, why not?
    static String genericArmorUUID                  = "9F3D476D-C118-4544-8365-64846904B48E";
    static String genericKnockbackResistanceUUID    = "0-1-438D-0-28D34";

    public static final StatusEffect VENOM = registerEffect(
            "venom",
            new VenomPoisonEffect(StatusEffectCategory.HARMFUL,7350627)
    );
    public static final StatusEffect MOLTEN_FLARE = registerEffect(
            "molten_flare",
            new MoltenFlareEffect(StatusEffectCategory.NEUTRAL,13570080)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_SPEED,
                            genericAttackSpeedUUID,
                            0.6,
                            EntityAttributeModifier.Operation.ADDITION
                    )
    );
    public static final StatusEffect CHILL = registerEffect(
            "chill",
            new ChillEffect(StatusEffectCategory.HARMFUL,10875635)
    );
    public static final StatusEffect MIMICRY = registerEffect(
            "mimicry",
            new MimicryEffect(StatusEffectCategory.NEUTRAL,0)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_SPEED,
                            genericAttackSpeedUUID,
                            attributes.getMimicryTwistedSwingSpeedModifier(),
                            EntityAttributeModifier.Operation.ADDITION
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            genericAttackDamageUUID,
                            attributes.getMimicryTwistedDamageModifier(),
                            EntityAttributeModifier.Operation.ADDITION
                    )
    );
    public static final StatusEffect LANCE = registerEffect(
            "lance_mounted",
            new LanceEffect(StatusEffectCategory.NEUTRAL,0)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            genericAttackDamageUUID,
                            3,
                            EntityAttributeModifier.Operation.ADDITION
                    )
    );
    public static final StatusEffect HARVEST = registerEffect(
            "harvest",
            new HarvestEffect(StatusEffectCategory.BENEFICIAL,7865862)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_SPEED,
                            genericAttackSpeedUUID,
                            0.8,
                            EntityAttributeModifier.Operation.ADDITION
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            genericMoveSpeedUUID,
                            0.04,
                            EntityAttributeModifier.Operation.ADDITION
                    )
    );
    public static final StatusEffect RAGE = registerEffect(
            "rage",
            new RageEffect(StatusEffectCategory.NEUTRAL,7865862)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            genericAttackDamageUUID,
                            1.5,
                            EntityAttributeModifier.Operation.ADDITION
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            genericMoveSpeedUUID,
                            0.04,
                            EntityAttributeModifier.Operation.ADDITION
                    )
    );
    public static final StatusEffect GROTESQUE = registerEffect(
            "grotesque",
            new GrotesqueEffect(StatusEffectCategory.NEUTRAL,8948877)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ARMOR,
                            genericArmorUUID,
                            3,
                            EntityAttributeModifier.Operation.ADDITION
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE,
                            genericKnockbackResistanceUUID,
                            0.225,
                            EntityAttributeModifier.Operation.ADDITION
                    )
    );
    public static final StatusEffect SOLIDIFIED = registerEffect(
            "solidified",
            new SolidifyEffect(StatusEffectCategory.NEUTRAL,8948877)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ARMOR,
                            genericArmorUUID,
                            10,
                            EntityAttributeModifier.Operation.ADDITION
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_SPEED,
                            genericAttackSpeedUUID,
                            -99,
                            EntityAttributeModifier.Operation.ADDITION
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            genericAttackDamageUUID,
                            -9999,
                            EntityAttributeModifier.Operation.ADDITION
                    )
    );
    public static final StatusEffect STUNNED = registerEffect(
            "stunned",
            new SolidifyEffect(StatusEffectCategory.HARMFUL,8948877)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ARMOR,
                            genericArmorUUID,
                            10,
                            EntityAttributeModifier.Operation.ADDITION
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_SPEED,
                            genericAttackSpeedUUID,
                            -99, EntityAttributeModifier.Operation.ADDITION
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            genericAttackDamageUUID,
                            -9999,
                            EntityAttributeModifier.Operation.ADDITION
                    )
    );
    public static final StatusEffect STUNNED_MOXIE = registerEffect(
            "stunned_moxie",
            new SolidifyEffect(StatusEffectCategory.HARMFUL,8948877)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_SPEED,
                            genericAttackSpeedUUID,
                            -99,
                            EntityAttributeModifier.Operation.ADDITION
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            genericAttackDamageUUID,
                            -9999,
                            EntityAttributeModifier.Operation.ADDITION
                    )
    );
    public static final StatusEffect WITHERING_FATE = registerEffect(
            "withering_fate",
            new ModStatusEffect(StatusEffectCategory.HARMFUL,2818819)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_MAX_HEALTH,
                            "ebed9986-78c5-4c08-9f2f-05e929ee5fa7",
                            -1, EntityAttributeModifier.Operation.ADDITION)
    );
    public static final StatusEffect MISTIFIED = registerEffect(
            "mistified",
            new MistyEffect(StatusEffectCategory.BENEFICIAL,0)
    );
    public static final StatusEffect INSANITY = registerEffect(
            "insanity",
            new InsanityEffect(StatusEffectCategory.HARMFUL,700)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            genericAttackDamageUUID,
                            -3, EntityAttributeModifier.Operation.ADDITION
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            genericMoveSpeedUUID,
                            -0.03,
                            EntityAttributeModifier.Operation.ADDITION)
    );
    public static final StatusEffect TIDEBREAKER = registerEffect(
            "tidebreaker_rain",
            new TidebreakerEffect(StatusEffectCategory.BENEFICIAL,700)
    );
    public static final StatusEffect BLESSING = registerEffect(
            "blessing",
            new ModStatusEffect(StatusEffectCategory.BENEFICIAL,16709211)
    );
    public static final StatusEffect CURSE = registerEffect(
            "curse",
            new ModStatusEffect(StatusEffectCategory.HARMFUL,3152180)
    );
    public static final StatusEffect SUFFOCATION = registerEffect(
            "constricted",
            new SuffocatingEffect(StatusEffectCategory.HARMFUL,0)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            genericMoveSpeedUUID,
                            -0.03,
                            EntityAttributeModifier.Operation.ADDITION
                    )
    );
    public static final StatusEffect RADIANT_MARK = registerEffect(
            "radiant_mark",
            new RadiantEffect(StatusEffectCategory.HARMFUL,0)
    );
    public static final StatusEffect FORESEEN = registerEffect(
            "soul_lock",
            new SoulForesightEffect(StatusEffectCategory.HARMFUL,0)
    );
    public static final StatusEffect ARMOUR_CRUNCH = registerEffect(
            "armor_crunch",
            new ModStatusEffect(StatusEffectCategory.HARMFUL,9849600)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ARMOR,
                            genericArmorUUID,
                            -2,
                            EntityAttributeModifier.Operation.ADDITION
            )
    );
    public static void registerModEffects() {
        SimplyMore.LOGGER.info("Registering Status Effects for " + SimplyMore.ID);
    }

    public static StatusEffect registerEffect (String name, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(SimplyMore.ID, name),effect);
    }
}
