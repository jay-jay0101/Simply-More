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
import net.rosemarythyme.simplymore.effect.*;
import net.sweenus.simplyswords.api.ObserverStatusEffectSyncRegistry;


public class StatusEffectRegistry {
    public static final DeferredRegister<StatusEffect> EFFECTS =
            DeferredRegister.create(SimplyMore.ID, RegistryKeys.STATUS_EFFECT);

    /// From <code>simplyswords: EffectRegistry#getReference</code>
    public static RegistryEntry<StatusEffect> getReference(RegistrySupplier<StatusEffect> input) {
        return EFFECTS.getRegistrar().getHolder(input.getId());
    }

    public static final RegistrySupplier<StatusEffect> WOUNDED = registerEffect(
            "wounded",
            new StatusEffect(StatusEffectCategory.HARMFUL,0x780606)
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
            new StunEffect(StatusEffectCategory.HARMFUL,0x888C8D)
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

    public static final RegistrySupplier<StatusEffect> STUN = registerEffect(
            "stun",
            new StunEffect(StatusEffectCategory.HARMFUL,0x888C8D)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_SPEED,
                            SimplyMore.identifier("stun_attack_speed"),
                            -99,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            SimplyMore.identifier("stun_damage"),
                            -9999,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    )
    );

    public static final RegistrySupplier<StatusEffect> HEX = registerEffect(
            "hex",
            new HexEffect(StatusEffectCategory.HARMFUL,0xA020F0)
    );

    public static final RegistrySupplier<StatusEffect> STARLIGHT = registerEffect(
            "starlight",
            new StarlightEffect(StatusEffectCategory.BENEFICIAL,0xADD8E6)
    );

    public static final RegistrySupplier<StatusEffect> SOUL_HEALTH = registerEffect(
            "soul_health",
            new StatusEffect(StatusEffectCategory.BENEFICIAL,0x780606).addAttributeModifier(
                    EntityAttributes.GENERIC_MAX_HEALTH,
                    SimplyMore.identifier("soul_health"),
                    2,
                    EntityAttributeModifier.Operation.ADD_VALUE
            )
    );


    public static final RegistrySupplier<StatusEffect> BLOOM = registerEffect(
            "bloom",
            new BloomEffect(StatusEffectCategory.BENEFICIAL,0x780606)
    );

    public static final RegistrySupplier<StatusEffect> LIGHTWEIGHT = registerEffect(
            "lightweight",
            new FallDamageImmunityEffect(StatusEffectCategory.BENEFICIAL, 0xBFBFBF)
    );

    public static final RegistrySupplier<StatusEffect> VENOM = registerEffect(
            "venom",
            new VenomPoisonEffect(StatusEffectCategory.HARMFUL,0x702963)
    );

    public static final RegistrySupplier<StatusEffect> CHILL = registerEffect(
            "chill",
            new ChillEffect(StatusEffectCategory.HARMFUL,0xA5F2F3)
    );

    public static final RegistrySupplier<StatusEffect> GROTESQUE_WARD = registerEffect(
            "grotesque_ward",
            new StatusEffect(StatusEffectCategory.HARMFUL, 0xF9CA5)
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

    public static final RegistrySupplier<StatusEffect> DECAYING = registerEffect(
            "decaying",
            new DecayingEffect(StatusEffectCategory.HARMFUL,0x2B0303)
    );

    public static final RegistrySupplier<StatusEffect> LUNACY = registerEffect(
            "lunacy",
            new LunacyEffect(StatusEffectCategory.HARMFUL,0x800080)
    );

    public static final RegistrySupplier<StatusEffect> BLESSING = registerEffect(
            "blessing",
            new StatusEffect(StatusEffectCategory.BENEFICIAL,0xFEF65B)
    );

    public static final RegistrySupplier<StatusEffect> FRAGILE = registerEffect(
            "fragile",
            new StatusEffect(StatusEffectCategory.HARMFUL,0xB9A6BD)
    );

    public static final RegistrySupplier<StatusEffect> ARMOUR_CRUNCH = registerEffect(
            "armor_crunch",
            new StatusEffect(StatusEffectCategory.HARMFUL,0x964B00)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ARMOR,
                            SimplyMore.identifier("armor_crunch"),
                            -2,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    )
    );

    public static final RegistrySupplier<StatusEffect> DAZZLED = registerEffect(
            "dazzled",
            new StatusEffect(StatusEffectCategory.HARMFUL,0xEEDC82)
    );
    
    public static void register() {
        EFFECTS.register();

        ObserverStatusEffectSyncRegistry.register(BLESSING.getId());
    }

    public static RegistrySupplier<StatusEffect> registerEffect(String name, StatusEffect effect) {
        return EFFECTS.register(
                Identifier.of(SimplyMore.ID, name),
                () -> effect
        );
    }
}
