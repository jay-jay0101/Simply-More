package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.VisualEffectsUtils;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class StasisItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = effect.stasis.cooldown;
    int onHitCooldown = effect.stasis.stunTime;


    public StasisItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.SWORD, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            if (!attacker.getWorld().isClient()) {
                if (MathUtils.chance(attacker, effect.stasis.chance)) {
                    attacker.getWorld().playSound(null,attacker.getX(),attacker.getY(),attacker.getZ(),SoundEvents.ITEM_TRIDENT_THUNDER, SoundCategory.PLAYERS,0.5f,2f);
                    ((ServerWorld) attacker.getWorld()).spawnParticles(ParticleTypes.ELECTRIC_SPARK,attacker.getX(),attacker.getY()+0.5,attacker.getZ(),50,0.15,0.25,0.15,0.1);
                    if (target instanceof PlayerEntity playerTarget) {
                        for (ItemStack item : playerTarget.getHandItems()) {
                            if (!playerTarget.getItemCooldownManager().isCoolingDown(item.getItem())) {
                                playerTarget.getItemCooldownManager().set(item.getItem(), onHitCooldown);
                            }
                        }
                    }
                }
            }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingTicks) {
        if (!world.isClient() && user instanceof PlayerEntity player) {
            ServerWorld serverWorld = (ServerWorld) world;
            if (remainingTicks % 4 == 0) {
                world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.PLAYERS, 0.5f, 1f);
            }
            if (remainingTicks == 1) {
                createLightningStrike(player, serverWorld);
                damageAndElectrifyEnemies(user, player, serverWorld);
                player.getItemCooldownManager().set(stack.getItem(), skillCooldown);
            }
        }
        super.usageTick(world, user, stack, remainingTicks);
    }

    private void createLightningStrike(PlayerEntity player, ServerWorld world) {
        for (int i = 0; i < 30; i++) {
            int randomX = player.getRandom().nextInt(9) - 5;
            int randomZ = player.getRandom().nextInt(9) - 5;
            BlockPos position = player.getBlockPos().add(randomX, 0, randomZ);
            LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
            if (lightning != null) {
                lightning.refreshPositionAfterTeleport(position.getX(), position.getY(), position.getZ());
                lightning.setCosmetic(true);
                world.spawnEntity(lightning);
            }
        }
    }

    private void damageAndElectrifyEnemies(LivingEntity user, PlayerEntity player, ServerWorld world) {
        int boxRange = effect.stasis.range;

        Box box = MathUtils.createCuboidBox(user.getPos(), -boxRange, -2, -boxRange, boxRange, boxRange*2, boxRange);
        List<LivingEntity> targets = AttackUtils.getTargets(user, box);
        for (LivingEntity target : targets) {
            target.damage(player.getDamageSources().magic(), effect.stasis.strikeDamage);

            LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
            if (lightning != null) {
                lightning.refreshPositionAfterTeleport(target.getX(), target.getY(), target.getZ());
                lightning.setCosmetic(true);
                world.spawnEntity(lightning);
                target.onStruckByLightning(world, lightning);
            }
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return effect.stasis.strikeWindup;
    }


    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        VisualEffectsUtils.handleFootfalls(entity, stack, world, ParticleTypes.GLOW);
        super.inventoryTick(stack, world, entity, slot, selected);
    }


    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.stasis.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.stasis.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.stasis.tooltip4").setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ModItemsRegistry.STASIS));
        }

        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 700;
        @ValidatedInt.Restrict(min = 0)
        public int stunTime = 80;
        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.2f;
        @ValidatedFloat.Restrict(min = 0f)
        public float strikeDamage = 16;
        @ValidatedInt.Restrict(min = 0)
        public int strikeWindup = 60;
        @ValidatedInt.Restrict(min = 0)
        public int range = 4;
    }
}
