package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble;
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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.BlizzardEntity;
import net.rosemarythyme.simplymore.entity.IcewallEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.sweenus.simplyswords.api.WeaponAbilityActivationSource;
import net.sweenus.simplyswords.api.WeaponAbilityContext;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;
import net.sweenus.simplyswords.item.interfaces.UniqueWeaponActiveAbility;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class GrandfrostItem extends SimplyMoreUniqueSwordItem implements TwoHandedWeapon, UniqueWeaponActiveAbility {
    public static GrandfrostItem.EffectSettings SETTINGS = UNIQUE_CONFIG.grandfrost;
    public GrandfrostItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, ServerWorld world, int consecutiveHits, boolean isFirstInTick) {
        if (target.isBlocking() || MathUtils.chance(attacker, SETTINGS.chance)) {
            new TargetList(target)
                    .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.CHILL), SETTINGS.chillTime, 0)
                    .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.STUN), SETTINGS.stunTime, 0)
                    .applyEffect(StatusEffects.SLOWNESS, SETTINGS.chillTime, 2);

            AudioVisualUtils.playSound(world, target.getPos(), new Sound(SoundEvents.ENTITY_PLAYER_HURT_FREEZE));
            AudioVisualUtils.particleAroundEntity(target, ParticleTypes.SNOWFLAKE, 40, 0.5f, 0);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return useFromDefaultInput(world, user, hand);
    }

    @Override
    public boolean canActivate(WeaponAbilityContext context) {
        return context.actor().isAlive();
    }

    @Override
    public boolean activate(WeaponAbilityContext context) {
        List<BlizzardEntity> blizzards = AttackUtils.getOwnedAbilities(context.actor(), BlizzardEntity.class);

        if(blizzards.isEmpty()) {
            if(!AttackUtils.spawnAbility(new BlizzardEntity(context.actor(), context.actor().getPos()), context.actor(), true)) return false;
            AudioVisualUtils.playSound(context.world(), context.origin(), new Sound(SoundRegistry.ELEMENTAL_SWORD_ICE_ATTACK_03.get()));

            int walls = (int) Math.ceil(SETTINGS.radius * Math.PI);
            float arc = 360f / walls;

            for(int i = 0; i < walls; i++) {
                float yaw = arc * i;
                Vec3d delta = MathUtils.getDirectionalVector(yaw, 0).multiply(SETTINGS.radius);

                AttackUtils.spawnAbility(new IcewallEntity(context.actor(), context.origin().add(delta)), context.actor(), true);
            }

            return context.activationSource() != WeaponAbilityActivationSource.PLAYER;
        }

        List<IcewallEntity> walls = AttackUtils.getOwnedAbilities(context.actor(), IcewallEntity.class);

        blizzards.forEach(Entity::discard);
        walls.forEach(IcewallEntity::lower);

        AudioVisualUtils.playSound(context.world(), context.origin(), new Sound(SoundRegistry.ELEMENTAL_SWORD_ICE_ATTACK_01.get()));

        return true;
    }

    @Override
    public int getActivationCooldownTicks(ItemStack stack, WeaponAbilityContext context) {
        return SETTINGS.cooldown;
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.ITEM_SNOWBALL, ParticleTypes.ITEM_SNOWBALL, ParticleTypes.SNOWFLAKE);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.grandfrost.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.grandfrost.tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.grandfrost.tooltip3").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.grandfrost.tooltip4").setStyle(Styles.TEXT));
        appendAbilityCooldownTooltip(tooltip, itemStack, SETTINGS.cooldown);

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.GRANDFROST));
        }

        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.25f;
        @ValidatedInt.Restrict(min = 0)
        public int chillTime = 240;
        @ValidatedInt.Restrict(min = 0)
        public int stunTime = 10;
        @ValidatedDouble.Restrict(min = 0)
        public double radius = 6.5;
        @ValidatedInt.Restrict(min = 0)
        public int wallDuration = 500;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 500;
        @ValidatedFloat.Restrict(min = 0f)
        public float knockUpDamage = 12f;
        @ValidatedDouble.Restrict(min = 0f)
        public double knockUp = 0.25f;
        @ValidatedFloat.Restrict(min = 0f)
        public float blizzardDamage = 4f;
    }
}
