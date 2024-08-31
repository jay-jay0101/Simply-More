package net.rosemarythyme.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.EruptionAreaEffectCloudEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;


public class MoltenFlareItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = 300;

    public MoltenFlareItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getWorld().isClient()) return super.postHit(stack, target, attacker);

        if (attacker.getRandom().nextBetween(1, 100) <= 20) {
            eruption(attacker.hasStatusEffect(ModEffectsRegistry.MOLTEN_FLARE) ? 7 : 4, attacker);
            if (attacker.hasStatusEffect(ModEffectsRegistry.MOLTEN_FLARE)) {
                attacker.removeStatusEffect(ModEffectsRegistry.MOLTEN_FLARE);
            }
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {
            user.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.MOLTEN_FLARE,100));
            user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
        }
        return super.use(world, user, hand);
    }

    private void eruption(int radius, LivingEntity attacker) {
        ((ServerWorld) attacker.getWorld()).spawnParticles(ParticleTypes.LAVA, attacker.getX(), attacker.getY(), attacker.getZ(), 100, 0, 0, 0, 0);
        attacker.getWorld().spawnEntity(new EruptionAreaEffectCloudEntity(attacker.getWorld(),attacker.getX(),attacker.getY(),attacker.getZ(),radius,attacker));
        attacker.getWorld().playSound(null, attacker.getBlockPos(), SoundRegistry.SPELL_FIRE.get(), attacker.getSoundCategory(), 2F, 0.3F);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        int stepMod = 0;
        SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.LAVA, ParticleTypes.LAVA, ParticleTypes.SMOKE);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style rightClickStyle = HelperMethods.getStyle("rightclick");
        Style abilityStyle = HelperMethods.getStyle("ability");
        Style textStyle = HelperMethods.getStyle("text");

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.molten_flare.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.molten_flare.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.molten_flare.tooltip3").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.molten_flare.tooltip4").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.molten_flare.tooltip5").setStyle(textStyle));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
}
