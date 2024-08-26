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
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;

public class LustrousMoxieItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = 400;

    public LustrousMoxieItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if(target.hasStatusEffect(ModEffectsRegistry.RADIANT_MARK)) {
                target.damage(attacker.getDamageSources().magic(),target.getStatusEffect(ModEffectsRegistry.RADIANT_MARK).getAmplifier() + 1);
            }
            if (attacker.getRandom().nextBetween(1, 100) <= 20) {
                if(target.hasStatusEffect(ModEffectsRegistry.RADIANT_MARK)) {
                    int amplifier = target.getStatusEffect(ModEffectsRegistry.RADIANT_MARK).getAmplifier() + 1;
                    int duration = 240 - (amplifier * 40);
                    target.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.RADIANT_MARK, duration, amplifier), attacker);
                } else {
                    target.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.RADIANT_MARK, 200, 0), attacker);
                }
            }
        }
        return super.postHit(stack, target, attacker);
    }

    public void attack(PlayerEntity user) {
        if (user.getWorld().isClient()) {
            return;
        }

        locateAndTeleportToRadiantMarkedTarget(user);
        damageAndKnockbackRadiantMarkedTarget(user.getAttacking(), user, 15, 2f);
        damageAndKnockbackNearbyNonRadiantMarkedEntities(user, 5, 20, 2.5f);

        user.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.STUNNED_MOXIE,30,0));
        user.getWorld().playSound(null,user.getBlockPos(),SoundRegistry.ELEMENTAL_SWORD_ICE_ATTACK_01.get(), SoundCategory.PLAYERS);
        user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
    }

    private void locateAndTeleportToRadiantMarkedTarget(PlayerEntity user) {
        Box box = new Box(user.getX() - 20,user.getY() - 20,user.getZ() - 20,user.getX() + 20,user.getY() + 20,user.getZ() + 20);
        List<LivingEntity> potentiallyMarkedLivingEntities = user.getWorld().getNonSpectatingEntities(LivingEntity.class, box);
        LivingEntity markedEntity = potentiallyMarkedLivingEntities.stream().filter(livingEntity -> livingEntity.hasStatusEffect(ModEffectsRegistry.RADIANT_MARK)).findAny().orElse(null);

        if (markedEntity == null || (markedEntity == user || markedEntity.isTeammate(user))) {
            return;
        }

        if (potentiallyMarkedLivingEntities.size() > 1) {
            user.teleport(markedEntity.getX(), markedEntity.getY(), markedEntity.getZ(), false);
            ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.WAX_OFF, user.getX(), user.getY() + 2, user.getZ(), 500, 3, 3, 3, 0);
        }
    }

    private void damageAndKnockbackRadiantMarkedTarget(LivingEntity targetEntity, PlayerEntity user, int damage, float knockbackStrength) {
        if (targetEntity == user.getAttacking()) {
            targetEntity.removeStatusEffect(ModEffectsRegistry.RADIANT_MARK);
            knockbackAndDamageEntity(targetEntity, user, damage, knockbackStrength);
        }
    }

    private void damageAndKnockbackNearbyNonRadiantMarkedEntities(PlayerEntity user, int radius, int damage, float knockbackStrength) {
        Box box = new Box(user.getX() - radius,user.getY() - radius,user.getZ() - radius,user.getX() + radius,user.getY() + radius,user.getZ() + radius);
        List<LivingEntity> nearbyLivingEntities = user.getWorld().getNonSpectatingEntities(LivingEntity.class, box);
        for (LivingEntity livingEntity : nearbyLivingEntities) {
            nearbyLivingEntities.remove(user.getAttacking());
            knockbackAndDamageEntity(livingEntity, user, damage, knockbackStrength);
        }
    }

    private void knockbackAndDamageEntity(LivingEntity targetEntity, PlayerEntity user, int damage, float knockbackStrength) {
        targetEntity.damage(user.getDamageSources().playerAttack(user), damage);

        Vec3d userPosition = user.getPos();
        Vec3d entityPosition = targetEntity.getPos();

        double deltaX = entityPosition.getX() - userPosition.getX();
        double deltaZ = entityPosition.getZ() - userPosition.getZ();
        double distance = Math.hypot(deltaX, deltaZ);

        if (distance == 0) {
            return;
        }

        double normalizedDeltaX = deltaX / distance;
        double normalizedDeltaZ = deltaZ / distance;

        targetEntity.setVelocity(normalizedDeltaX * knockbackStrength, 0.2, normalizedDeltaZ * knockbackStrength);
        targetEntity.velocityModified = true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!user.getWorld().isClient && user instanceof PlayerEntity player) {
             if(remainingUseTicks == 1)
                 attack(player);
        }
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 15;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        int stepMod = 0;
        SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.WAX_OFF);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style RIGHTCLICK = HelperMethods.getStyle("rightclick");
        Style ABILITY = HelperMethods.getStyle("ability");
        Style TEXT = HelperMethods.getStyle("text");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip2").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip3").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip4").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip5").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip6").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(RIGHTCLICK));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip7").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip8").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip9").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip10").setStyle(TEXT));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
}
