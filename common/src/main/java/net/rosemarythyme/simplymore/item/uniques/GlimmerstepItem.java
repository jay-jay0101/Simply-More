package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;
import org.joml.Vector3f;

import java.util.List;

public class GlimmerstepItem extends SimplyMoreUniqueSwordItem {
    public GlimmerstepItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getWorld().isClient) return super.postHit(stack, target, attacker);

        float chance = EntityUtils.isRidingLivingEntity(attacker) ?
                UNIQUE_CONFIG.glimmerstep.chanceMounted:
                UNIQUE_CONFIG.glimmerstep.chance;

        if (MathUtils.chance(attacker, chance)) {
            EntityUtils.reapplyAndIncrementEffect(attacker, StatusEffectRegistry.getReference(StatusEffectRegistry.STARLIGHT), UNIQUE_CONFIG.glimmerstep.starlightTime, 1, UNIQUE_CONFIG.glimmerstep.maxStarlight);
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, UNIQUE_CONFIG.glimmerstep.blindTime));

            AudioVisualUtils.playSound(attacker.getWorld(), attacker.getPos(), new Sound(SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE).setPitch(2f));
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if(!user.hasStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.STARLIGHT))) {
            return TypedActionResult.fail(itemStack);
        }

        return AttackUtils.holdToUse(user, hand);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        super.usageTick(world, user, stack, remainingUseTicks);

        if (user.getWorld().isClient) return;
        if (!(user instanceof PlayerEntity player)) return;

        if(remainingUseTicks == UNIQUE_CONFIG.glimmerstep.explosionWindup) {
            AudioVisualUtils.playSound(user.getWorld(), user.getPos(), new Sound(SoundEvents.BLOCK_BEACON_ACTIVATE).setPitch(1.2f));
        }

        AudioVisualUtils.particleAroundEntity(player,
                new DustParticleEffect(new Vector3f(2f, 2f, 1f), 2f),
                Math.min(getMaxUseTime(stack, player) - remainingUseTicks, 60),
                4f, 0f
        );

        if(remainingUseTicks == 1) {
            user.stopUsingItem();

            RegistryEntry<StatusEffect> starlight = StatusEffectRegistry.getReference(StatusEffectRegistry.STARLIGHT);
            if(!player.hasStatusEffect(starlight)) return;

            AttackUtils.cuboidAttack(player, player.getPos().add(0d, 3d, 0d), UNIQUE_CONFIG.glimmerstep.explosionRange, 5, AttackUtils.AttackTarget.ENEMIES)
                    .damage(UNIQUE_CONFIG.glimmerstep.explosionDamagePerStarlight * player.getStatusEffect(starlight).getAmplifier(), player.getDamageSources().explosion(player, player));

            player.getItemCooldownManager().set(this, UNIQUE_CONFIG.glimmerstep.cooldown);
            user.removeStatusEffect(starlight);

            AudioVisualUtils.particleAroundEntity(player, ParticleTypes.EXPLOSION, 80, 2d, 0d);
            AudioVisualUtils.playSound(world, user.getPos(), new Sound(SoundEvents.ENTITY_GENERIC_EXPLODE.value()).setPitch(0.8f));
        }

        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return UNIQUE_CONFIG.glimmerstep.explosionWindup;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.ELECTRIC_SPARK, ParticleTypes.ELECTRIC_SPARK, ParticleTypes.FIREWORK);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.glimmerstep.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.glimmerstep.tooltip2",
                UNIQUE_CONFIG.glimmerstep.maxStarlight).setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.glimmerstep.tooltip5").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.glimmerstep.tooltip6").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.glimmerstep.tooltip8").setStyle(textStyle));
        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.GLIMMERSTEP));
        }

        @ValidatedInt.Restrict(min = 0)
        public int maxStarlight = 10;
        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.25f;
        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chanceMounted = 0.4f;
        @ValidatedFloat.Restrict(min = 0f)
        public float explosionDamagePerStarlight = 3.2f;
        @ValidatedInt.Restrict(min = 0)
        public int blindTime = 40;
        @ValidatedInt.Restrict(min = 0)
        public int baseSpeedFrequency = 200;
        @ValidatedInt.Restrict(min = 0)
        public int speedFrequencyPerStack = 10;
        @ValidatedInt.Restrict(min = 0)
        public int speedTime = 40;
        @ValidatedInt.Restrict(min = 0)
        public int explosionRange = 5;
        @ValidatedInt.Restrict(min = 0)
        public int explosionWindup = 60;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 800;
        @ValidatedInt.Restrict(min = 0)
        public int starlightTime = 800;
    }
}
