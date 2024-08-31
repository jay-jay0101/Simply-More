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
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;

public class GrandfrostItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = 500;

    public GrandfrostItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            if (!attacker.getWorld().isClient()) {
                if (attacker.getRandom().nextBetween(1, 100) <= 25 || target.isBlocking()) {
                    target.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.CHILL, 140, 0), attacker);
                }
            }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.getWorld().isClient()) {
            return super.use(world, user, hand);
        }

        Box box = new Box(user.getX() - 5, user.getY() - 2, user.getZ() - 5, user.getX() + 5, user.getY() + 5, user.getZ() + 5);
        List<LivingEntity> livingEntities = user.getWorld().getNonSpectatingEntities(LivingEntity.class, box);

        if (livingEntities.size() > 1) {
            for (LivingEntity livingEntity : livingEntities) {
                if (livingEntity == user || livingEntity.isTeammate(user)) {
                    continue;
                }

                Vec3d userPosition = user.getPos();
                Vec3d entityPosition = livingEntity.getPos();

                double deltaX = entityPosition.getX() - userPosition.getX();
                double deltaZ = entityPosition.getZ() - userPosition.getZ();
                double distance = Math.hypot(deltaX, deltaZ);

                if (distance == 0) {
                    return super.use(world, user, hand);
                }

                float knockbackStrength = 3.5f;
                double normalizedDeltaX = deltaX / distance;
                double normalizedDeltaZ = deltaZ / distance;

                livingEntity.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.CHILL, 200, 0));
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 3));
                livingEntity.setVelocity(normalizedDeltaX * knockbackStrength, 0.4, normalizedDeltaZ * knockbackStrength);
                livingEntity.velocityModified = true;
            }
            user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
            ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.SNOWFLAKE, user.getX(), user.getY() + 3, user.getZ(), 1000, 3, 0, 3, 0.25);
            user.getWorld().playSound(null, user.getBlockPos(), SoundRegistry.ELEMENTAL_SWORD_ICE_ATTACK_03.get(), user.getSoundCategory(), 2F, 0.3F);
        }
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        int stepMod = 0;
        SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.ITEM_SNOWBALL, ParticleTypes.ITEM_SNOWBALL, ParticleTypes.SNOWFLAKE);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style rightClickStyle = HelperMethods.getStyle("rightclick");
        Style abilityStyle = HelperMethods.getStyle("ability");
        Style textStyle = HelperMethods.getStyle("text");

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.grandfrost.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.grandfrost.tooltip2").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.grandfrost.tooltip3").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.grandfrost.tooltip4").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.grandfrost.tooltip5").setStyle(textStyle));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
}
