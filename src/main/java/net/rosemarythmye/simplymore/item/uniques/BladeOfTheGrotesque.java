package net.rosemarythmye.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
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
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import net.rosemarythmye.simplymore.effect.ModEffects;
import net.rosemarythmye.simplymore.item.UniqueSword;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;

public class BladeOfTheGrotesque extends UniqueSword {
    int skillCooldown = 500;


    public BladeOfTheGrotesque(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }


    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {
            user.addStatusEffect(new StatusEffectInstance(ModEffects.SOLIDIFIED,50));
            user.getWorld().playSound(null, user.getBlockPos(), SoundEvents.UI_STONECUTTER_TAKE_RESULT, user.getSoundCategory(), 2F, 1F);
            ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.ASH,user.getX(),user.getEyeY()-0.25,user.getZ(),1000,0.2,0.5,0.2,1);
            user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
        }
        return super.use(world, user, hand);
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style RIGHTCLICK = HelperMethods.getStyle("rightclick");
        Style ABILITY = HelperMethods.getStyle("ability");
        Style TEXT = HelperMethods.getStyle("text");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip2").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip3").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(RIGHTCLICK));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip4").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip5").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip6").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip7").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip8").setStyle(TEXT));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }

    private static int stepMod = 0;

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        if(entity instanceof PlayerEntity playerEntity && selected && world.getTime() % 20 == 0) playerEntity.addStatusEffect(new StatusEffectInstance(ModEffects.GROTESQUE,9999999,0,true,false,false));

        if (stepMod > 0) {
            --stepMod;
        }

        if (stepMod <= 0) {
            stepMod = 7;
        }

        HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.SMOKE, ParticleTypes.SMOKE, ParticleTypes.ASH, true);
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
