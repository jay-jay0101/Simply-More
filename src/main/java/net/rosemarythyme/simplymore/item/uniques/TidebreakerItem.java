package net.rosemarythyme.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;


public class TidebreakerItem extends SimplyMoreUniqueSwordItem {

    int skillCooldown = effect.getInsanityTeleportCooldown();
    int lastHitTime;
    LivingEntity lastHit;

    public TidebreakerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }
    
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if (attacker.getRandom().nextBetween(1, 100) <= effect.getInsanityCloudChance()) {
                if (!attacker.hasStatusEffect(ModEffectsRegistry.TIDEBREAKER)) {
                    attacker.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.TIDEBREAKER, effect.getInsanityCloudDuration(), 0), attacker);
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
                || lastHit.distanceTo(user) > effect.getInsanityTeleportMaxDistance())
            return super.use(world, user, hand);

        if (shouldTeleport(user, lastHit)) {
            swapUserAndTarget(user, lastHit);
            resetLastHit();
            user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
        }


        return super.use(world, user, hand);
    }

    private boolean shouldTeleport(PlayerEntity user, LivingEntity target) {
        return target.getWorld() == user.getWorld() && target.distanceTo(user) <= effect.getInsanityTeleportMaxDistance();
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

        user.teleport(targetX, targetY, targetZ);
        target.teleport(userX, userY, userZ);

        world.playSound(null, targetX, targetY, targetZ, SoundRegistry.ELEMENTAL_BOW_WATER_SHOOT_IMPACT_02.get(), SoundCategory.PLAYERS, 1, 1);
        world.playSound(null, userX, userY, userZ, SoundRegistry.ELEMENTAL_BOW_WATER_SHOOT_IMPACT_02.get(), SoundCategory.PLAYERS, 1, 1);

        serverWorld.spawnParticles(ParticleTypes.SPLASH, targetX, targetY, targetZ, 300, 2, 0, 2, 0);
        serverWorld.spawnParticles(ParticleTypes.SPLASH, userX, userY, userZ, 300, 2, 0, 2, 0);
    }

    int stepMod = 0;
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient) {
            lastHitTime++;

            if (lastHitTime > effect.getInsanityTeleportMaxTime())
                resetLastHit();
        }
        stepMod = SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.BUBBLE, ParticleTypes.BUBBLE, ParticleTypes.FALLING_WATER);
        super.inventoryTick(stack, world, entity, slot, selected);
    }


    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style rightClickStyle = HelperMethods.getStyle("rightclick");
        Style abilityStyle = HelperMethods.getStyle("ability");
        Style textStyle = HelperMethods.getStyle("text");

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.tidebreaker.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.tidebreaker.tooltip2").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.tidebreaker.tooltip3").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.tidebreaker.tooltip4").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.tidebreaker.tooltip5").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.tidebreaker.tooltip6").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.tidebreaker.tooltip7",
                SimplyMoreHelperMethods.translateTicks(effect.getInsanityTeleportMaxTime()),
                effect.getInsanityTeleportMaxDistance()).setStyle(textStyle));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
    
    // Made this a separate method to make it easier to understand what is going on for a reader of the code
    private void resetLastHit() {
        lastHit = null;
    }
}
