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
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.config.WeaponAttributesConfig;
import net.rosemarythyme.simplymore.entity.GreatSlitherFangEntity;
import net.rosemarythyme.simplymore.entity.JetAreaEffectCloudEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.item.interfaces.CooldownOnUnselected;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;
import java.util.UUID;


public class CindergorgeItem extends SimplyMoreUniqueSwordItem implements CooldownOnUnselected {

    int skillCooldown = effect.getCindergorgeMaxCooldown();

    public CindergorgeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            startUsing(itemStack, hand);
            return TypedActionResult.consume(itemStack);
        }
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {

        int ticksUntilUseEnd = this.getMaxUseTime(stack) - remainingUseTicks;
        if (remainingUseTicks == 1) {
            user.stopUsingItem();
        }

        float originalYaw = user.getYaw();
        float turnAngle = 54f * (1 - ((float) remainingUseTicks / effect.getCindergorgeMaxDuration()));
        user.setYaw(originalYaw + turnAngle);

        if(user instanceof ServerPlayerEntity) {
                double yawAngle = Math.toRadians(user.getYaw());
                double cosYaw = Math.cos(yawAngle);
                double sinYaw = Math.sin(yawAngle);

                for (int distanceMultiplier = 1; distanceMultiplier < effect.getCindergorgeFireRange(); distanceMultiplier++) {
                    double offsetX = -distanceMultiplier * sinYaw;
                    double offsetZ = distanceMultiplier * cosYaw;

                    double spawnX = user.getX() + 1.2 * offsetX;
                    double spawnZ = user.getZ() + 1.2 * offsetZ;

                    ((ServerWorld) world).spawnParticles(ParticleTypes.FLAME, spawnX, user.getEyeY(), spawnZ, 20, 0.2, 0.2, 0.2, 0.1);
                    world.playSound(null, spawnX, user.getEyeY(), spawnZ, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.PLAYERS, 0.1f, 1f);

                    for (LivingEntity entity : user.getWorld().getNonSpectatingEntities(LivingEntity.class,new Box(spawnX-0.75,user.getEyeY()-0.75,spawnZ-0.75,spawnX+0.75,user.getEyeY()+0.75,spawnZ+0.75)))
                    {
                        if (entity.isTeammate(user) || entity == user || entity.isInvulnerable()) continue;

                        entity.damage(user.getDamageSources().inFire(),effect.getCindergorgeFireDamage());
                        entity.setOnFireFor(3);
                    }
                }
        }

    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        float relativeTime = (1 - ((float) remainingUseTicks / effect.getCindergorgeMaxDuration()));
        float cooldown = skillCooldown * relativeTime;
        cooldown = Math.max(cooldown, 120f);
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, (int) (150f * relativeTime)));
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
        ((PlayerEntity) user).getItemCooldownManager().set(this.getDefaultStack().getItem(), (int) cooldown);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return effect.getCindergorgeMaxDuration();
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }


    int stepMod = 0;
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(entity instanceof PlayerEntity player) {
            detectCooldown(player, selected, stack, skillCooldown, false);
        }

        stepMod = SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.LAVA);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style rightClickStyle = HelperMethods.getStyle("rightclick");
        Style abilityStyle = HelperMethods.getStyle("ability");
        Style textStyle = HelperMethods.getStyle("text");

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.cindergorge.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.cindergorge.tooltip2").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.cindergorge.tooltip3").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.cindergorge.tooltip4").setStyle(textStyle));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.cindergorge.tooltip5").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.cindergorge.tooltip6").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.cindergorge.tooltip7").setStyle(textStyle));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
}
