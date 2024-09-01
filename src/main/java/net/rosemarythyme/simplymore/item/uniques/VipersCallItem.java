package net.rosemarythyme.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.VipersCallProjectileAreaEffectCloudEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class VipersCallItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = 1200;

    public String[] effectBlacklist =
    {
        "simplyswords:fatal_flicker",
        "minecraft:absorption",
        "simplyswords:flameseed",
        "simplyswords:frenzy"
    };

    public VipersCallItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient) {
            VipersCallProjectileAreaEffectCloudEntity chakram = new VipersCallProjectileAreaEffectCloudEntity(user.getWorld(),user.getX(),user.getEyeY()-0.6,user.getZ(),user);
            user.getWorld().spawnEntity(chakram);
            user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
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

        int stepMod = 0;
        SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.ASH);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private boolean isInBlacklist(StatusEffectInstance effect) {
        List<StatusEffect> blacklistedEffects = new ArrayList<>(List.of());
        Arrays.stream(effectBlacklist).toList().forEach(
                (effectType) -> blacklistedEffects.add(
                        Registries.STATUS_EFFECT.get(new Identifier(effectType))));
        return blacklistedEffects.contains(effect.getEffectType());
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style rightClickStyle = HelperMethods.getStyle("rightclick");
        Style abilityStyle = HelperMethods.getStyle("ability");
        Style textStyle = HelperMethods.getStyle("text");

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

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
}
