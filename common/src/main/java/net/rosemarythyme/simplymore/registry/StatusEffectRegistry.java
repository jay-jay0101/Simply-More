package net.rosemarythyme.simplymore.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.effect.*;


public class StatusEffectRegistry {
    private static final UniqueEffectConfig UNIQUE_CONFIG = ConfigWrapper.UNIQUE;

    public static final DeferredRegister<StatusEffect> EFFECTS =
            DeferredRegister.create(SimplyMore.ID, RegistryKeys.STATUS_EFFECT);

    /// From <code>simplyswords: EffectRegistry#getReference</code>
    public static RegistryEntry<StatusEffect> getReference(RegistrySupplier<StatusEffect> input) {
        return EFFECTS.getRegistrar().getHolder(input.getId());
    }

    public static final RegistrySupplier<StatusEffect> WOUNDED = registerEffect(
            "wounded",
            new StatusEffect(StatusEffectCategory.HARMFUL,7865862)
    );

    public static final RegistrySupplier<StatusEffect> IMPLICIT_MINING_FATIGUE = registerEffect(
            "implicit_mining_fatigue",
            new StatusEffect(StatusEffectCategory.HARMFUL,0x4A4187).addAttributeModifier(
                    EntityAttributes.GENERIC_ATTACK_SPEED,
                    SimplyMore.identifier("implicit_attack_speed"),
                    -0.1F,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            )
    );

    public static final RegistrySupplier<StatusEffect> IMPLICIT_STUN = registerEffect(
            "implicit_stun",
            new SolidifyEffect(StatusEffectCategory.HARMFUL,8948877)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_SPEED,
                            SimplyMore.identifier("implicit_stun_attack_speed"),
                            -99,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            SimplyMore.identifier("implicit_stun_damage"),
                            -9999,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    )
    );

    public static final RegistrySupplier<StatusEffect> MIMICRY_HAPPENING = registerEffect(
            "mimicry_happening",
            new MimicryEffect(StatusEffectCategory.BENEFICIAL,0)
    );

    public static final RegistrySupplier<StatusEffect> HEX = registerEffect(
            "hex",
            new HexEffect(StatusEffectCategory.HARMFUL,10494192)
    );

    public static final RegistrySupplier<StatusEffect> STARLIGHT = registerEffect(
            "starlight",
            new StarlightEffect(StatusEffectCategory.BENEFICIAL,11393254)
    );

    public static final RegistrySupplier<StatusEffect> GRASPING = registerEffect(
            "grasping",
            new MyrmedgeEffect(StatusEffectCategory.BENEFICIAL,7865862).addAttributeModifier(
                    EntityAttributes.GENERIC_ATTACK_SPEED,
                    SimplyMore.identifier("grasping_attack_speed"),
                    -99,
                    EntityAttributeModifier.Operation.ADD_VALUE
            ).addAttributeModifier(
                    EntityAttributes.GENERIC_ATTACK_DAMAGE,
                    SimplyMore.identifier("grasping_damage"),
                    -9999,
                    EntityAttributeModifier.Operation.ADD_VALUE
            )
    );

    public static final RegistrySupplier<StatusEffect> SOUL_HEALTH = registerEffect(
            "soul_health",
            new StatusEffect(StatusEffectCategory.BENEFICIAL,7865862).addAttributeModifier(
                    EntityAttributes.GENERIC_MAX_HEALTH,
                    SimplyMore.identifier("soul_health"),
                    2,
                    EntityAttributeModifier.Operation.ADD_VALUE
            )
    );


    public static final RegistrySupplier<StatusEffect> BLOOM = registerEffect(
            "bloom",
            new BloomEffect(StatusEffectCategory.BENEFICIAL,7865862)
    );

    public static final RegistrySupplier<StatusEffect> RAVENOUS = registerEffect(
            "ravenous",
            new RevvengineRushEffect(StatusEffectCategory.BENEFICIAL,9109504)
    );

    public static final RegistrySupplier<StatusEffect> LIGHTWEIGHT = registerEffect(
            "lightweight",
            new FallDamageImmunityEffect(StatusEffectCategory.BENEFICIAL, (int) 0xBFBFBF)
    );

    public static final RegistrySupplier<StatusEffect> VENOM = registerEffect(
            "venom",
            new VenomPoisonEffect(StatusEffectCategory.HARMFUL,7350627)
    );

    public static final RegistrySupplier<StatusEffect> CHILL = registerEffect(
            "chill",
            new ChillEffect(StatusEffectCategory.HARMFUL,10875635)
    );

    public static final RegistrySupplier<StatusEffect> HARVEST = registerEffect(
            "harvest",
            new HarvestEffect(StatusEffectCategory.BENEFICIAL,7865862)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_SPEED,
                            SimplyMore.identifier("harvest_attack_speed"),
                            0.8,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            SimplyMore.identifier("harvest_speed"),
                            0.04,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    )
    );

    public static final RegistrySupplier<StatusEffect> RAGE = registerEffect(
            "rage",
            new RageEffect(StatusEffectCategory.NEUTRAL,7865862)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            SimplyMore.identifier("rage_damage"),
                            1.5,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            SimplyMore.identifier("rage_speed"),
                            0.04,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    )
    );

    public static final RegistrySupplier<StatusEffect> GROTESQUE_WARD = registerEffect(
            "grotesque_ward",
            new StatusEffect(StatusEffectCategory.HARMFUL, 1023141)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            SimplyMore.identifier("grotesque_ward_damage"),
                            -1,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            SimplyMore.identifier("grotesque_ward_speed"),
                            -0.01,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    )
    );

    public static final RegistrySupplier<StatusEffect> SOLIDIFIED = registerEffect(
            "solidified",
            new SolidifyEffect(StatusEffectCategory.BENEFICIAL,8948877)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ARMOR,
                            SimplyMore.identifier("solid_armor"),
                            UNIQUE_CONFIG.blade_of_the_grotesque.selfStunnedArmorBuff,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_SPEED,
                            SimplyMore.identifier("solid_attack_speed"),
                            -99,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            SimplyMore.identifier("solid_damage"),
                            -9999,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    )
    );
    public static final RegistrySupplier<StatusEffect> STUNNED = registerEffect(
            "stunned",
            new SolidifyEffect(StatusEffectCategory.HARMFUL,8948877)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ARMOR,
                            SimplyMore.identifier("stunned_armor"),
                            UNIQUE_CONFIG.blade_of_the_grotesque.attackerStunnedArmorBuff,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_SPEED,
                            SimplyMore.identifier("stunned_attack_speed"),
                            -99, EntityAttributeModifier.Operation.ADD_VALUE
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            SimplyMore.identifier("stunned_damage"),
                            -9999,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    )
    );
    public static final RegistrySupplier<StatusEffect> STUNNED_MOXIE = registerEffect(
            "stunned_moxie",
            new SolidifyEffect(StatusEffectCategory.HARMFUL,8948877)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_SPEED,
                            SimplyMore.identifier("moxie_stun_attack_speed"),
                            -99,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            SimplyMore.identifier("moxie_stun_damage"),
                            -9999,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    )
    );
    public static final RegistrySupplier<StatusEffect> WITHERING_FATE = registerEffect(
            "withering_fate",
            new StatusEffect(StatusEffectCategory.HARMFUL,2818819)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_MAX_HEALTH,
                            SimplyMore.identifier("withering_fate"),
                            -1, EntityAttributeModifier.Operation.ADD_VALUE)
    );
    public static final RegistrySupplier<StatusEffect> MISTIFIED = registerEffect(
            "mistified",
            new MistyEffect(StatusEffectCategory.BENEFICIAL,0)
    );
    public static final RegistrySupplier<StatusEffect> INSANITY = registerEffect(
            "insanity",
            new InsanityEffect(StatusEffectCategory.HARMFUL,700)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            SimplyMore.identifier("insanity_damage"),
                            -3, EntityAttributeModifier.Operation.ADD_VALUE
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            SimplyMore.identifier("insanity_speed"),
                            -0.03,
                            EntityAttributeModifier.Operation.ADD_VALUE)
    );
    public static final RegistrySupplier<StatusEffect> TIDEBREAKER = registerEffect(
            "tidebreaker_rain",
            new TidebreakerEffect(StatusEffectCategory.BENEFICIAL,700)
    );
    public static final RegistrySupplier<StatusEffect> BLESSING = registerEffect(
            "blessing",
            new StatusEffect(StatusEffectCategory.BENEFICIAL,16709211)
    );
    public static final RegistrySupplier<StatusEffect> CURSE = registerEffect(
            "curse",
            new StatusEffect(StatusEffectCategory.HARMFUL,3152180)
    );
    public static final RegistrySupplier<StatusEffect> SUFFOCATION = registerEffect(
            "constricted",
            new SuffocatingEffect(StatusEffectCategory.HARMFUL,0)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            SimplyMore.identifier("constricted_speed"),
                            -0.03,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    )
    );
    public static final RegistrySupplier<StatusEffect> RADIANT_MARK = registerEffect(
            "radiant_mark",
            new RadiantEffect(StatusEffectCategory.HARMFUL,0)
    );
    public static final RegistrySupplier<StatusEffect> FORESEEN = registerEffect(
            "soul_lock",
            new SoulForesightEffect(StatusEffectCategory.HARMFUL,0)
    );
    public static final RegistrySupplier<StatusEffect> ARMOUR_CRUNCH = registerEffect(
            "armor_crunch",
            new StatusEffect(StatusEffectCategory.HARMFUL,9849600)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ARMOR,
                            SimplyMore.identifier("armor_crunch"),
                            -2,
                            EntityAttributeModifier.Operation.ADD_VALUE
            )
    );
    
    public static void register() {
        EFFECTS.register();
    }

    public static RegistrySupplier<StatusEffect> registerEffect(String name, StatusEffect effect) {
        return EFFECTS.register(
                Identifier.of(SimplyMore.ID, name),
                () -> effect
        );
    }
}
