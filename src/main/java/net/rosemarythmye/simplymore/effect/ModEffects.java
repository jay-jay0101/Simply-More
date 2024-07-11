package net.rosemarythmye.simplymore.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.rosemarythmye.simplymore.Simplymore;

public class ModEffects {

    public static final StatusEffect TOXIC = registerEffect("toxic",new LowPoison(StatusEffectCategory.HARMFUL,10874896));
    public static final StatusEffect VENOM = registerEffect("venom",new VenomPoison(StatusEffectCategory.HARMFUL,7350627));
    public static final StatusEffect MOLTEN_FLARE = registerEffect("molten_flare",new MoltenFlareEffect(StatusEffectCategory.BENEFICIAL,13570080).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED,"9cffce95-2df6-4e81-b2be-e3098ab8232b",0.5, EntityAttributeModifier.Operation.ADDITION));
    public static final StatusEffect CHILL = registerEffect("chill",new Chill(StatusEffectCategory.HARMFUL,10875635));
    public static final StatusEffect MIMICRY = registerEffect("mimicry",new MimicryEffect(StatusEffectCategory.NEUTRAL,0).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED,"65bd4374-bd51-45de-a339-a433b44082dc",-0.6, EntityAttributeModifier.Operation.ADDITION).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE,"ec6152d6-eefb-4f51-929a-7a189b805b0b",2, EntityAttributeModifier.Operation.ADDITION));
    public static final StatusEffect LANCE = registerEffect("lance_mounted",new LanceEffect(StatusEffectCategory.BENEFICIAL,0).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE,"6612cc75-0724-4307-87a3-5036db4e1320",3, EntityAttributeModifier.Operation.ADDITION));
    public static final StatusEffect HARVEST = registerEffect("harvest",new Harvest(StatusEffectCategory.BENEFICIAL,7865862).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED,"2c37414e-9386-4080-b809-ebd3e145cb40",0.8, EntityAttributeModifier.Operation.ADDITION).addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,"3b30bbc5-0f61-4d07-afde-316b9e03ac61",0.04, EntityAttributeModifier.Operation.ADDITION));
    public static void registerModEffects() {
        Simplymore.LOGGER.info("Registering Status Effects for " + Simplymore.ID);
    }

    public static StatusEffect registerEffect (String name, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(Simplymore.ID, name),effect);
    }
}
