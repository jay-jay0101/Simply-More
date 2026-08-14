package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.LightOrbEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;
import net.sweenus.simplyswords.item.interfaces.UniqueWeaponActiveAbility;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class LustrousMoxieItem extends SimplyMoreUniqueSwordItem implements TwoHandedWeapon, UniqueWeaponActiveAbility {
    public static LustrousMoxieItem.EffectSettings SETTINGS = UNIQUE_CONFIG.lustrous_moxie;

    public LustrousMoxieItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, ServerWorld world, int consecutiveHits, boolean isFirstInTick) {
        if(MathUtils.chance(attacker, SETTINGS.chance)) {
            List<LightOrbEntity> orbs = AttackUtils.getOwnedAbilities(attacker, LightOrbEntity.class)
                            .stream().filter(orb -> orb.getPerson().isPresent() && orb.getPerson().get().equals(target.getUuid())).toList();

            AudioVisualUtils.playSound(world, target.getPos(), new Sound(SoundRegistry.ELEMENTAL_BOW_HOLY_SHOOT_IMPACT_03.get()));
            AudioVisualUtils.particleAroundEntity(target, ParticleTypes.FLASH, 1, 0, 0);

            if(orbs.size() >= SETTINGS.maxOrbs) {
                orbs.forEach(LightOrbEntity::explode);
                AudioVisualUtils.playSound(world, target.getPos(), new Sound(SoundEvents.ITEM_TRIDENT_THUNDER.value()));
            } else {
                AttackUtils.spawnAbility(new LightOrbEntity(attacker, target.getPos(), target, orbs.size()), attacker);
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return useFromDefaultInput(world, user, hand);
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.WAX_OFF);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip3").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip4").setStyle(Styles.TEXT));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.LUSTROUS_MOXIE));
        }

        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.2f;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 400;
        @ValidatedInt.Restrict(min = 0)
        public int orbDuration = 2400;
        @ValidatedInt.Restrict(min = 0)
        public float maxOrbs = 3;
        @ValidatedFloat.Restrict(min = 0)
        public float explosionDamage = 8;
        @ValidatedInt.Restrict(min = 0)
        public int dazzleDuration = 120;

    }
}
