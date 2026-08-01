package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class RuyiJinguBangItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = UNIQUE_CONFIG.ruyi_jingu_bang.cooldown;

    public RuyiJinguBangItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return itemStack.getDamage() >= itemStack.getMaxDamage() - 1
                ? TypedActionResult.fail(itemStack)
                : TypedActionResult.consume(itemStack);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (world instanceof ServerWorld serverWorld
                && user instanceof PlayerEntity
                && remainingUseTicks % 20 == 0
                && remainingUseTicks > 9999799) {
            serverWorld.playSound(null, user.getX(), user.getY(), user.getZ(), SoundRegistry.ELEMENTAL_BOW_EARTH_SHOOT_IMPACT_03.get(), SoundCategory.PLAYERS, 0.5f, 1f);
        }
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 9999999;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        super.onStoppedUsing(stack, world, user, remainingUseTicks);

        if (!user.getWorld().isClient && user instanceof PlayerEntity player) {
            if (remainingUseTicks < 9999979) {
                player.getItemCooldownManager().set(this, skillCooldown);

                int useTime = Math.min(player.getItemUseTime(), 200);
                int range = (int) Math.floor(useTime / 5f);
                float damage = 12 * (useTime / 50f);
                float yawRadians = (float) Math.toRadians(user.getYaw() + 90);
                float pitchRadians = (float) Math.toRadians(user.getPitch());

                for (int i = 0; i <= range; i++) {
                    float offsetX = (float) (Math.cos(yawRadians) * Math.cos(pitchRadians)) * i;
                    float offsetY = (float) Math.sin(pitchRadians) * -i;
                    float offsetZ = (float) (Math.sin(yawRadians) * Math.cos(pitchRadians)) * i;

                    float userX = (float) user.getX();
                    float userY = (float) user.getY();
                    float userZ = (float) user.getZ();

                    user.getWorld().playSound(null, userX + offsetX, userY + offsetY, userZ + offsetZ, SoundRegistry.DARK_SWORD_BLOCK.get(), SoundCategory.PLAYERS, 0.2f, 1);

                    Box box = MathUtils.createCubeBox(user.getPos().add(offsetX, offsetY, offsetZ), 1);
                    List<LivingEntity> targets = AttackUtils.cuboidAttack(user, box);

                    DamageSource damageSource = player.getDamageSources().playerAttack(player);
                    for (LivingEntity target : targets) {
                        target.damage(damageSource, damage);
                        target.setVelocity(offsetX / i, offsetY / i, offsetZ / i);
                        target.velocityModified = true;
                    }

                    ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.CLOUD, userX + offsetX, userY + offsetY, userZ + offsetZ, 15, 1, 1, 1, 0.1);
                }
            }
        }
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.WAX_ON);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.ruyi_jingu_bang.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.ruyi_jingu_bang.tooltip2").setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.RUYI_JINGU_BANG));
        }

        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 300;
    }
}
