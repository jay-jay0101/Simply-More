package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.DugBlockEntity;
import net.rosemarythyme.simplymore.entity.EarthquakeVisualEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.item.components.CounterComponent;
import net.rosemarythyme.simplymore.item.interfaces.HudOverlayItem;
import net.rosemarythyme.simplymore.item.interfaces.StoppableAbilityItem;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import net.rosemarythyme.simplymore.world.PlayerItemUseManager;
import net.sweenus.simplyswords.api.SpellScalingProfile;
import net.sweenus.simplyswords.api.WeaponAbilityActivationSource;
import net.sweenus.simplyswords.api.WeaponAbilityContext;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.UniqueWeaponActiveAbility;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;
import java.util.Set;


public class MoundshifterItem extends SimplyMoreUniqueSwordItem implements UniqueWeaponActiveAbility, StoppableAbilityItem, HudOverlayItem {
    public static MoundshifterItem.EffectSettings SETTINGS = UNIQUE_CONFIG.moundshifter;

    @Override
    public CounterComponent getDefaultCounterComponent() {
        return new CounterComponent(0, SETTINGS.maxHeat);
    }

    public MoundshifterItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, ServerWorld world, int consecutiveHits, boolean isFirstInTick) {
        if(!isFirstInTick) return;

        if(MathUtils.getCounterComponent(stack).value() >= SETTINGS.maxHeat) {
            BlockHitResult result = EntityUtils.raycastDown(target, target.getPos(), world, 5);
            if(result.getType() == HitResult.Type.MISS) return;

            MathUtils.setCounterComponentValue(stack, 0);
            createEarthquake(world, attacker, result.getPos());
        } else {
            MathUtils.addToCounterComponent(stack, consecutiveHits > 2 ? 2 : 1);
        }
    }

    private static void createEarthquake(ServerWorld world, LivingEntity attacker, Vec3d pos) {
        AudioVisualUtils.applyScreenshake(world, pos, attacker, 20, 4, 40);

        AudioVisualUtils.playSound(world, pos, new Sound(SoundRegistry.ELEMENTAL_BOW_EARTH_SHOOT_IMPACT_02.get()).setPitch(0));
        AudioVisualUtils.playSound(world, pos, new Sound(SoundRegistry.ELEMENTAL_SWORD_EARTH_ATTACK_02.get()).setPitch(0));
        AudioVisualUtils.playSound(world, pos, new Sound(SoundRegistry.ELEMENTAL_SWORD_EARTH_ATTACK_03.get()).setPitch(0));

        AttackUtils.cuboidAttack(attacker, pos.offset(Direction.UP, 3), 8, 5, AttackUtils.AttackTarget.ENEMIES)
                .damage(AttackUtils.scaleDamage(SpellScalingProfile.NATURE, attacker, 1/9f, 1f, SETTINGS.earthquakeDamage), attacker.getDamageSources().explosion(attacker, attacker))
                .knockback(pos, SETTINGS.earthquakeStrength);

        AttackUtils.spawnAbility(new EarthquakeVisualEntity(attacker, pos), attacker);

        MathUtils.getPositionsOnFloor(world, BlockPos.ofFloored(pos), 8, 8, 3, 100)
                .forEach(block -> AudioVisualUtils.dustPillar(world, block));

        Set<BlockPos> blocks = MathUtils.getPositionsOnFloor(world, BlockPos.ofFloored(pos), 5, 2, 3, SETTINGS.blocks);

        int offset = 0;
        for(BlockPos block : blocks) {
            AttackUtils.spawnProjectile(new DugBlockEntity(attacker, block.toCenterPos(), world.getBlockState(block), offset++), attacker);
        }
    }

    @Override
    public void onSwing(ItemStack stack, ServerWorld world, LivingEntity user) {
        AttackUtils.getOwnedProjectiles(user, DugBlockEntity.class)
                .forEach(DugBlockEntity::tryFire);

        super.onSwing(stack, world, user);
    }

    @Override
    public boolean canActivate(WeaponAbilityContext context) {
        return context.actor().isAlive() && (context.actor().isOnGround() || (context.actor().getVehicle() instanceof LivingEntity vehicle && vehicle.isOnGround()));
    }

    @Override
    public boolean activate(WeaponAbilityContext context) {
        if(context.activationSource() == WeaponAbilityActivationSource.PLAYER) return false;
        ActiveAbilityManager.SERVER.start(context.actor(), ActiveAbilityManager.Type.DRILL, SETTINGS.maxDrillTime);

        return true;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingTicks) {
        if(world.isClient) return;

        if (remainingTicks == getMaxUseTime(stack, user) - 1) {
            ActiveAbilityManager.SERVER.start(user, ActiveAbilityManager.Type.DRILL, SETTINGS.maxDrillTime);
        }

        if (remainingTicks < 1 && user instanceof PlayerEntity player) {
            PlayerItemUseManager.stop(player, stack, true);
        }
    }

    @Override
    public int getActivationCooldownTicks(ItemStack stack, WeaponAbilityContext context) {
        return SETTINGS.cooldown;
    }

    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return SETTINGS.maxDrillTime;
    }

    @Override
    public TypedActionResult<ItemStack> startPlayerAbility(World world, PlayerEntity user, Hand hand) {
        if(!(world instanceof ServerWorld serverWorld)) return TypedActionResult.pass(user.getStackInHand(hand));
        return AttackUtils.holdToUse(serverWorld, user, hand);
    }

    @Override
    public void stop(ItemStack stack, ServerWorld world, LivingEntity user, int remainingDuration) {
        ActiveAbilityManager.SERVER.stop(user, ActiveAbilityManager.Type.DRILL);

        if(remainingDuration > getMaxUseTime(stack, user) - 10) return;
        emerge(world, user.getVehicle() instanceof LivingEntity vehicle ? vehicle : user, remainingDuration < getMaxUseTime(stack, user) - 30);
    }

    public static void emerge(ServerWorld world, LivingEntity user, boolean includeEarthquake) {
        user.addVelocity(0, 0.5f, 0);
        user.velocityModified = true;

        AudioVisualUtils.dustPillar(world, user.getBlockPos());
        Vec3d pos = user.getPos();

        LivingEntity source = user;
        if(user.getFirstPassenger() instanceof LivingEntity entity) {
            source = entity;
        }

        if(includeEarthquake && user.isOnGround()) createEarthquake(world, source, pos);

        AttackUtils.cubeAttack(source, pos, 3, AttackUtils.AttackTarget.ENEMIES)
                .addVelocity(0, 1f, 0);

        if(source instanceof PlayerEntity player) {
            player.getItemCooldownManager().set(ItemRegistry.MOUNDSHIFTER.get(), SETTINGS.cooldown);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return useFromDefaultInput(world, user, hand);
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
        tooltip.add(Text.translatable("item.simplymore.moundshifter.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.moundshifter.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.moundshifter.tooltip3").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.moundshifter.tooltip4").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.moundshifter.tooltip5").setStyle(textStyle));
        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }


    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MOUNDSHIFTER));
        }

        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 300;
        @ValidatedInt.Restrict(min = 0)
        public int maxHeat = 12;
        @ValidatedFloat.Restrict(min = 0f)
        public float earthquakeDamage = 12f;
        @ValidatedFloat.Restrict(min = 0f)
        public float blockDamage = 6f;
        @ValidatedFloat.Restrict(min = 0f)
        public float blockSpeed = 2f;
        @ValidatedInt.Restrict(min = 0)
        public int blockStunTime = 20;
        @ValidatedInt.Restrict(min = 0)
        public int blocks = 3;
        @ValidatedFloat.Restrict(min = 0)
        public float earthquakeStrength = 1.8f;
        @ValidatedFloat.Restrict(min = 0)
        public float speedMultiplier = 10f;
        @ValidatedInt.Restrict(min = 0)
        public int maxDrillTime = 200;
    }
}
