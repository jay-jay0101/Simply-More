package net.rosemarythyme.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;

public class StasisItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = 700;
    int onHitCooldown = 80;


    public StasisItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            if (!attacker.getWorld().isClient()) {
                if (attacker.getRandom().nextBetween(1, 100) <= 20) {
                    attacker.getWorld().playSound(null,attacker.getX(),attacker.getY(),attacker.getZ(),SoundEvents.ITEM_TRIDENT_THUNDER, SoundCategory.PLAYERS,0.5f,2f);
                    ((ServerWorld) attacker.getWorld()).spawnParticles(ParticleTypes.ELECTRIC_SPARK,attacker.getX(),attacker.getY()+0.5,attacker.getZ(),50,0.15,0.25,0.15,0.1);
                    if (target instanceof PlayerEntity playerTarget) {
                        for (ItemStack item : playerTarget.getHandItems()) {
                            if (!playerTarget.getItemCooldownManager().isCoolingDown(item.getItem())) {
                                playerTarget.getItemCooldownManager().set(item.getItem(), onHitCooldown);
                            }
                        }
                    }
                }
            }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingTicks) {
        if (!world.isClient() && user instanceof PlayerEntity player) {
            ServerWorld serverWorld = (ServerWorld) world;
            if (remainingTicks % 4 == 0) {
                world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.PLAYERS, 0.5f, 1f);
            }
            if (remainingTicks == 1) {
                createLightningStrike(player, serverWorld);
                damageAndElectrifyEnemies(user, player, serverWorld);
                player.getItemCooldownManager().set(stack.getItem(), skillCooldown);
            }
        }
        super.usageTick(world, user, stack, remainingTicks);
    }

    private void createLightningStrike(PlayerEntity player, ServerWorld world) {
        for (int i = 0; i < 30; i++) {
            int randomX = player.getRandom().nextInt(9) - 5;
            int randomZ = player.getRandom().nextInt(9) - 5;
            BlockPos position = player.getBlockPos().add(randomX, 0, randomZ);
            LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
            if (lightning != null) {
                lightning.refreshPositionAfterTeleport(position.getX(), position.getY(), position.getZ());
                lightning.setCosmetic(true);
                world.spawnEntity(lightning);
            }
        }
        Box box = new Box(player.getX() - 4, player.getY() - 2, player.getZ() - 4, player.getX() + 4, player.getY() + 6, player.getZ() + 4);
        for (LivingEntity entity : world.getNonSpectatingEntities(LivingEntity.class, box)) {
            LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
            if (entity == player || entity.isTeammate(player)) {
                continue;
            }
            entity.damage(player.getDamageSources().magic(), 12);
            entity.onStruckByLightning(world, lightning);
        }
    }

    private void damageAndElectrifyEnemies(LivingEntity user, PlayerEntity player, ServerWorld world) {
        Box box = new Box(user.getX() - 4, user.getY() - 2, user.getZ() - 4, user.getX() + 4, user.getY() + 6, user.getZ() + 4);
        for (LivingEntity entity : world.getNonSpectatingEntities(LivingEntity.class, box)) {
            if (entity == user || entity.isTeammate(user)) {
                continue;
            }
            entity.damage(player.getDamageSources().magic(), 12);
            entity.onStruckByLightning(world, null);
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 60;
    }


    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        int stepMod = 0;
        SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.GLOW);
        super.inventoryTick(stack, world, entity, slot, selected);
    }


    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style rightClickStyle = HelperMethods.getStyle("rightclick");
        Style abilityStyle = HelperMethods.getStyle("ability");
        Style textStyle = HelperMethods.getStyle("text");

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.stasis.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.stasis.tooltip2").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.stasis.tooltip3").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.stasis.tooltip4").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.stasis.tooltip5").setStyle(textStyle));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
}
