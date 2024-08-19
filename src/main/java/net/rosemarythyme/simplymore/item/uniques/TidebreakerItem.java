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
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.UniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;


public class TidebreakerItem extends UniqueSwordItem {

    int lastHitTime;

    LivingEntity lastHit;

    int skillCooldown = 400;

    public TidebreakerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if (attacker.getRandom().nextInt(100) <= 25) {
                if(!attacker.hasStatusEffect(ModEffectsRegistry.TIDEBREAKER)) {
                    attacker.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.TIDEBREAKER, 300, 0), attacker);
                }
            }

            lastHitTime = 0;
            lastHit = target;

        }
        return super.postHit(stack, target, attacker);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {
            if(lastHit != null && lastHit.isAlive()) {

                if(lastHit.getWorld() != user.getWorld() || lastHit.distanceTo(user) > 15) return super.use(world, user, hand);

                double x1 = lastHit.getX();
                double y1 = lastHit.getY();
                double z1 = lastHit.getZ();

                double x2 = user.getX();
                double y2 = user.getY();
                double z2 = user.getZ();

                user.teleport(x1,y1,z1);
                lastHit.teleport(x2,y2,z2);

                user.getWorld().playSound(null,x1,y1,z1, SoundRegistry.ELEMENTAL_BOW_WATER_SHOOT_IMPACT_02.get(), SoundCategory.PLAYERS,1,1);
                user.getWorld().playSound(null,x2,y2,z2, SoundRegistry.ELEMENTAL_BOW_WATER_SHOOT_IMPACT_02.get(), SoundCategory.PLAYERS,1,1);
                ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.SPLASH,x1,y1,z1,300,2,0,2,0);
                ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.SPLASH,x2,y2,z2,300,2,0,2,0);
                lastHit = null;

                user.getItemCooldownManager().set(this.getDefaultStack().getItem(),skillCooldown);
            }
        }
        return super.use(world, user, hand);
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style RIGHTCLICK = HelperMethods.getStyle("rightclick");
        Style ABILITY = HelperMethods.getStyle("ability");
        Style TEXT = HelperMethods.getStyle("text");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.tidebreaker.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.tidebreaker.tooltip2").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.tidebreaker.tooltip3").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.tidebreaker.tooltip4").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(RIGHTCLICK));
        tooltip.add(Text.translatable("item.simplymore.tidebreaker.tooltip5").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.tidebreaker.tooltip6").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.tidebreaker.tooltip7").setStyle(TEXT));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }

    private static int stepMod = 0;

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (stepMod > 0) {
            --stepMod;
        }

        if(!world.isClient) {
            lastHitTime++;

            if(lastHitTime>200) lastHit = null;

        }
        if (stepMod <= 0) {
            stepMod = 7;
        }

        HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.BUBBLE, ParticleTypes.BUBBLE, ParticleTypes.FALLING_WATER, true);
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
