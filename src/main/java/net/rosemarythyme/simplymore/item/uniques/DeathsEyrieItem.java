package net.rosemarythyme.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.CrowEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.registry.ModEntityRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;
import org.joml.Vector3f;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class DeathsEyrieItem extends SimplyMoreUniqueSwordItem {

    int skillCooldown = effect.getDeathsEyrieCooldown();
    public static final int maxCrows = 5;

    public DeathsEyrieItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }


    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient() && attacker instanceof PlayerEntity playerAttacker && !playerAttacker.getItemCooldownManager().isCoolingDown(this)) {
            if (attacker.getRandom().nextBetween(1, 100) <= effect.getDeathsEyrieBleedChance()) {
                int effectTime = effect.getDeathsEyrieBaseBleedTime();
                effectTime += effect.getDeathsEyrieCrowAdditionalBleedTime() * getCrows(stack);
                int amplifier = (int) Math.floor(0.75f * (getCrows(stack) -1));

                target.addStatusEffect(new StatusEffectInstance(
                        ModEffectsRegistry.BLEED,
                        effectTime,
                        amplifier
                ));

                int crows = getCrows(stack);
                if(crows < maxCrows) {
                    setCrows(stack, crows + 1);
                }

                attacker.getWorld().playSound(
                        null,
                        attacker.getX(),
                        attacker.getY(),
                        attacker.getZ(),
                        SoundRegistry.DARK_SWORD_ENCHANT.get(),
                        SoundCategory.PLAYERS,
                        1f,
                        1f
                );

            }
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.getWorld().isClient) return super.use(world, user, hand);

        Entity entityTarget = HelperMethods.getTargetedEntity(user, 20);
        if (entityTarget instanceof LivingEntity target && !target.isTeammate(user)) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 10, 0), user);
            List<CrowEntity> pets = world.getEntitiesByClass(CrowEntity.class, user.getBoundingBox().expand(10000),
                    crowEntity -> crowEntity.getOwner() == user
            );

            if (!pets.isEmpty()) {
                AtomicInteger offset = new AtomicInteger();
                pets.forEach(crowEntity -> {
                    crowEntity.setAttackingTime(offset.get() + (pets.size() * effect.getDeathsEyrieCrowAttackTimePerCrow()));
                    offset.getAndIncrement();
                    crowEntity.setAttackingUuid(target.getUuid());
                });

                setCrows(user.getStackInHand(hand), 1);
                user.getItemCooldownManager().set(this,skillCooldown);
            }
        }

        return super.use(world, user, hand);
    }

    public static int getCrows(ItemStack stack) {
        final int minCrows = 1; // Constant

        int crows = stack.getOrCreateNbt().getInt("simplymore:crow");

        crows = Math.max(minCrows, crows);
        crows = Math.min(maxCrows, crows);

        return crows;
    }

    public static void setCrows(ItemStack stack, int value) {
        stack.getOrCreateNbt().putInt("simplymore:crow", value);
    }

    int stepMod = 0;
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        if(entity instanceof PlayerEntity player && selected && !player.getWorld().isClient) {
            int crows = getCrows(stack);

            List<CrowEntity> pets = world.getEntitiesByClass(CrowEntity.class, player.getBoundingBox().expand(10000),
                    crowEntity -> crowEntity.getOwner() == player
            );

            if(!player.getItemCooldownManager().isCoolingDown(this)) {
                if (pets.size() > crows) {
                    pets.get(0).kill();
                } else if (pets.size() < crows) {
                    double dX = player.getRandom().nextBetween(-15,15) / 10d;
                    double dZ = player.getRandom().nextBetween(-15,15) / 10d;
                    CrowEntity crowEntity = new CrowEntity(ModEntityRegistry.CROW, world);
                    crowEntity.setOwner(player);
                    crowEntity.setPos(player.getX()+dX, player.getEyeY()+1, player.getZ()+dZ);

                    player.getWorld().playSound(
                            null,
                            player.getX()+dX,
                            player.getEyeY()+1,
                            player.getZ()+dZ,
                            SoundEvents.ENTITY_PARROT_IMITATE_PHANTOM,
                            SoundCategory.NEUTRAL,
                            1f,
                            0.6f
                    );


                    ((ServerWorld) player.getWorld()).spawnParticles(
                            new DustParticleEffect(new Vector3f(0f, 0f, 0.2f), 3f),
                            player.getX()+dX,
                            player.getEyeY()+1.25f,
                            player.getZ()+dZ,
                            5,
                            0.2f,0.2f,0.2f,
                            0
                    );

                    player.getWorld().spawnEntity(crowEntity);
                }
            }
        }

        stepMod = SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.WARPED_SPORE);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style rightClickStyle = HelperMethods.getStyle("rightclick");
        Style abilityStyle = HelperMethods.getStyle("ability");
        Style textStyle = HelperMethods.getStyle("text");

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.deaths_eyrie.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.deaths_eyrie.tooltip2").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.deaths_eyrie.tooltip3").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.deaths_eyrie.tooltip4").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.deaths_eyrie.tooltip5").setStyle(textStyle));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.deaths_eyrie.tooltip6").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.deaths_eyrie.tooltip7").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.deaths_eyrie.tooltip8").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.deaths_eyrie.tooltip9").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.deaths_eyrie.tooltip10").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.deaths_eyrie.tooltip11").setStyle(textStyle));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
}
