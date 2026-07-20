package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.RiftEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;
import org.joml.Vector3f;

import java.util.List;

public class MatterbaneItem extends SimplyMoreUniqueSwordItem {
    public MatterbaneItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordType.SWORD, settings);
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
        if (user.getWorld().isClient) return super.use(world, user, hand);

        AudioVisualUtils.playSound(world, user.getPos(), new Sound(SoundEvents.ENTITY_WARDEN_DIG).setPitch(2f));

        Vector3f color = this.getColor(user.getStackInHand(hand));
        AttackUtils.spawnAbility(new RiftEntity(user, user.getPos().add(0d, 3d, 0d), color), user);
        user.getItemCooldownManager().set(this, UNIQUE_CONFIG.matterbane.cooldown);

        return super.use(world, user, hand);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(attacker.getWorld().isClient) return super.postHit(stack, target, attacker);

        if (MathUtils.chance(attacker, UNIQUE_CONFIG.matterbane.chance)) {
            Vector3f color = this.getColor(stack);
            ServerWorld world = (ServerWorld) attacker.getWorld();


            AudioVisualUtils.playSound(world, attacker.getPos(), new Sound(SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED).setPitch(2f));
            AudioVisualUtils.particleLine(world, attacker.getEyePos(), attacker.getYaw(), attacker.getPitch(), UNIQUE_CONFIG.matterbane.range, new DustParticleEffect(color,2), 0.25d, 1, 0d, 0d);

            AttackUtils.lineAttack(attacker, attacker.getEyePos(), attacker.getYaw(), attacker.getPitch(), UNIQUE_CONFIG.matterbane.range, 0.25f, AttackUtils.AttackTarget.ENEMIES)
                    .forceDamage(UNIQUE_CONFIG.matterbane.damage, attacker.getDamageSources().indirectMagic(attacker, attacker));
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.ASH);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip2", UNIQUE_CONFIG.matterbane.range).setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip5").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip9").setStyle(Styles.TEXT));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MATTERBANE));
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
