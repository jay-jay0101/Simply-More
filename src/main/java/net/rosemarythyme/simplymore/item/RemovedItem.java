package net.rosemarythyme.simplymore.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;

public class RemovedItem extends Item {

    Item updatedItem;

    public RemovedItem(Settings settings, Item updatedItem) {
        super(settings);
        this.updatedItem = updatedItem;

    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {
            NbtCompound nbt = user.getStackInHand(hand).getNbt();
            ItemStack newItem = updatedItem.getDefaultStack();
            nbt = nbt == null? new NbtCompound():nbt.copy();

            newItem.setNbt(nbt);

            user.setStackInHand(hand, newItem);
        }
        return super.use(world, user, hand);
    }

    @Override
    public Text getName(ItemStack stack) {
        Style RED = Style.EMPTY.withColor(Formatting.RED);
        return Text.translatable("item.simplymore.removed_item").setStyle(RED);
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.removed_item.tooltip1").formatted(Formatting.GRAY, Formatting.ITALIC));
        tooltip.add(Text.translatable("item.simplymore.removed_item.tooltip2").formatted(Formatting.GRAY, Formatting.ITALIC));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.removed_item.tooltip3").formatted(Formatting.GRAY, Formatting.ITALIC));
        tooltip.add(Text.translatable( updatedItem.getTranslationKey() ).formatted(Formatting.YELLOW));
    }

}