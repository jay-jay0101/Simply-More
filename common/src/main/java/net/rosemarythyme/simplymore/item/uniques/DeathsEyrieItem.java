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
import net.rosemarythyme.simplymore.item.components.CounterComponent;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.VisualEffectsUtils;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;
import net.sweenus.simplyswords.util.Styles;
import org.joml.Vector3f;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class DeathsEyrieItem extends SimplyMoreUniqueSwordItem {

    @Override
    public CounterComponent getDefaultComponent() {
        return new CounterComponent(0, 5);
    }

    int skillCooldown = UNIQUE_CONFIG.deaths_eyrie.cooldown;
    public static final int maxCrows = 5;


    public DeathsEyrieItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.SWORD, settings);
    }


    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient() && attacker instanceof PlayerEntity playerAttacker && !playerAttacker.getItemCooldownManager().isCoolingDown(this)) {
            if (MathUtils.chance(attacker, UNIQUE_CONFIG.deaths_eyrie.chance)) {
                int effectTime = UNIQUE_CONFIG.deaths_eyrie.baseBleedTime;
                effectTime += UNIQUE_CONFIG.deaths_eyrie.additionalBleedTime * getCrows(stack);
                int amplifier = (int) Math.floor(0.75f * (getCrows(stack) -1));

                target.addStatusEffect(new StatusEffectInstance(
                        StatusEffectRegistry.getReference(StatusEffectRegistry.BLEED),
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
        if (entityTarget instanceof LivingEntity target && AttackUtils.canHitTarget(target, user)) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 10, 0), user);
            List<CrowEntity> pets = world.getEntitiesByClass(CrowEntity.class, user.getBoundingBox().expand(50),
                    crowEntity -> crowEntity.getOwner() == user
            );

            if (!pets.isEmpty()) {
                AtomicInteger offset = new AtomicInteger();
                pets.forEach(crowEntity -> {
                    crowEntity.setAttackingTime(offset.get() + (pets.size() * UNIQUE_CONFIG.deaths_eyrie.crowAttackTimePerCrow));
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

        int crows = MathUtils.getCounterComponent(stack).value();

        crows = Math.max(minCrows, crows);
        crows = Math.min(maxCrows, crows);

        return crows;
    }

    public static void setCrows(ItemStack stack, int value) {
        MathUtils.setCounterComponent(stack,
                MathUtils.getCounterComponent(stack).set(value));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(
                entity instanceof PlayerEntity player
                && selected
                && !player.getWorld().isClient
                && player.getMainHandStack().equals(stack)
        ) {
            int crows = getCrows(stack);

            List<CrowEntity> pets = world.getEntitiesByClass(CrowEntity.class, player.getBoundingBox().expand(50),
                    crowEntity -> crowEntity.getOwner() == player
            );

            if(!player.getItemCooldownManager().isCoolingDown(this)) {
                if (pets.size() > crows) {
                    pets.getFirst().kill();
                } else if (pets.size() < crows) {
                    double dX = player.getRandom().nextBetween(-15,15) / 10d;
                    double dZ = player.getRandom().nextBetween(-15,15) / 10d;
                    CrowEntity crowEntity = new CrowEntity(EntityRegistry.CROW.get(), world);
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

        VisualEffectsUtils.handleFootfalls(entity, stack, world, ParticleTypes.WARPED_SPORE);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.deaths_eyrie.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.deaths_eyrie.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplymore.deaths_eyrie.tooltip3").setStyle(textStyle));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.deaths_eyrie.tooltip6").setStyle(textStyle));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplymore.deaths_eyrie.tooltip9").setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.DEATHS_EYRIE));
        }


        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.25f;
        @ValidatedInt.Restrict(min = 0)
        public int baseBleedTime = 80;
        @ValidatedInt.Restrict(min = 0)
        public int additionalBleedTime = 20;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 550;
        @ValidatedInt.Restrict(min = 0)
        public int crowBleedTime = 120;
        @ValidatedInt.Restrict(min = 0)
        public int crowBlindTime = 20;
        @ValidatedInt.Restrict(min = 0)
        public int crowAttackTimePerCrow = 30;
        @ValidatedFloat.Restrict(min = 0f)
        public float crowDamage = 2.3f;
        @ValidatedFloat.Restrict(min = 0f)
        public float deathsEyrieCrowAttackHeal = 0.3f;
    }
}
