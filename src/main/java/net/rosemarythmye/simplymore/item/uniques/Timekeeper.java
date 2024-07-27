package net.rosemarythmye.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythmye.simplymore.item.UniqueSword;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;
import org.joml.Vector2d;

import java.util.List;


public class Timekeeper extends UniqueSword {
    int skillCooldown = 600;

    public Timekeeper(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!user.getWorld().isClient) {
            long dayTime = Math.abs(world.getTimeOfDay() % 24000);
            boolean fixedTime = world.getDimension().hasFixedTime();

            if(!fixedTime) {
                double x = user.getX();
                double y = user.getEyeY();
                double z = user.getZ();
                if(dayTime < 13000) {
                    world.playSound(null,x,y,z,SoundRegistry.ELEMENTAL_SWORD_FIRE_ATTACK_01.get(),SoundCategory.PLAYERS,1,1);
                    for (int i = 0; i<12; i++) {
                        float yaw = (float) Math.toRadians(user.getYaw()+90);
                        float pitch = (float) Math.toRadians(user.getPitch());

                        double dX = (Math.cos(yaw) * Math.cos(pitch) * i);
                        double dZ = (Math.sin(yaw) * Math.cos(pitch) * i);
                        double dY = Math.sin(pitch) * -i;

                        x = user.getX() + dX/2;
                        y = user.getEyeY() + dY/2;
                        z = user.getZ() + dZ/2;

                        ((ServerWorld) world).spawnParticles(ParticleTypes.FLAME,x,y,z,10,0.3,0.3,0.3,0);

                        for (LivingEntity target : world.getNonSpectatingEntities(LivingEntity.class,new Box(x-0.8,y-0.8,z-0.8,x+0.8,y+0.8,z+0.8))) {
                            if (target.isTeammate(user) || target == user) continue;
                            target.setVelocity(dX * 2.5/i,dY * 2.5/i,dZ * 2.5/i);
                            target.setOnFireFor(5);
                            target.damage(user.getDamageSources().onFire(),6);
                            target.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS,100));
                        }

                        user.getItemCooldownManager().set(this.getDefaultStack().getItem(), (int) (skillCooldown + (skillCooldown * Math.abs(6000-dayTime)/7000)));
                    }
                } else {
                    world.playSound(null,x,y,z,SoundRegistry.ELEMENTAL_SWORD_WIND_ATTACK_01.get(),SoundCategory.PLAYERS,1,1.5f);

                    for (int j = 0; j<5; j++) {
                        for (int i = 0; i<15; i++) {
                            float yaw = (float) Math.toRadians(user.getYaw());

                            yaw += (float) ((j-2) * Math.toRadians(30));

                            double dZ = Math.cos(yaw) * i;
                            double dX = Math.sin(yaw) * -i;

                            x = user.getX() + dX / 2;
                            y = user.getY() + 0.3;
                            z = user.getZ() + dZ / 2;


                            ((ServerWorld) world).spawnParticles(ParticleTypes.SWEEP_ATTACK, x, y, z, 1, 0, 0, 0, 0);

                            for (LivingEntity target : world.getNonSpectatingEntities(LivingEntity.class, new Box(x - 0.8, y - 5, z - 0.8, x + 0.8, y + 5, z + 0.8))) {
                                if (target.isTeammate(user) || target == user) continue;
                                target.setVelocity(0, 1.5, 0);
                                target.damage(user.getDamageSources().magic(), 2);
                                target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100,3));
                            }
                        }

                        user.getItemCooldownManager().set(this.getDefaultStack().getItem(), (int) (skillCooldown + (skillCooldown * Math.abs(18000-dayTime)/7000)));
                    }
                }
            }
        }
        return super.use(world, user, hand);
    }

    protected void dayPassiveEffect(ItemStack stack, LivingEntity target, LivingEntity attacker, ServerWorld world) {
        world.playSound(null,attacker.getX(),attacker.getY(),attacker.getZ(), SoundRegistry.MAGIC_SWORD_BLOCK_01.get(), SoundCategory.PLAYERS,0.25f,1);
        world.spawnParticles(ParticleTypes.ELECTRIC_SPARK,attacker.getX(),attacker.getY()+0.5,attacker.getZ(),20,0.7,0.7,0.7,0);
        for (LivingEntity passiveTarget : attacker.getWorld().getNonSpectatingEntities(LivingEntity.class,new Box(attacker.getX()-30,attacker.getY()-30,attacker.getZ()-30,attacker.getX()+30,attacker.getY()+30,attacker.getZ()+30))) {
            if (passiveTarget == attacker) continue;
            passiveTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 70, 0), attacker);
        }
    }

    protected void nightPassiveEffect(ItemStack stack, LivingEntity target, LivingEntity attacker, ServerWorld world) {
        world.playSound(null,attacker.getX(),attacker.getY(),attacker.getZ(), SoundRegistry.MAGIC_SWORD_BLOCK_01.get(), SoundCategory.PLAYERS,0.25f,1);
        world.spawnParticles(ParticleTypes.SQUID_INK,attacker.getX(),attacker.getY()+0.5,attacker.getZ(),20,0.7,0.7,0.7,0);
        for (LivingEntity passiveTarget : attacker.getWorld().getNonSpectatingEntities(LivingEntity.class,new Box(attacker.getX()-10,attacker.getY()-10,attacker.getZ()-10,attacker.getX()+10,attacker.getY()+10,attacker.getZ()+10))) {
            if (passiveTarget == attacker) continue;
            passiveTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 70, 0), attacker);
        }
        attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 70, 0), attacker);
        attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 70, 1), attacker);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if (attacker.getRandom().nextInt(100) <= 20) {
                long dayTime = Math.abs(attacker.getWorld().getTimeOfDay() % 24000);
                boolean fixedTime = attacker.getWorld().getDimension().hasFixedTime();
                if(fixedTime) {
                    if (attacker.getRandom().nextBoolean()) {
                        dayPassiveEffect(stack,target,attacker, ((ServerWorld) attacker.getWorld()));
                    } else {
                        nightPassiveEffect(stack,target,attacker, ((ServerWorld) attacker.getWorld()));
                    }
                } else {
                    if (dayTime<13000) {
                        dayPassiveEffect(stack,target,attacker, ((ServerWorld) attacker.getWorld()));
                    } else {
                        nightPassiveEffect(stack,target,attacker, ((ServerWorld) attacker.getWorld()));
                    }
                }
            }
        }
        return super.postHit(stack, target, attacker);
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style RIGHTCLICK = HelperMethods.getStyle("rightclick");
        Style ABILITY = HelperMethods.getStyle("ability");
        Style TEXT = HelperMethods.getStyle("text");
        tooltip.add(Text.literal(""));


        if (world != null) {
            long dayTime = Math.abs(world.getTimeOfDay() % 24000);
            boolean fixedTime = world.getDimension().hasFixedTime();

            if (fixedTime) {
                tooltip.add(Text.translatable("item.simplymore.timekeeper_timeless.tooltip1").setStyle(ABILITY));
                tooltip.add(Text.translatable("item.simplymore.timekeeper_timeless.tooltip2").setStyle(TEXT));
                tooltip.add(Text.literal(""));
                tooltip.add(Text.translatable("item.simplymore.timekeeper_timeless.tooltip3").setStyle(TEXT));
                tooltip.add(Text.translatable("item.simplymore.timekeeper_timeless.tooltip4").setStyle(TEXT));
                tooltip.add(Text.literal(""));
                tooltip.add(Text.translatable("item.simplymore.timekeeper_timeless.tooltip5").setStyle(TEXT));
                tooltip.add(Text.translatable("item.simplymore.timekeeper_timeless.tooltip6").setStyle(TEXT));
                tooltip.add(Text.literal(""));
                tooltip.add(Text.translatable("item.simplymore.timekeeper_timeless.tooltip7").setStyle(TEXT));
                tooltip.add(Text.translatable("item.simplymore.timekeeper_timeless.tooltip8").setStyle(TEXT));
                super.appendTooltip(itemStack, world, tooltip, tooltipContext);
                return;
            }

            if (dayTime < 13000) {
                tooltip.add(Text.translatable("item.simplymore.timekeeper_day.tooltip1").setStyle(ABILITY));
                tooltip.add(Text.translatable("item.simplymore.timekeeper_day.tooltip2").setStyle(TEXT));
                tooltip.add(Text.translatable("item.simplymore.timekeeper_day.tooltip3").setStyle(TEXT));
                tooltip.add(Text.literal(""));
                tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(RIGHTCLICK));
                tooltip.add(Text.translatable("item.simplymore.timekeeper_day.tooltip4").setStyle(TEXT));
                tooltip.add(Text.translatable("item.simplymore.timekeeper_day.tooltip5").setStyle(TEXT));
                tooltip.add(Text.translatable("item.simplymore.timekeeper_day.tooltip6").setStyle(TEXT));
                tooltip.add(Text.literal(""));
                tooltip.add(Text.translatable("item.simplymore.timekeeper_day.tooltip7").setStyle(TEXT));
                tooltip.add(Text.translatable("item.simplymore.timekeeper_day.tooltip8").setStyle(TEXT));
                tooltip.add(Text.literal(""));
                tooltip.add(Text.translatable("item.simplymore.timekeeper_day.tooltip9").setStyle(TEXT));
            } else {
                tooltip.add(Text.translatable("item.simplymore.timekeeper_night.tooltip1").setStyle(ABILITY));
                tooltip.add(Text.translatable("item.simplymore.timekeeper_night.tooltip2").setStyle(TEXT));
                tooltip.add(Text.translatable("item.simplymore.timekeeper_night.tooltip3").setStyle(TEXT));
                tooltip.add(Text.literal(""));
                tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(RIGHTCLICK));
                tooltip.add(Text.translatable("item.simplymore.timekeeper_night.tooltip4").setStyle(TEXT));
                tooltip.add(Text.translatable("item.simplymore.timekeeper_night.tooltip5").setStyle(TEXT));
                tooltip.add(Text.translatable("item.simplymore.timekeeper_night.tooltip6").setStyle(TEXT));
                tooltip.add(Text.literal(""));
                tooltip.add(Text.translatable("item.simplymore.timekeeper_night.tooltip7").setStyle(TEXT));
                tooltip.add(Text.translatable("item.simplymore.timekeeper_night.tooltip8").setStyle(TEXT));
                tooltip.add(Text.literal(""));
                tooltip.add(Text.translatable("item.simplymore.timekeeper_night.tooltip9").setStyle(TEXT));
            }
        }

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

        HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.ASH, ParticleTypes.ASH, ParticleTypes.ASH, true);
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
