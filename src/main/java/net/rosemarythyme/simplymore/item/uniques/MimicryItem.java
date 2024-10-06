package net.rosemarythyme.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;


public class MimicryItem extends SimplyMoreUniqueSwordItem {
    private final String PURITY_FORM = "purity";
    private final String TWISTED_FORM = "twisted";
    private final String FORM_STRING = "simplymore:form";
    
    int hitCount;
    int skillCooldown = 20;

    // public List<StatusEffect> positiveEffectsList = Registries.STATUS_EFFECT.stream().filter(StatusEffect::isBeneficial).collect(Collectors.toList());
    // public List<StatusEffect> negativeEffectsList = Registries.STATUS_EFFECT.stream().filter(statusEffect -> !statusEffect.isBeneficial()).collect(Collectors.toList());

    public List<StatusEffect> positiveEffectsList = List.of(
            StatusEffects.SPEED,
            StatusEffects.HASTE,
            StatusEffects.STRENGTH,
            StatusEffects.JUMP_BOOST,
            StatusEffects.REGENERATION,
            StatusEffects.RESISTANCE,
            StatusEffects.FIRE_RESISTANCE,
            StatusEffects.WATER_BREATHING,
            StatusEffects.INVISIBILITY,
            StatusEffects.NIGHT_VISION,
            StatusEffects.HEALTH_BOOST,
            StatusEffects.ABSORPTION,
            StatusEffects.LUCK,
            StatusEffects.SLOW_FALLING,
            StatusEffects.CONDUIT_POWER);

    public List<StatusEffect> negativeEffectsList = List.of(
            StatusEffects.SLOWNESS,
            StatusEffects.MINING_FATIGUE,
            StatusEffects.NAUSEA,
            StatusEffects.BLINDNESS,
            StatusEffects.HUNGER,
            StatusEffects.WEAKNESS,
            StatusEffects.POISON,
            StatusEffects.WITHER,
            StatusEffects.UNLUCK,
            StatusEffects.DARKNESS);

    public MimicryItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }


    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            this.hitCount++;

            NbtElement form = stack.getOrCreateNbt().get(FORM_STRING);
            if (form != null) {
                String formString = form.toString();
                if (hitCount % effect.getPurityHitsNeeded() == 0 && formString.equals("\"" + PURITY_FORM + "\"")) {
                    attacker.addStatusEffect(
                            new StatusEffectInstance(
                                    positiveEffectsList.get(target.getRandom().nextInt(positiveEffectsList.size())),
                                    effect.getPurityEffectTime(),
                                    0));
                } else if (hitCount % effect.getTwistedHitsNeeded() == 0 && formString.equals("\"" + TWISTED_FORM + "\"")) {
                    attacker.addStatusEffect(
                            new StatusEffectInstance(
                                    negativeEffectsList.get(target.getRandom().nextInt(negativeEffectsList.size())),
                                    effect.getTwistedEffectTime(),
                                    0));
                }
            }
        }
        return super.postHit(stack, target, attacker);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {
            this.hitCount = 0;
            NbtElement form = user.getStackInHand(hand).getOrCreateNbt().get(FORM_STRING);
            if (form == null) return TypedActionResult.fail(user.getStackInHand(hand));
            String formToString = form.toString();
            ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.ELECTRIC_SPARK, user.getX(), user.getY() + 0.5, user.getZ(), 50, 0.5, 0.5, 0.5, 0.25);
            user.getWorld().playSound(null, user.getBlockPos(), SoundRegistry.MAGIC_BOW_CHARGE_SHORT_VERSION.get(), user.getSoundCategory(), 2F, 1F);

            if (formToString.equals("\"" + TWISTED_FORM + "\"")) {
                user.getStackInHand(hand).getOrCreateNbt().putString(FORM_STRING, PURITY_FORM);
            } else {
                user.getStackInHand(hand).getOrCreateNbt().putString(FORM_STRING, TWISTED_FORM);
            }
            user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
        }
        return super.use(world, user, hand);
    }

    int stepMod = 0;

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        NbtElement form = stack.getOrCreateNbt().get(FORM_STRING);

        if (form == null || !(form.toString().equals("\"" + PURITY_FORM + "\"")
                || form.toString().equals("\"" + TWISTED_FORM + "\""))) {
            stack.getOrCreateNbt().putString(FORM_STRING, PURITY_FORM);
        }

        if (selected && entity instanceof PlayerEntity playerEntity) {
            String formString = form != null ? form.toString() : PURITY_FORM;
            if (formString.equals("\"" + TWISTED_FORM + "\"")) {
                playerEntity.addStatusEffect(
                        new StatusEffectInstance(
                                ModEffectsRegistry.MIMICRY,
                                9999999,
                                0,
                                true,
                                false,
                                false));
            } else {
                playerEntity.removeStatusEffect(ModEffectsRegistry.MIMICRY);
            }
        }

        stepMod = SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.ENCHANT);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style rightClickStyle = HelperMethods.getStyle("rightclick");
        Style abilityStyle = HelperMethods.getStyle("ability");
        Style textStyle = HelperMethods.getStyle("text");
        Style formStyle = Style.EMPTY.withColor(TextColor.fromRgb(16438297));

        NbtElement form = itemStack.getOrCreateNbt().get(FORM_STRING);
        String formString = form != null ? form.toString().replaceAll("\"","") : "purity";

        int hitsRequired = formString.equals("purity") ?
                effect.getPurityHitsNeeded():
                effect.getTwistedHitsNeeded();

        tooltip.add(Text.translatable("item.simplymore.mimicry_" + formString + ".tooltip1").setStyle(formStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.mimicry.tooltip2").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.mimicry_" + formString + ".tooltip3",hitsRequired).setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.mimicry_" + formString + ".tooltip4").setStyle(textStyle));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
}
