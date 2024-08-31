package net.rosemarythyme.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.RiftAreaEffectCloudEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.sweenus.simplyswords.util.HelperMethods;

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

    int skillCooldown = 1200;

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
            if (attacker.getRandom().nextBetween(1, 100) <= 15) {
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 20, 1), attacker);
            }
        }
        return super.postHit(stack, target, attacker);
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
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip3").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip4").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip5").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip6").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip7").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip8").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip9").append(Text.translatable("item.simplymore.matterbane.color_" + color)).setStyle(abilityStyle));

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
