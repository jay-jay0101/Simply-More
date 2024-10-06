package net.rosemarythyme.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;


public class SoulForeseerItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = 100;

    public SoulForeseerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getWorld().isClient())
            return super.postHit(stack, target, attacker);

        if (attacker.getWorld() instanceof ServerWorld serverworld
                && attacker.getRandom().nextBetween(1, 100) <= effect.getForeseenChance()
                && !target.hasStatusEffect(ModEffectsRegistry.FORESEEN)) {
            serverworld.playSound(null, attacker.getBlockPos(), SoundRegistry.MAGIC_SHAMANIC_NORDIC_27.get(), SoundCategory.PLAYERS);
            target.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.FORESEEN, effect.getForeseenTime(), 0));
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 10, 0));
            serverworld.spawnParticles(ParticleTypes.SOUL_FIRE_FLAME, attacker.getX(), attacker.getY() + 1, attacker.getZ(), 50, 0.25f, 0.25f, 0.25f, 0.1);
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!player.getWorld().isClient()) {
            boolean hasAffectedEntity = false;
            int boxRange = effect.getJudgeTeleportRange();
            Box box = new Box(player.getX() - boxRange, player.getY() - boxRange, player.getZ() - boxRange, player.getX() + boxRange, player.getY() + boxRange, player.getZ() + 20);
            List<LivingEntity> entities = player.getWorld().getNonSpectatingEntities(LivingEntity.class, box);

            for (LivingEntity livingEntity : entities) {
                if (livingEntity == player || livingEntity.isTeammate(player) || !livingEntity.hasStatusEffect(ModEffectsRegistry.FORESEEN)) {
                    continue;
                }

                livingEntity.removeStatusEffect(ModEffectsRegistry.FORESEEN);
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, effect.getJudgeTeleportNegativeEffectTime(), 3));
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, effect.getJudgeTeleportNegativeEffectTime(), 0));
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, effect.getJudgeTeleportNegativeEffectTime(), 1));
                livingEntity.teleport(player.getX(), player.getY(), player.getZ());
                livingEntity.velocityModified = true;
                hasAffectedEntity = true;
            }

            if (hasAffectedEntity) {
                player.getWorld().playSound(null, player.getBlockPos(), SoundRegistry.MAGIC_SHAMANIC_NORDIC_22.get(), SoundCategory.PLAYERS);
                player.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
            }
        }
        return super.use(world, player, hand);
    }

    int stepMod = 0;
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        stepMod = SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.SOUL, ParticleTypes.SCULK_SOUL, ParticleTypes.WARPED_SPORE);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style rightClickStyle = HelperMethods.getStyle("rightclick");
        Style abilityStyle = HelperMethods.getStyle("ability");
        Style textStyle = HelperMethods.getStyle("text");

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.soul_foreseer.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.soul_foreseer.tooltip2").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.soul_foreseer.tooltip3").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.soul_foreseer.tooltip4").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.soul_foreseer.tooltip5", SimplyMoreHelperMethods.translateTicks(effect.getForeseenTime())).setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.soul_foreseer.tooltip6").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.soul_foreseer.tooltip7").setStyle(textStyle));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
}
