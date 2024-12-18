package net.rosemarythyme.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.RiftAreaEffectCloudEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.util.HelperMethods;
import org.joml.Vector3f;

import java.util.List;

public class MatterbaneItem extends SimplyMoreUniqueSwordItem {

    private static final String[] colours = {
            "white",
            "orange",
            "magenta",
            "light_blue",
            "yellow",
            "lime",
            "pink",
            "gray",
            "light_gray",
            "cyan",
            "purple",
            "blue",
            "brown",
            "green",
            "red",
            "black"
    };

    Vector3f[] particleColours = new Vector3f[] {
            new Vector3f(1, 1, 1),
            new Vector3f(1, 0.667f, 0),
            new Vector3f(1, 0.333f, 1),
            new Vector3f(0.333f,1, 1),
            new Vector3f(1, 1, 0.333f),
            new Vector3f(0.333f,1, 0.333f),
            new Vector3f(1, 0.75f, 0.8f),
            new Vector3f(1, 0.333f, 0.333f),
            new Vector3f(0.667f,0.667f, 0.667f),
            new Vector3f(0, 0.667f, 0.667f),
            new Vector3f(0.667f,0, 0.667f),
            new Vector3f(0.333f,0.333f, 1),
            new Vector3f(0.667f,0.333f, 0),
            new Vector3f(0, 0.667f, 0),
            new Vector3f(0.667f,0, 0),
            new Vector3f(0, 0, 0)
    };

    int skillCooldown = effect.getMatterbaneRiftCooldown();

    public MatterbaneItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {


            Object color = user.getStackInHand(hand).getOrCreateNbt().get("simplymore:color");
            color = getMatterbaneColor(color);
            RiftAreaEffectCloudEntity riftAreaEffectCloudEntity = new RiftAreaEffectCloudEntity(user.getWorld(),user.getX(),user.getY()+3,user.getZ(),user, (int) color);
            user.getWorld().spawnEntity(riftAreaEffectCloudEntity);
            user.getWorld().playSound(null, user.getBlockPos(), SoundEvents.ENTITY_WARDEN_DIG, user.getSoundCategory(), 1F, 2F);
            user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
        }
        return super.use(world, user, hand);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if (attacker.getRandom().nextBetween(1, 100) <= effect.getMatterbaneBeamChance() && attacker instanceof PlayerEntity player) {
                fireBolt(player, stack);
            }
        }
        return super.postHit(stack, target, attacker);
    }

    public void fireBolt(PlayerEntity user, ItemStack stack) {
        user.getWorld().playSound(null, user.getBlockPos(), SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, user.getSoundCategory(), 1F, 2F);
        for(int j = 0; j<effect.getMatterbaneBeamRange()*4; j++) {

            float yaw = (float) Math.toRadians(user.getYaw()+90);

            float velocityX = (float) (Math.cos(yaw)) * 0.5f;
            float velocityZ = (float) (Math.sin(yaw)) * 0.5f;

            double dX = velocityX * j;
            double dZ = velocityZ * j;

            double x = user.getX();
            double y = (user.getEyeY() + user.getY())/2;
            double z = user.getZ();

            Object color = stack.getOrCreateNbt().get("simplymore:color");
            color = (getMatterbaneColor(color));

            DustParticleEffect particleEffect = new DustParticleEffect(particleColours[(int) color],2);

            ((ServerWorld) user.getWorld()).spawnParticles(particleEffect,x+dX,y,z+dZ,1,0,0,0,0);
            for (LivingEntity entity : user.getWorld().getNonSpectatingEntities(LivingEntity.class,new Box(x-0.6+dX,y-0.6,z-0.6+dZ,x+0.6+dX,y+0.6,z+0.6+dZ)))
            {
                if (entity.isTeammate(user) || entity == user || entity.isInvulnerable()) continue;

                entity.damage(user.getDamageSources().magic(),effect.getMatterbaneBeamDamage());
            }
        }
    }


    int stepMod = 0;
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        stepMod = SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.ASH);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style rightClickStyle = HelperMethods.getStyle("rightclick");
        Style abilityStyle = HelperMethods.getStyle("ability");
        Style textStyle = HelperMethods.getStyle("text");

        Object color = itemStack.getOrCreateNbt().get("simplymore:color");
        color = getMatterbaneColor(color);

        if ((int) color >= 0 && (int) color <= 15)
            color = colours[(int) color];
        else
            color = colours[14];

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip2").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip3", effect.getMatterbaneBeamRange()).setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip4").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip5").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip6").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip7").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip8").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip9").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip10").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip11").append(Text.translatable("item.simplymore.matterbane.color_" + color)).setStyle(abilityStyle));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }

    public static int getMatterbaneColor(Object color) {
        if (color == null) return 14;
        String colorString = color.toString().replaceAll("\"", "");
        int colorInt;
        try {
            colorInt = Integer.parseInt(colorString);
        } catch (NumberFormatException e) {
            return 14;
        }
        return Math.max(0, Math.min(colorInt, 15));
    }

}
