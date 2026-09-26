package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.projectiles.DripstoneSpikeEntity;
import net.rosemarythyme.simplymore.entity.projectiles.FallingDripstoneSpikeEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.item.components.CounterComponent;
import net.rosemarythyme.simplymore.item.interfaces.HudOverlayItem;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.*;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.sweenus.simplyswords.api.WeaponAbilityContext;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.UniqueWeaponActiveAbility;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class CrustspireItem extends SimplyMoreUniqueSwordItem implements HudOverlayItem, UniqueWeaponActiveAbility {
    public static final CrustspireItem.EffectSettings SETTINGS = UNIQUE_CONFIG.crustspire;

    public CrustspireItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return useFromDefaultInput(world, user, hand);
    }

    @Override
    public boolean canActivate(WeaponAbilityContext context) {
        return ItemStackUtils.getCounterComponent(context.actor().getStackInHand(context.hand())).value() > 0
                && context.actor().isAlive();
    }

    @Override
    public boolean activate(WeaponAbilityContext context) {
        ItemStackUtils.setCounterComponentValue(context.actor().getStackInHand(context.hand()), 0);
        SummonUtils.getOwnedProjectiles(context.actor(), DripstoneSpikeEntity.class).forEach(DripstoneSpikeEntity::fire);

        context.actor().addVelocity(MathUtils.getDirectionalVector(context.actor().getYaw(), context.actor().getPitch()).multiply(-SETTINGS.recoil));
        context.actor().velocityModified = true;

        AudioVisualUtils.playSound(context.world(), context.origin(), new Sound(SoundRegistry.ELEMENTAL_BOW_EARTH_SHOOT_IMPACT_03.get()).setPitch(1.5f));

        return true;
    }

    @Override
    public int getActivationCooldownTicks(ItemStack stack, WeaponAbilityContext context) {
        return SETTINGS.cooldown;
    }

    @Override
    public CounterComponent getDefaultCounterComponent() {
        return new CounterComponent(0, SETTINGS.maxDripstone, 0);
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, ServerWorld world, int consecutiveHits, boolean isFirstInTick) {
        if (isFirstInTick && MathUtils.chance(attacker, SETTINGS.chance)) {
            ItemStackUtils.addToCounterComponent(stack, 1);

            AudioVisualUtils.playSound(world, attacker.getPos(), new Sound(SoundRegistry.ELEMENTAL_SWORD_EARTH_ATTACK_03.get()).setPitch(0.8f));
            AudioVisualUtils.playSound(world, attacker.getPos(), new Sound(SoundRegistry.DARK_SWORD_UNFOLD.get()));
            AudioVisualUtils.applyScreenshake(world, attacker.getPos(), attacker,  12, 3f, 20);

            for (int i = 0; i < 12; i++) {
                float yaw = (i / 12f * 360f);
                if(i % 4 == 0) {
                    spawnFallingDripstone(attacker, yaw, 1.5f, world);
                }

                if (i % 2 == 0) {
                    spawnFallingDripstone(attacker, yaw, 3, world);
                }

                spawnFallingDripstone(attacker, yaw, 4.5f, world);
            }
        }
    }

    private void spawnFallingDripstone(LivingEntity owner, float yaw, float range, ServerWorld world) {
        FallingDripstoneSpikeEntity entity = new FallingDripstoneSpikeEntity(owner, Vec3d.ZERO, owner.getRandom());
        Vec3d pos = EntityUtils.rangeAroundPoint(owner.getEyePos().offset(Direction.UP, 1), owner, yaw, range);

        pos = EntityUtils.raycastUp(entity, pos, owner.getWorld(), 6).getPos();
        SummonUtils.spawnProjectile(new FallingDripstoneSpikeEntity(owner, pos, owner.getRandom()), owner);

        AudioVisualUtils.particleCube(world, pos, ParticleTypes.SMOKE, 5, 0.05f, 0f);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(world.isClient()) return;super.inventoryTick(stack, world, entity, slot, selected);

        if (entity instanceof LivingEntity owner && InventoryUtils.getHeldAwakenedStack(owner, stack.getItem()).equals(stack)) {
            SummonUtils.ensureEnumeratedEntities(
                    owner, DripstoneSpikeEntity.class,
                    ItemStackUtils.getCounterComponent(stack).value()
            );
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.ASH);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.crustspire.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.crustspire.tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.crustspire.tooltip3").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.crustspire.tooltip4", SETTINGS.dripstoneRefund, SETTINGS.dripstoneRefundNum).setStyle(Styles.TEXT));
        appendAbilityCooldownTooltip(tooltip, itemStack, SETTINGS.cooldown);

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.CRUSTSPIRE));
        }

        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.4f;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 30;

        @ValidatedInt.Restrict(min = 0)
        public int maxDripstone = 5;
        @ValidatedInt.Restrict(min = 0)
        public int dripstoneRefund = 3;
        @ValidatedInt.Restrict(min = 0)
        public int dripstoneRefundNum = 2;

        @ValidatedFloat.Restrict(min = 0)
        public float dripstoneDamage = 6f;
        @ValidatedInt.Restrict(min = 0)
        public int dripstoneEffectDuration = 120;
        @ValidatedInt.Restrict(min = 0)
        public int dripstoneSunderedArmor = 10;
        @ValidatedInt.Restrict(min = 0)
        public int dripstoneSunderedArmorMax = 50;
        @ValidatedFloat.Restrict(min = 0)
        public float dripstoneFireSpeed = 1.3f;

        @ValidatedFloat.Restrict(min = 0)
        public float recoil = 0.8f;
    }
}
