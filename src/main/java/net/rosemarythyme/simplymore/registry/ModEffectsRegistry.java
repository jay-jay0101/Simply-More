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
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.config.WeaponAttributesConfig;
import net.rosemarythyme.simplymore.config.WrapperConfig;
import net.rosemarythyme.simplymore.effect.*;

public class ModEffectsRegistry {
    static WrapperConfig config = AutoConfig.getConfigHolder(WrapperConfig.class).getConfig();
    static WeaponAttributesConfig attributes = config.weaponAttributes;
    static UniqueEffectConfig effects = config.uniqueEffects;

    public static final StatusEffect VENOM = registerEffect(
            "venom",
            new VenomPoisonEffect(StatusEffectCategory.HARMFUL,7350627)
    );
    public static final StatusEffect MOLTEN_FLARE = registerEffect(
            "molten_flare",
            new MoltenFlareEffect(StatusEffectCategory.NEUTRAL,13570080)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_SPEED,
                            "f53f18d4-ac4d-4010-aa0a-7d2d25050c66",
                            effects.getExecutingSliceSwingSpeedBonus(),
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
                            "ccb60c0e-fcc8-4a37-bcf7-fc4644dd6160",
                            attributes.getMimicryTwistedSwingSpeedModifier(),
                            EntityAttributeModifier.Operation.ADDITION
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            "f6de8d23-a536-43f7-8363-434609ae6e5b",
                            attributes.getMimicryTwistedDamageModifier(),
                            EntityAttributeModifier.Operation.ADDITION
                    )
    );
    public static final StatusEffect LANCE = registerEffect(
            "lance_mounted",
            new LanceEffect(StatusEffectCategory.NEUTRAL,0)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            "82a0acc0-2b8f-4ecb-89e6-8d9ae55c5777",
                            5,
                            EntityAttributeModifier.Operation.ADDITION
                    )
    );
    public static final StatusEffect HARVEST = registerEffect(
            "harvest",
            new HarvestEffect(StatusEffectCategory.BENEFICIAL,7865862)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_SPEED,
                            "c05d986c-5c04-434d-b5b0-72fc4b4151ab",
                            0.8,
                            EntityAttributeModifier.Operation.ADDITION
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            "443724ff-b478-4f43-bcea-ea4037d87d0c",
                            0.04,
                            EntityAttributeModifier.Operation.ADDITION
                    )
    );
    public static final StatusEffect RAGE = registerEffect(
            "rage",
            new RageEffect(StatusEffectCategory.NEUTRAL,7865862)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            "3e0c2338-1d22-4375-a70d-856dc5d2a0a6",
                            1.5,
                            EntityAttributeModifier.Operation.ADDITION
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            "b33f3caf-876d-4809-a55e-5647809cad16",
                            0.04,
                            EntityAttributeModifier.Operation.ADDITION
                    )
    );
    public static final StatusEffect GROTESQUE = registerEffect(
            "grotesque",
            new GrotesqueEffect(StatusEffectCategory.NEUTRAL,8948877)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ARMOR,
                            "3c943533-dab6-4f42-9195-21ca6838bb0b",
                            effects.getGrotesqueHeldArmorBuff(),
                            EntityAttributeModifier.Operation.ADDITION
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE,
                            "28a24cb0-f23b-49f7-9a46-a2776e7fb94d",
                            0.225,
                            EntityAttributeModifier.Operation.ADDITION
                    )
    );
    public static final StatusEffect SOLIDIFIED = registerEffect(
            "solidified",
            new SolidifyEffect(StatusEffectCategory.NEUTRAL,8948877)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ARMOR,
                            "695fbd36-b9e1-452d-8f41-2f850a4ccf6a",
                            effects.getSolidifyAttackerStunnedArmorBuff(),
                            EntityAttributeModifier.Operation.ADDITION
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_SPEED,
                            "8976a053-b499-4fba-9dcb-432c4694fbbb",
                            -99,
                            EntityAttributeModifier.Operation.ADDITION
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            "1f3ea85b-ab38-435f-858a-b508aefdeefe",
                            -9999,
                            EntityAttributeModifier.Operation.ADDITION
                    )
    );
    public static final StatusEffect STUNNED = registerEffect(
            "stunned",
            new SolidifyEffect(StatusEffectCategory.HARMFUL,8948877)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ARMOR,
                            "f00fd930-1cd9-4524-a375-0d50225d323f",
                            effects.getSolidifySelfStunnedArmorBuff(),
                            EntityAttributeModifier.Operation.ADDITION
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_SPEED,
                            "25b787e4-eb31-4257-95b2-e3962ccc1ea7",
                            -99, EntityAttributeModifier.Operation.ADDITION
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            "3a55dc27-08d0-4575-a6fc-b896f2c347b2",
                            -9999,
                            EntityAttributeModifier.Operation.ADDITION
                    )
    );
    public static final StatusEffect STUNNED_MOXIE = registerEffect(
            "stunned_moxie",
            new SolidifyEffect(StatusEffectCategory.HARMFUL,8948877)
                    .addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_SPEED,
                            "61b06328-fb0c-4d7c-ba2f-cdb8b370224c",
                            -99,
                            EntityAttributeModifier.Operation.ADDITION
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            "e8940579-765e-4458-928d-e430e50713c1",
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
                            "1ce784a4-a543-4436-a0fe-376bea83d30e",
                            -3, EntityAttributeModifier.Operation.ADDITION
                    ).addAttributeModifier(
                            EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            "42d45eb0-2230-48b0-9288-a3be65e15cf1",
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
                            "a6f03a84-1731-4558-9bf5-265f0d1dd84c",
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
                            "ec7aa674-2cba-464e-973b-2a26e7ff5c4a",
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
