package net.rosemarythyme.simplymore.item.uniques;

import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.item.UniqueSwordItem;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;

public class EarthshatterItem extends UniqueSwordItem {
    int skillCooldown = 600;

    public EarthshatterItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if (attacker.getRandom().nextBetween(1, 100) <= 15) {
                StatusEffectInstance armourCrunchEffect = target.getStatusEffect(ModEffectsRegistry.ARMOUR_CRUNCH);
                if (armourCrunchEffect != null) {
                    int amplifier = armourCrunchEffect.getAmplifier() + 1;
                    target.addStatusEffect(
                            new StatusEffectInstance(
                                    ModEffectsRegistry.ARMOUR_CRUNCH,
                                    200,
                                    amplifier
                            ), attacker);
                } else {
                    target.addStatusEffect(
                            new StatusEffectInstance(
                                    ModEffectsRegistry.ARMOUR_CRUNCH,
                                    200,
                                    0
                            ), attacker);
                }
            }
        }
        return super.postHit(stack, target, attacker);
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
            if(remainingUseTicks == this.getMaxUseTime(null) - 1)
                user.getWorld().playSound(null, user.getBlockPos(), SoundRegistry.DARK_SWORD_ENCHANT.get(), user.getSoundCategory(), 1.0f, 1.2f);
            if(remainingUseTicks == 1)
                attack(world, player);
        }
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    private void attack(World world, PlayerEntity player) {
        // Store frequently used values
        double playerX = player.getX();
        double playerY = player.getY();
        double playerZ = player.getZ();
        ServerWorld serverWorld = (ServerWorld) player.getWorld();

        // Spawn particles
        BlockStateParticleEffect particleEffect = new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.DIRT.getDefaultState());
        serverWorld.spawnParticles(particleEffect, playerX, playerY + 1, playerZ, 500, 3, 1, 3, 0);

        // Play sounds
        SoundCategory soundCategory = player.getSoundCategory();
        serverWorld.playSound(null, player.getBlockPos(), SoundRegistry.ELEMENTAL_SWORD_FIRE_ATTACK_01.get(), soundCategory, 1, 0);
        serverWorld.playSound(null, player.getBlockPos(), SoundRegistry.ELEMENTAL_SWORD_FIRE_ATTACK_02.get(), soundCategory, 1, 0);
        serverWorld.playSound(null, player.getBlockPos(), SoundRegistry.ELEMENTAL_SWORD_FIRE_ATTACK_03.get(), soundCategory, 1, 0);

        // Set cooldown
        player.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);

        // Damage entities in range
        Box box = new Box(playerX - 4, playerY - 2, playerZ - 4, playerX + 4, playerY + 5, playerZ + 4);
        DamageSource damageSource = player.getDamageSources().playerAttack(player);
        for (LivingEntity livingEntity : serverWorld.getNonSpectatingEntities(LivingEntity.class, box)) {
            if (livingEntity == player || livingEntity.isTeammate(player)) continue;
            livingEntity.damage(damageSource, 15);
            livingEntity.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.ARMOUR_CRUNCH, 160, 2));
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 160, 1));
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 160, 1));
            livingEntity.setVelocity(0, 1.2, 0);
            livingEntity.velocityModified = true;
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 40;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style RIGHTCLICK = HelperMethods.getStyle("rightclick");
        Style ABILITY = HelperMethods.getStyle("ability");
        Style TEXT = HelperMethods.getStyle("text");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.earthshatter.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.earthshatter.tooltip2").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.earthshatter.tooltip3").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.earthshatter.tooltip4").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.earthshatter.tooltip5").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(RIGHTCLICK));
        tooltip.add(Text.translatable("item.simplymore.earthshatter.tooltip6").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.earthshatter.tooltip7").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.earthshatter.tooltip8").setStyle(TEXT));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        int stepMod = 0;
        SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.ASH);
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
