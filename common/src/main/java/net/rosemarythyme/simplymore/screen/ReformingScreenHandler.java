package net.rosemarythyme.simplymore.screen;

import dev.architectury.networking.NetworkManager;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.item.ReformingRemnantItem;
import net.rosemarythyme.simplymore.networking.c2s.C2STransformRemnantPacket;
import net.rosemarythyme.simplymore.registry.ScreenHandlerRegistry;

import java.util.List;

public class ReformingScreenHandler extends ScreenHandler {
    public final Identifier block;
    public final List<Identifier> choises;
    public final boolean isMainHand;
    public int index = 0;

    public ReformingScreenHandler(int syncId, PlayerInventory ignored, PacketByteBuf buf) {
        this(syncId, buf.readIdentifier(), buf.readBoolean());
    }

    public ReformingScreenHandler(int syncId, Identifier block, boolean isMainHand) {
        super(ScreenHandlerRegistry.REFORM.get(), syncId);
        this.block = block;
        this.choises = ReformingRemnantItem.getUniques(block);
        this.isMainHand = isMainHand;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public void next(ButtonWidget button) {
        if(choises.isEmpty()) return;
        index = Math.abs(++index) % choises.size();
    }

    public void prev(ButtonWidget button) {
        if(choises.isEmpty()) return;
        index = Math.abs(--index) % choises.size();
    }

    public void accept() {
        if(choises.isEmpty()) return;

        Identifier item = choises.get(index);
        NetworkManager.sendToServer(new C2STransformRemnantPacket(item, isMainHand));
    }

    public ItemStack getStack() {
        if(this.index < 0) return ItemStack.EMPTY;
        if(this.index >= choises.size()) return ItemStack.EMPTY;

        Item item = Registries.ITEM.get(choises.get(index));
        return new ItemStack(item);
    }
}