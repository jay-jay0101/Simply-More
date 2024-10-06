package net.rosemarythyme.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
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
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;

public class BladeOfTheGrotesqueItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = effect.getSolidifyCooldown();
    int skillLength = effect.getSolidifySelfStunTime();

    public BladeOfTheGrotesqueItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {
            user.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.SOLIDIFIED,skillLength));
            user.getWorld().playSound(null, user.getBlockPos(), SoundEvents.UI_STONECUTTER_TAKE_RESULT, user.getSoundCategory(), 2F, 1F);
            ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.ASH,user.getX(),user.getEyeY()-0.25,user.getZ(),1000,0.2,0.5,0.2,1);
            user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
        }
        return super.use(world, user, hand);
    }

    int stepMod = 0;

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.getTime() % 20 == 0 && entity instanceof PlayerEntity playerEntity && selected)
            playerEntity.addStatusEffect(
                    new StatusEffectInstance(
                            ModEffectsRegistry.GROTESQUE,
                            9999999,
                            0,
                            true,
                            false,
                            false)
            );

        stepMod = SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.SMOKE, ParticleTypes.SMOKE, ParticleTypes.ASH);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style rightClickStyle = HelperMethods.getStyle("rightclick");
        Style abilityStyle = HelperMethods.getStyle("ability");
        Style textStyle = HelperMethods.getStyle("text");

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip2").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip3").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip4",
                SimplyMoreHelperMethods.translateTicks(skillLength)).setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip5").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip6").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip7").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip8").setStyle(textStyle));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
}
