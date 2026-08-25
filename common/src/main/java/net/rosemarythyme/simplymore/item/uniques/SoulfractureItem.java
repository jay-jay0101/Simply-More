package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.SoulFragmentEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.item.interfaces.HudOverlayItem;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.PredicateUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.sweenus.simplyswords.api.WeaponAbilityContext;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;
import net.sweenus.simplyswords.item.interfaces.UniqueWeaponActiveAbility;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class SoulfractureItem extends SimplyMoreUniqueSwordItem implements TwoHandedWeapon, UniqueWeaponActiveAbility, HudOverlayItem {
    public static final SoulfractureItem.EffectSettings SETTINGS = UNIQUE_CONFIG.soulfracture;

    public SoulfractureItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, ServerWorld world, int consecutiveHits, boolean isFirstInTick) {
        if(AttackUtils.canTarget(attacker, target, AttackUtils.AttackTarget.ENEMIES) && fragmentsFor(attacker, target).size() < 4) {
            if(MathUtils.chance(attacker, SETTINGS.chance)) {
                fragmentSoul(attacker, target);
            }
        }
    }

    private void fragmentSoul(LivingEntity attacker, LivingEntity target) {
        AttackUtils.spawnAbility(new SoulFragmentEntity(attacker, target.getEyePos(), target, attacker.getYaw() + 180), attacker);
        AudioVisualUtils.playSound(target.getWorld(), target.getPos(), new Sound(SoundRegistry.DARK_SWORD_ATTACK_WITH_BLOOD_03.get()));
        AudioVisualUtils.playSound(target.getWorld(), target.getPos(), new Sound(SoundEvents.ENTITY_VEX_DEATH));
        AudioVisualUtils.particleAroundEntity(target, ParticleTypes.WARPED_SPORE, 20, 0.25f, 0.4f);
        AudioVisualUtils.particleAroundEntity(target, ParticleTypes.SOUL_FIRE_FLAME, 40, 0.25f, 0.3f);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        return useFromDefaultInput(world, player, hand);
    }

    private static TargetList allFragments(LivingEntity attacker) {
        List<SoulFragmentEntity> targets = AttackUtils.getOwnedAbilities(attacker, SoulFragmentEntity.class);
        return new TargetList(new HashSet<>(targets)).filterByOwnedBy(attacker);
    }

    private static TargetList fragmentsFor(LivingEntity attacker, LivingEntity target) {
        return allFragments(attacker)
                .filter((fragment) -> {
                    Optional<UUID> person = ((SoulFragmentEntity)fragment).getPerson();
                    if(person.isEmpty()) return false;
                    return person.get() == target.getUuid();
                });
    }

    @Override
    public boolean canActivate(WeaponAbilityContext context) {
        return true;
    }

    @Override
    public boolean activate(WeaponAbilityContext context) {
        TargetList fragments = allFragments(context.actor());

        TargetList fullyFracturedEnemies = AttackUtils.cubeAttack(context.actor(), context.actor().getPos(), 50, AttackUtils.AttackTarget.ENEMIES)
                .filter((target) -> fragmentsFor(context.actor(), target).size() >= 4);

        TargetList closeEnemies = AttackUtils.cylinderAttack(context.actor(), context.actor().getPos(), SETTINGS.fragmentRadius, 3, AttackUtils.AttackTarget.ENEMIES)
                .exclude(fullyFracturedEnemies);

        if (closeEnemies.isEmpty() && fragments.isEmpty() && fullyFracturedEnemies.isEmpty()) return false;

        AudioVisualUtils.particleCylinder(context.world(), context.origin().offset(Direction.DOWN, 0.1f), ParticleTypes.SOUL_FIRE_FLAME, 300, (float) SETTINGS.fragmentRadius, 0, -0.2f);
        AudioVisualUtils.particleCylinder(context.world(), context.origin().offset(Direction.UP, 0.2f), ParticleTypes.SOUL, 150, (float) SETTINGS.fragmentRadius, 0, 0.02f);
        AudioVisualUtils.playSound(context.world(), context.origin(), new Sound(SoundRegistry.ELEMENTAL_SWORD_THUNDER_ATTACK_01.get()));
        AudioVisualUtils.playSound(context.world(), context.origin(), new Sound(SoundRegistry.SWING_OMEN_ONE.get()).setPitch(0));

        fragments.onEach((fragment) -> ((SoulFragmentEntity) fragment).rotate180());
        closeEnemies
                .damage(SETTINGS.stealFragmentDamage, context.actor().getDamageSources().indirectMagic(context.actor(), context.actor()))
                .applyEffect(StatusEffects.BLINDNESS, SETTINGS.blindDuration, 0)
                .onEach((target) -> this.fragmentSoul(context.actor(), target));

        fullyFracturedEnemies
                .damage(SETTINGS.returningFragmentDamage, context.actor().getDamageSources().indirectMagic(context.actor(), context.actor()))
                .onEach((target) -> fragmentsFor(context.actor(), target).onEach(
                        (fragment) -> ((SoulFragmentEntity) fragment).startConsumption()
                ))
                .applyEffect(StatusEffects.BLINDNESS, SETTINGS.blindDuration, 0)
                .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.STUN), SETTINGS.stunDuration, 0)
                .onEach((target) -> AudioVisualUtils.particleLine(context.world(), context.actor().getEyePos(), target.getEyePos(), ParticleTypes.SOUL, 0.25f, 2, 0.1f, 0.1f))
                .onFirst((target) -> {
                    context.actor().teleport(target.getX(), target.getY(), target.getZ(), false);
                    AudioVisualUtils.playSound(context.world(), context.origin(), new Sound(SoundEvents.ENTITY_WARDEN_SONIC_BOOM).setPitch(1.6f));
                });

        if(fullyFracturedEnemies.isPopulated()) {
            context.actor().addVelocity(MathUtils.getDirectionalVector(context.actor().getYaw(), context.actor().getPitch()).multiply(-1));
            context.actor().velocityModified = true;
        }

        return true;
    }

    @Override
    public int getActivationCooldownTicks(ItemStack stack, WeaponAbilityContext context) {
        return SETTINGS.cooldown;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if(world.isClient || !(entity instanceof LivingEntity attacker) || !selected) return;

        AttackUtils.cubeAttack(attacker, attacker.getPos(), 50, AttackUtils.AttackTarget.ENEMIES)
                .onEach((targetE) -> {
                    int fragments = fragmentsFor(attacker, targetE).size();
                    TargetList target = new TargetList(targetE);

                    if(fragments >= 1) {
                        target.applyEffect(StatusEffects.WEAKNESS, 10, 1);
                    }

                    if(fragments >= 2) {
                        target.applyEffect(StatusEffects.SLOWNESS, 10, 1);
                    }

                    if(fragments >= 3) {
                        target.applyEffect(StatusEffects.MINING_FATIGUE, 10, 1);
                    }

                    if(fragments >= 4) {
                        target.applyEffect(StatusEffects.WITHER, 10, 1)
                                .removeStatusEffects(PredicateUtils.BENEFICIAL_EFFECT);
                    }
                });
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.SOUL, ParticleTypes.SCULK_SOUL, ParticleTypes.WARPED_SPORE);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.soulfracture.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.soulfracture.tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.soulfracture.tooltip3").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.soulfracture.tooltip4").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.soulfracture.tooltip5").setStyle(Styles.TEXT));
        appendAbilityCooldownTooltip(tooltip, SETTINGS.cooldown);

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.SOULFRACTURE));
        }
        @ValidatedDouble.Restrict
        public double fragmentRadius = 6;
        @ValidatedFloat.Restrict
        public float fragmentPassiveRotationSpeed = 360 / (20 * 28f);
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 500;
        @ValidatedFloat.Restrict(min = 0f)
        public float chance = 0.3f;
        @ValidatedFloat.Restrict(min = 0f)
        public float fragmentHeal = 2f;
        @ValidatedFloat.Restrict(min = 0f)
        public float stealFragmentDamage = 3f;
        @ValidatedFloat.Restrict(min = 0f)
        public float returningFragmentDamage = 12f;
        @ValidatedInt.Restrict(min = 0)
        public int blindDuration = 100;
        @ValidatedInt.Restrict(min = 0)
        public int stunDuration = 20;
    }
}
