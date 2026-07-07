package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.item.components.CounterComponent;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;
import org.joml.Vector3d;

import java.util.List;

public class ExedrillItem extends SimplyMoreUniqueSwordItem{
    int skillCooldown = UNIQUE_CONFIG.exedrill.cooldown;
    static int maxHeat = UNIQUE_CONFIG.exedrill.maxHeat;
    static final int minHeat = 0; // Constant

    @Override
    public CounterComponent getDefaultCounterComponent() {
        return new CounterComponent(minHeat, maxHeat);
    }

    public ExedrillItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordType.LANCE, settings);
    }



    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getWorld().isClient())
            return super.postHit(stack, target, attacker);

        // Heat Explosion
        if(getHeat(stack) >= maxHeat  && attacker instanceof PlayerEntity player) {
            setHeat(stack, minHeat);
            List<LivingEntity> targets = tremorEffectHitbox(8, attacker.getPos(), ((ServerWorld) attacker.getWorld()), player);
            attacker.getWorld().playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 0.5f,0.5f);
            attacker.getWorld().playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 0.5f,1f);
            ((ServerWorld) attacker.getWorld()).spawnParticles(ParticleTypes.EXPLOSION, attacker.getX(), attacker.getEyeY(), attacker.getZ(), 4, 0.25, 0.25, 0.25, 0);

            targets.forEach(
                    livingEntity -> {
                        if(attacker.getVehicle() == livingEntity) return;

                        livingEntity.damage(
                                player.getDamageSources().explosion(player, player),
                                UNIQUE_CONFIG.exedrill.explosionDamage
                        );

                        double deltaX = livingEntity.getX() - player.getX();
                        double deltaZ = livingEntity.getZ() - player.getZ();
                        double distance = Math.hypot(deltaX, deltaZ);

                        float knockbackStrength = UNIQUE_CONFIG.exedrill.earthquakeStrength;
                        double normalizedDeltaX = deltaX / distance;
                        double normalizedDeltaZ = deltaZ / distance;

                        if(distance == 0) return;

                        livingEntity.addVelocity(normalizedDeltaX * knockbackStrength, 0.4f, normalizedDeltaZ * knockbackStrength);
                    }
            );

            return super.postHit(stack, target, attacker);
        }

        setHeat(stack,
                getHeat(stack) + UNIQUE_CONFIG.exedrill.hitHeatAmount);

        // Tremors
        float chance = attacker.getVehicle() instanceof LivingEntity ?
                UNIQUE_CONFIG.exedrill.chanceMounted:
                UNIQUE_CONFIG.exedrill.chance;
        if (MathUtils.chance(attacker, chance) && attacker instanceof PlayerEntity player) {
            List<LivingEntity> targets = tremorEffectHitbox(5, attacker.getPos(), ((ServerWorld) attacker.getWorld()), player);
            setHeat(stack,
                    getHeat(stack) + UNIQUE_CONFIG.exedrill.trembleHeatAmount);
            attacker.getWorld().playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 0.5f,0.5f);

            int effectTime = UNIQUE_CONFIG.exedrill.trembleEffectTime;
            targets.forEach(
                    entity -> {
                        if(attacker.getVehicle() == entity) return;

                        entity.addStatusEffect(new StatusEffectInstance(
                                StatusEffects.SLOWNESS,
                                effectTime,
                                1
                        ));
                        entity.addStatusEffect(new StatusEffectInstance(
                                StatusEffects.MINING_FATIGUE,
                                effectTime
                        ));
                        entity.addVelocity(0,0.2d,0);
                    }
            );
        }

        return super.postHit(stack, target, attacker);
    }

    public static int getHeat(ItemStack stack) {

        int heat = MathUtils.getCounterComponent(stack).value();

        heat = Math.max(minHeat, heat);
        heat = Math.min(maxHeat, heat);

        return heat;
    }

    public static void setHeat(ItemStack stack, int value) {
        MathUtils.setCounterComponent(stack,
                MathUtils.getCounterComponent(stack).set(value));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(user.getWorld().isClient()) {
            return super.use(world, user, hand);
        }

        for(int i = 0; i < UNIQUE_CONFIG.exedrill.rocksAmount; i++) {
            Vector3d normalisedVector = MathUtils.getNormalised2dVector(user.getYaw() + user.getRandom().nextBetween(-40,40));
            Vec3d rockVelocity = new Vec3d(
                    normalisedVector.x() * UNIQUE_CONFIG.exedrill.rockSpeed,
                    0.45f,
                    normalisedVector.z() * UNIQUE_CONFIG.exedrill.rockSpeed
            );

//            GhostFallingBlockEntity rock = new GhostFallingBlockEntity(
//                    world,
//                    user.getX(),
//                    user.getY(),
//                    user.getZ(),
//                    rockVelocity,
//                    user
//            );

            NbtCompound nbt = new NbtCompound();
            nbt.put("BlockState", NbtHelper.fromBlockState(Blocks.STONE.getDefaultState()));
//            rock.readCustomDataFromNbt(nbt);
//            user.getWorld().spawnEntity(rock);
            user.getWorld().playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, SoundCategory.PLAYERS, 0.5f,0.5f);
        }

        user.getItemCooldownManager().set(this, skillCooldown);

        return super.use(world, user, hand);
    }

    public static List<LivingEntity> tremorEffectHitbox(int range, Vec3d pos, ServerWorld world, PlayerEntity player) {
        world.spawnParticles(
                new BlockStateParticleEffect( ParticleTypes.BLOCK , Blocks.DIRT.getDefaultState() ),
                pos.getX(),
                pos.getY() + 0.5,
                pos.getZ(),
                (int)(Math.pow(range,2) * 30),
                range,
                0.5f,
                range,
                1f
        );

        Box box = MathUtils.createCuboidBox(pos, -range, -2, -range, range, range, range);

        return AttackUtils.getTargets(player, box);
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
        tooltip.add(Text.translatable("item.simplymore.exedrill.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.exedrill.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.exedrill.tooltip3").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.exedrill.tooltip6").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.exedrill.tooltip8").setStyle(textStyle));
        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.EXEDRILL));
        }

        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 250;
        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.15f;
        @ValidatedInt.Restrict(min = 0)
        public int trembleEffectTime = 80;
        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chanceMounted = 0.3f;
        @ValidatedInt.Restrict(min = 0)
        public int trembleHeatAmount = 6;
        @ValidatedInt.Restrict(min = 0)
        public int hitHeatAmount = 1;
        @ValidatedInt.Restrict(min = 0)
        public int maxHeat = 25;
        @ValidatedFloat.Restrict(min = 0f)
        public float explosionDamage = 12f;
        @ValidatedFloat.Restrict(min = 0f)
        public float rockDamage = 6f;
        @ValidatedFloat.Restrict(min = 0f)
        public float rockSpeed = 0.4f;
        @ValidatedInt.Restrict(min = 0)
        public int rockStunTime = 14;
        @ValidatedInt.Restrict(min = 0)
        public int rocksAmount = 5;
        @ValidatedInt.Restrict(min = 0)
        public float earthquakeStrength = 1.8f;
    }
}
