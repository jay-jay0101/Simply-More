package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.FlowerFieldAreaEffectCloudEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class PerforiscusItem extends SimplyMoreUniqueSwordItem implements TwoHandedWeapon {
    int skillCooldown = UNIQUE_CONFIG.perforiscus.cooldown;

    public static final int maxBloom = 15;

    public PerforiscusItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }



    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getWorld().isClient())
            return super.postHit(stack, target, attacker);

        int amplifier = attacker.hasStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.BLOOM)) ?
                attacker.getStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.BLOOM)).getAmplifier() +1 : 0;

        amplifier = Math.min(amplifier, maxBloom);

        attacker.addStatusEffect(
                new StatusEffectInstance(
                        StatusEffectRegistry.getReference(StatusEffectRegistry.BLOOM),
                        UNIQUE_CONFIG.perforiscus.bloomTime,
                        amplifier
                )
        );

        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        int amplifier = 0;

        try {
            amplifier = user.getStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.BLOOM)).getAmplifier();
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

            StatusEffectInstance effect = user.getStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.BLOOM));
            user.removeStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.BLOOM));

            if(amplifier > 7) {
                user.addStatusEffect(new StatusEffectInstance(
                        StatusEffectRegistry.getReference(StatusEffectRegistry.BLOOM),
                        effect.getDuration(),
                        effect.getAmplifier() - 8
                ));
            }

            user.getItemCooldownManager().set(this, skillCooldown);
        }

        return super.use(world, user, hand);
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.FALLING_SPORE_BLOSSOM);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.perforiscus.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.perforiscus.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.perforiscus.tooltip5").setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.PERFORISCUS));
        }

        @ValidatedInt.Restrict(min = 0)
        public int bloomTime = 500;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 800;
    }
}
