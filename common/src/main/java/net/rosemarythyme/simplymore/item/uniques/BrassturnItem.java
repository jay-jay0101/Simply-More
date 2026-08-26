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
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.config.WeaponAttributesConfig;
import net.rosemarythyme.simplymore.entity.JetAreaEffectCloudEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;
import java.util.UUID;


public class BrassturnItem extends SimplyMoreUniqueSwordItem {

    protected static WeaponAttributesConfig attributes = config.weaponAttributes;

    public BrassturnItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

//    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        double attackSpeedModifier = getOxidisation(stack) * ((3.4f + attributes.getBrassturnMaxSwingSpeed()) / -16f);

        Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers = super.getAttributeModifiers(slot);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(attributeModifiers);
        builder.put(
                EntityAttributes.GENERIC_ATTACK_SPEED,
                new EntityAttributeModifier(UUID.fromString("ebdfffcf-7f0d-4502-96ec-e4f6b995fe2f"), "Weapon modifier", attackSpeedModifier, EntityAttributeModifier.Operation.ADDITION)
        );

        return slot == EquipmentSlot.MAINHAND ? builder.build() : super.getAttributeModifiers(slot);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getWorld().isClient()) return super.postHit(stack, target, attacker);

        int oxidisation = getOxidisation(stack) + 1;
        saveOxidisation(stack, oxidisation);

        if(attacker.getRandom().nextBetween(1, 100) <= effect.getBrassturnJetChance()) {

            attacker.getWorld().playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.ENTITY_ZOMBIE_INFECT, SoundCategory.PLAYERS, 0.5f,2);

            ((ServerWorld) attacker.getWorld()).spawnEntity(new JetAreaEffectCloudEntity(
                    target.getWorld(),
                    target.getX(),
                    target.getY(),
                    target.getZ(),
                    attacker
            ));
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (getOxidisation(itemStack) <= 0) return TypedActionResult.fail(itemStack);

        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (getOxidisation(stack) <= 0) user.stopUsingItem();

        if (remainingUseTicks % effect.getBrassturnScrapeTime() == 0 && user.getWorld() instanceof ServerWorld serverWorld) {
            int oxidisation = getOxidisation(stack) - 1;
            saveOxidisation(stack, oxidisation);

            if(user.getRandom().nextBetween(1, 100) <= effect.getBrassturnSparkChance()) {
                serverWorld.spawnParticles(ParticleTypes.WAX_ON,user.getX(),user.getY(),user.getZ(),20,0.5,1,0.5,0.2);
                serverWorld.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_BEACON_POWER_SELECT, SoundCategory.PLAYERS, 0.5f,2);
                int boxSize = 3;
                Box box = new Box(user.getX() - boxSize, user.getY() - 2, user.getZ() - boxSize, user.getX() + boxSize, user.getY() + boxSize, user.getZ() + boxSize);
                List<LivingEntity> livingEntities = user.getWorld().getNonSpectatingEntities(LivingEntity.class, box);

                for (LivingEntity livingEntity : livingEntities) {
                    if (livingEntity == user || livingEntity.isTeammate(user)) {
                        continue;
                    }

                    livingEntity.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.STUNNED.get(), effect.getBrassturnSparkStunDuration(),0));
                }

            } else {
                serverWorld.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.PLAYERS, 1f,1);
            }
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 999999;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    public static int getOxidisation(ItemStack stack) {
        final int maxOxidisation = 16; // Constant

        NbtElement oxiRaw = stack.getOrCreateNbt().get("simplymore:oxidisation");
        if (oxiRaw == null) {
            saveOxidisation(stack, maxOxidisation);
            return maxOxidisation;
        }

        String oxiString = oxiRaw.toString().replaceAll("\"", "");

        int oxiNumber = 0;
        try {
            oxiNumber = Math.min(maxOxidisation, Integer.parseInt(oxiString));
        } catch (NumberFormatException e) {
            oxiNumber = maxOxidisation;
            saveOxidisation(stack, maxOxidisation);
        }

        return oxiNumber;
    }

    public static void saveOxidisation(ItemStack stack, int oxidisation) {
        stack.getOrCreateNbt().putString("simplymore:oxidisation",Integer.toString(oxidisation));
    }

    int stepMod = 0;
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        stepMod = SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.ASH);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style rightClickStyle = Styles.RIGHT_CLICK;
        Style abilityStyle = Styles.ABILITY;
        Style textStyle = Styles.TEXT;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.brassturn.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.brassturn.tooltip2").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.brassturn.tooltip3").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.brassturn.tooltip4").setStyle(textStyle));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.brassturn.tooltip5").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.brassturn.tooltip6").setStyle(textStyle));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
}
