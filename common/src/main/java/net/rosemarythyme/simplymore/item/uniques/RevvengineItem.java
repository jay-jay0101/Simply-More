package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;
import net.sweenus.simplyswords.util.Styles;
import org.joml.Vector3d;
import org.joml.Vector3f;

import java.util.List;

public class RevvengineItem extends SimplyMoreUniqueSwordItem implements TwoHandedWeapon {

    public RevvengineItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }



    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getWorld().isClient())
            return super.postHit(stack, target, attacker);

        if (MathUtils.chance(attacker, UNIQUE_CONFIG.revvengine.chance)) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffectRegistry.getReference(StatusEffectRegistry.WOUNDED), UNIQUE_CONFIG.revvengine.bleedTime, 0), attacker);
        }


        float extraDamage = getHealthModifiedValue(attacker,
                UNIQUE_CONFIG.revvengine.damageBuff,
                (float) HelperMethods.getEntityAttackDamage(attacker));

        if (attacker instanceof PlayerEntity playerAttacker) {
            target.timeUntilRegen = 0;
            target.damage(target.getDamageSources().playerAttack(playerAttacker), extraDamage);
        }

        return super.postHit(stack, target, attacker);
    }

    public static float getHealthModifiedValue(LivingEntity entity, float percentage, float value) {
        float hpPercentage = entity.getHealth() / entity.getMaxHealth();
        float extraPercentage = percentage * (1 - hpPercentage);

        return value * extraPercentage;
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

        int ticksUsed = this.getMaxUseTime(stack, user) - remainingUseTicks;

        if(ticksUsed >= UNIQUE_CONFIG.revvengine.p3windup) {
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
        } else if(ticksUsed >= UNIQUE_CONFIG.revvengine.p2windup) {
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
        } else if(ticksUsed >= UNIQUE_CONFIG.revvengine.p1windup) {
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

        int ticksUsed = this.getMaxUseTime(stack, user) - remainingUseTicks;

        int time = 0;
        int amplifier = 0;

        if(ticksUsed >= UNIQUE_CONFIG.revvengine.p3windup) {
            amplifier = 1;
            time = (int) getHealthModifiedValue(user, UNIQUE_CONFIG.revvengine.rangeBuff, 45);
            time += 40;
            ((PlayerEntity) user).getItemCooldownManager().set(this, UNIQUE_CONFIG.revvengine.p3cooldown);
        } else if(ticksUsed >= UNIQUE_CONFIG.revvengine.p2windup) {
            time = (int) getHealthModifiedValue(user, UNIQUE_CONFIG.revvengine.rangeBuff, 25);
            time += 25;
            ((PlayerEntity) user).getItemCooldownManager().set(this, UNIQUE_CONFIG.revvengine.p2cooldown);
        } else if(ticksUsed >= UNIQUE_CONFIG.revvengine.p1windup) {
            phase1(user);
        }

        if(time>0) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffectRegistry.getReference(StatusEffectRegistry.RAVENOUS), time, amplifier));
        }

        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    public void phase1(LivingEntity user) {
        Vec3d position = user.getEyePos();
        Vector3d normalisedVector = MathUtils.getNormalised2dVector(user.getYaw());

        Vec3d particlePos = new Vec3d(
                position.getX() + normalisedVector.x(),
                position.getY(),
                position.getZ() + normalisedVector.z()
        );

        Box box = MathUtils.createCubeBox(user.getPos(), 1);
        List<LivingEntity> targets = AttackUtils.cuboidAttack(user, box);

        for (LivingEntity target : targets) {
            target.damage(
                    user.getDamageSources().playerAttack((PlayerEntity) user),
                    getHealthModifiedValue(user,
                            UNIQUE_CONFIG.revvengine.damageBuff,
                            UNIQUE_CONFIG.revvengine.p1damage) + UNIQUE_CONFIG.revvengine.p1damage
            );

            target.addStatusEffect(
                    new StatusEffectInstance(
                            StatusEffectRegistry.getReference(StatusEffectRegistry.WOUNDED),
                            UNIQUE_CONFIG.revvengine.bleedTime,
                            0
                    )
            );
        }

        ((PlayerEntity) user).getItemCooldownManager().set(this, UNIQUE_CONFIG.revvengine.p1cooldown);

        user.getWorld().playSound(null, particlePos.getX(), particlePos.getY(), particlePos.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, SoundCategory.PLAYERS, 1,0.5f);


        ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.SWEEP_ATTACK, particlePos.getX(), particlePos.getY(), particlePos.getZ(), 1, 0, 0 , 0, 0);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 9999999;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.ASH);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.revvengine.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.revvengine.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.revvengine.tooltip4").setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.REVVENGINE));
        }

        @ValidatedInt.Restrict(min = 0)
        public int p1cooldown = 240;
        @ValidatedInt.Restrict(min = 0)
        public int p2cooldown = 360;
        @ValidatedInt.Restrict(min = 0)
        public int p3cooldown = 440;
        @ValidatedInt.Restrict(min = 0)
        public int p1windup = 10;
        @ValidatedInt.Restrict(min = 0)
        public int p2windup = 40;
        @ValidatedInt.Restrict(min = 0)
        public int p3windup = 80;
        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.2f;
        @ValidatedInt.Restrict(min = 0)
        public int bleedTime = 80;
        @ValidatedFloat.Restrict(min = 0f)
        public int p1damage = 7;
        @ValidatedFloat.Restrict(min = 0f)
        public int p2damage = 12;
        @ValidatedInt.Restrict(min = 0)
        public int p2effectTime = 120;
        @ValidatedFloat.Restrict(min = 0f)
        public int p3damage = 15;
        @ValidatedInt.Restrict(min = 0)
        public int p3effectTime = 100;
        @ValidatedInt.Restrict(min = 0)
        public int explosionWindup = 80;
        @ValidatedFloat.Restrict(min = 0f)
        public int explosionDamage = 10;
        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float damageBuff = 0.6f;
        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float rangeBuff = 0.5f;
    }
}
