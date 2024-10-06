package net.rosemarythyme.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
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
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;

public class RuyiJinguBangItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = effect.getRuyiCooldown();

    public RuyiJinguBangItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
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
    public int getMaxUseTime(ItemStack stack) {
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
                player.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);

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

                    Box box = new Box(userX - 1 + offsetX, userY - 1 + offsetY, userZ - 1 + offsetZ, userX + 1 + offsetX, userY + 1 + offsetY, userZ + 1 + offsetZ);
                    DamageSource damageSource = player.getDamageSources().playerAttack(player);
                    for (LivingEntity livingEntity : user.getWorld().getNonSpectatingEntities(LivingEntity.class, box)) {
                        if (livingEntity.isTeammate(user) || livingEntity == user || livingEntity.isInvulnerable()) continue;

                        livingEntity.damage(damageSource, damage);
                        livingEntity.setVelocity(offsetX / i, offsetY / i, offsetZ / i);
                        livingEntity.velocityModified = true;
                    }

                    ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.CLOUD, userX + offsetX, userY + offsetY, userZ + offsetZ, 15, 1, 1, 1, 0.1);
                }
            }
        }
    }

    int stepMod = 0;
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        stepMod = SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.WAX_ON);
        super.inventoryTick(stack, world, entity, slot, selected);
    }


    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style rightClickStyle = HelperMethods.getStyle("rightclick");
        Style abilityStyle = HelperMethods.getStyle("ability");
        Style textStyle = HelperMethods.getStyle("text");

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.ruyi_jingu_bang.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.ruyi_jingu_bang.tooltip2").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.ruyi_jingu_bang.tooltip3").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.ruyi_jingu_bang.tooltip4").setStyle(textStyle));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
}
