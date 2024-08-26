package net.rosemarythyme.simplymore.item.uniques.idols;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.AuraOfPurityAreaEffectCloudEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;


public class HolyLightItem extends SimplyMoreUniqueSwordItem {

    int skillCooldown = 800;

    public HolyLightItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        SimplyMoreHelperMethods.simplyMore$IdolHitEffects(
                attacker,
                ParticleTypes.FALLING_WATER,
                300,
                1.0D,
                1.0D,
                1.0D,
                0.0D,
                new AuraOfPurityAreaEffectCloudEntity(
                        attacker.getWorld(),
                        attacker.getX(),
                        attacker.getY(),
                        attacker.getZ(),
                        attacker
                )
        );

        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        SimplyMoreHelperMethods.simplyMore$IdolUseEffects(
                this,
                user,
                ModEffectsRegistry.BLESSING,
                160,
                SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON,
                2F,
                1.5F,
                ParticleTypes.WAX_OFF,
                50,
                0.25D,
                0.5D,
                0.25D,
                0.1D,
                skillCooldown
        );
        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style TEXT = HelperMethods.getStyle("text");
        Style ABILITY = HelperMethods.getStyle("ability");
        Style RIGHTCLICK = HelperMethods.getStyle("rightclick");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.holylight.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.holylight.tooltip2").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.holylight.tooltip3").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.holylight.tooltip4").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.holylight.tooltip5").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(RIGHTCLICK));
        tooltip.add(Text.translatable("item.simplymore.holylight.tooltip6").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.holylight.tooltip7").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.holylight.tooltip8").setStyle(TEXT));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
}
