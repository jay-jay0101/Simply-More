package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.annotations.Action;
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedSet;
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedRegistryType;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.VipersCallProjectileAreaEffectCloudEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.registry.EffectRegistry;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class VipersCallItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = effect.vipers_call.vipersCallCooldown;

    public VipersCallItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.SWORD, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient) {
            VipersCallProjectileAreaEffectCloudEntity chakram = new VipersCallProjectileAreaEffectCloudEntity(user.getWorld(),user.getX(),user.getEyeY()-0.6,user.getZ(),user);
            user.getWorld().spawnEntity(chakram);
            user.getItemCooldownManager().set(this, skillCooldown);
            user.getWorld().playSound(null, user.getBlockPos(), SoundRegistry.DARK_SWORD_WHOOSH_02.get(), user.getSoundCategory(), 2F, 1F);
        }
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        if (world.getTime() % 20 == 0 && entity instanceof PlayerEntity player && player.getStackInHand(Hand.MAIN_HAND).equals(stack)) {
            for (StatusEffectInstance effect : player.getStatusEffects()) {
                if (effect.getDuration()<25) {
                    if (isInBlacklist(effect)) continue;
                    player.addStatusEffect(new StatusEffectInstance(effect.getEffectType(),25,0));
                }
            }
        }

        SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, ParticleTypes.ASH);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private boolean isInBlacklist(StatusEffectInstance status) {
        return getBlacklist().contains(status.getEffectType().value());
    }

    private Set<StatusEffect> blacklist = null;

    private Set<StatusEffect> getBlacklist() {
        if(blacklist == null) {
            blacklist = Stream.concat(
                    Set.of(EffectRegistry.FATAL_FLICKER.get(), StatusEffects.ABSORPTION.value(),
                            EffectRegistry.FLAMESEED.get(), EffectRegistry.FRENZY.get()).stream(),
                    effect.vipers_call.blacklist.stream()
            ).collect(Collectors.toSet());
        }

        return blacklist;
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.vipers_call.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.vipers_call.tooltip2").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.vipers_call.tooltip3").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.vipers_call.tooltip4").setStyle(textStyle));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplymore.vipers_call.tooltip5").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.vipers_call.tooltip6").setStyle(textStyle));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.vipers_call.tooltip7").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.vipers_call.tooltip8").setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ModItemsRegistry.VIPERS_CALL));
        }

        @ValidatedInt.Restrict(min = 0)
        public int vipersCallCooldown = 1200;
        @RequiresAction(action = Action.RESTART)
        public ValidatedSet<StatusEffect> blacklist = ValidatedRegistryType.of(StatusEffects.ABSORPTION.value(), Registries.STATUS_EFFECT, (entry) -> true).toSet();
    }
}
