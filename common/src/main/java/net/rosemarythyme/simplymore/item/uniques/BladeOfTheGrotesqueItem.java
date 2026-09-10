package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.annotations.Action;
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.block.Blocks;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.entity.StatueEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.item.interfaces.StackModifierItem;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import net.sweenus.simplyswords.api.WeaponAbilityActivationSource;
import net.sweenus.simplyswords.api.WeaponAbilityContext;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;
import net.sweenus.simplyswords.item.interfaces.UniqueWeaponActiveAbility;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class BladeOfTheGrotesqueItem extends SimplyMoreUniqueSwordItem implements TwoHandedWeapon, StackModifierItem, UniqueWeaponActiveAbility {
    public static BladeOfTheGrotesqueItem.EffectSettings SETTINGS = UNIQUE_CONFIG.blade_of_the_grotesque;

    public BladeOfTheGrotesqueItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return useFromDefaultInput(world, user, hand);
    }

    @Override
    public boolean canActivate(WeaponAbilityContext context) {
        return context.actor().isAlive() && !ActiveAbilityManager.SERVER.isInAbility(context.actor(), ActiveAbilityManager.Type.STATUE);
    }

    @Override
    public boolean activate(WeaponAbilityContext context) {
        ActiveAbilityManager.SERVER.start(context.actor(), ActiveAbilityManager.Type.STATUE, SETTINGS.selfStunTime);
        AttackUtils.spawnAbility(new StatueEntity(context.actor(), context.origin(), context.actor().getYaw()), context.actor());

        AudioVisualUtils.playSound(context.world(), context.origin(), new Sound(SoundEvents.UI_STONECUTTER_TAKE_RESULT));
        return context.activationSource() != WeaponAbilityActivationSource.PLAYER;
    }

    public static void causeStun(LivingEntity attacker) {
        AudioVisualUtils.playSound(attacker.getWorld(), attacker.getPos(), new Sound(SoundRegistry.DARK_SWORD_BREAKS.get()));

        AudioVisualUtils.particleAroundEntity(attacker, ParticleTypes.SOUL, 200, 2f, 0.1f);
        breakOutVisuals(attacker);
        EntityUtils.cooldown(attacker, ItemRegistry.BLADE_OF_THE_GROTESQUE.get(), BladeOfTheGrotesqueItem.SETTINGS.cooldown, true);

        ActiveAbilityManager.SERVER.stop(attacker, ActiveAbilityManager.Type.STATUE);

        AttackUtils.cubeAttack(attacker, attacker.getPos(), UNIQUE_CONFIG.blade_of_the_grotesque.auraRange, AttackUtils.AttackTarget.ENEMIES)
                .onEach(target -> {
                    ActiveAbilityManager.SERVER.start(target, ActiveAbilityManager.Type.PETRIFIED, SETTINGS.auraStunTime);
                    AttackUtils.spawnAbility(new StatueEntity(target, target.getPos(), target.getYaw()), target);
                });
    }

    public static void breakOutVisuals(LivingEntity target) {
        AudioVisualUtils.particleAroundEntity(target, new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.DEEPSLATE.getDefaultState()), 100, 0.1f, 0.1f);
        AudioVisualUtils.playSound(target.getWorld(), target.getPos(), new Sound(SoundEvents.UI_STONECUTTER_TAKE_RESULT).setPitch(0.4f));
    }

    @Override
    public int getActivationCooldownTicks(ItemStack stack, WeaponAbilityContext context) {
        return SETTINGS.cooldown;
    }

    @Override
    public AttributeModifiersComponent getModifier(LivingEntity entity, ItemStack stack, AttributeModifiersComponent base) {
        return base.with(
                EntityAttributes.GENERIC_MOVEMENT_SPEED,
                new EntityAttributeModifier(
                        SimplyMore.identifier("grotesque_slowdown"),
                        SETTINGS.selfSlow,
                        EntityAttributeModifier.Operation.ADD_VALUE
                ),
                AttributeModifierSlot.MAINHAND
        );
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.getTime() % 40 != 0) return;
        if (world.isClient) return;

        if (entity instanceof LivingEntity user && selected) {
            AttackUtils.cubeAttack(user, user.getPos(), UNIQUE_CONFIG.blade_of_the_grotesque.auraRange, AttackUtils.AttackTarget.ENEMIES)
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
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip3").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip4"));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip5").setStyle(Styles.TEXT));

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
        public int cooldown = 500;
        @RequiresAction(action = Action.RESTART)
        public float selfSlow = -0.02f;
        @ValidatedInt.Restrict(min = 0)
        public int maxAuraWard = 5;
    }
}
