package net.rosemarythyme.simplymore.item.uniques.idols;

import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedSet;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.interfaces.LegendaryItem;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.ConfigUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class HolylightItem extends AscendedIdolItem implements LegendaryItem {

    public HolylightItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient) return super.use(world, user, hand);

        AttackUtils.cubeAttack(user, user.getPos(), 10, AttackUtils.AttackTarget.ALLIES_AND_USER)
                .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.BLESSING), 100, 0);

        AudioVisualUtils.playSound(world,user.getPos(), new Sound(SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON).setPitch(1.5f));
        AudioVisualUtils.particleAroundEntity(user, ParticleTypes.WAX_OFF, 50, 0.25d, 0.1f);

        user.getItemCooldownManager().set(this, UNIQUE_CONFIG.holylight.cooldown);

        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.holylight.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.holylight.tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.holylight.tooltip6",
                UNIQUE_CONFIG.holylight.blessingHeal/2).setStyle(Styles.TEXT));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }


    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.ASH);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.HOLYLIGHT));
        }

        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.15f;
        @ValidatedFloat.Restrict(min = 0f)
        public float blessingHeal = 4f;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 800;
        public boolean includeGlobalBlacklist = true;
        public ValidatedSet<Identifier> blacklist = ConfigUtils.createEffectList(
                Identifier.of("simplyswords:battle_fatigue")
        );
    }
}
