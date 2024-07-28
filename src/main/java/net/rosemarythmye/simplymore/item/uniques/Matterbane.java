package net.rosemarythmye.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.world.World;
import net.rosemarythmye.simplymore.entity.Rift;
import net.rosemarythmye.simplymore.entity.VipersCallProjectile;
import net.rosemarythmye.simplymore.item.UniqueSword;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.Arrays;
import java.util.List;

public class Matterbane extends UniqueSword {

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

    int skillCooldown = 1200;
    public Matterbane(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if (attacker.getRandom().nextInt(100) <= 15) {
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 20, 1), attacker);
            }
        }
        return super.postHit(stack, target, attacker);
    }


    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style RIGHTCLICK = HelperMethods.getStyle("rightclick");
        Style ABILITY = HelperMethods.getStyle("ability");
        Style TEXT = HelperMethods.getStyle("text");
        Object color = itemStack.getOrCreateNbt().get("simplymore:color");
        if (color == null) color = "14";
        else color = color.toString().replaceAll("\"","");
        try {
            color = Integer.parseInt((String) color);
        } catch (NumberFormatException e) {
            color = 14;
        }
        if ((int) color >= 0 && (int) color <= 15) color = colours[(int) color];
        else color = colours[14];

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip2").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(RIGHTCLICK));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip3").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip4").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip5").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip6").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip7").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip8").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip9").append(Text.translatable("item.simplymore.matterbane.color_" + color)).setStyle(ABILITY));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {
            Object color = user.getStackInHand(hand).getOrCreateNbt().get("simplymore:color");
            if (color == null) color = "14";
            else color = color.toString().replaceAll("\"","");
            try {
                color = Integer.parseInt((String) color);
            } catch (NumberFormatException e) {
                color = 14;
            }
            if ((int) color < 0 || (int) color > 15) color = 14;
            Rift rift = new Rift(user.getWorld(),user.getX(),user.getY()+3,user.getZ(),user, (int) color);
            user.getWorld().spawnEntity(rift);
            user.getWorld().playSound(null, user.getBlockPos(), SoundEvents.ENTITY_WARDEN_DIG, user.getSoundCategory(), 1F, 2F);
            user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
        }
        return super.use(world, user, hand);
    }

}
