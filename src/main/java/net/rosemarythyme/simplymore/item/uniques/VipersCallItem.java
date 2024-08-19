package net.rosemarythyme.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.VipersCallProjectileAreaEffectCloudEntity;
import net.rosemarythyme.simplymore.item.UniqueSwordItem;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;


public class VipersCallItem extends UniqueSwordItem {
    int skillCooldown = 1200;

    public VipersCallItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }



    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient) {
            VipersCallProjectileAreaEffectCloudEntity chakram = new VipersCallProjectileAreaEffectCloudEntity(user.getWorld(),user.getX(),user.getEyeY()-0.6,user.getZ(),user);
            user.getWorld().spawnEntity(chakram);
            user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
            user.getWorld().playSound(null, user.getBlockPos(), SoundRegistry.DARK_SWORD_WHOOSH_02.get(), user.getSoundCategory(), 2F, 1F);
        }
        return super.use(world, user, hand);
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style RIGHTCLICK = HelperMethods.getStyle("rightclick");
        Style ABILITY = HelperMethods.getStyle("ability");
        Style TEXT = HelperMethods.getStyle("text");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.vipers_call.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.vipers_call.tooltip2").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.vipers_call.tooltip3").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.vipers_call.tooltip4").setStyle(TEXT));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplymore.vipers_call.tooltip5").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.vipers_call.tooltip6").setStyle(TEXT));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(RIGHTCLICK));
        tooltip.add(Text.translatable("item.simplymore.vipers_call.tooltip7").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.vipers_call.tooltip8").setStyle(TEXT));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
    private static int stepMod = 0;

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        if (world.getTime() % 20 == 0 && entity instanceof PlayerEntity player && player.getStackInHand(Hand.MAIN_HAND).equals(stack)) {
            for (StatusEffectInstance effect : player.getStatusEffects()) {
                if (effect.getDuration()<25) {
                    if(effect.getEffectType()==StatusEffects.ABSORPTION) continue;
                    player.addStatusEffect(new StatusEffectInstance(effect.getEffectType(),25,0));
                }
            }
        }

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
