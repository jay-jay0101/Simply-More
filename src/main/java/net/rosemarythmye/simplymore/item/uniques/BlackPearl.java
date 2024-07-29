package net.rosemarythmye.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythmye.simplymore.entity.BlackPearlFireball;
import net.rosemarythmye.simplymore.item.UniqueSword;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class BlackPearl extends UniqueSword {
    int skillCooldown = 180;

    public BlackPearl(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if(attacker.getRandom().nextInt(100) <= 10) {
                Collection<StatusEffectInstance> effects = target.getStatusEffects();
                List<StatusEffectInstance> positiveEffects = new ArrayList<>();
                for (StatusEffectInstance effect : effects) {
                    if (effect.getEffectType().getCategory() == StatusEffectCategory.BENEFICIAL)
                        positiveEffects.add(effect);
                }

                if (!positiveEffects.isEmpty()) {
                    StatusEffectInstance plunderedEffect = positiveEffects.get(attacker.getRandom().nextInt(positiveEffects.size()));

                    if(plunderedEffect.getAmplifier()>4) {
                        plunderedEffect = new StatusEffectInstance(plunderedEffect.getEffectType(),plunderedEffect.getDuration(),4);
                    }

                    if(plunderedEffect.getDuration() == -1 || plunderedEffect.getDuration()>600) {
                        plunderedEffect = new StatusEffectInstance(plunderedEffect.getEffectType(),600,plunderedEffect.getAmplifier());
                    }


                    attacker.addStatusEffect(plunderedEffect);
                    target.removeStatusEffect(plunderedEffect.getEffectType());

                    attacker.getWorld().playSound(null, attacker.getBlockPos(), SoundRegistry.DARK_SWORD_BLOCK.get(), SoundCategory.PLAYERS,1,1);
                }
            }
        }

        return super.postHit(stack, target, attacker);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {
            float VelocityPower = 3;
            float yaw = (float) Math.toRadians(user.getYaw()+90);
            float pitch = (float) Math.toRadians(user.getPitch());

            float velocityX = (float) (Math.cos(yaw) * Math.cos(pitch)) * VelocityPower;
            float velocityZ = (float) (Math.sin(yaw) * Math.cos(pitch)) * VelocityPower;
            float velocityY = (float) Math.sin(pitch) * -VelocityPower;

            BlackPearlFireball fireball = new BlackPearlFireball(world,user,velocityX,velocityY,velocityZ);
            fireball.setPos(user.getX()+(velocityX/2),user.getEyeY()+(velocityY/2),user.getZ()+(velocityZ/2));
            world.spawnEntity(fireball);
            user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
        }
        return super.use(world, user, hand);
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style RIGHTCLICK = HelperMethods.getStyle("rightclick");
        Style ABILITY = HelperMethods.getStyle("ability");
        Style TEXT = HelperMethods.getStyle("text");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.black_pearl.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.black_pearl.tooltip2").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.black_pearl.tooltip3").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(RIGHTCLICK));
        tooltip.add(Text.translatable("item.simplymore.black_pearl.tooltip4").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.black_pearl.tooltip5").setStyle(TEXT));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }

    private static int stepMod = 0;

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (stepMod > 0) {
            --stepMod;
        }

        if (stepMod <= 0) {
            stepMod = 7;
        }

        HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.LANDING_HONEY, ParticleTypes.LANDING_HONEY, ParticleTypes.ASH, true);
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
