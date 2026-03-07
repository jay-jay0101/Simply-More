package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;
import org.joml.Vector3f;

import java.util.List;

public class GlimmerstepItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = effect.glimmerstep.cooldown;

    public GlimmerstepItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.LANCE, settings);
    }



    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getWorld().isClient())
            return super.postHit(stack, target, attacker);

        float chance = attacker.getVehicle() instanceof LivingEntity ?
                effect.glimmerstep.chanceMounted:
                effect.glimmerstep.chance;
        if (SimplyMoreHelperMethods.chance(attacker, chance)) {
            if (attacker.hasStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.STARLIGHT))) {
                int amplifier = attacker.getStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.STARLIGHT)).getAmplifier();
                amplifier = Math.min(amplifier + 1, effect.glimmerstep.maxStarlight - 1);
                attacker.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.getReference(ModEffectsRegistry.STARLIGHT), effect.glimmerstep.starlightTime, amplifier), attacker);
            } else {
                attacker.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.getReference(ModEffectsRegistry.STARLIGHT), effect.glimmerstep.starlightTime, 0), attacker);
            }

            target.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, effect.glimmerstep.blindTime));

            attacker.getWorld().playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.PLAYERS, 1f,2f);
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if(!user.hasStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.STARLIGHT))) {
            return TypedActionResult.fail(itemStack);
        }

        user.setCurrentHand(hand);
        return itemStack.getDamage() >= itemStack.getMaxDamage() - 1
                ? TypedActionResult.fail(itemStack)
                : TypedActionResult.consume(itemStack);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (user.getWorld().isClient || !(user instanceof PlayerEntity)) {
            super.usageTick(world, user, stack, remainingUseTicks);
            return;
        }

        int ticksUsed = getMaxUseTime(stack, user) - remainingUseTicks;

        if(ticksUsed == 1) {
            user.getWorld().playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.PLAYERS, 1f,1.2f);
        }


        ((ServerWorld) user.getWorld()).spawnParticles(
                new DustParticleEffect(new Vector3f(2f,2f,1f), 2f),
                user.getX(),
                user.getEyeY(),
                user.getZ(),
                Math.min(ticksUsed, Math.min(60, effect.glimmerstep.explosionWindup)),
                4f,
                4f,
                4f,
                0f
        );

        if(ticksUsed == effect.glimmerstep.explosionWindup) {
            user.stopUsingItem();

            int boxSize = effect.glimmerstep.explosionRange;
            Box box = new Box(user.getX() - boxSize, user.getY() - 2, user.getZ() - boxSize, user.getX() + boxSize, user.getY() + boxSize, user.getZ() + boxSize);
            List<LivingEntity> livingEntities = user.getWorld().getNonSpectatingEntities(LivingEntity.class, box);
            float damage;
            try {
                damage = effect.glimmerstep.explosionDamagePerStarlight * (user.getStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.STARLIGHT)).getAmplifier() + 1);
            } catch (NullPointerException e) {
                damage = effect.glimmerstep.explosionDamagePerStarlight;
            }

            ((PlayerEntity) user).getItemCooldownManager().set(this, skillCooldown);
            user.removeStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.STARLIGHT));

            float finalDamage = damage;
            livingEntities.stream().filter(
                    livingEntity -> livingEntity != user.getVehicle()
            ).forEach(
                    livingEntity -> livingEntity.damage(user.getDamageSources().explosion(user, user),
                            (SimplyMoreHelperMethods.checkFriendlyFire(livingEntity, user) || livingEntity == user)?
                                    finalDamage * (effect.glimmerstep.glimmerstepAllyDamage) : finalDamage)
            );

            ((ServerWorld) user.getWorld()).spawnParticles(
                    ParticleTypes.EXPLOSION,
                    user.getX(),
                    user.getEyeY(),
                    user.getZ(),
                    80,
                    2f,
                    2f,
                    2f,
                    0f
            );

            user.getWorld().playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1f,0.8f);
        }

        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 9999999;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, ParticleTypes.ELECTRIC_SPARK, ParticleTypes.ELECTRIC_SPARK, ParticleTypes.FIREWORK);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.glimmerstep.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.glimmerstep.tooltip2",
                effect.glimmerstep.maxStarlight).setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.glimmerstep.tooltip5").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.glimmerstep.tooltip6").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.glimmerstep.tooltip8").setStyle(textStyle));
        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ModItemsRegistry.GLIMMERSTEP));
        }

        @ValidatedInt.Restrict(min = 0)
        public int maxStarlight = 10;
        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.25f;
        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chanceMounted = 0.4f;
        @ValidatedFloat.Restrict(min = 0f)
        public float explosionDamagePerStarlight = 3.2f;
        @ValidatedInt.Restrict(min = 0)
        public int blindTime = 40;
        @ValidatedInt.Restrict(min = 0)
        public int baseSpeedFrequency = 200;
        @ValidatedInt.Restrict(min = 0)
        public int speedFrequencyPerStack = 10;
        @ValidatedInt.Restrict(min = 0)
        public int speedTime = 40;
        @ValidatedInt.Restrict(min = 0)
        public int explosionRange = 5;
        @ValidatedInt.Restrict(min = 0)
        public int explosionWindup = 60;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 800;
        @ValidatedFloat.Restrict(min = 0f)
        public float glimmerstepAllyDamage = 0.8f;
        @ValidatedInt.Restrict(min = 0)
        public int starlightTime = 800;
    }
}
