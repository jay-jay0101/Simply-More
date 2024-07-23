package net.rosemarythmye.simplymore.item.uniques;

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
import net.rosemarythmye.simplymore.effect.ModEffects;
import net.rosemarythmye.simplymore.item.UniqueSword;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;

public class TheVesselBreach extends UniqueSword {
    int skillCooldown = 1800;

    public TheVesselBreach(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            if (!attacker.getWorld().isClient()) {
                if(!(target instanceof ArmorStandEntity)) {
                    if (!attacker.hasStatusEffect(ModEffects.RAGE)) {
                        attacker.heal(this.getAttackDamage() / 10);
                    } else {
                        attacker.heal(this.getAttackDamage() / 3);
                    }
                }
            }
        return super.postHit(stack, target, attacker);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient) {
            user.damage(user.getDamageSources().genericKill(), user.getMaxHealth()*0.3f);
            user.addStatusEffect(new StatusEffectInstance(ModEffects.RAGE, 200, 0));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE,12,4));
            user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
            ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.CRIMSON_SPORE, user.getX(), user.getY() + 0.5, user.getZ(), 500, 0.5, 0.5, 0.5, 0.25);
            user.getWorld().playSound(null, user.getBlockPos(), SoundRegistry.MAGIC_SWORD_ATTACK_WITH_BLOOD_04.get(), user.getSoundCategory(), 2F, 0F);
        }
        return super.use(world, user, hand);
    }


    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style RIGHTCLICK = HelperMethods.getStyle("rightclick");
        Style ABILITY = HelperMethods.getStyle("ability");
        Style TEXT = HelperMethods.getStyle("text");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.the_vessel_breach.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.the_vessel_breach.tooltip2").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(RIGHTCLICK));
        tooltip.add(Text.translatable("item.simplymore.the_vessel_breach.tooltip3").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.the_vessel_breach.tooltip4").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.the_vessel_breach.tooltip5").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.the_vessel_breach.tooltip6").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.the_vessel_breach.tooltip7").setStyle(TEXT));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }

    private static int stepMod = 0;

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        if (stepMod > 0) {
            --stepMod;
        }

        if (stepMod <= 0) {
            stepMod = 7;
        }

        HelperMethods.createFootfalls(entity, stack, world, stepMod,ParticleTypes.LANDING_LAVA , ParticleTypes.LANDING_LAVA, ParticleTypes.CRIMSON_SPORE, true);
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
