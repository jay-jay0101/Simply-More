package net.rosemarythyme.simplymore.mixin;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.item.components.ReformTypeComponent;
import net.rosemarythyme.simplymore.registry.item.ItemComponentRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.DataUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.sweenus.simplyswords.item.ContainedRemnantItem;
import net.sweenus.simplyswords.registry.SoundRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collections;

@Mixin(ContainedRemnantItem.class)
public class ContainedRemnantItemMixin {
    @Inject(method = "addTransformation", at = @At("HEAD"))
    private static void simplymore$addTransformation(Block block, Identifier identifier, CallbackInfo ci) {
        if(DataUtils.TRANSFORMATIONS.containsKey(block)) {
            DataUtils.TRANSFORMATIONS.get(block).add(identifier);
        } else {
            DataUtils.TRANSFORMATIONS.put(block, new ArrayList<>(Collections.singleton(identifier)));
        }
    }

    @Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Ldev/architectury/registry/registries/DeferredRegister;getRegistrar()Ldev/architectury/registry/registries/Registrar;", ordinal = 0), cancellable = true)
    private void simplymore$createReformingRemnant(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        if(!(context.getPlayer() instanceof PlayerEntity player)) return;

        Block block = context.getWorld().getBlockState(context.getBlockPos()).getBlock();
        if(!DataUtils.TRANSFORMATIONS.containsKey(block)) return;
        if(DataUtils.TRANSFORMATIONS.get(block).size() <= 1) return;

        ItemStack newItem = new ItemStack(ItemRegistry.REFORMING_REMNANT);
        newItem.set(ItemComponentRegistry.REFORM.get(), new ReformTypeComponent(Registries.BLOCK.getId(block)));

        context.getStack().decrement(1);

        AudioVisualUtils.particleRing((ServerWorld) context.getWorld(), player.getPos(), ParticleTypes.CAMPFIRE_COSY_SMOKE, 1f, 6);
        AudioVisualUtils.playSound(player.getWorld(), player.getPos(), new Sound(SoundRegistry.DARK_ACTIVATION_DISTORTED.get(), 0.4f, 1.8f));

        player.dropItem(newItem, false);
        cir.setReturnValue(ActionResult.CONSUME);
    }
}
