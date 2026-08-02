package net.rosemarythyme.simplymore.registry.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.registry.TagRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.sweenus.simplyswords.SimplySwords;
import net.sweenus.simplyswords.api.SimplySwordsAPI;
import net.sweenus.simplyswords.api.WeaponImplicitDefinition;
import net.sweenus.simplyswords.item.component.WeaponImplicitComponent;
import net.sweenus.simplyswords.registry.EffectRegistry;

public class ImplicitRegistry {
    public static final WeaponImplicitDefinition GRANDSWORD = register(
            SimplyMore.identifier("grandsword_sunder"),
            SimplyMore.identifier("grandsword"),
            2, 10, "grandsword",
            null,
            ImplicitRegistry::GrandswordImplicit,
            null
    );

    public static final WeaponImplicitDefinition LANCE = register(
            SimplyMore.identifier("friendship"),
            SimplyMore.identifier("lance"),
            40, 80, "friendship",
            ImplicitRegistry::friendship,
            null,
            null
    );

    public static final WeaponImplicitDefinition KHOPESH = register(
            SimplyMore.identifier("disarm"),
            SimplyMore.identifier("kopesh"),
            20, 60, "disarm",
            null,
            ImplicitRegistry::fatigue,
            null
    );

    public static final WeaponImplicitDefinition PERNACH = register(
            SimplyMore.identifier("stun"),
            SimplyMore.identifier("pernach"),
            1, 12, "stun",
            null,
            ImplicitRegistry::stun,
            null
    );

    private static void fatigue(ItemStack stack, WeaponImplicitComponent data, LivingEntity target, LivingEntity attacker, float damage) {
        if (!MathUtils.chance(attacker, (data.value()/100f))) return;
        RegistryEntry<StatusEffect> fatigue = StatusEffectRegistry.getReference(StatusEffectRegistry.IMPLICIT_MINING_FATIGUE);

        StatusEffectInstance current = attacker.getStatusEffect(fatigue);
        int currentAmp = current == null ? 0 : current.getAmplifier();

        int next = Math.min(3, currentAmp + data.value());
        target.addStatusEffect(new StatusEffectInstance(fatigue, 200, Math.max(0, next)), attacker);

        if (target.getWorld() instanceof ServerWorld) {
            AudioVisualUtils.particleAroundEntity(target, ParticleTypes.CRIT, 8, 0.28f, 0.05f);
        }
    }

    private static void stun(ItemStack stack, WeaponImplicitComponent data, LivingEntity target, LivingEntity attacker, float damage) {
        if (!MathUtils.chance(attacker, (data.value()/100f))) return;
        RegistryEntry<StatusEffect> stun = StatusEffectRegistry.getReference(StatusEffectRegistry.IMPLICIT_STUN);

        if(target.hasStatusEffect(stun)) return;

        target.addStatusEffect(new StatusEffectInstance(stun, 15, 0), attacker);

        if (target.getWorld() instanceof ServerWorld) {
            AudioVisualUtils.particleAroundEntity(target, ParticleTypes.CRIT, 8, 0.28f, 0.05f);
        }
    }

    private static float friendship(ItemStack stack, WeaponImplicitComponent data, LivingEntity target, DamageSource source, float amount) {
        if(!(source.getAttacker() instanceof LivingEntity attacker)) return amount;
        if(!EntityUtils.isRidingLivingEntity(attacker)) return amount;

        AudioVisualUtils.particleAroundEntity(target, ParticleTypes.CRIT, 8, 0.28f, 0.05f);
        float multiplier = 1 + (data.value()/100f);
        return amount * multiplier;
    }

    private static void GrandswordImplicit(ItemStack stack, WeaponImplicitComponent data, LivingEntity target, LivingEntity attacker, float damage) {
        AttackUtils.breakShield(target);

        RegistryEntry<StatusEffect> sunder = EffectRegistry.getReference(EffectRegistry.SUNDERED_ARMOR);
        StatusEffectInstance current = target.getStatusEffect(sunder);

        int currentAmp = current == null ? 0 : current.getAmplifier();

        int next = Math.min(50, currentAmp + data.value());
        target.addStatusEffect(new StatusEffectInstance(sunder, 200, Math.max(0, next)), attacker);

        if (target.getWorld() instanceof ServerWorld) {
            AudioVisualUtils.particleAroundEntity(target, ParticleTypes.CRIT, 8, 0.28f, 0.05f);
            AudioVisualUtils.particleAroundEntity(target, ParticleTypes.POOF, 4, 0.28f, 0.05f);
            AudioVisualUtils.particleAroundEntity(target, ParticleTypes.ANGRY_VILLAGER, 6, 0.28f, 0.025f);
        }
    }

    public static WeaponImplicitDefinition register(Identifier id, Identifier weaponType, int min, int max, String key, WeaponImplicitDefinition.DamageHandler damage, WeaponImplicitDefinition.HitHandler hit, WeaponImplicitDefinition.IncomingDamageHandler incoming) {
        WeaponImplicitDefinition definition = new WeaponImplicitDefinition(id, weaponType, min, max, damage, hit, incoming, (component) -> Text.translatable("tooltip.simplymore.implicit." + key, component.value()));
        SimplySwordsAPI.registerWeaponImplicit(definition);

        return definition;
    }

    public static void register() {
        SimplySwordsAPI.registerWeaponType(TagRegistry.GREAT_KATANA, Identifier.of(SimplySwords.MOD_ID, "katana"));
        SimplySwordsAPI.registerWeaponType(TagRegistry.GRANDSWORD, GRANDSWORD.weaponType());
        SimplySwordsAPI.registerWeaponType(TagRegistry.BACKHAND_BLADE, Identifier.of(SimplySwords.MOD_ID, "halberd"));
        SimplySwordsAPI.registerWeaponType(TagRegistry.LANCE, LANCE.weaponType());
        SimplySwordsAPI.registerWeaponType(TagRegistry.KHOPESH, KHOPESH.weaponType());
        SimplySwordsAPI.registerWeaponType(TagRegistry.DAGGER, Identifier.of(SimplySwords.MOD_ID, "dagger"));
        SimplySwordsAPI.registerWeaponType(TagRegistry.PERNACH, PERNACH.weaponType());
        SimplySwordsAPI.registerWeaponType(TagRegistry.QUARTERSTAFF, Identifier.of(SimplySwords.MOD_ID, "warglaive"));
        SimplySwordsAPI.registerWeaponType(TagRegistry.GREAT_SPEAR, Identifier.of(SimplySwords.MOD_ID, "spear"));
        SimplySwordsAPI.registerWeaponType(TagRegistry.DEER_HORNS, KHOPESH.weaponType());

        SimplySwordsAPI.registerWeaponType(ItemRegistry.THE_BLOOD_HARVESTER.get(), Identifier.of(SimplySwords.MOD_ID, "scythe"));
        SimplySwordsAPI.registerWeaponType(ItemRegistry.BLACK_PEARL.get(), Identifier.of(SimplySwords.MOD_ID, "cutlass"));
        SimplySwordsAPI.registerWeaponType(ItemRegistry.BLADE_OF_THE_GROTESQUE.get(), Identifier.of(SimplySwords.MOD_ID, "claymore"));
        SimplySwordsAPI.registerWeaponType(ItemRegistry.VIPERS_CALL.get(), Identifier.of(SimplySwords.MOD_ID, "chakram"));
        SimplySwordsAPI.registerWeaponType(ItemRegistry.MATTERBANE.get(), Identifier.of(SimplySwords.MOD_ID, "longsword"));
        SimplySwordsAPI.registerWeaponType(ItemRegistry.EARTHSHATTER.get(), Identifier.of(SimplySwords.MOD_ID, "greataxe"));
        SimplySwordsAPI.registerWeaponType(ItemRegistry.DEATHS_EYRIE.get(), Identifier.of(SimplySwords.MOD_ID, "claymore"));
        SimplySwordsAPI.registerWeaponType(ItemRegistry.REVVENGINE.get(), Identifier.of(SimplySwords.MOD_ID, "katana"));

        SimplySwordsAPI.registerWeaponType(ItemRegistry.MIMICRY_RAPIER.get(), Identifier.of(SimplySwords.MOD_ID, "rapier"));
        SimplySwordsAPI.registerWeaponType(ItemRegistry.MIMICRY_CUTLASS.get(), Identifier.of(SimplySwords.MOD_ID, "cutlass"));
        SimplySwordsAPI.registerWeaponType(ItemRegistry.MIMICRY_SAI.get(), Identifier.of(SimplySwords.MOD_ID, "sai"));
        SimplySwordsAPI.registerWeaponType(ItemRegistry.MIMICRY_DAGGER.get(), Identifier.of(SimplySwords.MOD_ID, "dagger"));
        SimplySwordsAPI.registerWeaponType(ItemRegistry.MIMICRY_CLAYMORE.get(), Identifier.of(SimplySwords.MOD_ID, "claymore"));
        SimplySwordsAPI.registerWeaponType(ItemRegistry.MIMICRY_LONGSWORD.get(), Identifier.of(SimplySwords.MOD_ID, "longsword"));
        SimplySwordsAPI.registerWeaponType(ItemRegistry.MIMICRY_GREATHAMMER.get(), Identifier.of(SimplySwords.MOD_ID, "greathammer"));
        SimplySwordsAPI.registerWeaponType(ItemRegistry.MIMICRY_KATANA.get(), Identifier.of(SimplySwords.MOD_ID, "katana"));
        SimplySwordsAPI.registerWeaponType(ItemRegistry.MIMICRY_SPEAR.get(), Identifier.of(SimplySwords.MOD_ID, "spear"));
        SimplySwordsAPI.registerWeaponType(ItemRegistry.MIMICRY_GLAIVE.get(), Identifier.of(SimplySwords.MOD_ID, "glaive"));
        SimplySwordsAPI.registerWeaponType(ItemRegistry.MIMICRY_HALBERD.get(), Identifier.of(SimplySwords.MOD_ID, "halberd"));
        SimplySwordsAPI.registerWeaponType(ItemRegistry.MIMICRY_WARGLAIVE.get(), Identifier.of(SimplySwords.MOD_ID, "warglaive"));
        SimplySwordsAPI.registerWeaponType(ItemRegistry.MIMICRY_CHAKRAM.get(), Identifier.of(SimplySwords.MOD_ID, "chakram"));
        SimplySwordsAPI.registerWeaponType(ItemRegistry.MIMICRY_SCYTHE.get(), Identifier.of(SimplySwords.MOD_ID, "scythe"));
        SimplySwordsAPI.registerWeaponType(ItemRegistry.MIMICRY_GREATAXE.get(), Identifier.of(SimplySwords.MOD_ID, "greataxe"));
        SimplySwordsAPI.registerWeaponType(ItemRegistry.MIMICRY_TWINBLADE.get(), Identifier.of(SimplySwords.MOD_ID, "twinblade"));
    }
}
