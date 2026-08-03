package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.legacy.GreatSlitherFangEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class GreatSlitherItem extends SimplyMoreUniqueSwordItem {
    public GreatSlitherItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getWorld().isClient()) return super.postHit(stack, target, attacker);

        if (MathUtils.chance(attacker, UNIQUE_CONFIG.great_slither.chance)) {
            if(target.hasStatusEffect(StatusEffects.POISON)) {
                target.addStatusEffect(new StatusEffectInstance(StatusEffectRegistry.getReference(StatusEffectRegistry.VENOM), UNIQUE_CONFIG.great_slither.venomTime, 0), attacker);
            } else {
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, UNIQUE_CONFIG.great_slither.poisonTime, 0), attacker);
            }
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.getWorld().isClient()) return super.use(world, user, hand);

        for(int i = -1; i<2; i++){
            float yaw = user.getYaw() + (i * 15);
            Vec3d direction = MathUtils.getDirectionalVector(yaw, 0f);

            for (int j = 1; j < UNIQUE_CONFIG.great_slither.range; j++) {
                Vec3d spawnPos = user.getPos().add(direction.multiply(1.2 * j));

                world.spawnEntity(new GreatSlitherFangEntity(world, spawnPos.getX(), user.getY(), spawnPos.getZ(), yaw, 0, user));
            }
        }

        user.getItemCooldownManager().set(this, UNIQUE_CONFIG.great_slither.cooldown);
        return super.use(world, user, hand);
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.SNEEZE, ParticleTypes.SNEEZE, ParticleTypes.SPORE_BLOSSOM_AIR);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.great_slither.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.great_slither.tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.great_slither.tooltip4").setStyle(Styles.TEXT));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.GREAT_SLITHER));
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
