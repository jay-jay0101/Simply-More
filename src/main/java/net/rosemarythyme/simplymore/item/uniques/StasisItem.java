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
import net.rosemarythyme.simplymore.item.UniqueSwordItem;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;

public class StasisItem extends UniqueSwordItem {
    int skillCooldown = 700;
    int onHitCooldown = 80;


    public StasisItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            if (!attacker.getWorld().isClient()) {
                if (attacker.getRandom().nextInt(100) <= 20) {
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


    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }


    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!user.getWorld().isClient && user instanceof PlayerEntity player) {
            ServerWorld serverWorld = ((ServerWorld) world);
            if(remainingUseTicks % 4 == 0) serverWorld.playSound(null,user.getX(),user.getY(),user.getZ(),SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER,SoundCategory.PLAYERS,0.5f,1f);
            if(remainingUseTicks==1) {
                for (int i = 0; i < 30; i++) {
                    int dX = user.getRandom().nextInt(9)-5;
                    int dZ = user.getRandom().nextInt(9)-5;
                    BlockPos pos = user.getBlockPos();
                    pos = new BlockPos(pos.getX()+dX,pos.getY(),pos.getZ()+dZ);
                    LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(serverWorld);
                    if (lightningEntity != null) {
                        lightningEntity.refreshPositionAfterTeleport(pos.getX(),pos.getY(),pos.getZ());
                        lightningEntity.setCosmetic(true);
                        serverWorld.spawnEntity(lightningEntity);
                    }
                }
                LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(serverWorld);
                for(LivingEntity entity : player.getWorld().getNonSpectatingEntities(LivingEntity.class, new Box(player.getX()-4,player.getY()-2,player.getZ()-4,player.getX()+4,player.getY()+6,player.getZ()+4))) {
                    if(entity == user || entity.isTeammate(user)) continue;
                    entity.damage(player.getDamageSources().magic(),12);
                    entity.onStruckByLightning(serverWorld,lightningEntity);
                }

                player.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
            }
        }
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    public int getMaxUseTime(ItemStack stack) {
        return 60;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style RIGHTCLICK = HelperMethods.getStyle("rightclick");
        Style ABILITY = HelperMethods.getStyle("ability");
        Style TEXT = HelperMethods.getStyle("text");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.stasis.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.stasis.tooltip2").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.stasis.tooltip3").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(RIGHTCLICK));
        tooltip.add(Text.translatable("item.simplymore.stasis.tooltip4").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.stasis.tooltip5").setStyle(TEXT));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }

    private static int stepMod = 0;

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (stepMod > 0) {
            --stepMod;
        }

        if (stepMod <= 0) {
            stepMod = 7;
        }

        HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.GLOW, ParticleTypes.GLOW, ParticleTypes.GLOW, true);
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
