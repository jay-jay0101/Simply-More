package net.rosemarythyme.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.PoisonBoltAreaEffectCloudEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class SerpentineValourItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = effect.getSerpentinePoisonBoltCooldown();

    public SerpentineValourItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }


    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getWorld().isClient())
            return super.postHit(stack, target, attacker);

        if (target.hasStatusEffect(StatusEffects.POISON) || target.hasStatusEffect(ModEffectsRegistry.VENOM.get())) {
            target.timeUntilRegen = 0;
            target.damage(target.getDamageSources().generic(), effect.getSerpentinePoisonedTargetDamageBuff());
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {
            Box entitySearchBox = new Box(
                    user.getX() - 5,
                    user.getY() - 5,
                    user.getZ() - 5,
                    user.getX() + 5,
                    user.getY() + 5,
                    user.getZ() + 5
            );

            boolean hasEnemies = user.getWorld().getNonSpectatingEntities(LivingEntity.class, entitySearchBox).stream()
                    .anyMatch(entity -> entity != user && !entity.isTeammate(user));

            int poisonBoltAreaEffectCloudEntityBehavior = hasEnemies ? -2 : 0;

            for (int j = 0; j < 4; j++) {

                int offsetX = j % 2 == 0
                        ? (j / 2 == 0 ? -1 : 1)
                        : 0;

                int offsetZ = j % 2 == 1
                        ? (j / 2 == 0 ? -1 : 1)
                        : 0;

                PoisonBoltAreaEffectCloudEntity entity = new PoisonBoltAreaEffectCloudEntity(
                        user.getWorld(),
                        user.getX() + offsetX,  // Add the x-offset to the user's x-coordinate
                        user.getY() + 2,           // Keep the y-coordinate constant (2 blocks above the user)
                        user.getZ() + offsetZ,     // Add the z-offset to the user's z-coordinate
                        user,
                        poisonBoltAreaEffectCloudEntityBehavior
                );

                // Spawn the entity in the world
                world.spawnEntity(entity);
            }
            user.getWorld().playSound(null, user.getBlockPos(), SoundRegistry.MAGIC_SHAMANIC_VOICE_15.get(), SoundCategory.PLAYERS, 0.4f, 1);
        }
        user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
        return super.use(world, user, hand);
    }

    int stepMod = 0;
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        stepMod = SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.SNEEZE, ParticleTypes.SNEEZE, ParticleTypes.SPORE_BLOSSOM_AIR);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style rightClickStyle = Styles.RIGHT_CLICK;
        Style abilityStyle = Styles.ABILITY;
        Style textStyle = Styles.TEXT;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.serpentine_valour.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.serpentine_valour.tooltip2").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.serpentine_valour.tooltip3").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.serpentine_valour.tooltip4").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.serpentine_valour.tooltip5").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.serpentine_valour.tooltip6").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.serpentine_valour.tooltip7").setStyle(textStyle));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
}
