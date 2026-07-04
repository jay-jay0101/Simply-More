package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.RiftAreaEffectCloudEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.VisualEffectsUtils;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;
import org.joml.Vector3f;

import java.util.List;

public class MatterbaneItem extends SimplyMoreUniqueSwordItem {

    int skillCooldown = effect.matterbane.cooldown;

    public MatterbaneItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.SWORD, settings);
    }

    public Vector3f getColor(ItemStack stack) {
        DyedColorComponent color = stack.get(DataComponentTypes.DYED_COLOR);
        if(color == null) {
            return new Vector3f(1f, 0f, 0f);
        } else {
            int rgb = color.rgb();
            int r = (rgb >> 16) & 0xFF;
            int g = (rgb >> 8) & 0xFF;
            int b = rgb & 0xFF;

            return new Vector3f(r / 255f, g / 255f, b / 255f);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {
            Vector3f color = getColor(user.getStackInHand(hand));
            RiftAreaEffectCloudEntity riftAreaEffectCloudEntity = new RiftAreaEffectCloudEntity(user.getWorld(),user.getX(),user.getY()+3,user.getZ(),user, color);
            user.getWorld().spawnEntity(riftAreaEffectCloudEntity);
            user.getWorld().playSound(null, user.getBlockPos(), SoundEvents.ENTITY_WARDEN_DIG, user.getSoundCategory(), 1F, 2F);
            user.getItemCooldownManager().set(this, skillCooldown);
        }
        return super.use(world, user, hand);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if (MathUtils.chance(attacker, effect.matterbane.chance) && attacker instanceof PlayerEntity player) {
                fireBolt(player, getColor(attacker.getStackInHand(Hand.MAIN_HAND)));
            }
        }
        return super.postHit(stack, target, attacker);
    }

    public void fireBolt(PlayerEntity user, Vector3f color) {
        user.getWorld().playSound(null, user.getBlockPos(), SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, user.getSoundCategory(), 1F, 2F);
        for(int j = 0; j< effect.matterbane.range * 4; j++) {

            float yaw = (float) Math.toRadians(user.getYaw()+90);

            float velocityX = (float) (Math.cos(yaw)) * 0.25f;
            float velocityZ = (float) (Math.sin(yaw)) * 0.25f;

            double dX = velocityX * j;
            double dZ = velocityZ * j;

            double x = user.getX();
            double y = (user.getEyeY() + user.getY())/2;
            double z = user.getZ();


            DustParticleEffect particleEffect = new DustParticleEffect(color,2);

            ((ServerWorld) user.getWorld()).spawnParticles(particleEffect,x+dX,y,z+dZ,1,0,0,0,0);

            Box box = MathUtils.createCubeBox(new Vec3d(x, y, z).add(dX, 0, dZ), 0.6);
            List<LivingEntity> targets = AttackUtils.getTargets(user, box);
            for (LivingEntity target : targets) {
                target.damage(user.getDamageSources().magic(),effect.matterbane.damage);
            }
        }
    }


    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        VisualEffectsUtils.handleFootfalls(entity, stack, world, ParticleTypes.ASH);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip2", effect.matterbane.range).setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip5").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip9").setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ModItemsRegistry.MATTERBANE));
        }

        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.2f;
        @ValidatedInt.Restrict(min = 0)
        public int range = 8;
        @ValidatedFloat.Restrict(min = 0f)
        public int damage = 6;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 1200;
    }
}
