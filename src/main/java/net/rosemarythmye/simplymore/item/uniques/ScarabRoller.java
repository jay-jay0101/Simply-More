package net.rosemarythmye.simplymore.item.uniques;

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
import net.rosemarythmye.simplymore.item.UniqueSword;
import net.sweenus.simplyswords.util.HelperMethods;
import org.joml.Vector2d;

import java.util.List;


public class ScarabRoller extends UniqueSword {
    int skillCooldown = 300;

    public ScarabRoller(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if (attacker.getRandom().nextInt(100) <= 15) {
                attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 100, 0), attacker);
            }
        }
        return super.postHit(stack, target, attacker);
    }

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

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }

    public static float activeSpeed = 1.2f;
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (user instanceof PlayerEntity) {
            Vector2d currentPos = new Vector2d(user.getX(),user.getZ());
            double userRotation = (user.getYaw() + 180) * (Math.PI / 180);
            Vector2d movement = new Vector2d(activeSpeed * Math.sin(userRotation), -activeSpeed * Math.cos(userRotation));

            user.move(MovementType.SHULKER,new Vec3d(movement.x,0,movement.y));

            if(!user.getWorld().isClient) {
                for (LivingEntity livingEntity : user.getWorld().getNonSpectatingEntities(LivingEntity.class,user.getBoundingBox().expand(2))) {
                    if(livingEntity == user || livingEntity.isTeammate(user)) continue;
                    double xVelocity = livingEntity.getX()-user.getX();
                    double zVelocity = livingEntity.getZ()-user.getZ();
                    double ratioMax = Math.abs(xVelocity)+ Math.abs(zVelocity);
                    float strength = 0.5f;

                    xVelocity *= strength/ratioMax;
                    zVelocity *= strength/ratioMax;

                    livingEntity.setVelocity(xVelocity,0.4,zVelocity);
                    if(!livingEntity.isBlocking()) livingEntity.damage(user.getDamageSources().playerAttack(((PlayerEntity) user)),5f);
                }

                double moveDistance = Math.abs(user.getX()-currentPos.x)+Math.abs(user.getZ()-currentPos.y);

                if(moveDistance < 1.1) {
                    ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.EXPLOSION,user.getX(),user.getY()+0.75,user.getZ(),3,0,0,0,0);
                    user.getWorld().playSound(null, user.getBlockPos(),SoundEvents.ENTITY_GENERIC_EXPLODE,user.getSoundCategory());
                    user.stopUsingItem();
                }
            }
        }
        super.usageTick(world, user, stack, remainingUseTicks);
    }
    public int getMaxUseTime(ItemStack stack) {
        return 600;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        Vector2d currentPos = new Vector2d(user.getX(),user.getZ());
        double userRotation = (user.getYaw() + 180) * (Math.PI / 180);
        Vector2d movement = new Vector2d(activeSpeed * Math.sin(userRotation), -activeSpeed * Math.cos(userRotation));
        user.setVelocity(movement.x,user.getVelocity().getY(),movement.y);

        ((PlayerEntity) user).getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }
    private static int stepMod = 0;

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (stepMod > 0) {
            --stepMod;
        }

        if (stepMod <= 0) {
            stepMod = 7;
        }

        HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.ASH, ParticleTypes.ASH, ParticleTypes.ASH, true);
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
