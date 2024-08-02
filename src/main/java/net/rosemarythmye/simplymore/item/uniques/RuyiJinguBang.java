package net.rosemarythmye.simplymore.item.uniques;

import com.ibm.icu.text.MessagePattern;
import net.fabricmc.loader.impl.game.minecraft.applet.AppletLauncher;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.particle.BlockDustParticle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtFloat;
import net.minecraft.nbt.NbtList;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythmye.simplymore.item.UniqueSword;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;

public class RuyiJinguBang extends UniqueSword {
    int skillCooldown = 700;


    public RuyiJinguBang(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }


    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!user.getWorld().isClient && user instanceof PlayerEntity) {
            ServerWorld serverWorld = ((ServerWorld) world);
            if(remainingUseTicks % 20 == 0 && remainingUseTicks>9999799) serverWorld.playSound(null,user.getX(),user.getY(),user.getZ(), SoundRegistry.ELEMENTAL_BOW_EARTH_SHOOT_IMPACT_03.get(), SoundCategory.PLAYERS,0.5f,1f);
        }
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    public int getMaxUseTime(ItemStack stack) {
        return 9999999;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }


    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style RIGHTCLICK = HelperMethods.getStyle("rightclick");
        Style ABILITY = HelperMethods.getStyle("ability");
        Style TEXT = HelperMethods.getStyle("text");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.ruyi_jingu_bang.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(RIGHTCLICK));
        tooltip.add(Text.translatable("item.simplymore.ruyi_jingu_bang.tooltip2").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.ruyi_jingu_bang.tooltip3").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.ruyi_jingu_bang.tooltip4").setStyle(TEXT));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
        if(!user.getWorld().isClient && user instanceof PlayerEntity player) {
            if(remainingUseTicks<9999979) {
                player.getItemCooldownManager().set(this.getDefaultStack().getItem(),skillCooldown);

                int itemUseTime = player.getItemUseTime();
                if(itemUseTime>200) itemUseTime = 200;

                int range = ((int) Math.floor(itemUseTime / 5f));
                float damage = 8 * (itemUseTime/50f);
                float yaw = (float) Math.toRadians(user.getYaw()+90);
                float pitch = (float) Math.toRadians(user.getPitch());

                for(int i = 0; i<range+1; i++) {

                    float dX = (float) (Math.cos(yaw) * Math.cos(pitch)) * i;
                    float dZ = (float) (Math.sin(yaw) * Math.cos(pitch)) * i;
                    float dY = (float) Math.sin(pitch) * -i;

                    float x = (float) user.getX();
                    float y = (float) user.getY();
                    float z = (float) user.getZ();

                    user.getWorld().playSound(null,x+dX,y+dY,z+dZ,SoundRegistry.DARK_SWORD_BLOCK.get(),SoundCategory.PLAYERS,1,1);


                    for (LivingEntity entity : user.getWorld().getNonSpectatingEntities(LivingEntity.class,new Box(x-1+dX,y-1+dY,z-1+dZ,x+1+dX,y+1+dY,z+1+dZ)))
                    {
                        if(entity.isTeammate(user) || entity == user || entity.isInvulnerable()) continue;

                        entity.damage(user.getDamageSources().playerAttack(((PlayerEntity) user)),damage);
                        entity.setVelocity(dX/i,dY/i,dZ/i);
                    }

                    ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.CLOUD,x+dX,y+dY,z+dZ,15,1,1,1,0.1);

                }

            }
        }
    }

    private static int stepMod = 0;

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (stepMod > 0) {
            --stepMod;
        }

        if (stepMod <= 0) {
            stepMod = 7;
        }

        HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.WAX_ON, ParticleTypes.WAX_ON, ParticleTypes.WAX_ON, true);
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
