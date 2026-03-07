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
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.item.components.DayTimeComponent;
import net.rosemarythyme.simplymore.registry.ModComponentRegistry;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class TimekeeperItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = effect.timekeeper.cooldown;

    public TimekeeperItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.SWORD, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient)
            return super.use(world, user, hand);

        double playerX = user.getX();
        double playerY = user.getEyeY();
        double playerZ = user.getZ();

        long currentTime = Math.abs(world.getTimeOfDay() % 24000);

        boolean isFixedTime = world.getDimension().hasFixedTime();

        if (!isFixedTime) {
            if (currentTime < 13000) {
                spawnFlameAttack(world, user, playerX, playerY, playerZ);
            } else {
                spawnWindAttack(world, user, playerX, user.getY(), playerZ);
            }

            user.getItemCooldownManager().set(this, calculateCooldown(currentTime));
        }
        return super.use(world, user, hand);
    }
    private void spawnFlameAttack(World world, PlayerEntity player, double playerX, double playerY, double playerZ) {
        for (int i = 0; i < 12; i++) {
            float yaw = (float) Math.toRadians(player.getYaw() + 90);
            float pitch = (float) Math.toRadians(player.getPitch());
            
            double distanceX = Math.cos(yaw) * Math.cos(pitch) * i;
            double distanceZ = Math.sin(yaw) * Math.cos(pitch) * i;
            double distanceY = Math.sin(pitch) * -i;
            
            ((ServerWorld) world).spawnParticles(ParticleTypes.FLAME, playerX + distanceX / 2, playerY + distanceY / 2, playerZ + distanceZ / 2, 10, 0.3, 0.3, 0.3, 0);
            
            Box box = new Box(
                    playerX + distanceX / 2 - 0.8,
                    playerY + distanceY / 2 - 0.8,
                    playerZ + distanceZ / 2 - 0.8,
                    playerX + distanceX / 2 + 0.8,
                    playerY + distanceY / 2 + 0.8,
                    playerZ + distanceZ / 2 + 0.8);
            
            List<LivingEntity> entities = world.getNonSpectatingEntities(LivingEntity.class, box);
            
            for (LivingEntity entity : entities) {
                if (SimplyMoreHelperMethods.checkFriendlyFire(entity, player) || entity == player)
                    continue;
                entity.setVelocity(distanceX * 2.5 / i, distanceY * 2.5 / i, distanceZ * 2.5 / i);
                entity.velocityModified = true;
                entity.setOnFireFor(5);
                entity.damage(player.getDamageSources().onFire(), effect.timekeeper.dayDamage);
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, effect.timekeeper.dayActiveBlindnessTime));
            }
        }
        world.playSound(null, playerX, playerY, playerZ, SoundRegistry.ELEMENTAL_SWORD_FIRE_ATTACK_01.get(), SoundCategory.PLAYERS, 1, 1);
    }
    private void spawnWindAttack(World world, PlayerEntity player, double playerX, double playerY, double playerZ) {
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 15; i++) {
                double yaw = Math.toRadians(player.getYaw());
                yaw += Math.toRadians((j - 2) * 30);
                double distanceZ = Math.cos(yaw) * i;
                double distanceX = Math.sin(yaw) * -i;
                
                ((ServerWorld) world).spawnParticles(ParticleTypes.SWEEP_ATTACK, playerX + distanceX / 2, playerY + 0.3, playerZ + distanceZ / 2, 1, 0, 0, 0, 0);
                
                Box box = new Box(
                        playerX + distanceX / 2 - 0.8,
                        playerY + 0.3 - 5,
                        playerZ + distanceZ / 2 - 0.8,
                        playerX + distanceX / 2 + 0.8,
                        playerY + 0.3 + 5,
                        playerZ + distanceZ / 2 + 0.8);

                List<LivingEntity> entities = world.getNonSpectatingEntities(LivingEntity.class, box);
                
                for (LivingEntity entity : entities) {
                    if (SimplyMoreHelperMethods.checkFriendlyFire(entity, player) || entity == player)
                        continue;
                    entity.setVelocity(0, 1.5, 0);
                    entity.velocityModified = true;
                    entity.damage(player.getDamageSources().magic(), effect.timekeeper.nightDamage);
                    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, effect.timekeeper.nightActiveSlownessTime, 3));
                }
            }
        }
        world.playSound(null, playerX, playerY, playerZ, SoundRegistry.ELEMENTAL_SWORD_WIND_ATTACK_01.get(), SoundCategory.PLAYERS, 1, 1.5f);
    }
    private int calculateCooldown(long currentTime) {
        return (currentTime < 13000)
                ? (int) (skillCooldown + (skillCooldown * Math.abs(6000 - currentTime) / 7000))
                : (int) (skillCooldown + (skillCooldown * Math.abs(18000 - currentTime) / 7000));
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if (SimplyMoreHelperMethods.chance(attacker, effect.timekeeper.chance)) {
                long dayTime = Math.abs(attacker.getWorld().getTimeOfDay() % 24000);
                boolean isFixedTime  = attacker.getWorld().getDimension().hasFixedTime();

                if (isFixedTime) {
                    applyRandomEffect(attacker, (ServerWorld) attacker.getWorld());
                } else {
                    applyTimeBasedEffect(dayTime, attacker, (ServerWorld) attacker.getWorld());
                }
            }
        }
        return super.postHit(stack, target, attacker);
    }

    private void applyDayPassiveEffect(LivingEntity attacker, ServerWorld world) {
        world.playSound(null,attacker.getX(),attacker.getY(),attacker.getZ(), SoundRegistry.MAGIC_SWORD_BLOCK_01.get(), SoundCategory.PLAYERS,0.25f,1);
        world.spawnParticles(ParticleTypes.ELECTRIC_SPARK,attacker.getX(),attacker.getY()+0.5,attacker.getZ(),20,0.7,0.7,0.7,0);
        for (LivingEntity passiveTarget : attacker.getWorld().getNonSpectatingEntities(LivingEntity.class,new Box(attacker.getX()-30,attacker.getY()-30,attacker.getZ()-30,attacker.getX()+30,attacker.getY()+30,attacker.getZ()+30))) {
            if (passiveTarget == attacker || SimplyMoreHelperMethods.checkFriendlyFire(passiveTarget, attacker)) continue;
            passiveTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, effect.timekeeper.dayPassiveEffectTime, 0), attacker);
        }
    }

    private void applyNightPassiveEffect(LivingEntity attacker, ServerWorld world) {
        world.playSound(null,attacker.getX(),attacker.getY(),attacker.getZ(), SoundRegistry.MAGIC_SWORD_BLOCK_01.get(), SoundCategory.PLAYERS,0.25f,1);
        world.spawnParticles(ParticleTypes.SQUID_INK,attacker.getX(),attacker.getY()+0.5,attacker.getZ(),20,0.7,0.7,0.7,0);
        for (LivingEntity passiveTarget : attacker.getWorld().getNonSpectatingEntities(LivingEntity.class,new Box(attacker.getX()-10,attacker.getY()-10,attacker.getZ()-10,attacker.getX()+10,attacker.getY()+10,attacker.getZ()+10))) {
            if (passiveTarget == attacker || SimplyMoreHelperMethods.checkFriendlyFire(passiveTarget, attacker)) continue;
            passiveTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, effect.timekeeper.nightPassiveEffectTime, 0), attacker);
        }
        attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, effect.timekeeper.nightPassiveEffectTime, 0), attacker);
        attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, effect.timekeeper.nightPassiveEffectTime, 1), attacker);
    }

    private void applyRandomEffect(LivingEntity entity, ServerWorld world) {
        if (entity.getRandom().nextBoolean()) {
            applyDayPassiveEffect(entity, world);
        } else {
            applyNightPassiveEffect(entity, world);
        }
    }

    private void applyTimeBasedEffect(long timeOfDay, LivingEntity entity, ServerWorld world) {
        if (timeOfDay < 13000) {
            applyDayPassiveEffect(entity, world);
        } else {
            applyNightPassiveEffect(entity, world);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        if (world != null) {
            long dayTime = Math.abs(world.getTimeOfDay() % 24000);
            boolean isFixedTime = world.getDimension().hasFixedTime();

            if (isFixedTime) {
                stack.set(ModComponentRegistry.DAYTIME.get(),
                        DayTimeComponent.of(DayTimeComponent.DayForm.TIMELESS));
            } else {
                if (dayTime < 13000) {
                    stack.set(ModComponentRegistry.DAYTIME.get(),
                            DayTimeComponent.of(DayTimeComponent.DayForm.DAY));
                } else {
                    stack.set(ModComponentRegistry.DAYTIME.get(),
                            DayTimeComponent.of(DayTimeComponent.DayForm.NIGHT));
                }
            }
        }

        SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, ParticleTypes.ASH);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));

        DayTimeComponent component = itemStack.get(ModComponentRegistry.DAYTIME.get());

        if(component == null) {
            appendDayTooltips(tooltip, rightClickStyle, abilityStyle, textStyle);
            super.appendTooltip(itemStack, tooltipContext, tooltip, type);
            return;
        }

        switch(component.getForm()) {
            case DAY -> appendDayTooltips(tooltip, rightClickStyle, abilityStyle, textStyle);
            case NIGHT -> appendNightTooltips(tooltip, rightClickStyle, abilityStyle, textStyle);
            case TIMELESS -> appendFixedTimeTooltips(tooltip, rightClickStyle, abilityStyle, textStyle);
        }

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    private void appendFixedTimeTooltips(List<Text> tooltip, @SuppressWarnings("unused") Style rightClickStyle, Style abilityStyle, Style textStyle) {
        tooltip.add(Text.translatable("item.simplymore.timekeeper_timeless.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.timekeeper_timeless.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.timekeeper_timeless.tooltip3").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.timekeeper_timeless.tooltip5").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.timekeeper_timeless.tooltip7").setStyle(textStyle));
    }

    private void appendDayTooltips(List<Text> tooltip, Style rightClickStyle, Style abilityStyle, Style textStyle) {
        tooltip.add(Text.translatable("item.simplymore.timekeeper_day.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.timekeeper_day.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.timekeeper_day.tooltip4").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.timekeeper_day.tooltip7").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.timekeeper_day.tooltip9").setStyle(textStyle));
    }

    private void appendNightTooltips(List<Text> tooltip, Style rightClickStyle, Style abilityStyle, Style textStyle) {
        tooltip.add(Text.translatable("item.simplymore.timekeeper_night.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.timekeeper_night.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.timekeeper_night.tooltip4").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.timekeeper_night.tooltip7").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.timekeeper_night.tooltip9").setStyle(textStyle));
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ModItemsRegistry.TIMEKEEPER)); // TODO: gotta change this probably
        }

        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 400;
        @ValidatedInt.Restrict(min = 0)
        public int nightActiveSlownessTime = 100;
        @ValidatedInt.Restrict(min = 0)
        public int dayActiveBlindnessTime = 100;
        @ValidatedInt.Restrict(min = 0)
        public int nightPassiveEffectTime = 70;
        @ValidatedInt.Restrict(min = 0)
        public int dayPassiveEffectTime = 70;
        @ValidatedFloat.Restrict(min = 0f)
        public float nightDamage = 2f;
        @ValidatedFloat.Restrict(min = 0f)
        public float dayDamage = 6f;
        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.2f;
    }
}
