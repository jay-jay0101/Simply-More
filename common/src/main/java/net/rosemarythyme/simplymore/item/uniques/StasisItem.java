package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.LightningPointEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.item.interfaces.StoppableAbilityItem;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.rosemarythyme.simplymore.world.PlayerItemUseManager;
import net.sweenus.simplyswords.api.WeaponAbilityActivationSource;
import net.sweenus.simplyswords.api.WeaponAbilityContext;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.UniqueWeaponActiveAbility;
import net.sweenus.simplyswords.util.Styles;
import net.sweenus.simplyswords.world.WeaponAbilityCooldownManager;

import java.util.HashSet;
import java.util.List;

public class StasisItem extends SimplyMoreUniqueSwordItem implements UniqueWeaponActiveAbility, StoppableAbilityItem {
    public static StasisItem.EffectSettings SETTINGS = UNIQUE_CONFIG.stasis;

    public StasisItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, ServerWorld world, int consecutiveHits, boolean isFirstInTick) {
        if (MathUtils.chance(attacker, UNIQUE_CONFIG.stasis.chance)) {
            AudioVisualUtils.playSound(target.getWorld(), target.getPos(), new Sound(SoundEvents.ITEM_TRIDENT_THUNDER.value(), 0.5f, 2f));
            AudioVisualUtils.particleAroundEntity(target, ParticleTypes.ELECTRIC_SPARK, 50, 0.25f, 0.1f);

            if (target instanceof PlayerEntity playerTarget) {
                for (ItemStack item : playerTarget.getInventory().main) {
                    if (!playerTarget.getItemCooldownManager().isCoolingDown(item.getItem())) {
                        playerTarget.getItemCooldownManager().set(item.getItem(), SETTINGS.stunTime);
                    }
                }
            } else {
                WeaponAbilityCooldownManager.setCooldown((ServerWorld) target.getWorld(), target, target.getStackInHand(Hand.MAIN_HAND), SETTINGS.stunTime);
                WeaponAbilityCooldownManager.setCooldown((ServerWorld) target.getWorld(), target, target.getStackInHand(Hand.OFF_HAND), SETTINGS.stunTime);
            }
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
        if(context.activationSource() == WeaponAbilityActivationSource.PLAYER) return false;

        for(int i = 0; i < SETTINGS.strikes; i++) {
            AttackUtils.spawnAbility(new LightningPointEntity(context.actor(), context.origin(), i), context.actor());
        }

        return true;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingTicks) {
        if(world.isClient) return;

        if (remainingTicks == getMaxUseTime(stack, user) - 1) {
            for(int i = 0; i < SETTINGS.strikes; i++) {
                AttackUtils.spawnAbility(new LightningPointEntity(user, user.getPos(), i), user);
            }
        }

        ServerWorld serverWorld = (ServerWorld) world;
        if (remainingTicks % 4 == 0) {
            AudioVisualUtils.playSound(serverWorld, user.getPos(), new Sound(SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER).setVolume(0.5f));
        }

        if (remainingTicks < 1 && user instanceof PlayerEntity player) {
            PlayerItemUseManager.stop(player, stack, true);
        }
    }

    @Override
    public void stop(ItemStack stack, World world, LivingEntity user, int remainingDuration) {
        if(remainingDuration > 1) {
            new TargetList(new HashSet<>(user.getWorld().getNonSpectatingEntities(LightningPointEntity.class, MathUtils.createCubeBox(user.getPos(), 50))))
                    .filterByOwnedBy(user)
                    .discard();
            return;
        }

        if(user instanceof PlayerEntity player) {
            player.getItemCooldownManager().set(this, SETTINGS.cooldown);
        } else {
            WeaponAbilityCooldownManager.setCooldown((ServerWorld) world, user, stack, SETTINGS.cooldown);
        }
    }

    @Override
    public TypedActionResult<ItemStack> startPlayerAbility(World world, PlayerEntity user, Hand hand) {
        if(!(world instanceof ServerWorld serverWorld)) return TypedActionResult.pass(user.getStackInHand(hand));
        return AttackUtils.holdToUse(serverWorld, user, hand);
    }

    @Override
    public int getActivationCooldownTicks(ItemStack stack, WeaponAbilityContext context) {
        return SETTINGS.cooldown;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return UNIQUE_CONFIG.stasis.strikeWindup;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.GLOW);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.stasis.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.stasis.tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.stasis.tooltip3").setStyle(Styles.TEXT));
        appendAbilityCooldownTooltip(tooltip, SETTINGS.cooldown);

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.STASIS));
        }

        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 300;
        @ValidatedInt.Restrict(min = 0)
        public int stunTime = 180;
        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.2f;
        @ValidatedFloat.Restrict(min = 0f)
        public float strikeDamage = 8;
        @ValidatedInt.Restrict(min = 0)
        public int strikeWindup = 30;
        @ValidatedDouble.Restrict(min = 0)
        public double radius = 3;
        @ValidatedInt.Restrict(min=1, max=10)
        public int strikes = 3;
        @ValidatedInt.Restrict(min=1)
        public int delay = 10;
    }
}
