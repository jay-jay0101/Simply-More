package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.CrowEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.item.components.CounterComponent;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class DeathsEyrieItem extends SimplyMoreUniqueSwordItem implements TwoHandedWeapon {

    @Override
    public CounterComponent getDefaultCounterComponent() {
        return new CounterComponent(0, 5);
    }

    public static final int MAX_CROWS = 5;


    public DeathsEyrieItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }


    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(attacker.getWorld().isClient) return super.postHit(stack, target, attacker);
        if(!(attacker instanceof  PlayerEntity player) || player.getItemCooldownManager().isCoolingDown(this)) return super.postHit(stack, target, attacker);

        if (MathUtils.chance(attacker, UNIQUE_CONFIG.deaths_eyrie.chance)) {
            int crows = MathUtils.getCounterComponent(stack).value();

            int effectTime = UNIQUE_CONFIG.deaths_eyrie.baseBleedTime;
            effectTime += UNIQUE_CONFIG.deaths_eyrie.additionalBleedTime * crows;
            int amplifier = (int) Math.floor(0.75f * (crows - 1));

            target.addStatusEffect(new StatusEffectInstance(StatusEffectRegistry.getReference(StatusEffectRegistry.WOUNDED), effectTime, amplifier));

            MathUtils.addToCounterComponent(stack, 1);

            AudioVisualUtils.playSound(attacker.getWorld(), attacker.getPos(), new Sound(SoundRegistry.DARK_SWORD_ENCHANT.get()));
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.getWorld().isClient) return super.use(world, user, hand);

        LivingEntity target = AttackUtils.getTargetedEntity(user, UNIQUE_CONFIG.culterex.range, AttackUtils.AttackTarget.ENEMIES);
        if(target == null) return super.use(world, user, hand);

        AudioVisualUtils.targetIndicator(target);

        TargetList crows = AttackUtils.cubeAttack(user, user.getPos(), 50, AttackUtils.AttackTarget.ALLIES)
                .filterByType(CrowEntity.class)
                .filterByOwnedBy(user);

        int attackTime = UNIQUE_CONFIG.deaths_eyrie.crowAttackTimePerCrow * crows.size();

        crows.onEachEnumerated((offset, entity) -> {
            CrowEntity crow = (CrowEntity) entity;
            crow.setAttackingUuid(target.getUuid());
            crow.setAttackingTime(offset + attackTime);
        });

        if(crows.isPopulated()) {
            MathUtils.setCounterComponentValue(user.getStackInHand(hand), 1);
            user.getItemCooldownManager().set(this, UNIQUE_CONFIG.deaths_eyrie.cooldown);
        }

        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient) return;
        if (!(entity instanceof PlayerEntity player)) return;
        if (!EntityUtils.isHolding(player, stack)) return;
        if (player.getItemCooldownManager().isCoolingDown(this)) return;

        int crowStacks = MathUtils.getCounterComponent(stack).value();

        TargetList crows = AttackUtils.cubeAttack(player, player.getPos(), 50, AttackUtils.AttackTarget.ALLIES)
                .filterByType(CrowEntity.class)
                .filterByOwnedBy(player);

        if(crows.size() > crowStacks) {
            crows.kill();
        } else if (crows.size() < crowStacks) {
            int crowsToAdd = crowStacks - crows.size();

            for (int i = 0; i < crowsToAdd; i++) {
                EntityUtils.spawnAround(world, new CrowEntity(player, world), player.getEyePos().add(0d, 1d, 0d), 1.5d, 0d);
            }
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.WARPED_SPORE);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.deaths_eyrie.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.deaths_eyrie.tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplymore.deaths_eyrie.tooltip3").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.deaths_eyrie.tooltip6").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplymore.deaths_eyrie.tooltip9").setStyle(Styles.TEXT));

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
        public float crowHeal = 0.3f;
    }
}
