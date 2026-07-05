package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.item.components.DayTimeComponent;
import net.rosemarythyme.simplymore.registry.ModComponentRegistry;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;
import net.rosemarythyme.simplymore.util.VisualEffectsUtils;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;

import java.util.List;


public class TimekeeperItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = UNIQUE_CONFIG.timekeeper.cooldown;

    public TimekeeperItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.SWORD, settings);
    }


    private int calculateCooldown(long currentTime) {
        return (currentTime < 13000)
                ? (int) (skillCooldown + (skillCooldown * Math.abs(6000 - currentTime) / 7000))
                : (int) (skillCooldown + (skillCooldown * Math.abs(18000 - currentTime) / 7000));
    }

    private void applyTimeBasedEffect(long timeOfDay, LivingEntity entity, ServerWorld world) {
        if (timeOfDay < 13000) {
            //day
        } else {
            //night
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        if (world != null) {
            long dayTime = Math.abs(world.getTimeOfDay() % 24000);
            boolean isFixedTime = world.getDimension().hasFixedTime();

            if (isFixedTime) {
                stack.set(ModComponentRegistry.DAYTIME.get(),
                        DayTimeComponent.of(DayTimeComponent.DayForm.TIMELESS));
            } else {
                if (dayTime < 13000) {
                    stack.set(ModComponentRegistry.DAYTIME.get(),
                            DayTimeComponent.of(DayTimeComponent.DayForm.DAY));
                } else {
                    stack.set(ModComponentRegistry.DAYTIME.get(),
                            DayTimeComponent.of(DayTimeComponent.DayForm.NIGHT));
                }
            }
        }

        VisualEffectsUtils.handleFootfalls(entity, stack, world, ParticleTypes.ASH);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
//        Style textStyle = Styles.TEXT;
//        Style abilityStyle = Styles.ABILITY;
//        Style rightClickStyle = Styles.RIGHT_CLICK;
//
//        tooltip.add(Text.literal(""));
//
//        DayTimeComponent component = itemStack.get(ModComponentRegistry.DAYTIME.get());
//
//        if(component == null) {
//            appendDayTooltips(tooltip, rightClickStyle, abilityStyle, textStyle);
//            super.appendTooltip(itemStack, tooltipContext, tooltip, type);
//            return;
//        }
//
//        switch(component.getForm()) {
//            case DAY -> appendDayTooltips(tooltip, rightClickStyle, abilityStyle, textStyle);
//            case NIGHT -> appendNightTooltips(tooltip, rightClickStyle, abilityStyle, textStyle);
//            case TIMELESS -> appendFixedTimeTooltips(tooltip, rightClickStyle, abilityStyle, textStyle);
//        }

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ModItemsRegistry.TIMEKEEPER)); // TODO: gotta change this probably
        }

        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 400;
        @ValidatedInt.Restrict(min = 0)
        public int nightActiveSlownessTime = 100;
        @ValidatedInt.Restrict(min = 0)
        public int dayActiveBlindnessTime = 100;
        @ValidatedInt.Restrict(min = 0)
        public int nightPassiveEffectTime = 70;
        @ValidatedInt.Restrict(min = 0)
        public int dayPassiveEffectTime = 70;
        @ValidatedFloat.Restrict(min = 0f)
        public float nightDamage = 2f;
        @ValidatedFloat.Restrict(min = 0f)
        public float dayDamage = 6f;
        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.2f;
    }
}
