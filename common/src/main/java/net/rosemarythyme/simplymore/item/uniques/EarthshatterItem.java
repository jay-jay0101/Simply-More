package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.TwoHandedWeapon;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class EarthshatterItem extends SimplyMoreUniqueSwordItem implements TwoHandedWeapon {
    public EarthshatterItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordType.GRANDSWORD, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getWorld().isClient()) return super.postHit(stack, target, attacker);

        if (MathUtils.chance(attacker, UNIQUE_CONFIG.earthshatter.chance)) {
            EntityUtils.reapplyAndIncrementEffect(target, StatusEffectRegistry.getReference(StatusEffectRegistry.ARMOUR_CRUNCH), 200, 1, 20);
        }

        return super.postHit(stack, target, attacker);
    }



    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return AttackUtils.holdToUse(user, hand);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (world.isClient) return;
        if (!(user instanceof PlayerEntity player)) return;

        if (remainingUseTicks == this.getMaxUseTime(stack, user) - 1) {
            AudioVisualUtils.playSound(world, user.getPos(), new Sound(SoundRegistry.DARK_SWORD_ENCHANT.get()).setPitch(1.2f));
        } else if (remainingUseTicks == 1) {
            BlockStateParticleEffect particle = new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.DIRT.getDefaultState());
            AudioVisualUtils.particleCuboid((ServerWorld) world, player.getPos().add(0d, 1d, 0d), particle, 3, 1, 500, 0d);

            AudioVisualUtils.playSound(world, player.getPos(), new Sound(SoundRegistry.ELEMENTAL_SWORD_FIRE_ATTACK_01.get()).setPitch(0));
            AudioVisualUtils.playSound(world, player.getPos(), new Sound(SoundRegistry.ELEMENTAL_SWORD_FIRE_ATTACK_02.get()).setPitch(0));
            AudioVisualUtils.playSound(world, player.getPos(), new Sound(SoundRegistry.ELEMENTAL_SWORD_FIRE_ATTACK_03.get()).setPitch(0));

            player.getItemCooldownManager().set(this, UNIQUE_CONFIG.earthshatter.cooldown);

            AttackUtils.cuboidAttack(player, player.getPos().add(0d, 1.5d, 0d), 3.5d, 4d, AttackUtils.AttackTarget.ENEMIES)
                    .damage(15, player.getDamageSources().playerAttack(player))
                    .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.ARMOUR_CRUNCH), UNIQUE_CONFIG.earthshatter.slamEffectTime, 2)
                    .applyEffect(StatusEffects.WEAKNESS, UNIQUE_CONFIG.earthshatter.slamEffectTime, 1)
                    .applyEffect(StatusEffects.SLOWNESS, UNIQUE_CONFIG.earthshatter.slamEffectTime, 1)
                    .addVelocity(0d, 1.2d, 0d);
        }

        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 40;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.ASH);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.earthshatter.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.earthshatter.tooltip3").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.earthshatter.tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.earthshatter.tooltip6").setStyle(Styles.TEXT));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.EARTHSHATTER));
        }

        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.15f;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 600;
        @ValidatedInt.Restrict(min = 0)
        public int slamEffectTime = 160;
    }
}
