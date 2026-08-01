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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class CindergorgeItem extends SimplyMoreUniqueSwordItem {

    public CindergorgeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
//        startUsing(user.getStackInHand(hand), hand);
        return AttackUtils.holdToUse(user, hand);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (remainingUseTicks == 1) user.stopUsingItem();

        float originalYaw = user.getYaw();
        float turnAngle = 54f * (1 - ((float) remainingUseTicks / UNIQUE_CONFIG.cindergorge.maxUseTime));
        user.setYaw(originalYaw + turnAngle);

        if(world.isClient) return;

        AudioVisualUtils.playSound(world, user.getEyePos(), new Sound(SoundEvents.ITEM_FIRECHARGE_USE));
        AudioVisualUtils.particleLine((ServerWorld) world, user.getEyePos(), user.getYaw(), user.getPitch(), UNIQUE_CONFIG.cindergorge.range, ParticleTypes.FLAME, 0.75, 20, 0.2d, 0.1d);

        AttackUtils.lineAttack(user, user.getEyePos(), user.getYaw(), 0, UNIQUE_CONFIG.cindergorge.range, 0.75, AttackUtils.AttackTarget.ENEMIES)
            .setOnFireFor(3)
            .damage(UNIQUE_CONFIG.cindergorge.fireDamage, user.getDamageSources().inFire());
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        float relativeTime = 1 - ((float) remainingUseTicks / getMaxUseTime(stack, user));
        float cooldown = relativeTime;

        cooldown = Math.max(cooldown, 120f);

        user.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, (int) (150f * relativeTime)));

        ((PlayerEntity) user).getItemCooldownManager().set(this, (int) cooldown);
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return UNIQUE_CONFIG.cindergorge.maxUseTime;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.LAVA);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.cindergorge.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.cindergorge.tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.cindergorge.tooltip5").setStyle(Styles.TEXT));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.CINDERGORGE));
        }
        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.4f;
        @ValidatedFloat.Restrict(min = 0f)
        public float thornsDamage = 3f;
        @ValidatedFloat.Restrict(min = 0f)
        public float fireThornsDamage = 6f;
        @ValidatedInt.Restrict(min = 0)
        public int maxUseTime = 200;
        @ValidatedInt.Restrict(min = 0)
        public int range = 5;
        @ValidatedFloat.Restrict(min = 0f)
        public float fireDamage = 5f;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 600;
    }
}
