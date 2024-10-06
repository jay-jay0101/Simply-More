package net.rosemarythyme.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.BlackPearlFireballEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;


public class BlackPearlItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = 180;

    public BlackPearlItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }


    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            int randomNumber = attacker.getRandom().nextBetween(1, 100);
            if (randomNumber <= 10) {
                List<StatusEffectInstance> positiveEffects = target.getStatusEffects().stream()
                        .filter(effect -> effect.getEffectType().getCategory() == StatusEffectCategory.BENEFICIAL)
                        .toList();

                if (!positiveEffects.isEmpty()) {
                    StatusEffectInstance plunderedEffect = positiveEffects.get(attacker.getRandom().nextInt(positiveEffects.size()));

                    int amplifier = Math.min(plunderedEffect.getAmplifier(), 4);
                    int duration = Math.min(plunderedEffect.getDuration(), 600);

                    if (plunderedEffect.getDuration() == -1) {
                        duration = 600;
                    }

                    StatusEffectInstance newEffect = new StatusEffectInstance(plunderedEffect.getEffectType(), duration, amplifier);

                    attacker.addStatusEffect(newEffect);
                    target.removeStatusEffect(plunderedEffect.getEffectType());

                    attacker.getWorld().playSound(null, attacker.getBlockPos(), SoundRegistry.DARK_SWORD_BLOCK.get(), SoundCategory.PLAYERS, 1, 1);
                }
            }
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {
            float velocityPower = 3.0f;
            float yawRadians = (float) Math.toRadians(user.getYaw() + 90);
            float pitchRadians = (float) Math.toRadians(user.getPitch());

            float velocityX = (float) (Math.cos(yawRadians) * Math.cos(pitchRadians)) * velocityPower;
            float velocityZ = (float) (Math.sin(yawRadians) * Math.cos(pitchRadians)) * velocityPower;
            float velocityY = (float) Math.sin(pitchRadians) * -velocityPower;

            BlackPearlFireballEntity fireballEntity = new BlackPearlFireballEntity(world, user, velocityX, velocityY, velocityZ);
            fireballEntity.setPos(
                    user.getX() + (velocityX / 2),
                    user.getEyeY() + (velocityY / 2),
                    user.getZ() + (velocityZ / 2)
            );
            world.spawnEntity(fireballEntity);
            user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
        }
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        int stepMod = 0;
        SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.LANDING_HONEY, ParticleTypes.LANDING_HONEY, ParticleTypes.ASH);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style rightClickStyle = HelperMethods.getStyle("rightclick");
        Style abilityStyle = HelperMethods.getStyle("ability");
        Style textStyle = HelperMethods.getStyle("text");

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.black_pearl.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.black_pearl.tooltip2").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.black_pearl.tooltip3").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.black_pearl.tooltip4").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.black_pearl.tooltip5").setStyle(textStyle));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
}
