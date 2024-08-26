package net.rosemarythyme.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.util.HelperMethods;
import org.joml.Vector2d;

import java.util.ArrayList;
import java.util.List;


public class ScarabRollerItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = 300;
    private final List<LivingEntity> hitEntities = new ArrayList<>();
    float activeSpeed = 1f;

    public ScarabRollerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    /*
     * Refactored the `use` method to improve efficiency and conciseness.
     *  Removed the unnecessary `setCurrentHand` method call as the `hand` parameter is already being passed.
     *  Replaced the `if-else` statement with a ternary operator to express the logic in a more concise way.
     *      Ternary operators are highly optimised and can significantly improve the performance of your code.
     */
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        return itemStack.getDamage() >= itemStack.getMaxDamage() - 1
                ? TypedActionResult.fail(itemStack)
                : TypedActionResult.consume(itemStack);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getWorld().isClient())
            return super.postHit(stack, target, attacker);
        /* Technically, the more efficient way to do this would be to use `attacker.getRandom().nextInt(x)`. But,
         *  you need to consider that the counting starts at `0` and not `1`. As such, a way to think about this is
         *  that the less than sign is an arrow that points the intended proc percentage towards the number that the
         *  percentage should be taken from. See example below.
         *
         * if (attacker.getRandom().nextInt(100) < 15) {
         *  ...
         * }
         */
        if (attacker.getRandom().nextBetween(1, 100) <= 15) {
            attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 100, 0), attacker);
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (user.hasStatusEffect(StatusEffects.SLOW_FALLING)) {
            return;
        }

        if (!(user instanceof PlayerEntity player)) {
            return;
        }

        Vector2d currentPosition = new Vector2d(user.getX(), user.getZ());
        double userRotation = (user.getYaw() + 180) * (Math.PI / 180);

        Vector2d movement = new Vector2d(activeSpeed * Math.sin(userRotation), -activeSpeed * Math.cos(userRotation));

        user.move(MovementType.SHULKER, new Vec3d(movement.x, 0, movement.y));

        if (!world.isClient) {
            hitEntities.add(user);

            for (LivingEntity entity : world.getNonSpectatingEntities(LivingEntity.class, user.getBoundingBox().expand(2))) {
                if (entity == user || entity.isTeammate(user) || hitEntities.contains(entity)) {
                    continue;
                }

                double xVelocity = entity.getX() - user.getX();
                double zVelocity = entity.getZ() - user.getZ();
                double ratioMax = Math.hypot(xVelocity, zVelocity);
                float strength = 0.8f;

                xVelocity *= strength / ratioMax;
                zVelocity *= strength / ratioMax;

                entity.setVelocity(xVelocity, 0.4, zVelocity);
                entity.velocityModified = true;

                hitEntities.add(entity);
                if (!entity.isBlocking()) {
                    entity.damage(player.getDamageSources().playerAttack(player), 5f);
                }
            }

            double moveDistance = currentPosition.distance(user.getX(), user.getZ());

            if (moveDistance < 0.9) {
                ((ServerWorld) world).spawnParticles(ParticleTypes.EXPLOSION, user.getX(), user.getY() + 0.75, user.getZ(), 3, 0, 0, 0, 0);
                world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE, user.getSoundCategory());
                user.stopUsingItem();
            }
        }

        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 300;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        double userRotation = (user.getYaw() + 180) * Math.PI / 180;
        float velocityX = (float) (activeSpeed * Math.sin(userRotation));
        float velocityY = (float) user.getVelocity().getY();
        float velocityZ = (float) (-activeSpeed * Math.cos(userRotation));

        user.setVelocity(velocityX, velocityY, velocityZ);
        hitEntities.clear();
        ((PlayerEntity) user).getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        if(entity instanceof PlayerEntity player && !hitEntities.isEmpty() && !player.isUsingItem()) {
            hitEntities.clear();
            player.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
        }

        int stepMod = 0;
        SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.ASH);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style RIGHTCLICK = HelperMethods.getStyle("rightclick");
        Style ABILITY = HelperMethods.getStyle("ability");
        Style TEXT = HelperMethods.getStyle("text");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.scarab_roller.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.scarab_roller.tooltip2").setStyle(TEXT));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(RIGHTCLICK));
        tooltip.add(Text.translatable("item.simplymore.scarab_roller.tooltip3").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.scarab_roller.tooltip4").setStyle(TEXT));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplymore.scarab_roller.tooltip5").setStyle(TEXT));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }

}
