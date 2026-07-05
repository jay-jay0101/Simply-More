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
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.GreatSlitherFangEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.VisualEffectsUtils;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class GreatSlitherItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = uniqueConfig.great_slither.cooldown;

    public GreatSlitherItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.SWORD, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if (MathUtils.chance(attacker, uniqueConfig.great_slither.chance)) {
                if(target.hasStatusEffect(StatusEffects.POISON)) {
                    target.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.getReference(ModEffectsRegistry.VENOM), uniqueConfig.great_slither.venomTime, 0), attacker);
                } else {
                    target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, uniqueConfig.great_slither.poisonTime, 0), attacker);
                }
            }
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.getWorld().isClient()) {
            return super.use(world, user, hand);
        }

        for(int i = -1; i<2; i++){
            float yawInternal = user.getYaw() + (i * 15);
            double yawAngle = Math.toRadians(yawInternal);
            double cosYaw = Math.cos(yawAngle);
            double sinYaw = Math.sin(yawAngle);

            for (int distanceMultiplier = 1; distanceMultiplier < uniqueConfig.great_slither.range; distanceMultiplier++) {
                double offsetX = -distanceMultiplier * sinYaw;
                double offsetZ = distanceMultiplier * cosYaw;

                double spawnX = user.getX() + 1.2 * offsetX;
                double spawnZ = user.getZ() + 1.2 * offsetZ;

                world.spawnEntity(new GreatSlitherFangEntity(world, spawnX, user.getY(), spawnZ, yawInternal, 0, user));
            }
        }

        user.getItemCooldownManager().set(this, skillCooldown);
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        VisualEffectsUtils.handleFootfalls(entity, stack, world, ParticleTypes.SNEEZE, ParticleTypes.SNEEZE, ParticleTypes.SPORE_BLOSSOM_AIR);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.great_slither.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.great_slither.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.great_slither.tooltip4").setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ModItemsRegistry.GREAT_SLITHER));
        }

        @ValidatedInt.Restrict(min = 0)
        public int poisonTime = 90;
        @ValidatedInt.Restrict(min = 0)
        public int venomTime = 40;
        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.25f;
        @ValidatedInt.Restrict(min = 0)
        public int range = 7;
        @ValidatedFloat.Restrict(min = 0f)
        public float fangDamage = 4f;
        @ValidatedInt.Restrict(min = 0)
        public int fangsVenomTime = 90;
        @ValidatedInt.Restrict(min = 0)
        public int fangsSlowTime = 35;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 300;
    }
}
