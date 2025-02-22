package net.rosemarythyme.simplymore.item.uniques;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
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
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;
import java.util.UUID;

public class BladeOfTheGrotesqueItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = effect.getGrotesqueSolidifyCooldown();
    int skillLength = effect.getGrotesqueSolidifySelfStunTime();

    public BladeOfTheGrotesqueItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {

        Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers = super.getAttributeModifiers(slot);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(attributeModifiers);
        builder.put(
                EntityAttributes.GENERIC_MOVEMENT_SPEED,
                new EntityAttributeModifier(UUID.fromString("e86446a8-b79b-46af-b171-4b47626fc4c3"), "Weapon modifier", effect.getGrotesqueSelfSlow(), EntityAttributeModifier.Operation.ADDITION)
        );

        return slot == EquipmentSlot.MAINHAND ? builder.build() : super.getAttributeModifiers(slot);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {
            user.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.SOLIDIFIED.get(),skillLength));
            user.getWorld().playSound(null, user.getBlockPos(), SoundEvents.UI_STONECUTTER_TAKE_RESULT, user.getSoundCategory(), 2F, 1F);
            ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.ASH,user.getX(),user.getEyeY()-0.25,user.getZ(),1000,0.2,0.5,0.2,1);
            user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
        }
        return super.use(world, user, hand);
    }

    int stepMod = 0;

    public static void causeStun(LivingEntity player) {
        int boxRange = effect.getGrotesqueAuraRange();
        Box box = new Box(
                player.getX() + boxRange,
                player.getY() + boxRange,
                player.getZ() + boxRange,
                player.getX() - boxRange,
                player.getY() - boxRange,
                player.getZ() - boxRange
        );

        List<LivingEntity> livingEntities = player.getWorld().getNonSpectatingEntities(LivingEntity.class, box);
        for (LivingEntity livingEntity : livingEntities) {
            if (livingEntity == player || livingEntity.isTeammate(player)) {
                continue;
            }

            livingEntity.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.STUNNED.get(),effect.getGrotesqueSolidifyAuraStunTime()),player);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.getTime() % 40 == 0 && entity instanceof ServerPlayerEntity player && selected) {
            ((ServerWorld) player.getWorld()).spawnParticles(ParticleTypes.BUBBLE_POP,player.getX(), player.getY(), player.getZ(), 200, 2,2,2, 0.1f);
            int boxRange = effect.getGrotesqueAuraRange();
            Box box = new Box(
                    player.getX() + boxRange,
                    player.getY() + boxRange,
                    player.getZ() + boxRange,
                    player.getX() - boxRange,
                    player.getY() - boxRange,
                    player.getZ() - boxRange
            );

            List<LivingEntity> livingEntities = entity.getWorld().getNonSpectatingEntities(LivingEntity.class, box);
            for (LivingEntity livingEntity : livingEntities) {
                if (livingEntity == entity || livingEntity.isTeammate(entity)) {
                    continue;
                }

                if(livingEntity.hasStatusEffect(ModEffectsRegistry.GROTESQUE_WARD.get())) {
                    int amplifier = livingEntity.getStatusEffect(ModEffectsRegistry.GROTESQUE_WARD.get()).getAmplifier();
                    amplifier = (int) Math.min(amplifier + 1, effect.getGrotesqueMaxAuraWard());
                    livingEntity.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.GROTESQUE_WARD.get(),50,amplifier));
                } else {
                    livingEntity.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.GROTESQUE_WARD.get(),50,0));
                }
            }
        }

        stepMod = SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.SMOKE, ParticleTypes.SMOKE, ParticleTypes.ASH);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style rightClickStyle = HelperMethods.getStyle("rightclick");
        Style abilityStyle = HelperMethods.getStyle("ability");
        Style textStyle = HelperMethods.getStyle("text");

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip2").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip3").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip4").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip5",
                SimplyMoreHelperMethods.translateTicks(skillLength)).setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip6").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip7").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.blade_of_the_grotesque.tooltip8").setStyle(textStyle));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
}
