package net.rosemarythyme.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.util.HelperMethods;
import org.joml.Vector3d;

import java.util.List;
import java.util.UUID;


public class MyrmedgeItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = effect.getMyrmedgeCooldown() + effect.getMyrmedgeMaxGrabTime();

    public MyrmedgeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public static float getHungerModifiedValue(PlayerEntity entity, int percentage, float value) {
        float hungerPercentage = entity.getHungerManager().getFoodLevel() / 20f;
        float extraPercentage = percentage * (1 - hungerPercentage);

        return value * (extraPercentage/100f);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(user.getWorld().isClient)
            return super.use(world, user, hand);

        ItemStack stack = user.getStackInHand(hand);

        Entity entity = HelperMethods.getTargetedEntity(user, 2);

        if(entity instanceof LivingEntity target) {
            if(target == user || target.isTeammate(user) || target.isDead()) {
                return super.use(world, user, hand);
            }

            stack.getOrCreateNbt().putUuid("simplymore:grabbed", target.getUuid());
            user.getItemCooldownManager().set(this, skillCooldown);

            user.addStatusEffect(
                    new StatusEffectInstance(
                            ModEffectsRegistry.GRASPING.get(),
                            effect.getMyrmedgeMaxGrabTime()
                    )
            );
            user.getWorld().playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_EVOKER_FANGS_ATTACK, SoundCategory.PLAYERS, 1,1.5f);
        }

        return super.use(world, user, hand);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getWorld().isClient())
            return super.postHit(stack, target, attacker);


        if (attacker instanceof PlayerEntity playerAttacker) {
            float extraDamage = getHungerModifiedValue(playerAttacker,
                    effect.getMyrmedgeMaxDamagePercentageBuff(),
                    config.weaponAttributes.getMyrmedgeDamage());

            target.timeUntilRegen = 0;
            target.damage(target.getDamageSources().playerAttack(playerAttacker), extraDamage);
        }


        return super.postHit(stack, target, attacker);
    }


    int stepMod = 0;
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        if(world.getTime() % 20 == 0 && entity instanceof PlayerEntity playerEntity) {
            int amplifier = (int) Math.floor(getHungerModifiedValue(
                    playerEntity,
                    500,
                    1
            )) -1;

            if(amplifier>=0) {
                playerEntity.addStatusEffect(
                        new StatusEffectInstance(
                                StatusEffects.SPEED,
                                25,
                                amplifier
                        )
                );
            }

        }

        // Grasping
        if(entity instanceof PlayerEntity player
        && (selected || ( player.getOffHandStack() == stack  && player.getMainHandStack().getItem() != stack.getItem() ))
        && player.hasStatusEffect(ModEffectsRegistry.GRASPING.get())
        && !player.getWorld().isClient
        && player.isAlive()) {


            if(stack.getOrCreateNbt().contains("simplymore:grabbed")) {
                UUID uuid = stack.getOrCreateNbt().getUuid("simplymore:grabbed");
                Entity target = ((ServerWorld) world).getEntity(uuid);

                if(target instanceof LivingEntity livingTarget && livingTarget.isAlive()) {
                    // Ticking
                    livingTarget.addStatusEffect(
                            new StatusEffectInstance(
                                    StatusEffects.MINING_FATIGUE,
                                    10,
                                    1
                            )
                    );

                    livingTarget.addStatusEffect(
                            new StatusEffectInstance(
                                    StatusEffects.WEAKNESS,
                                    10,
                                    1
                            )
                    );

                    Position blockPos = entity.raycast(1.2, 0, false).getPos();
                    livingTarget.teleport(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                    livingTarget.limitFallDistance();
                    livingTarget.fallDistance = 0;

                    // Sap
                    if(world.getTime() % 20 == 0) {
                        if(livingTarget instanceof PlayerEntity playerTarget) {
                            playerTarget.getHungerManager().setSaturationLevel(0);
                            playerTarget.getHungerManager().setFoodLevel(
                                    playerTarget.getHungerManager().getFoodLevel() - 1
                            );
                        }

                        target.damage(player.getDamageSources().playerAttack(player), 1);
                        entity.getWorld().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENTITY_EVOKER_FANGS_ATTACK, SoundCategory.PLAYERS, 1,2f);
                        player.getHungerManager().add(2, 0.1f);
                        if(player.getHungerManager().getFoodLevel() >= 20f) {
                            player.heal(1);
                        }
                    }

                    // Throw
                    if(player.getStatusEffect(ModEffectsRegistry.GRASPING.get()).getDuration() == 1) {
                        Vector3d normalisedVector = SimplyMoreHelperMethods.getNormalised2dVector(player.getYaw()).mul(effect.getMyrmedgeThrowStrength());
                        livingTarget.setVelocity(new Vec3d(
                                normalisedVector.x(),
                                0.2f,
                                normalisedVector.z()
                        ));
                        livingTarget.velocityModified = true;
                    }
                } else {
                    player.removeStatusEffect(ModEffectsRegistry.GRASPING.get());
                }
            }
        }

        stepMod = SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.ASH);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style rightClickStyle = HelperMethods.getStyle("rightclick");
        Style abilityStyle = HelperMethods.getStyle("ability");
        Style textStyle = HelperMethods.getStyle("text");

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.myrmedge.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.myrmedge.tooltip2").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.myrmedge.tooltip3").setStyle(textStyle));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.myrmedge.tooltip4").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.myrmedge.tooltip5").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.myrmedge.tooltip6").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.myrmedge.tooltip7",SimplyMoreHelperMethods.translateTicks(
                effect.getMyrmedgeMaxGrabTime()
        )).setStyle(textStyle));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplymore.myrmedge.tooltip8").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.myrmedge.tooltip9").setStyle(textStyle));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }

}
