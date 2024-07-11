package net.rosemarythmye.simplymore.item.itemclasses.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythmye.simplymore.entity.GreatSlitherFang;
import net.rosemarythmye.simplymore.item.itemclasses.UniqueSword;
import net.sweenus.simplyswords.util.HelperMethods;
import org.joml.Vector2d;

import java.util.List;


public class GreatSlither extends UniqueSword {
    int skillCooldown = 200;

    public GreatSlither(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if (attacker.getRandom().nextInt(100) <= 25) {
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 90, 0), attacker);
            }
        }

        return super.postHit(stack, target, attacker);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {
            for (int i = 1; i < 7; i++) {
                Vector2d position = new Vector2d(user.getX(), user.getZ());
                double userRotation = (user.getYaw() + 180) * (Math.PI / 180);
                Vector2d rotation = new Vector2d(1.2 * i * Math.sin(userRotation), 1.2 * i * Math.cos(userRotation));
                position = new Vector2d(position.x + rotation.x, position.y - rotation.y);
                world.spawnEntity(new GreatSlitherFang(world, position.x, user.getY(), position.y, user.getYaw(), 0, user));
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
        tooltip.add(Text.translatable("item.simplymore.great_slither.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.great_slither.tooltip2").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(RIGHTCLICK));
        tooltip.add(Text.translatable("item.simplymore.great_slither.tooltip3").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.great_slither.tooltip4").setStyle(TEXT));

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
