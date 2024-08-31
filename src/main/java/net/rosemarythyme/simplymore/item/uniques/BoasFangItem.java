package net.rosemarythyme.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.util.HelperMethods;
import org.joml.Vector3f;

import java.util.List;


public class BoasFangItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = 600;

    public BoasFangItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if (attacker.getRandom().nextBetween(1, 100) <= 20) {
                target.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.SUFFOCATION,120));
            }
        }
        return super.postHit(stack, target, attacker);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {

            for (int i = 0; i<10;i++) {
                float yaw = (float) Math.toRadians(user.getYaw()+90 + user.getRandom().nextInt(61) - 30);
                float pitch = (float) Math.toRadians(user.getPitch() + user.getRandom().nextInt(61) - 30);

                float velocityX = (float) (Math.cos(yaw) * Math.cos(pitch)) * 0.5f;
                float velocityZ = (float) (Math.sin(yaw) * Math.cos(pitch)) * 0.5f;
                float velocityY = (float) Math.sin(pitch) * -0.5f;

                user.getWorld().playSound(null,user.getBlockPos(), SoundEvents.ENTITY_LLAMA_SPIT,SoundCategory.PLAYERS,1f,0f);

                for(int j = 0; j<14;j++) {
                    double dX = velocityX * j;
                    double dY = velocityY * j;
                    double dZ = velocityZ * j;

                    double x = user.getX();
                    double y = user.getEyeY();
                    double z = user.getZ();

                    DustParticleEffect particleEffect = new DustParticleEffect(new Vector3f(0.05f,1f,0.1f),1);

                    ((ServerWorld) user.getWorld()).spawnParticles(particleEffect,x+dX,y+dY,z+dZ,1,0,0,0,0);
                    for (LivingEntity entity : user.getWorld().getNonSpectatingEntities(LivingEntity.class,new Box(x-0.25+dX,y-0.25+dY,z-0.25+dZ,x+0.25+dX,y+0.25+dY,z+0.25+dZ)))
                    {
                        if (entity.isTeammate(user) || entity == user || entity.isInvulnerable()) continue;
                        if (entity.isBlocking()) continue;

                        entity.damage(user.getDamageSources().magic(),5);
                        entity.setVelocity(velocityX/2,velocityY/2,velocityZ/2);
                        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON,120,1));
                    }
                }
                user.setVelocity(user.getRotationVector().negate().multiply(2));
                user.setVelocity(user.getVelocity().x, 0.0, user.getVelocity().z);
                user.velocityModified = true;
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED,80,1));
            }


            user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
        }
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        int stepMod = 0;
        SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.SPORE_BLOSSOM_AIR);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style rightClickStyle = HelperMethods.getStyle("rightclick");
        Style abilityStyle = HelperMethods.getStyle("ability");
        Style textStyle = HelperMethods.getStyle("text");

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.boas_fang.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.boas_fang.tooltip2").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.boas_fang.tooltip3").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.boas_fang.tooltip4").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.boas_fang.tooltip5").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.boas_fang.tooltip6").setStyle(textStyle));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
}
