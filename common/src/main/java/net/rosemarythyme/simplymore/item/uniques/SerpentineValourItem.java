package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.PoisonBoltAreaEffectCloudEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.VisualEffectsUtils;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class SerpentineValourItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = effect.serpentine_valour.cooldown;

    public SerpentineValourItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.SWORD, settings);
    }


    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getWorld().isClient())
            return super.postHit(stack, target, attacker);

        if (target.hasStatusEffect(StatusEffects.POISON) || target.hasStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.VENOM))) {
            target.timeUntilRegen = 0;
            target.damage(target.getDamageSources().generic(), effect.serpentine_valour.damageBonus);
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {
            Box entitySearchBox = new Box(
                    user.getX() - 5,
                    user.getY() - 5,
                    user.getZ() - 5,
                    user.getX() + 5,
                    user.getY() + 5,
                    user.getZ() + 5
            );

            boolean hasEnemies = user.getWorld().getNonSpectatingEntities(LivingEntity.class, entitySearchBox).stream()
                    .anyMatch(entity -> entity != user && !AttackUtils.checkFriendlyFire(entity, user));

            int poisonBoltAreaEffectCloudEntityBehavior = hasEnemies ? -2 : 0;

            for (int j = 0; j < 4; j++) {

                int offsetX = j % 2 == 0
                        ? (j / 2 == 0 ? -1 : 1)
                        : 0;

                int offsetZ = j % 2 == 1
                        ? (j / 2 == 0 ? -1 : 1)
                        : 0;

                PoisonBoltAreaEffectCloudEntity entity = new PoisonBoltAreaEffectCloudEntity(
                        user.getWorld(),
                        user.getX() + offsetX,  // Add the x-offset to the user's x-coordinate
                        user.getY() + 2,           // Keep the y-coordinate constant (2 blocks above the user)
                        user.getZ() + offsetZ,     // Add the z-offset to the user's z-coordinate
                        user,
                        poisonBoltAreaEffectCloudEntityBehavior
                );

                // Spawn the entity in the world
                world.spawnEntity(entity);
            }
            user.getWorld().playSound(null, user.getBlockPos(), SoundRegistry.MAGIC_SHAMANIC_VOICE_15.get(), SoundCategory.PLAYERS, 0.4f, 1);
        }
        user.getItemCooldownManager().set(this, skillCooldown);
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        VisualEffectsUtils.handleFootfalls(entity, stack, world, ParticleTypes.SNEEZE, ParticleTypes.SNEEZE, ParticleTypes.SPORE_BLOSSOM_AIR);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.serpentine_valour.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.serpentine_valour.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.serpentine_valour.tooltip4").setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ModItemsRegistry.SERPENTINE_VALOUR));
        }

        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 700;
        @ValidatedInt.Restrict(min = 0)
        public int lifespan = 10;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 2f;
        @ValidatedInt.Restrict(min = 0)
        public int venomTime = 160;
        @ValidatedFloat.Restrict(min = 0f)
        public float damageBonus = 4f;
    }
}
