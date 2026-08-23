package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.LightOrbEntity;
import net.rosemarythyme.simplymore.entity.LightbeamEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.item.interfaces.StoppableAbilityItem;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.world.PlayerItemUseManager;
import net.sweenus.simplyswords.api.WeaponAbilityActivationSource;
import net.sweenus.simplyswords.api.WeaponAbilityContext;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;
import net.sweenus.simplyswords.item.interfaces.UniqueWeaponActiveAbility;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class LustrousMoxieItem extends SimplyMoreUniqueSwordItem implements TwoHandedWeapon, UniqueWeaponActiveAbility, StoppableAbilityItem {
    public static LustrousMoxieItem.EffectSettings SETTINGS = UNIQUE_CONFIG.lustrous_moxie;

    public LustrousMoxieItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, ServerWorld world, int consecutiveHits, boolean isFirstInTick) {
        if(MathUtils.chance(attacker, SETTINGS.chance)) {
            List<LightOrbEntity> orbs = AttackUtils.getOwnedAbilities(attacker, LightOrbEntity.class)
                            .stream().filter(orb -> orb.getPerson().isPresent() && orb.getPerson().get().equals(target.getUuid())).toList();

            AudioVisualUtils.playSound(world, target.getPos(), new Sound(SoundRegistry.ELEMENTAL_BOW_HOLY_SHOOT_IMPACT_03.get()));
            AudioVisualUtils.particleAroundEntity(target, ParticleTypes.FLASH, 1, 0, 0);

            if(orbs.size() >= SETTINGS.maxOrbs) {
                orbs.forEach(LightOrbEntity::explode);
                AudioVisualUtils.playSound(world, target.getPos(), new Sound(SoundEvents.ITEM_TRIDENT_THUNDER.value()));
            } else {
                AttackUtils.spawnAbility(new LightOrbEntity(attacker, target.getPos(), target, orbs.size()), attacker);
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> startPlayerAbility(World world, PlayerEntity user, Hand hand) {
        if(!(world instanceof ServerWorld serverWorld)) return TypedActionResult.pass(user.getStackInHand(hand));

        List<LightbeamEntity> beams = AttackUtils.getOwnedAbilities(user, LightbeamEntity.class).stream().filter(LightbeamEntity::isFired).toList();
        if(!beams.isEmpty()) {
            teleport(user, beams.getFirst());
            return TypedActionResult.pass(user.getStackInHand(hand));
        }

        return AttackUtils.holdToUse(serverWorld, user, hand);
    }

    public static BlockPos getTeleportPos(LightbeamEntity beam) {
        BlockPos beamPos = beam.getBlockPos();

        for (int i = 0; i <= 5; i++) {
            BlockPos pos = beamPos.up(i);

            if (beam.getWorld().getBlockState(pos).isAir()) {
                return pos;
            }
        }

        return null;
    }

    private void teleport(LivingEntity user, LightbeamEntity beam) {
        BlockPos pos = getTeleportPos(beam);
        if(pos == null) return;

        AudioVisualUtils.particleAroundEntity(user, ParticleTypes.WAX_OFF, 40, 0.25f, 0);
        AudioVisualUtils.playSound(user.getWorld(), user.getPos(), new Sound(SoundRegistry.SWING_OMEN_ONE.get()).setPitch(2));

        user.teleport((ServerWorld) user.getWorld(), pos.getX(), pos.getY(), pos.getZ(), PositionFlag.getFlags(6), user.getYaw(), user.getPitch());

        AudioVisualUtils.particleAroundEntity(user, ParticleTypes.FIREWORK, 100, 0.25f, 0.2f);
        AudioVisualUtils.particleAroundEntity(user, ParticleTypes.FLASH, 1, 0, 0f);
        AudioVisualUtils.playSound(user.getWorld(), user.getPos(), new Sound(SoundRegistry.SWING_OMEN_ONE.get()).setPitch(2));

        beam.stopFiring();

        if(user instanceof PlayerEntity player) {
            player.getItemCooldownManager().set(this, SETTINGS.cooldown);
            PlayerItemUseManager.stop(player, player.getActiveItem(), false);
        }
    }

    @Override
    public int getActivationCooldownTicks(ItemStack stack, WeaponAbilityContext context) {
        return SETTINGS.cooldown;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return AttackUtils.PSEUDOINFINITE_DURATION;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return useFromDefaultInput(world, user, hand);
    }

    @Override
    public boolean activate(WeaponAbilityContext context) {
        if(context.activationSource() == WeaponAbilityActivationSource.PLAYER) return false;

        LightbeamEntity beam = new LightbeamEntity(context.actor(), context.origin());
        AttackUtils.spawnAbility(beam, context.actor());
        beam.tryFire();

        AudioVisualUtils.playSound(context.world(), context.origin(), new Sound(SoundRegistry.MAGIC_SWORD_SPELL_02.get()).setPitch(0));
        AudioVisualUtils.playSound(context.world(), context.origin(), new Sound(SoundEvents.ITEM_TRIDENT_THUNDER.value()));

        return true;
    }

    @Override
    public boolean canActivate(WeaponAbilityContext context) {
        return context.actor().isAlive();
    }

    @Override
    public void stop(ItemStack stack, ServerWorld world, LivingEntity user, int remainingDuration) {
        int chargeTime = getMaxUseTime(stack, user) - remainingDuration;

        List<LightbeamEntity> beams = AttackUtils.getOwnedAbilities(user, LightbeamEntity.class);

        if(chargeTime < 10) {
            beams.forEach(LightbeamEntity::stopFiring);

            if(user instanceof PlayerEntity player) {
                player.getItemCooldownManager().set(ItemRegistry.LUSTROUS_MOXIE.get(), LustrousMoxieItem.SETTINGS.cancelCooldown);
            }
        } else {
            beams.forEach(LightbeamEntity::tryFire);
            AudioVisualUtils.playSound(world, user.getPos(), new Sound(SoundEvents.ITEM_TRIDENT_THUNDER.value()));
        }
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingTicks) {
        if(world.isClient) return;

        if (remainingTicks == getMaxUseTime(stack, user) - 1) {
            AttackUtils.spawnAbility(new LightbeamEntity(user, user.getPos()), user);
            AudioVisualUtils.playSound(world, user.getPos(), new Sound(SoundRegistry.MAGIC_SWORD_SPELL_02.get()).setPitch(0));
        }
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.WAX_OFF);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip2", SETTINGS.maxOrbs).setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip3").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip4").setStyle(Styles.TEXT));
        appendAbilityCooldownTooltip(tooltip, SETTINGS.cooldown);

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.LUSTROUS_MOXIE));
        }

        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.2f;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 400;
        @ValidatedInt.Restrict(min = 0)
        public int cancelCooldown = 200;
        @ValidatedInt.Restrict(min = 0)
        public int orbDuration = 2400;
        @ValidatedInt.Restrict(min = 0)
        public int maxOrbs = 3;
        @ValidatedFloat.Restrict(min = 0)
        public float explosionDamage = 8;
        @ValidatedInt.Restrict(min = 0)
        public int dazzleDuration = 120;
        @ValidatedFloat.Restrict(min = 0)
        public float minSpeed = 0.8f;
        @ValidatedFloat.Restrict(min = 0)
        public float maxSpeed = 2f;
        @ValidatedInt.Restrict(min = 0)
        public int beamDuration = 40;
        @ValidatedInt.Restrict(min = 0)
        public int maxChargeTime = 40;
        @ValidatedFloat.Restrict(min = 0)
        public float minSize = 1f;
        @ValidatedFloat.Restrict(min = 0)
        public float maxSize = 2f;
        @ValidatedInt.Restrict(min = 0)
        public int glowDuration = 80;
        @ValidatedFloat.Restrict(min = 0)
        public int beamDamage = 8;
    }
}
