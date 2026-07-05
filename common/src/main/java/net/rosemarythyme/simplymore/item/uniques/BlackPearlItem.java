package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedSet;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.entity.BlackPearlFireballEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.rosemarythyme.simplymore.util.ConfigUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.VisualEffectsUtils;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class BlackPearlItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = UNIQUE_CONFIG.black_pearl.cooldown;

    public BlackPearlItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.SWORD, settings);
    }


    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if (MathUtils.chance(attacker, UNIQUE_CONFIG.black_pearl.chance)) {
                List<StatusEffectInstance> possibleEffects = target.getStatusEffects().stream()
                        .filter(effect -> effect.getEffectType().value().getCategory() == StatusEffectCategory.BENEFICIAL)
                        .filter(effect -> !ConfigUtils.isEffectBlacklisted(effect.getEffectType(), UNIQUE_CONFIG.black_pearl.blacklist, UNIQUE_CONFIG.black_pearl.includeGlobalBlacklist))
                        .toList();

                if (!possibleEffects.isEmpty()) {
                    StatusEffectInstance plunderedEffect = possibleEffects.get(attacker.getRandom().nextInt(possibleEffects.size()));

                    int amplifier = Math.min(plunderedEffect.getAmplifier(), 4);
                    int duration = Math.min(plunderedEffect.getDuration(), 600);

                    if (plunderedEffect.getDuration() == StatusEffectInstance.INFINITE) {
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
    //TODO: redo
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {
            float velocityPower = 3.0f;
            float yawRadians = (float) Math.toRadians(user.getYaw() + 90);
            float pitchRadians = (float) Math.toRadians(user.getPitch());

            float velocityX = (float) (Math.cos(yawRadians) * Math.cos(pitchRadians)) * velocityPower;
            float velocityZ = (float) (Math.sin(yawRadians) * Math.cos(pitchRadians)) * velocityPower;
            float velocityY = (float) Math.sin(pitchRadians) * -velocityPower;

            BlackPearlFireballEntity fireballEntity = new BlackPearlFireballEntity(world, user, new Vec3d(velocityX, velocityY, velocityZ));
            fireballEntity.setPos(
                    user.getX() + (velocityX / 2),
                    user.getEyeY() + (velocityY / 2),
                    user.getZ() + (velocityZ / 2)
            );
            world.spawnEntity(fireballEntity);
            user.getItemCooldownManager().set(this, skillCooldown);
        }
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        VisualEffectsUtils.handleFootfalls(entity, stack, world, ParticleTypes.LANDING_HONEY, ParticleTypes.LANDING_HONEY, ParticleTypes.ASH);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.black_pearl.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.black_pearl.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.black_pearl.tooltip4").setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.BLACK_PEARL));
        }

        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.1f;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 180;
        public boolean includeGlobalBlacklist = true;
        public ValidatedSet<Identifier> blacklist = ConfigUtils.createEffectList(
                SimplyMore.identifier("blessing")
        );
    }
}
