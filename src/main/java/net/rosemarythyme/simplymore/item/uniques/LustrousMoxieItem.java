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
            StatusEffectInstance radiantMarkEffect = target.getStatusEffect(ModEffectsRegistry.RADIANT_MARK);
            if (target.hasStatusEffect(ModEffectsRegistry.RADIANT_MARK) && radiantMarkEffect != null) {
                target.damage(attacker.getDamageSources().magic(),radiantMarkEffect.getAmplifier() + 1);
            }
            if (attacker.getRandom().nextBetween(1, 100) <= 20) {
                if (target.hasStatusEffect(ModEffectsRegistry.RADIANT_MARK) && radiantMarkEffect != null) {
                    int amplifier = radiantMarkEffect.getAmplifier() + 1;
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

        LivingEntity target = locateRadiantMarkedTarget(user);

        if(target != null) {
            damageAndKnockbackAndTeleportToRadiantMarkedTarget(target, user);
            damageAndKnockbackNearbyNonRadiantMarkedEntities(target, user);

            user.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.STUNNED_MOXIE, 30, 0));
            user.getWorld().playSound(null, user.getBlockPos(), SoundRegistry.ELEMENTAL_SWORD_ICE_ATTACK_01.get(), SoundCategory.PLAYERS);
            user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
        }
    }

    private LivingEntity locateRadiantMarkedTarget(PlayerEntity user) {
        Box box = new Box(user.getX() - 20,user.getY() - 20,user.getZ() - 20,user.getX() + 20,user.getY() + 20,user.getZ() + 20);
        List<LivingEntity> potentiallyMarkedLivingEntities = user.getWorld().getNonSpectatingEntities(LivingEntity.class, box);
        LivingEntity markedEntity = potentiallyMarkedLivingEntities.stream().filter(livingEntity -> livingEntity.hasStatusEffect(ModEffectsRegistry.RADIANT_MARK)).findAny().orElse(null);

        if (markedEntity == null || (markedEntity == user || markedEntity.isTeammate(user))) {
            return null;
        }

        return markedEntity;
    }

    private void damageAndKnockbackAndTeleportToRadiantMarkedTarget(LivingEntity targetEntity, PlayerEntity user) {
        if (targetEntity == user.getAttacking()) {
            user.teleport(targetEntity.getX(), targetEntity.getY(), targetEntity.getZ(), false);
            ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.WAX_OFF, user.getX(), user.getY() + 2, user.getZ(), 500, 3, 3, 3, 0);
            targetEntity.removeStatusEffect(ModEffectsRegistry.RADIANT_MARK);
            knockbackAndDamageEntity(targetEntity, user, 15, (float) 2.0);
        }
    }

    private void damageAndKnockbackNearbyNonRadiantMarkedEntities(LivingEntity targetEntity, PlayerEntity user) {
        Box box = new Box(user.getX() - 5,user.getY() - 5,user.getZ() - 5,user.getX() + 5,user.getY() + 5,user.getZ() + 5);
        List<LivingEntity> nearbyLivingEntities = user.getWorld().getNonSpectatingEntities(LivingEntity.class, box);
        nearbyLivingEntities.remove(targetEntity);
        for (LivingEntity livingEntity : nearbyLivingEntities) {
            knockbackAndDamageEntity(livingEntity, user, 10, (float) 2.5);
        }
    }

    private void knockbackAndDamageEntity(LivingEntity targetEntity, PlayerEntity user, int damage, float knockbackStrength) {
        if(targetEntity==user || targetEntity.isTeammate(user)) {
            return;
        }

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
        user.setCurrentHand(hand);
        return itemStack.getDamage() >= itemStack.getMaxDamage() - 1
                ? TypedActionResult.fail(itemStack)
                : TypedActionResult.consume(itemStack);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!user.getWorld().isClient && user instanceof PlayerEntity player) {
             if (remainingUseTicks == 1)
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
        Style rightClickStyle = HelperMethods.getStyle("rightclick");
        Style abilityStyle = HelperMethods.getStyle("ability");
        Style textStyle = HelperMethods.getStyle("text");

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip2").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip3").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip4").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip5").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip6").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip7").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip8").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip9").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip10").setStyle(textStyle));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
}
