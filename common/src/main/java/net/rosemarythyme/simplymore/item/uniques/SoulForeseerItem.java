package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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


public class SoulForeseerItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = 100;

    public SoulForeseerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.SWORD, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getWorld().isClient())
            return super.postHit(stack, target, attacker);

        if (attacker.getWorld() instanceof ServerWorld serverworld
                && MathUtils.chance(attacker, uniqueConfig.soul_foreseer.chance)
                && !target.hasStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.FORESEEN))) {
            serverworld.playSound(null, attacker.getBlockPos(), SoundRegistry.MAGIC_SHAMANIC_NORDIC_27.get(), SoundCategory.PLAYERS);
            target.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.getReference(ModEffectsRegistry.FORESEEN), uniqueConfig.soul_foreseer.effectTime, 0));
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 10, 0));
            serverworld.spawnParticles(ParticleTypes.SOUL_FIRE_FLAME, attacker.getX(), attacker.getY() + 1, attacker.getZ(), 50, 0.25f, 0.25f, 0.25f, 0.1);
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!player.getWorld().isClient()) {
            boolean hasAffectedEntity = false;
            Box box = MathUtils.createCubeBox(player.getPos(), uniqueConfig.soul_foreseer.range);
            List<LivingEntity> targets = AttackUtils.getTargets(player, box);

            for (LivingEntity livingEntity : targets) {
                if (!livingEntity.hasStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.FORESEEN))) continue;

                livingEntity.removeStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.FORESEEN));
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, uniqueConfig.soul_foreseer.effectTime, 3));
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, uniqueConfig.soul_foreseer.effectTime, 0));
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, uniqueConfig.soul_foreseer.effectTime, 1));
                livingEntity.teleport(player.getX(), player.getY(), player.getZ(), false);
                livingEntity.velocityModified = true;
                hasAffectedEntity = true;
            }

            if (hasAffectedEntity) {
                player.getWorld().playSound(null, player.getBlockPos(), SoundRegistry.MAGIC_SHAMANIC_NORDIC_22.get(), SoundCategory.PLAYERS);
                player.getItemCooldownManager().set(this, skillCooldown);
            }
        }
        return super.use(world, player, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        VisualEffectsUtils.handleFootfalls(entity, stack, world, ParticleTypes.SOUL, ParticleTypes.SCULK_SOUL, ParticleTypes.WARPED_SPORE);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.soul_foreseer.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.soul_foreseer.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.soul_foreseer.tooltip5", MathUtils.translateTicks(uniqueConfig.soul_foreseer.foreseenTime)).setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.soul_foreseer.tooltip6").setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ModItemsRegistry.SOUL_FORESEER));
        }

        @ValidatedInt.Restrict(min = 0)
        public int foreseenTime = 160;
        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.3f;
        @ValidatedInt.Restrict(min = 0)
        public int range = 20;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 80;
    }
}
