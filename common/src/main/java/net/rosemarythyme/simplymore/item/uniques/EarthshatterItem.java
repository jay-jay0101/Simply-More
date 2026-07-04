package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
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
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.VisualEffectsUtils;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class EarthshatterItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = effect.earthshatter.cooldown;

    public EarthshatterItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.GRANDSWORD, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if (MathUtils.chance(attacker, effect.earthshatter.chance)) {
                StatusEffectInstance armourCrunchEffect = target.getStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.ARMOUR_CRUNCH));
                if (armourCrunchEffect != null) {
                    int amplifier = armourCrunchEffect.getAmplifier() + 1;
                    target.addStatusEffect(
                            new StatusEffectInstance(
                                    ModEffectsRegistry.getReference(ModEffectsRegistry.ARMOUR_CRUNCH),
                                    200,
                                    amplifier
                            ), attacker);
                } else {
                    target.addStatusEffect(
                            new StatusEffectInstance(
                                    ModEffectsRegistry.getReference(ModEffectsRegistry.ARMOUR_CRUNCH),
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
        user.setCurrentHand(hand);
        return itemStack.getDamage() >= itemStack.getMaxDamage() - 1
                ? TypedActionResult.fail(itemStack)
                : TypedActionResult.consume(itemStack);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!user.getWorld().isClient && user instanceof PlayerEntity player) {
            if (remainingUseTicks == this.getMaxUseTime(stack, user) - 1)
                user.getWorld().playSound(null, user.getBlockPos(), SoundRegistry.DARK_SWORD_ENCHANT.get(), user.getSoundCategory(), 1.0f, 1.2f);
            if (remainingUseTicks == 1)
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
        player.getItemCooldownManager().set(this, skillCooldown);

        // Damage entities in range
        Box box = new Box(playerX - 4, playerY - 2, playerZ - 4, playerX + 4, playerY + 5, playerZ + 4);
        DamageSource damageSource = player.getDamageSources().playerAttack(player);
        for (LivingEntity livingEntity : serverWorld.getNonSpectatingEntities(LivingEntity.class, box)) {
            if (livingEntity == player || AttackUtils.checkFriendlyFire(livingEntity, player)) continue;
            livingEntity.damage(damageSource, 15);
            int effectTime = effect.earthshatter.slamEffectTime;
            livingEntity.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.getReference(ModEffectsRegistry.ARMOUR_CRUNCH), effectTime, 2));
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, effectTime, 1));
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, effectTime, 1));
            livingEntity.setVelocity(0, 1.2, 0);
            livingEntity.velocityModified = true;
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 40;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        VisualEffectsUtils.handleFootfalls(entity, stack, world, ParticleTypes.ASH);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.earthshatter.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.earthshatter.tooltip3").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.earthshatter.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.earthshatter.tooltip6").setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ModItemsRegistry.EARTHSHATTER));
        }


        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.15f;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 600;
        @ValidatedInt.Restrict(min = 0)
        public int slamEffectTime = 160;
    }
}
