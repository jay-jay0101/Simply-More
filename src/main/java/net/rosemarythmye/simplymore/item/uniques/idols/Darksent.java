package net.rosemarythmye.simplymore.item.uniques.idols;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythmye.simplymore.effect.ModEffects;
import net.rosemarythmye.simplymore.entity.AuraOfCorruption;
import net.rosemarythmye.simplymore.item.UniqueSword;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;


public class Darksent extends UniqueSword {

    int skillCooldown = 800;

    public Darksent(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if (attacker.getRandom().nextInt(100) <= 10) {
                ((ServerWorld) attacker.getWorld()).spawnParticles(ParticleTypes.FALLING_OBSIDIAN_TEAR, attacker.getX(), attacker.getY()+1, attacker.getZ(), 300, 1, 1, 1, 0);
                attacker.getWorld().spawnEntity(new AuraOfCorruption(attacker.getWorld(),attacker.getX(),attacker.getY(),attacker.getZ(),attacker));
                attacker.getWorld().playSound(null, attacker.getBlockPos(), SoundEvents.ITEM_BUCKET_FILL, attacker.getSoundCategory(), 2F, 0.3F);
            }
        }
        return super.postHit(stack, target, attacker);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {
            for (LivingEntity livingEntity : user.getWorld().getNonSpectatingEntities(LivingEntity.class,new Box(user.getX()-10,user.getY()-10,user.getZ()-10,user.getX()+10,user.getY()+10,user.getZ()+10))) {
                if (livingEntity == user || livingEntity.isTeammate(user)) continue;

                livingEntity.addStatusEffect(new StatusEffectInstance(ModEffects.CURSE, 160));
            }

            user.getWorld().playSound(null, user.getBlockPos(), SoundEvents.ENTITY_EVOKER_PREPARE_ATTACK, user.getSoundCategory(), 2F, 1.5F);
            ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.SCULK_SOUL,user.getX(),user.getY()+1,user.getZ(),50,0.25,0.5,0.25,0.1);
            user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
        }
        return super.use(world, user, hand);
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style TEXT = HelperMethods.getStyle("text");
        Style ABILITY = HelperMethods.getStyle("ability");
        Style RIGHTCLICK = HelperMethods.getStyle("rightclick");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.darksent.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.darksent.tooltip2").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.darksent.tooltip3").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.darksent.tooltip4").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.darksent.tooltip5").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(RIGHTCLICK));
        tooltip.add(Text.translatable("item.simplymore.darksent.tooltip6").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.darksent.tooltip7").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.darksent.tooltip8").setStyle(TEXT));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
}
