package net.rosemarythyme.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;
import org.joml.Vector3d;
import org.joml.Vector3f;

import javax.smartcardio.ATR;
import java.util.List;

public class RevvengineItem extends SimplyMoreUniqueSwordItem {

    public RevvengineItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }



    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getWorld().isClient())
            return super.postHit(stack, target, attacker);

        if (attacker.getRandom().nextBetween(1, 100) <= effect.getRevvengineBleedChance()) {
            target.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.BLEED, effect.getRevvengineBleedTime(), 0), attacker);
        }


        float extraDamage = getHealthModifiedValue(attacker,
                effect.getRevvengineMaxDamagePercentageBuff(),
                config.weaponAttributes.getRevvengineDamage());

        if (attacker instanceof PlayerEntity playerAttacker) {
            target.timeUntilRegen = 0;
            target.damage(target.getDamageSources().playerAttack(playerAttacker), extraDamage);
        }

        return super.postHit(stack, target, attacker);
    }

    public static float getHealthModifiedValue(LivingEntity entity, int percentage, float value) {
        float hpPercentage = entity.getHealth() / entity.getMaxHealth();
        float extraPercentage = percentage * (1 - hpPercentage);

        return value * (extraPercentage/100f);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return itemStack.getDamage() >= itemStack.getMaxDamage() - 1
                ? TypedActionResult.fail(itemStack)
                : TypedActionResult.consume(itemStack);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if(user.getWorld().isClient()) {
            super.usageTick(world, user, stack, remainingUseTicks);
            return;
        }

        if(user.age % 5 == 0)
            user.getWorld().playSound(null, user.getX(), user.getY(), user.getZ(), SoundRegistry.MAGIC_BOW_PULL_BACK_SHORT_VERSION_02.get(), SoundCategory.PLAYERS, 1, 0.5f);

        int ticksUsed = this.getDefaultStack().getMaxUseTime() - remainingUseTicks;

        if(ticksUsed >= effect.getRevvenginePhase3MinimumCharge()) {
            ((ServerWorld) user.getWorld()).spawnParticles(
                    new DustParticleEffect(
                            new Vector3f(0f,0f,0f),
                            1
                    ),
                    user.getX(),
                    user.getEyeY(),
                    user.getZ(),
                    22,
                    0.75f,
                    0.75f,
                    0.75f,
                    0.3f
            );
            ((ServerWorld) user.getWorld()).spawnParticles(
                    ParticleTypes.LAVA,
                    user.getX(),
                    user.getY(),
                    user.getZ(),
                    3,
                    0.2f,
                    0.2f,
                    0.2f,
                    0.3f
            );
        } else if(ticksUsed >= effect.getRevvenginePhase2MinimumCharge()) {
            ((ServerWorld) user.getWorld()).spawnParticles(
                    new DustParticleEffect(
                            new Vector3f(0.5f,0.5f,0.5f),
                            1
                    ),
                    user.getX(),
                    user.getEyeY(),
                    user.getZ(),
                    15,
                    0.75f,
                    0.75f,
                    0.75f,
                    0.3f
            );
        } else if(ticksUsed >= effect.getRevvenginePhase1MinimumCharge()) {
            ((ServerWorld) user.getWorld()).spawnParticles(
                    new DustParticleEffect(
                            new Vector3f(1f,1f,1f),
                            1
                    ),
                    user.getX(),
                    user.getEyeY(),
                    user.getZ(),
                    8,
                    0.75f,
                    0.75f,
                    0.75f,
                    0.3f
            );
        }


        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if(user.getWorld().isClient()) {
            super.onStoppedUsing(stack, world, user, remainingUseTicks);
            return;
        }

        int ticksUsed = this.getDefaultStack().getMaxUseTime() - remainingUseTicks;

        int time = 0;
        int amplifier = 0;

        if(ticksUsed >= effect.getRevvenginePhase3MinimumCharge()) {
            amplifier = 1;
            time = (int) getHealthModifiedValue(user, effect.getRevvengineMaxRangePercentageBuff(), 45);
            time += 40;
            ((PlayerEntity) user).getItemCooldownManager().set(this, effect.getRevvenginePhase3Cooldown());
        } else if(ticksUsed >= effect.getRevvenginePhase2MinimumCharge()) {
            time = (int) getHealthModifiedValue(user, effect.getRevvengineMaxRangePercentageBuff(), 25);
            time += 25;
            ((PlayerEntity) user).getItemCooldownManager().set(this, effect.getRevvenginePhase2Cooldown());
        } else if(ticksUsed >= effect.getRevvenginePhase1MinimumCharge()) {
            phase1(user);
        }

        if(time>0) {
            user.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.RAVENOUS, time, amplifier));
        }

        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    public void phase1(LivingEntity user) {
        Vec3d position = user.getEyePos();
        Vector3d normalisedVector = SimplyMoreHelperMethods.getNormalised2dVector(user.getYaw());

        Vec3d particlePos = new Vec3d(
                position.getX() + normalisedVector.x(),
                position.getY(),
                position.getZ() + normalisedVector.z()
        );

        Box box = new Box(
                particlePos.getX() - 1,
                particlePos.getY() - 1,
                particlePos.getZ() - 1,
                particlePos.getX() + 1,
                particlePos.getY() + 1,
                particlePos.getZ() + 1

        );
        for (LivingEntity livingEntity : user.getWorld().getNonSpectatingEntities(LivingEntity.class, box)) {
            if (livingEntity.isTeammate(user) || livingEntity == user || livingEntity.isInvulnerable()) continue;

            livingEntity.damage(
                    user.getDamageSources().playerAttack((PlayerEntity) user),
                    getHealthModifiedValue(user,
                            effect.getRevvengineMaxDamagePercentageBuff(),
                            effect.getRevvenginePhase1Damage()) + effect.getRevvenginePhase1Damage()
            );

            livingEntity.addStatusEffect(
                    new StatusEffectInstance(
                            ModEffectsRegistry.BLEED,
                            effect.getRevvengineBleedTime(),
                            0
                    )
            );
        }

        ((PlayerEntity) user).getItemCooldownManager().set(this, effect.getRevvenginePhase1Cooldown());

        user.getWorld().playSound(null, particlePos.getX(), particlePos.getY(), particlePos.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, SoundCategory.PLAYERS, 1,0.5f);


        ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.SWEEP_ATTACK, particlePos.getX(), particlePos.getY(), particlePos.getZ(), 1, 0, 0 , 0, 0);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 9999999;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    int stepMod = 0;
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        stepMod = SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.ASH);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style rightClickStyle = HelperMethods.getStyle("rightclick");
        Style abilityStyle = HelperMethods.getStyle("ability");
        Style textStyle = HelperMethods.getStyle("text");

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.revvengine.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.revvengine.tooltip2").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.revvengine.tooltip3").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.revvengine.tooltip4").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.revvengine.tooltip5").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.revvengine.tooltip6").setStyle(textStyle));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
}
