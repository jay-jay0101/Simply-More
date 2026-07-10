package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.annotations.Action;
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.component.DataComponentTypes;
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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.TwoHandedWeapon;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class BladeOfTheGrotesqueItem extends SimplyMoreUniqueSwordItem implements TwoHandedWeapon {
    int skillCooldown = UNIQUE_CONFIG.blade_of_the_grotesque.cooldown;
    int skillLength = UNIQUE_CONFIG.blade_of_the_grotesque.selfStunTime;

    public BladeOfTheGrotesqueItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordType.SWORD, settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffectRegistry.getReference(StatusEffectRegistry.SOLIDIFIED),skillLength));
            user.getWorld().playSound(null, user.getBlockPos(), SoundEvents.UI_STONECUTTER_TAKE_RESULT, user.getSoundCategory(), 2F, 1F);
            ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.ASH,user.getX(),user.getEyeY()-0.25,user.getZ(),1000,0.2,0.5,0.2,1);
            user.getItemCooldownManager().set(this, skillCooldown);
        }
        return super.use(world, user, hand);
    }


    public static void causeStun(LivingEntity attacker) {
        Box box = MathUtils.createCubeBox(attacker.getPos(), UNIQUE_CONFIG.blade_of_the_grotesque.auraRange);

        List<LivingEntity> targets = AttackUtils.cuboidAttack(attacker, box);
        for (LivingEntity target : targets) {
            target.addStatusEffect(
                    new StatusEffectInstance(
                            StatusEffectRegistry.getReference(StatusEffectRegistry.STUNNED),
                            UNIQUE_CONFIG.blade_of_the_grotesque.auraStunTime),
                    attacker
            );
        }
    }

    public void modifyStackAttributes(ItemStack stack) {
        AttributeModifiersComponent modifiers = stack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);

        if(modifiers == null) {
            modifiers = AttributeModifiersComponent.builder().build();
        }

        stack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, modifiers.with(
                EntityAttributes.GENERIC_MOVEMENT_SPEED,
                new EntityAttributeModifier(
                        SimplyMore.identifier("grotesque_slowdown"),
                        UNIQUE_CONFIG.blade_of_the_grotesque.selfSlow,
                        EntityAttributeModifier.Operation.ADD_VALUE
                ),
                AttributeModifierSlot.MAINHAND
        ));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.getTime() % 40 == 0) {
            modifyStackAttributes(stack);
        }

        if (world.getTime() % 40 == 0 && entity instanceof ServerPlayerEntity player && selected) {
            ServerWorld serverWorld = player.getServerWorld();
            serverWorld.spawnParticles(ParticleTypes.BUBBLE_POP,player.getX(), player.getY(), player.getZ(), 200, 2,2,2, 0.1f);

            Box box = MathUtils.createCubeBox(player.getPos(), UNIQUE_CONFIG.blade_of_the_grotesque.auraRange);

            List<LivingEntity> targets = AttackUtils.cuboidAttack(player, box);
            for (LivingEntity target : targets) {
                EntityUtils.reapplyAndIncrementEffect(target,
                        StatusEffectRegistry.getReference(StatusEffectRegistry.GROTESQUE_WARD),
                        50,
                        1,
                        UNIQUE_CONFIG.blade_of_the_grotesque.maxAuraWard
                );
            }
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.SMOKE, ParticleTypes.SMOKE, ParticleTypes.ASH);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip4").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip5",
                MathUtils.translateTicks(skillLength)).setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip7").setStyle(textStyle));

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
