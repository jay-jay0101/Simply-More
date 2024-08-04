package net.rosemarythmye.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stat;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythmye.simplymore.effect.ModEffects;
import net.rosemarythmye.simplymore.entity.GreatSlitherFang;
import net.rosemarythmye.simplymore.entity.PoisonBolt;
import net.rosemarythmye.simplymore.item.UniqueSword;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;
import org.joml.Vector2d;

import java.util.List;


public class SerpentineValour extends UniqueSword {
    int skillCooldown = 700;

    public SerpentineValour(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if (target.hasStatusEffect(StatusEffects.POISON) || target.hasStatusEffect(ModEffects.VENOM)) {
                target.setHealth(target.getHealth()-4);
            }
        }

        return super.postHit(stack, target, attacker);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {
            for (int i = 1; i < 7; i++) {

                boolean wait = false;

                for (LivingEntity target : user.getWorld().getNonSpectatingEntities(LivingEntity.class,new Box(user.getX()-5,user.getY()-5,user.getZ()-5,user.getX()+5,user.getY()+5,user.getZ()+5))) {
                    if(target == user || target.isTeammate(user)) continue;
                    wait = true;
                }
                if(wait) {
                    PoisonBolt shot = new PoisonBolt(user.getWorld(), user.getX() + 1, user.getY() + 2, user.getZ(), user, -2);
                    world.spawnEntity(shot);
                    shot = new PoisonBolt(user.getWorld(), user.getX() - 1, user.getY() + 2, user.getZ(), user, -2);
                    world.spawnEntity(shot);
                    shot = new PoisonBolt(user.getWorld(), user.getX(), user.getY() + 2, user.getZ() - 1, user, -2);
                    world.spawnEntity(shot);
                    shot = new PoisonBolt(user.getWorld(), user.getX(), user.getY() + 2, user.getZ() + 1, user, -2);
                    world.spawnEntity(shot);
                } else {
                    PoisonBolt shot = new PoisonBolt(user.getWorld(), user.getX() + 1, user.getY() + 2, user.getZ(), user, 0);
                    world.spawnEntity(shot);
                    shot = new PoisonBolt(user.getWorld(), user.getX() - 1, user.getY() + 2, user.getZ(), user, 0);
                    world.spawnEntity(shot);
                    shot = new PoisonBolt(user.getWorld(), user.getX(), user.getY() + 2, user.getZ() - 1, user, 0);
                    world.spawnEntity(shot);
                    shot = new PoisonBolt(user.getWorld(), user.getX(), user.getY() + 2, user.getZ() + 1, user, 0);
                    world.spawnEntity(shot);
                }
                user.getWorld().playSound(null, user.getBlockPos(), SoundRegistry.MAGIC_SHAMANIC_VOICE_15.get(), SoundCategory.PLAYERS, 0.4f, 1);
            }
            user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
        }
        return super.use(world, user, hand);
    }


    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style RIGHTCLICK = HelperMethods.getStyle("rightclick");
        Style ABILITY = HelperMethods.getStyle("ability");
        Style TEXT = HelperMethods.getStyle("text");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.serpentine_valour.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.serpentine_valour.tooltip2").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.serpentine_valour.tooltip3").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(RIGHTCLICK));
        tooltip.add(Text.translatable("item.simplymore.serpentine_valour.tooltip4").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.serpentine_valour.tooltip5").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.serpentine_valour.tooltip6").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.serpentine_valour.tooltip7").setStyle(TEXT));

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

        HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.SNEEZE, ParticleTypes.SNEEZE, ParticleTypes.SPORE_BLOSSOM_AIR, true);
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
