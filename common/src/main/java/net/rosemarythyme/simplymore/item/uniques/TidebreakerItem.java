package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.VisualEffectsUtils;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class TidebreakerItem extends SimplyMoreUniqueSwordItem {

    int skillCooldown = UNIQUE_CONFIG.tidebreaker.cooldown;
    int lastHitTime;
    LivingEntity lastHit;

    public TidebreakerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.SWORD, settings);
    }
    
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if (MathUtils.chance(attacker, UNIQUE_CONFIG.tidebreaker.chance)) {
                if (!attacker.hasStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.TIDEBREAKER))) {
                    attacker.addStatusEffect(new StatusEffectInstance(StatusEffectRegistry.getReference(StatusEffectRegistry.TIDEBREAKER), UNIQUE_CONFIG.tidebreaker.cloudTime, 0), attacker);
                }
            }

            lastHitTime = 0;
            lastHit = target;

        }
        return super.postHit(stack, target, attacker);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.getWorld().isClient())
            return super.use(world, user, hand);

        if (lastHit == null
                || !lastHit.isAlive()
                || lastHit.getWorld() != user.getWorld()
                || lastHit.distanceTo(user) > UNIQUE_CONFIG.tidebreaker.range)
            return super.use(world, user, hand);

        if (shouldTeleport(user, lastHit)) {
            swapUserAndTarget(user, lastHit);
            resetLastHit();
            user.getItemCooldownManager().set(this, skillCooldown);
        }


        return super.use(world, user, hand);
    }

    private boolean shouldTeleport(PlayerEntity user, LivingEntity target) {
        return target.getWorld() == user.getWorld() && target.distanceTo(user) <= UNIQUE_CONFIG.tidebreaker.range;
    }

    private void swapUserAndTarget(PlayerEntity user, LivingEntity target) {
        World world = user.getWorld();
        ServerWorld serverWorld = (ServerWorld) world;

        double targetX = target.getX();
        double targetY = target.getY();
        double targetZ = target.getZ();

        double userX = user.getX();
        double userY = user.getY();
        double userZ = user.getZ();

        user.teleport(targetX, targetY, targetZ, false);
        target.teleport(userX, userY, userZ, false);

        world.playSound(null, targetX, targetY, targetZ, SoundRegistry.ELEMENTAL_BOW_WATER_SHOOT_IMPACT_02.get(), SoundCategory.PLAYERS, 1, 1);
        world.playSound(null, userX, userY, userZ, SoundRegistry.ELEMENTAL_BOW_WATER_SHOOT_IMPACT_02.get(), SoundCategory.PLAYERS, 1, 1);

        serverWorld.spawnParticles(ParticleTypes.SPLASH, targetX, targetY, targetZ, 300, 2, 0, 2, 0);
        serverWorld.spawnParticles(ParticleTypes.SPLASH, userX, userY, userZ, 300, 2, 0, 2, 0);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient) {
            lastHitTime++;

            if (lastHitTime > UNIQUE_CONFIG.tidebreaker.teleportTime)
                resetLastHit();
        }
        VisualEffectsUtils.handleFootfalls(entity, stack, world, ParticleTypes.BUBBLE, ParticleTypes.BUBBLE, ParticleTypes.FALLING_WATER);
        super.inventoryTick(stack, world, entity, slot, selected);
    }


    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.tidebreaker.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.tidebreaker.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.tidebreaker.tooltip5",
                MathUtils.translateTicks(UNIQUE_CONFIG.tidebreaker.teleportTime),
                UNIQUE_CONFIG.tidebreaker.range).setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }
    
    // Made this a separate method to make it easier to understand what is going on for a reader of the code
    private void resetLastHit() {
        lastHit = null;
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.TIDEBREAKER));
        }

        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 400;
        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.25f;
        @ValidatedInt.Restrict(min = 0)
        public int cloudTime = 300;
        @ValidatedInt.Restrict(min = 0)
        public int range = 15;
        @ValidatedInt.Restrict(min = 0)
        public int teleportTime = 200;
    }
}
