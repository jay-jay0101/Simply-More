package net.rosemarythmye.simplymore.item.itemclasses.uniques;

import com.google.common.collect.Multimap;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
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
import net.rosemarythmye.simplymore.effect.ModEffects;
import net.rosemarythmye.simplymore.item.itemclasses.UniqueSword;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;


public class Mimicry extends UniqueSword {

    int hitCount;
    int skillCooldown = 20;

    public StatusEffect[] PositiveEffects = new StatusEffect[]{
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
            StatusEffects.CONDUIT_POWER
    };

    public StatusEffect[] NegativeEffects = new StatusEffect[]{
            StatusEffects.SLOWNESS,
            StatusEffects.MINING_FATIGUE,
            StatusEffects.NAUSEA,
            StatusEffects.BLINDNESS,
            StatusEffects.HUNGER,
            StatusEffects.WEAKNESS,
            StatusEffects.POISON,
            StatusEffects.WITHER,
            StatusEffects.UNLUCK,
            StatusEffects.DARKNESS
    };

    private Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;


    public Mimicry(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            this.hitCount++;

            Object form = stack.getOrCreateNbt().get("simplymore:form");
            if (form==null) super.postHit(stack, target, attacker);
            form = form.toString();

            StatusEffect statusEffect = null;
            if (this.hitCount % 5 == 0 && form.toString().equals("\"purity\"")) {
                statusEffect = PositiveEffects[attacker.getRandom().nextInt(PositiveEffects.length)];
                attacker.addStatusEffect(new StatusEffectInstance(statusEffect,200,1));
            } else if (this.hitCount % 4 == 0 && form.toString().equals("\"twisted\"")) {
                statusEffect = NegativeEffects[attacker.getRandom().nextInt(NegativeEffects.length)];
                target.addStatusEffect(new StatusEffectInstance(statusEffect,200,1));
            }
        }
        return super.postHit(stack, target, attacker);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {
            this.hitCount = 0;
            Object form = user.getStackInHand(hand).getOrCreateNbt().get("simplymore:form");
            if (form==null) return TypedActionResult.fail(user.getStackInHand(hand));
            form = form.toString();
            ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.ELECTRIC_SPARK,user.getX(),user.getY()+0.5,user.getZ(),50,0.5,0.5,0.5,0.25);
            user.getWorld().playSound((PlayerEntity)null, user.getBlockPos(), SoundRegistry.MAGIC_BOW_CHARGE_SHORT_VERSION.get(), user.getSoundCategory(), 2F, 1F);

            if (form.equals("\"twisted\"")) user.getStackInHand(hand).getOrCreateNbt().putString("simplymore:form", "purity");
            else user.getStackInHand(hand).getOrCreateNbt().putString("simplymore:form", "twisted");
            user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
        }
        return super.use(world, user, hand);
    }


    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style RIGHTCLICK = HelperMethods.getStyle("rightclick");
        Style ABILITY = HelperMethods.getStyle("ability");
        Style TEXT = HelperMethods.getStyle("text");
        Style FORM = Style.EMPTY.withColor(TextColor.fromRgb(16438297));

        Object form = itemStack.getOrCreateNbt().get("simplymore:form");
        if (form == null) form = "purity";
        else form = form.toString().replaceAll("\"","");
        tooltip.add(Text.translatable("item.simplymore.mimicry_"+form+".tooltip1").setStyle(FORM));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.mimicry.tooltip2").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.mimicry_"+form+".tooltip3").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(RIGHTCLICK));
        tooltip.add(Text.translatable("item.simplymore.mimicry_"+form+".tooltip4").setStyle(TEXT));


        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }


    private static int stepMod = 0;
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        NbtElement form = stack.getOrCreateNbt().get("simplymore:form");
        if (form == null || !(form.toString().equals("\"purity\"") || form.toString().equals("\"twisted\""))) stack.getOrCreateNbt().putString("simplymore:form","purity");

        form = stack.getOrCreateNbt().get("simplymore:form");

        if (selected && entity instanceof PlayerEntity) {
            if(form.toString().equals("\"twisted\"")) ((PlayerEntity) entity).addStatusEffect(new StatusEffectInstance(ModEffects.MIMICRY,9999999,0,true,false,false));
            else ((PlayerEntity) entity).removeStatusEffect(ModEffects.MIMICRY);
        }

        if (stepMod > 0) {
            --stepMod;
        }

        if (stepMod <= 0) {
            stepMod = 7;
        }

        HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.ENCHANT, ParticleTypes.ENCHANT, ParticleTypes.ENCHANT, true);
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
