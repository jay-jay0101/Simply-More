package net.rosemarythmye.simplymore.item.uniques.idols;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.rosemarythmye.simplymore.entity.AuraOfCorruption;
import net.rosemarythmye.simplymore.entity.AuraOfPurity;
import net.rosemarythmye.simplymore.item.UniqueSword;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;


public class TarnishedIdol extends UniqueSword {

    public TarnishedIdol(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if (attacker.getRandom().nextInt(100) <= 10) {
                ((ServerWorld) attacker.getWorld()).spawnParticles(ParticleTypes.FALLING_OBSIDIAN_TEAR, attacker.getX(), attacker.getY()+1, attacker.getZ(), 300, 1, 1, 1, 0);
                attacker.getWorld().spawnEntity(new AuraOfCorruption(attacker.getWorld(),attacker.getX(),attacker.getY(),attacker.getZ(),attacker));
                attacker.getWorld().playSound(null, attacker.getBlockPos(), SoundEvents.ITEM_BUCKET_FILL, attacker.getSoundCategory(), 2F, 0.3F);
            }
        }
        return super.postHit(stack, target, attacker);
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style TEXT = HelperMethods.getStyle("text");
        Style ABILITY = HelperMethods.getStyle("ability");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.tarnished_idol.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.tarnished_idol.tooltip2").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.tarnished_idol.tooltip3").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.tarnished_idol.tooltip4").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.tarnished_idol.tooltip5").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.tarnished_idol.tooltip6").setStyle(TEXT));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
}
