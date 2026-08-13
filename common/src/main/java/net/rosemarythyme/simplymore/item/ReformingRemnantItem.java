package net.rosemarythyme.simplymore.item;

import dev.architectury.registry.menu.MenuRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.components.ReformTypeComponent;
import net.rosemarythyme.simplymore.registry.item.ItemComponentRegistry;
import net.rosemarythyme.simplymore.screen.ReformingScreenHandler;
import net.rosemarythyme.simplymore.util.DataUtils;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class ReformingRemnantItem extends Item {
    public ReformingRemnantItem() {
        super(new Settings().rarity(Rarity.EPIC).fireproof().maxCount(1));
    }

    public static List<Identifier> getUniques(ItemStack stack) {
        ReformTypeComponent reform = stack.getOrDefault(ItemComponentRegistry.REFORM.get(), ReformTypeComponent.DEFAULT);
        return getUniques(reform.block());
    }

    public static List<Identifier> getUniques(Identifier blockId) {
        Block block = Registries.BLOCK.get(blockId);
        List<Identifier> blocks = DataUtils.TRANSFORMATIONS.get(block);
        if(blocks == null) return List.of();

        return blocks;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(user.getWorld().isClient) return super.use(world, user, hand);

        ItemStack stack = user.getStackInHand(hand);
        ReformTypeComponent reform = stack.getOrDefault(ItemComponentRegistry.REFORM.get(), ReformTypeComponent.DEFAULT);

        if(reform == null) return TypedActionResult.fail(stack);

        SimpleNamedScreenHandlerFactory factory = new SimpleNamedScreenHandlerFactory(
                ((syncId, playerInventory, player1) ->
                        new ReformingScreenHandler(syncId, reform.block(), hand == Hand.MAIN_HAND)),
                Text.literal("test")
        );

        MenuRegistry.openExtendedMenu((ServerPlayerEntity) user, factory, buf -> {
            buf.writeIdentifier(reform.block());
            buf.writeBoolean(hand == Hand.MAIN_HAND);
        });

        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        tooltip.add(Text.translatable("item.simplymore.reforming_remnant.tooltip1").formatted(Formatting.GRAY));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.reforming_remnant.tooltip2").formatted(Formatting.GRAY));
    }

    public Text getName(ItemStack stack) {
        return Text.translatable(this.getTranslationKey(stack)).setStyle(Styles.LEGENDARY);
    }
}
