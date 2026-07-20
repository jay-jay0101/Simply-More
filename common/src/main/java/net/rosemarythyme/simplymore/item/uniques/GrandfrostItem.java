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
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.TwoHandedWeapon;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class GrandfrostItem extends SimplyMoreUniqueSwordItem implements TwoHandedWeapon {
    public GrandfrostItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordType.GRANDSWORD, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(attacker.getWorld().isClient()) return super.postHit(stack, target, attacker);

        if (target.isBlocking() || MathUtils.chance(attacker, UNIQUE_CONFIG.grandfrost.chance)) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffectRegistry.getReference(StatusEffectRegistry.CHILL), UNIQUE_CONFIG.grandfrost.chillTime, 0), attacker);
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.getWorld().isClient()) return super.use(world, user, hand);

        TargetList targets = AttackUtils.cubeAttack(user, user.getPos(), UNIQUE_CONFIG.grandfrost.blizzardRange, AttackUtils.AttackTarget.ENEMIES)
                .knockback(user, UNIQUE_CONFIG.grandfrost.blizzardStrength)
                .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.CHILL), UNIQUE_CONFIG.grandfrost.blizzardEffectTime, 0)
                .applyEffect(StatusEffects.SLOWNESS, UNIQUE_CONFIG.grandfrost.blizzardEffectTime, 3);

        if(targets.isPopulated()) {
            user.getItemCooldownManager().set(this, UNIQUE_CONFIG.grandfrost.cooldown);
            AudioVisualUtils.rainParticlesAboveEntity(user, ParticleTypes.SNOWFLAKE, 1000, 3d, 0.25d);
            AudioVisualUtils.playSound(world, user.getPos(), new Sound(SoundRegistry.ELEMENTAL_SWORD_ICE_ATTACK_03.get()).setPitch(0.3f));
        }

        return super.use(world, user, hand);
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.ITEM_SNOWBALL, ParticleTypes.ITEM_SNOWBALL, ParticleTypes.SNOWFLAKE);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.grandfrost.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.grandfrost.tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.grandfrost.tooltip4").setStyle(Styles.TEXT));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.GRANDFROST));
        }

        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.25f;
        @ValidatedInt.Restrict(min = 0)
        public int chillTime = 140;
        @ValidatedInt.Restrict(min = 0)
        public int blizzardRange = 5;
        @ValidatedFloat.Restrict(min = 0f)
        public float blizzardStrength = 3.5f;
        @ValidatedInt.Restrict(min = 0)
        public int blizzardEffectTime = 200;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 500;
    }
}
