package net.rosemarythyme.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;

public class TheBloodHarvesterItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = 1800;

    public TheBloodHarvesterItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            if (!attacker.getWorld().isClient()) {
                if (!(target instanceof ArmorStandEntity)) {
                    if (!attacker.hasStatusEffect(ModEffectsRegistry.HARVEST)) {
                        attacker.heal(this.getAttackDamage() / 10);
                    } else {
                        attacker.heal(this.getAttackDamage() / 5);
                        target.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER,80,0));
                    }
                }
            }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient) {
            user.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.HARVEST, 300, 0));
            user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
            ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.CRIMSON_SPORE, user.getX(), user.getY() + 0.5, user.getZ(), 500, 0.5, 0.5, 0.5, 0.25);
            user.getWorld().playSound(null, user.getBlockPos(), SoundRegistry.MAGIC_SWORD_ATTACK_WITH_BLOOD_04.get(), user.getSoundCategory(), 2F, 0F);
        }
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        int stepMod = 0;
        SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.LANDING_LAVA, ParticleTypes.LANDING_LAVA, ParticleTypes.CRIMSON_SPORE);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style rightClickStyle = HelperMethods.getStyle("rightclick");
        Style abilityStyle = HelperMethods.getStyle("ability");
        Style textStyle = HelperMethods.getStyle("text");

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.the_blood_harvester.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.the_blood_harvester.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.the_blood_harvester.tooltip3").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.the_blood_harvester.tooltip4").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.the_blood_harvester.tooltip5").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.the_blood_harvester.tooltip6").setStyle(textStyle));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
}
