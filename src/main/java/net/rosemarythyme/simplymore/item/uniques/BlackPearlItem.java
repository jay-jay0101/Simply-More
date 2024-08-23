package net.rosemarythyme.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.BlackPearlFireballEntity;
import net.rosemarythyme.simplymore.item.UniqueSwordItem;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;


public class BlackPearlItem extends UniqueSwordItem {
    int skillCooldown = 180;

    public BlackPearlItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    /*
    1. Random number generation:
        Original code:      attacker.getRandom().nextInt(100) is called multiple times.
        Suggested code:     int randomNumber = attacker.getRandom().nextBetween(1, 100); is called once and stored in a
                                variable and provides a number between 1 and 100.
        Difference:         The suggested code is more efficient because it generates a random number only once, whereas
                                the original code generates a new random number every time it's needed. Whilst, in this
                                case, the number is generated only once, it is often better to define a random as a
                                variable that can be referenced, as needed, rather tha having to generate it each time.
                                In this situation it is less relevant, but making it a habit can make it easier to use
                                randoms in such a manner when needed.

    2. Filtering beneficial effects:
        Original code:      Uses a for loop to iterate over all effects and checks each one to see if it's beneficial.
        Suggested code:     Uses a Stream to filter the list of effects and collect the beneficial ones into a new list.
        Difference:         The suggested code is more concise and potentially more efficient, especially for large lists
                                of effects. The Stream API is designed to handle large datasets and can take advantage of
                                parallel processing. Also, in a modded environment, there can be a massive number of
                                effects on a player. Taking advantage of the Stream API, one can more efficiently get
                                the same information that would be provided via the original for loop.

    3. Creating a new StatusEffectInstance:
        Original code:      Creates a new StatusEffectInstance with the same effect type, duration, and amplifier as the
                                plundered effect, but with some modifications (e.g., capping the amplifier at 4).
        Suggested code:     Creates a new StatusEffectInstance with the same effect type, but with a new duration and
                                amplifier that are calculated based on the plundered effect's values.
        Difference:         The suggested code is more explicit about what values are being used to create the new
                                effect instance. It also uses the Math.min function to ensure that the duration and
                                amplifier are capped at the desired values.

    4. Adding the new effect to the attacker:
        Original code:      attacker.addStatusEffect(plunderedEffect);
        Suggested code:     attacker.addStatusEffect(newEffect);
        Difference:         The suggested code adds the new effect instance to the attacker, whereas the original code
                                adds the plundered effect instance. This is because the suggested code creates a new effect
                                instance with the desired values, whereas the original code modifies the plundered
                                effect instance. This also prevents issues with reapplication of the status effect
                                that is plundered, which can happen in heavily modded environments.

    In terms of performance, the suggested code is likely to be more efficient than the original code, especially for large
    lists of effects. The use of Stream API and the reduction of random number generation can lead to significant
    performance improvements.

    In terms of functionality, both codes achieve the same goal: they "plunder" a beneficial effect from the target and
    add it to the attacker, with some modifications to the effect's values. However, the suggested code is more explicit
    and concise in its implementation, making it easier to understand and maintain.
    */

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            int randomNumber = attacker.getRandom().nextBetween(1, 100);
            if (randomNumber <= 10) {
                List<StatusEffectInstance> positiveEffects = target.getStatusEffects().stream()
                        .filter(effect -> effect.getEffectType().getCategory() == StatusEffectCategory.BENEFICIAL)
                        .toList();

                if (!positiveEffects.isEmpty()) {
                    StatusEffectInstance plunderedEffect = positiveEffects.get(attacker.getRandom().nextInt(positiveEffects.size()));

                    int amplifier = Math.min(plunderedEffect.getAmplifier(), 4);
                    int duration = Math.min(plunderedEffect.getDuration(), 600);

                    if (plunderedEffect.getDuration() == -1) {
                        duration = 600;
                    }

                    StatusEffectInstance newEffect = new StatusEffectInstance(plunderedEffect.getEffectType(), duration, amplifier);

                    attacker.addStatusEffect(newEffect);
                    target.removeStatusEffect(plunderedEffect.getEffectType());

                    attacker.getWorld().playSound(null, attacker.getBlockPos(), SoundRegistry.DARK_SWORD_BLOCK.get(), SoundCategory.PLAYERS, 1, 1);
                }
            }
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {
            float velocityPower = 3.0f;
            float yawRadians = (float) Math.toRadians(user.getYaw() + 90);
            float pitchRadians = (float) Math.toRadians(user.getPitch());

            float velocityX = (float) (Math.cos(yawRadians) * Math.cos(pitchRadians)) * velocityPower;
            float velocityZ = (float) (Math.sin(yawRadians) * Math.cos(pitchRadians)) * velocityPower;
            float velocityY = (float) Math.sin(pitchRadians) * -velocityPower;

            BlackPearlFireballEntity fireballEntity = new BlackPearlFireballEntity(world, user, velocityX, velocityY, velocityZ);
            fireballEntity.setPos(
                    user.getX() + (velocityX / 2),
                    user.getEyeY() + (velocityY / 2),
                    user.getZ() + (velocityZ / 2)
            );
            world.spawnEntity(fireballEntity);
            user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
        }
        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style RIGHTCLICK = HelperMethods.getStyle("rightclick");
        Style ABILITY = HelperMethods.getStyle("ability");
        Style TEXT = HelperMethods.getStyle("text");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.black_pearl.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.black_pearl.tooltip2").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.black_pearl.tooltip3").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(RIGHTCLICK));
        tooltip.add(Text.translatable("item.simplymore.black_pearl.tooltip4").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.black_pearl.tooltip5").setStyle(TEXT));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }



    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        int stepMod = 0;
        SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.LANDING_HONEY, ParticleTypes.LANDING_HONEY, ParticleTypes.ASH);
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
