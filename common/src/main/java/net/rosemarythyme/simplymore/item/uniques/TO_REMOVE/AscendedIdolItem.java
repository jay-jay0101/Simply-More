package net.rosemarythyme.simplymore.item.uniques.TO_REMOVE;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.entity.AuraOfPurityEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class AscendedIdolItem extends SimplyMoreUniqueSwordItem {

    public AscendedIdolItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    protected Identifier getConfigPath() {
        return Identifier.of("simplymore.unique_effect.holylight");
    }

    @Override
    public FootfallParticles getFootfalls() {
        return FootfallParticles.none();
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(attacker.getWorld().isClient) return super.postHit(stack, target, attacker);

        if (MathUtils.chance(attacker, UNIQUE_CONFIG.holylight.chance)) {
            AudioVisualUtils.particleAroundEntity(attacker, ParticleTypes.FALLING_WATER, 300, 1d, 0f);
            AudioVisualUtils.playSound(attacker.getWorld(), attacker.getPos(), new Sound(SoundEvents.ITEM_BUCKET_FILL).setPitch(0.3f));

            AttackUtils.spawnAbility(new AuraOfPurityEntity(attacker, attacker.getPos()), attacker);
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.ascended_idol.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.ascended_idol.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.ascended_idol.tooltip6").setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }
}
