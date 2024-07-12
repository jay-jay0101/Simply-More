package net.rosemarythmye.simplymore.item.uniques;

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
import net.rosemarythmye.simplymore.effect.ModEffects;
import net.rosemarythmye.simplymore.entity.Eruption;
import net.rosemarythmye.simplymore.item.UniqueSword;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;


public class MoltenFlare extends UniqueSword {
    int skillCooldown = 300;

    public MoltenFlare(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if (!attacker.hasStatusEffect(ModEffects.MOLTEN_FLARE)) {
                if (attacker.getRandom().nextInt(100) <= 20) {
                    eruption(4, target, attacker);
                }
            } else {
                eruption(7, target, attacker);
                attacker.removeStatusEffect(ModEffects.MOLTEN_FLARE);
            }
        }
        return super.postHit(stack, target, attacker);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {
            user.addStatusEffect(new StatusEffectInstance(ModEffects.MOLTEN_FLARE,100));
            user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
        }
        return super.use(world, user, hand);
    }

    private void eruption(int radius,LivingEntity target,LivingEntity attacker) {
        ((ServerWorld) attacker.getWorld()).spawnParticles(ParticleTypes.LAVA, attacker.getX(), attacker.getY(), attacker.getZ(), 100, 0, 0, 0, 0);
        attacker.getWorld().spawnEntity(new Eruption(attacker.getWorld(),attacker.getX(),attacker.getY(),attacker.getZ(),radius,attacker));
        attacker.getWorld().playSound(null, attacker.getBlockPos(), SoundRegistry.SPELL_FIRE.get(), attacker.getSoundCategory(), 2F, 0.3F);
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style RIGHTCLICK = HelperMethods.getStyle("rightclick");
        Style ABILITY = HelperMethods.getStyle("ability");
        Style TEXT = HelperMethods.getStyle("text");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.molten_flare.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.molten_flare.tooltip2").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(RIGHTCLICK));
        tooltip.add(Text.translatable("item.simplymore.molten_flare.tooltip3").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.molten_flare.tooltip4").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.molten_flare.tooltip5").setStyle(TEXT));

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

        HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.LAVA, ParticleTypes.LAVA, ParticleTypes.SMOKE, true);
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
