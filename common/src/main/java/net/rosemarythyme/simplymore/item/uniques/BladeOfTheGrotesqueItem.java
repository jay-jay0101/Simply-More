package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.annotations.Action;
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.item.interfaces.StackModifierItem;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class BladeOfTheGrotesqueItem extends SimplyMoreUniqueSwordItem implements TwoHandedWeapon, StackModifierItem {

    public BladeOfTheGrotesqueItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordType.SWORD, settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.getWorld().isClient) return super.use(world, user, hand);

        user.addStatusEffect(new StatusEffectInstance(StatusEffectRegistry.getReference(StatusEffectRegistry.SOLIDIFIED), UNIQUE_CONFIG.blade_of_the_grotesque.selfStunTime));

        AudioVisualUtils.playSound(user.getWorld(), user.getPos(), new Sound(SoundEvents.UI_STONECUTTER_TAKE_RESULT));
        AudioVisualUtils.particleAroundEntity(user, ParticleTypes.ASH, 500, 0.2, 1);
        user.getItemCooldownManager().set(this, UNIQUE_CONFIG.blade_of_the_grotesque.cooldown);

        return super.use(world, user, hand);
    }


    public static void causeStun(LivingEntity attacker) {
        AttackUtils.cubeAttack(attacker, attacker.getPos(), UNIQUE_CONFIG.blade_of_the_grotesque.auraRange, AttackUtils.AttackTarget.ENEMIES)
                .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.STUNNED), UNIQUE_CONFIG.blade_of_the_grotesque.auraStunTime, 0);
    }

    @Override
    public AttributeModifiersComponent getModifier(ItemStack stack, AttributeModifiersComponent base) {
        return base.with(
                EntityAttributes.GENERIC_MOVEMENT_SPEED,
                new EntityAttributeModifier(
                        SimplyMore.identifier("grotesque_slowdown"),
                        UNIQUE_CONFIG.blade_of_the_grotesque.selfSlow,
                        EntityAttributeModifier.Operation.ADD_VALUE
                ),
                AttributeModifierSlot.MAINHAND
        );
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.getTime() % 40 != 0) return;

        if (entity instanceof ServerPlayerEntity player && selected) {
            AudioVisualUtils.particleAroundEntity(player, ParticleTypes.BUBBLE_POP, 200, 2f, 0.1f);

            AttackUtils.cubeAttack(player, player.getPos(), UNIQUE_CONFIG.blade_of_the_grotesque.auraRange, AttackUtils.AttackTarget.ENEMIES)
                    .incrementEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.GROTESQUE_WARD), 50, 1, UNIQUE_CONFIG.blade_of_the_grotesque.maxAuraWard);
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.SMOKE, ParticleTypes.SMOKE, ParticleTypes.ASH);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip4").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip5"));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip7").setStyle(Styles.TEXT));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.BLADE_OF_THE_GROTESQUE));
        }

        @ValidatedInt.Restrict(min = 0)
        public int selfStunTime = 50;
        @ValidatedInt.Restrict(min = 0)
        public int auraStunTime = 50;
        @ValidatedDouble.Restrict(min = 0d)
        public double auraRange = 4;
        @ValidatedInt.Restrict(min = 0)
        @RequiresAction(action = Action.RESTART)
        public int selfStunnedArmorBuff = 10;
        @ValidatedInt.Restrict(min = 0)
        @RequiresAction(action = Action.RESTART)
        public int attackerStunnedArmorBuff = 10;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 500;
        @RequiresAction(action = Action.RESTART)
        public float selfSlow = -0.02f;
        @ValidatedInt.Restrict(min = 0)
        public int maxAuraWard = 5;
    }
}
