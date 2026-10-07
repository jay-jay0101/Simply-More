package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.TargetUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import net.sweenus.simplyswords.api.WeaponAbilityContext;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.UniqueWeaponActiveAbility;
import net.sweenus.simplyswords.registry.ParticlesRegistry;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;
import java.util.Optional;


public class DesolateRuinItem extends SimplyMoreUniqueSwordItem implements UniqueWeaponActiveAbility {
    public static DesolateRuinItem.EffectSettings SETTINGS = UNIQUE_CONFIG.desolate_ruin;

    public DesolateRuinItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, ServerWorld world, int consecutiveHits, boolean isFirstInTick) {
        if(ActiveAbilityManager.SERVER.isInAbility(attacker, ActiveAbilityManager.Type.WRAITH)) {
            ActiveAbilityManager.SERVER.stop(attacker, ActiveAbilityManager.Type.WRAITH);

            if(!target.isAlive()) {
                startAbility(attacker, SETTINGS.extraDuration);
            }
        }

        if (MathUtils.chance(attacker, SETTINGS.chance)) {
            AudioVisualUtils.playSound(world, target.getPos(), new Sound(SoundRegistry.ELEMENTAL_SWORD_FIRE_ATTACK_02.get()).randomisePitch(0.2f, 1.8f, attacker.getRandom()));
            AudioVisualUtils.particleCylinder(world, target.getPos(), ParticleTypes.END_ROD, 35, 0.35f, 0, -0.25f);
            AudioVisualUtils.particleCylinder(world, target.getPos(), ParticleTypes.WHITE_ASH, 300, 0.35f, 0, -0.25f);

            new TargetList(target).setOnFireFor(SETTINGS.flameTime / 20f, ActiveAbilityManager.Type.WHITE_FIRE);
        }

        RegistryEntry<StatusEffect> decaying = StatusEffectRegistry.getReference(StatusEffectRegistry.DECAYING);
        if(target.hasStatusEffect(decaying)) {
            EntityUtils.reapplyAndIncrementEffect(target, decaying, SETTINGS.decayingTime, 0, 0xFF);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return useFromDefaultInput(world, user, hand);
    }

    @Override
    public boolean canActivate(WeaponAbilityContext context) {
        return context.actor().isAlive();
    }

    @Override
    public boolean activate(WeaponAbilityContext context) {
        if(ActiveAbilityManager.SERVER.isInAbility(context.actor(), ActiveAbilityManager.Type.WRAITH)) {
            return tryBackstab(context.actor());
        } else {
            startAbility(context.actor(), SETTINGS.duration);
        }

        return true;
    }

    private boolean tryBackstab(LivingEntity attacker) {
        Optional<LivingEntity> possibleTarget = TargetUtils.findTarget(attacker, SETTINGS.range, TargetUtils.TargetType.ENEMIES);
        if(possibleTarget.isEmpty()) return false;
        LivingEntity target = possibleTarget.get();

        AudioVisualUtils.playSound(target.getWorld(), target.getPos(), new Sound(SoundRegistry.ELEMENTAL_SWORD_WIND_ATTACK_02.get()));
        AudioVisualUtils.particleAroundEntity(target, ParticlesRegistry.BLOOD_SPRAY.get(), 40, 0.2f, 0.35f);

        new TargetList(target).applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.DECAYING), SETTINGS.decayingTime, 0)
                .damageWithEnchants(10, attacker);

        EntityUtils.backstab(attacker, target);
        ActiveAbilityManager.SERVER.stop(attacker, ActiveAbilityManager.Type.WRAITH);

        if(!target.isAlive()) {
            startAbility(attacker, SETTINGS.extraDuration);
        }

        return true;
    }

    private void startAbility(LivingEntity owner, int duration) {
        Vec3d velocity = MathUtils.getDirectionalVector(owner.getYaw(), 0).offset(Direction.UP, 0.25f);

        owner.addVelocity(velocity.multiply(-0.75f));
        owner.velocityModified = true;

        AudioVisualUtils.particleAroundEntity(owner, ParticleTypes.CRIT, 100, 0.75f, 0.15f);
        AudioVisualUtils.playSound(owner.getWorld(), owner.getPos(), new Sound(SoundRegistry.ELEMENTAL_BOW_POISON_ATTACK_01.get()).setPitch(0.2f));
        ActiveAbilityManager.SERVER.start(owner, ActiveAbilityManager.Type.WRAITH, duration);
    }

    @Override
    public int getActivationCooldownTicks(ItemStack stack, WeaponAbilityContext context) {
        return ActiveAbilityManager.SERVER.isInAbility(context.actor(), ActiveAbilityManager.Type.WRAITH) ?
                SETTINGS.cooldownAfterStart : SETTINGS.cooldownAfterBackstab;
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.WHITE_ASH);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.desolate_ruin.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.desolate_ruin.tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.desolate_ruin.tooltip3").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.desolate_ruin.tooltip4").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.desolate_ruin.tooltip5").setStyle(Styles.TEXT));
        appendAbilityCooldownTooltip(tooltip, itemStack, SETTINGS.cooldownAfterBackstab);

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.DESOLATE_RUIN));
        }

        @ValidatedInt.Restrict(min = 0)
        public int cooldownAfterBackstab = 350;
        @ValidatedInt.Restrict(min = 0)
        public int cooldownAfterStart = 20;
        @ValidatedInt.Restrict(min = 0)
        public int duration = 300;
        @ValidatedInt.Restrict(min = 0)
        public int extraDuration = 100;
        @ValidatedFloat.Restrict(min = 0)
        public float range = 10f;
        @ValidatedFloat.Restrict(min = 0)
        public float damage = 10;
        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.25f;
        @ValidatedInt.Restrict(min = 0)
        public int flameTime = 100;
        @ValidatedInt.Restrict(min = 0)
        public int decayingTime = 200;
    }
}
