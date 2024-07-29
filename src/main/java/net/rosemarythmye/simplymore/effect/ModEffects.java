package net.rosemarythmye.simplymore.effect;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.rosemarythmye.simplymore.Simplymore;
import net.rosemarythmye.simplymore.config.WeaponAttributesConfig;
import net.rosemarythmye.simplymore.config.WrapperConfig;

public class ModEffects {
    static WrapperConfig config = AutoConfig.getConfigHolder(WrapperConfig.class).getConfig();
    static WeaponAttributesConfig attributes = config.weaponAttributes;

    public static final StatusEffect TOXIC = registerEffect("toxic",new LowPoison(StatusEffectCategory.HARMFUL,10874896));
    public static final StatusEffect VENOM = registerEffect("venom",new VenomPoison(StatusEffectCategory.HARMFUL,7350627));
    public static final StatusEffect MOLTEN_FLARE = registerEffect("molten_flare",new MoltenFlareEffect(StatusEffectCategory.NEUTRAL,13570080).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED,"9cffce95-2df6-4e81-b2be-e3098ab8232b",0.5, EntityAttributeModifier.Operation.ADDITION));
    public static final StatusEffect CHILL = registerEffect("chill",new Chill(StatusEffectCategory.HARMFUL,10875635));
    public static final StatusEffect MIMICRY = registerEffect("mimicry",new MimicryEffect(StatusEffectCategory.NEUTRAL,0).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED,"65bd4374-bd51-45de-a339-a433b44082dc",attributes.getMimicryTwistedSwingSpeedModifier(), EntityAttributeModifier.Operation.ADDITION).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE,"ec6152d6-eefb-4f51-929a-7a189b805b0b",attributes.getMimicryTwistedDamageModifier(), EntityAttributeModifier.Operation.ADDITION));
    public static final StatusEffect LANCE = registerEffect("lance_mounted",new LanceEffect(StatusEffectCategory.NEUTRAL,0).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE,"6612cc75-0724-4307-87a3-5036db4e1320",3, EntityAttributeModifier.Operation.ADDITION));
    public static final StatusEffect HARVEST = registerEffect("harvest",new Harvest(StatusEffectCategory.BENEFICIAL,7865862).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED,"2c37414e-9386-4080-b809-ebd3e145cb40",0.8, EntityAttributeModifier.Operation.ADDITION).addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,"3b30bbc5-0f61-4d07-afde-316b9e03ac61",0.04, EntityAttributeModifier.Operation.ADDITION));
    public static final StatusEffect RAGE = registerEffect("rage",new Rage(StatusEffectCategory.NEUTRAL,7865862).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE,"137c5dd9-3a9d-4aae-921c-52478b9c5ba9",1.5, EntityAttributeModifier.Operation.ADDITION).addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,"3b30bbc5-0f61-4d07-afde-316b9e03ac61",0.04, EntityAttributeModifier.Operation.ADDITION));
    public static final StatusEffect GROTESQUE = registerEffect("grotesque",new GrotesqueEffect(StatusEffectCategory.NEUTRAL,8948877).addAttributeModifier(EntityAttributes.GENERIC_ARMOR,"06cc168c-e841-457f-a553-c1e633cd7388",3, EntityAttributeModifier.Operation.ADDITION).addAttributeModifier(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE,"3b30bbc5-0f61-4d07-afde-316b9e03ac61",0.225, EntityAttributeModifier.Operation.ADDITION));
    public static final StatusEffect SOLIDIFIED = registerEffect("solidified",new Solidify(StatusEffectCategory.NEUTRAL,8948877).addAttributeModifier(EntityAttributes.GENERIC_ARMOR,"67dbcdbb-1dac-48c9-87af-e900d71258b4",10, EntityAttributeModifier.Operation.ADDITION).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED,"052196c3-69eb-4fda-9098-9218e2749708",-99, EntityAttributeModifier.Operation.ADDITION).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE,"a1b83396-99a4-4535-9a44-339110f9bde2",-9999, EntityAttributeModifier.Operation.ADDITION));
    public static final StatusEffect STUNNED = registerEffect("stunned",new Solidify(StatusEffectCategory.HARMFUL,8948877).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED,"28908fac-dfb9-4cb7-b4e1-e4c55a661730",-99, EntityAttributeModifier.Operation.ADDITION).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE,"6c4be639-f6e6-40ce-a1b7-6374f17d5d85",-9999, EntityAttributeModifier.Operation.ADDITION));
    public static final StatusEffect WITHERING_FATE = registerEffect("withering_fate",new ModStatusEffect(StatusEffectCategory.HARMFUL,2818819).addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH,"ebed9986-78c5-4c08-9f2f-05e929ee5fa7",-1, EntityAttributeModifier.Operation.ADDITION));
    public static final StatusEffect MISTIFIED = registerEffect("mistified",new MistyEffect(StatusEffectCategory.BENEFICIAL,0));
    public static void registerModEffects() {
        Simplymore.LOGGER.info("Registering Status Effects for " + Simplymore.ID);
    }

    public static StatusEffect registerEffect (String name, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(Simplymore.ID, name),effect);
    }
}
