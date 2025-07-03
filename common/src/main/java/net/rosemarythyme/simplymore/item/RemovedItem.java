package net.rosemarythyme.simplymore.item;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class RemovedItem extends Item {

    RegistrySupplier<Item> updatedItem;

    public RemovedItem(Settings settings, RegistrySupplier<Item> updatedItem) {
        super(settings);
        this.updatedItem = updatedItem;

    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {
            ItemStack oldItem = user.getStackInHand(hand);
            ItemStack newItem = oldItem.copyComponentsToNewStack(this.updatedItem.get(), oldItem.getCount());
            user.setStackInHand(hand, newItem);
        }
        return super.use(world, user, hand);
    }

    @Override
    public Text getName(ItemStack stack) {
        Style RED = Style.EMPTY.withColor(Formatting.RED);
        return Text.translatable("item.simplymore.removed_item").setStyle(RED);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType tooltipType) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.removed_item.tooltip1").formatted(Formatting.GRAY, Formatting.ITALIC));
        tooltip.add(Text.translatable("item.simplymore.removed_item.tooltip2").formatted(Formatting.GRAY, Formatting.ITALIC));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.removed_item.tooltip3").formatted(Formatting.GRAY, Formatting.ITALIC));
        tooltip.add(Text.translatable( updatedItem.get().getTranslationKey() ).formatted(Formatting.YELLOW));
    }

}