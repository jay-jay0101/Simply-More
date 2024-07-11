package net.rosemarythmye.simplymore.item.itemclasses.uniques;

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
import net.rosemarythmye.simplymore.item.itemclasses.UniqueSword;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;

public class TheBloodHarvester extends UniqueSword {
    int skillCooldown = 1800;

    public TheBloodHarvester(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            if (!attacker.getWorld().isClient()) {
                if(!(target instanceof ArmorStandEntity)) {
                    if (!attacker.hasStatusEffect(ModEffects.HARVEST)) {
                        attacker.heal(this.getAttackDamage() / 10);
                    } else {
                        attacker.heal(this.getAttackDamage() / 5);
                        target.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER,80,0));
                    }
                }
            }
        return super.postHit(stack, target, attacker);
    }


    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient) {
            user.addStatusEffect(new StatusEffectInstance(ModEffects.HARVEST, 300, 0));
            user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
            ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.CRIMSON_SPORE, user.getX(), user.getY() + 0.5, user.getZ(), 500, 0.5, 0.5, 0.5, 0.25);
            user.getWorld().playSound((PlayerEntity) null, user.getBlockPos(), SoundRegistry.MAGIC_SWORD_ATTACK_WITH_BLOOD_04.get(), user.getSoundCategory(), 2F, 0F);
        }
        return super.use(world, user, hand);
    }


    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style RIGHTCLICK = HelperMethods.getStyle("rightclick");
        Style ABILITY = HelperMethods.getStyle("ability");
        Style TEXT = HelperMethods.getStyle("text");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.the_blood_harvester.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.the_blood_harvester.tooltip2").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(RIGHTCLICK));
        tooltip.add(Text.translatable("item.simplymore.the_blood_harvester.tooltip3").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.the_blood_harvester.tooltip4").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.the_blood_harvester.tooltip5").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.the_blood_harvester.tooltip6").setStyle(TEXT));

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
