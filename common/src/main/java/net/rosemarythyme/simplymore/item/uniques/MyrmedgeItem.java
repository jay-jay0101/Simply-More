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
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.item.components.GrabbedComponent;
import net.rosemarythyme.simplymore.registry.item.ItemComponentRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.HelperMethods;
import net.sweenus.simplyswords.util.Styles;
import org.joml.Vector3d;

import java.util.List;
import java.util.UUID;


public class MyrmedgeItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = UNIQUE_CONFIG.myrmedge.cooldown + UNIQUE_CONFIG.myrmedge.grabTime;

    public MyrmedgeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    public static float getHungerModifiedValue(PlayerEntity entity, float percentage, float value) {
        float hungerPercentage = entity.getHungerManager().getFoodLevel() / 20f;
        float extraPercentage = percentage * (1 - hungerPercentage);

        return value * (extraPercentage);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(user.getWorld().isClient)
            return super.use(world, user, hand);

        ItemStack stack = user.getStackInHand(hand);

        Entity entity = HelperMethods.getTargetedEntity(user, 2);

        if(entity instanceof LivingEntity target) {
            if(!AttackUtils.canTarget(user, target, AttackUtils.AttackTarget.ENEMIES)) return super.use(world, user, hand);

            stack.set(ItemComponentRegistry.GRABBED.get(), new GrabbedComponent(target.getUuid()));
            user.getItemCooldownManager().set(this, skillCooldown);

            user.addStatusEffect(
                    new StatusEffectInstance(
                            StatusEffectRegistry.getReference(StatusEffectRegistry.GRASPING),
                            UNIQUE_CONFIG.myrmedge.grabTime
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
                    UNIQUE_CONFIG.myrmedge.maxDamageBonus,
                    (float) HelperMethods.getEntityAttackDamage(attacker));

            target.timeUntilRegen = 0;
            target.damage(target.getDamageSources().playerAttack(playerAttacker), extraDamage);
        }


        return super.postHit(stack, target, attacker);
    }


    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        if(world.getTime() % 20 == 0 && entity instanceof PlayerEntity playerEntity) {
            int amplifier = (int) Math.floor(getHungerModifiedValue(
                    playerEntity,
                    5,
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
        && player.hasStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.GRASPING))
        && !player.getWorld().isClient
        && player.isAlive()) {


            if(stack.get(ItemComponentRegistry.GRABBED.get()) != null) {
                UUID uuid = stack.get(ItemComponentRegistry.GRABBED.get()).entityId();
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
                    livingTarget.teleport(blockPos.getX(), blockPos.getY(), blockPos.getZ(), false);
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
                    if(player.getStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.GRASPING)).getDuration() == 1) {
                        Vector3d normalisedVector = MathUtils.getNormalised2dVector(player.getYaw()).mul(UNIQUE_CONFIG.myrmedge.throwStrength);
                        livingTarget.setVelocity(new Vec3d(
                                normalisedVector.x(),
                                0.2f,
                                normalisedVector.z()
                        ));
                        livingTarget.velocityModified = true;
                    }
                } else {
                    player.removeStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.GRASPING));
                }
            }
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.ASH);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.myrmedge.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.myrmedge.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.myrmedge.tooltip4").setStyle(textStyle));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplymore.myrmedge.tooltip7", MathUtils.translateTicks(
                UNIQUE_CONFIG.myrmedge.grabTime
        )).setStyle(textStyle));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplymore.myrmedge.tooltip8").setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MYRMEDGE));
        }

        @ValidatedFloat.Restrict(min = 0f)
        public float maxDamageBonus = 0.4f;
        @ValidatedInt.Restrict(min = 0)
        public int grabTime = 100;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 300;
        @ValidatedFloat.Restrict(min = 0f)
        public float throwStrength = 1.6f;
    }
}
