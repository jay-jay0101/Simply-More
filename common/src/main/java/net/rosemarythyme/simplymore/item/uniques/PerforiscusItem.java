package net.rosemarythyme.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.FlowerFieldAreaEffectCloudEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class PerforiscusItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = effect.getPerforiscusCooldown();

    public static final int maxBloom = 15;

    public PerforiscusItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }



    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getWorld().isClient())
            return super.postHit(stack, target, attacker);

        int amplifier = attacker.hasStatusEffect(ModEffectsRegistry.BLOOM.get()) ?
                attacker.getStatusEffect(ModEffectsRegistry.BLOOM.get()).getAmplifier() +1 : 0;

        amplifier = Math.min(amplifier, maxBloom);

        attacker.addStatusEffect(
                new StatusEffectInstance(
                        ModEffectsRegistry.BLOOM.get(),
                        effect.getPerforiscusBloomTime(),
                        amplifier
                )
        );

        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        int amplifier = 0;

        try {
            amplifier = user.getStatusEffect(ModEffectsRegistry.BLOOM.get()).getAmplifier();
        } catch (NullPointerException ignored) {
        }

        if (amplifier >= 7) {
            user.getWorld().spawnEntity(
                    new FlowerFieldAreaEffectCloudEntity(
                            user.getWorld(),
                            user.getX(),
                            user.getY(),
                            user.getZ(),
                            user
                    )
            );

            StatusEffectInstance effect = user.getStatusEffect(ModEffectsRegistry.BLOOM.get());
            user.removeStatusEffect(ModEffectsRegistry.BLOOM.get());

            if(amplifier > 7) {
                user.addStatusEffect(new StatusEffectInstance(
                        ModEffectsRegistry.BLOOM.get(),
                        effect.getDuration(),
                        effect.getAmplifier() - 8
                ));
            }

            user.getItemCooldownManager().set(this, skillCooldown);
        }

        return super.use(world, user, hand);
    }


    int stepMod = 0;
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        stepMod = SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.FALLING_SPORE_BLOSSOM);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style rightClickStyle = Styles.RIGHT_CLICK;
        Style abilityStyle = Styles.ABILITY;
        Style textStyle = Styles.TEXT;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.perforiscus.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.perforiscus.tooltip2").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.perforiscus.tooltip3").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.perforiscus.tooltip4").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.perforiscus.tooltip5").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.perforiscus.tooltip6").setStyle(textStyle));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
}
