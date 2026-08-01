package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;
import org.joml.Vector3f;

import java.util.List;


public class BoasFangItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = UNIQUE_CONFIG.boas_fang.cooldown;

    public BoasFangItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if (MathUtils.chance(attacker, UNIQUE_CONFIG.boas_fang.chance)) {
                target.addStatusEffect(new StatusEffectInstance(StatusEffectRegistry.getReference(StatusEffectRegistry.SUFFOCATION), UNIQUE_CONFIG.boas_fang.suffocationTime));
            }
        }
        return super.postHit(stack, target, attacker);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {

            for (int i = 0; i<10;i++) {
                float yaw = (float) Math.toRadians(user.getYaw()+90 + user.getRandom().nextInt(61) - 30);
                float pitch = (float) Math.toRadians(user.getPitch() + user.getRandom().nextInt(61) - 30);

                float velocityX = (float) (Math.cos(yaw) * Math.cos(pitch)) * 0.5f;
                float velocityZ = (float) (Math.sin(yaw) * Math.cos(pitch)) * 0.5f;
                float velocityY = (float) Math.sin(pitch) * -0.5f;

                user.getWorld().playSound(null,user.getBlockPos(), SoundEvents.ENTITY_LLAMA_SPIT,SoundCategory.PLAYERS,1f,0f);

                for(int j = 0; j<14;j++) {
                    double dX = velocityX * j;
                    double dY = velocityY * j;
                    double dZ = velocityZ * j;

                    double x = user.getX();
                    double y = user.getEyeY();
                    double z = user.getZ();

                    DustParticleEffect particleEffect = new DustParticleEffect(new Vector3f(0.05f,1f,0.1f),1);

                    ((ServerWorld) user.getWorld()).spawnParticles(particleEffect,x+dX,y+dY,z+dZ,1,0,0,0,0);

                    Box box = MathUtils.createCubeBox(user.getEyePos().add(dX, dY, dZ), 0.25);
                    List<LivingEntity> targets = AttackUtils.cuboidAttack(user, box);
                    for (LivingEntity target : targets) {
                        if (target.isBlocking()) continue;

                        target.damage(user.getDamageSources().magic(), UNIQUE_CONFIG.boas_fang.spitDamage);
                        target.setVelocity(velocityX/2,velocityY/2,velocityZ/2);
                        target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, UNIQUE_CONFIG.boas_fang.poisonTime,1));
                    }
                }
                user.setVelocity(user.getRotationVector().negate().multiply(UNIQUE_CONFIG.boas_fang.spitPushback));
                user.setVelocity(user.getVelocity().x, 0.0, user.getVelocity().z);
                user.velocityModified = true;
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, UNIQUE_CONFIG.boas_fang.spitSpeedTime,1));
            }


            user.getItemCooldownManager().set(this, skillCooldown);
        }
        return super.use(world, user, hand);
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.SPORE_BLOSSOM_AIR);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.boas_fang.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.boas_fang.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.boas_fang.tooltip5").setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.BOAS_FANG));
        }

        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.4f;
        @ValidatedInt.Restrict(min = 0)
        public int suffocationTime = 100;
        @ValidatedInt.Restrict(min = 0)
        public int spitSpeedTime = 80;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 600;
        @ValidatedInt.Restrict(min = 0)
        public int poisonTime = 80;
        @ValidatedFloat.Restrict(min = 0f)
        public float spitDamage = 5f;
        @ValidatedFloat.Restrict(min = 0f)
        public float spitPushback = 2f;
    }
}
