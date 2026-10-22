package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.WaterLiquidEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.*;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.sweenus.simplyswords.api.WeaponAbilityContext;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.UniqueWeaponActiveAbility;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class TidebreakerItem extends SimplyMoreUniqueSwordItem implements UniqueWeaponActiveAbility {
    public static TidebreakerItem.EffectSettings SETTINGS = UNIQUE_CONFIG.tidebreaker;

    public TidebreakerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, ServerWorld world, int consecutiveHits, boolean isFirstInTick) {
        if(MathUtils.chance(attacker, SETTINGS.chance)) {
            new TargetList(target).incrementEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.LUNACY), SETTINGS.lunacyDuration, 1, 9);
            AudioVisualUtils.playSound(attacker.getWorld(), attacker.getPos(), new Sound(SoundEvents.ENTITY_WARDEN_ATTACK_IMPACT).randomisePitch(0.1f, 0.25f, attacker.getRandom()));
        }

        if (canTeleport(attacker, target)) {
            float yaw = MathUtils.getYawAndPitch(MathUtils.normalisedDirectionBetween(target.getEyePos(), attacker.getEyePos(), false)).getLeft();
            yaw += attacker.getRandom().nextBoolean() ? SETTINGS.teleportAngle : -SETTINGS.teleportAngle;

            float range = (float) attacker.getPos().distanceTo(new Vec3d(target.getX(), attacker.getY(), target.getZ()));
            Vec3d teleportPosition = EntityUtils.rangeAroundPoint(target.getPos(), attacker, yaw, (float) Math.min(range, SETTINGS.maxTeleportRange));
            teleport(attacker, teleportPosition, yaw);
        }
    }

    private boolean canTeleport(LivingEntity attacker, LivingEntity target) {
        if(!MathUtils.chance(attacker, SETTINGS.teleportChance)) return false;
        if (!(attacker instanceof PlayerEntity)) return true;
        if (!attacker.isSneaking()) return false;

        LivingEntity lookingAt = TargetUtils.findLivingTarget(attacker, (float) SETTINGS.maxTeleportRange, TargetUtils.TargetType.ENEMIES).orElse(null);
        return target.equals(lookingAt);
    }

    private void teleport(LivingEntity attacker, Vec3d pos, float yaw) {
        ServerWorld world = (ServerWorld) attacker.getWorld();
        teleportFx(attacker);

        float eyeHeight = attacker.getEyeHeight(attacker.getPose());
        AudioVisualUtils.particleLine(world, attacker.getEyePos(), pos.offset(Direction.UP, eyeHeight), ParticleTypes.RAIN, 0.25f, 5, 0.1f, 0);

        attacker.teleport(world, pos.getX(), pos.getY(), pos.getZ(), PositionFlag.getFlags(0b11111), yaw + 180f, 0);
        teleportFx(attacker);
    }

    private void teleportFx(LivingEntity attacker) {
        AudioVisualUtils.particleAroundEntity(attacker, ParticleTypes.RAIN, 40, 0.25f, 0.1f);
        AudioVisualUtils.playSound(attacker.getWorld(), attacker.getPos(), new Sound(SoundEvents.ITEM_BUCKET_EMPTY).randomisePitch(0f, 0.25f, attacker.getRandom()));
    }

    @Override
    public boolean canActivate(WeaponAbilityContext context) {
        return context.actor().isAlive() && context.actor().isOnGround();
    }

    @Override
    public boolean activate(WeaponAbilityContext context) {
        spawnWater(context.actor());
        return true;
    }

    @Override
    public int getActivationCooldownTicks(ItemStack stack, WeaponAbilityContext context) {
        return 100;
    }

    @Override
    public TypedActionResult<ItemStack> startPlayerAbility(World world, PlayerEntity user, Hand hand) {
        if(!(world instanceof ServerWorld serverWorld)) return TypedActionResult.fail(user.getStackInHand(hand));
        return AttackUtils.holdToUse(serverWorld, user, hand);
    }

    private void spawnWater(LivingEntity user) {
        if(!(user.getWorld() instanceof ServerWorld world)) return;
        if(!user.isOnGround()) return;

        AudioVisualUtils.playSound(user.getWorld(), user.getPos(), new Sound(SoundRegistry.ELEMENTAL_SWORD_WATER_ATTACK_01.get()).randomisePitch(0.75f, 1.25f, user.getRandom()));
        AudioVisualUtils.applyScreenshake(world, user.getPos(), user, 12, 2, 15);
        SummonUtils.spawnAbility(new WaterLiquidEntity(user, user.getPos(), 1), user, true);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return useFromDefaultInput(world, user, hand);
    }

    @Override
    protected int getUniqueWeaponMaxUseTime(ItemStack stack, LivingEntity user) {
        return MathUtils.PSEUDOINFINITE_DURATION;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        EntityUtils.cooldown(user, stack.getItem(), 10, false);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if(!EntityUtils.isOnGround(user)) {
            BlockHitResult result = EntityUtils.raycastDown(user, user.getPos(), user.getWorld(), 3);
            if(result.getType() != HitResult.Type.MISS) {
                user.refreshPositionAfterTeleport(result.getPos());
            }
        }

        if(remainingUseTicks % SETTINGS.ticksBetweenWater == 0) {
            spawnWater(user);
        }
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.BUBBLE, ParticleTypes.BUBBLE, ParticleTypes.FALLING_WATER);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.tidebreaker.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.tidebreaker.tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.tidebreaker.tooltip3").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.tidebreaker.tooltip4").setStyle(Styles.TEXT));
        appendAbilityCooldownTooltip(tooltip, itemStack, 10);

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.TIDEBREAKER));
        }

        @ValidatedFloat.Restrict(min = 0)
        public float teleportAngle = 40;
        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.15f;
        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float teleportChance = 0.75f;
        @ValidatedInt.Restrict(min = 0)
        public int lunacyDuration = 300;
        @ValidatedDouble.Restrict(min = 0)
        public double maxTeleportRange = 7;
        @ValidatedInt.Restrict(min = 0)
        public int ticksBetweenWater = 30;
        @ValidatedInt.Restrict(min = 0)
        public int waterRange = 9;
        @ValidatedFloat.Restrict(min = 0f)
        public float waterDamage = 4f;
        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float waterKnockback = 0.75f;
    }
}
