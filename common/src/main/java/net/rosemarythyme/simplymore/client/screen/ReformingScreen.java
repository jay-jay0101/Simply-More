package net.rosemarythyme.simplymore.client.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.rosemarythyme.simplymore.screen.ReformingScreenHandler;

public class ReformingScreen extends HandledScreen<ReformingScreenHandler> {
    private ButtonWidget nextButton;
    private ButtonWidget prevButton;
    private ButtonWidget acceptButton;

    private static final int ARROW_SIZE = 20;
    private static final int BUTTON_WIDTH = 80;
    private static final int SPACING = 4;
    private static final int SPRITE_SIZE = 100;

    public ReformingScreen(ReformingScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int halfSize = SPRITE_SIZE / 2;
        int centerX = this.x + this.backgroundWidth / 2;
        int centerY = this.y + this.backgroundHeight / 2;

        MatrixStack stack = context.getMatrices();
        stack.push();

        float scale = SPRITE_SIZE / 16f;
        stack.translate(centerX, centerY, 0);
        stack.scale(scale, scale, scale);

        context.setShaderColor(0x00, 0x00, 0x00, 0xFF);
        context.drawItem(handler.getStack(), -8, -8);
        context.setShaderColor(0xFF, 0xFF, 0xFF, 0xFF);

        stack.pop();
    }

    @Override
    protected void init() {
        super.init();

        int centerX = this.x + this.backgroundWidth / 2;
        int halfButton = BUTTON_WIDTH / 2;
        int buttonLine = this.y + this.backgroundHeight / 2 + (SPRITE_SIZE / 2) + 20;

        prevButton = ButtonWidget.builder(
                Text.literal("←"),
                handler::prev
        ).dimensions(centerX - halfButton - SPACING - ARROW_SIZE, buttonLine, ARROW_SIZE, ARROW_SIZE)
        .build();

        acceptButton = ButtonWidget.builder(
                Text.translatable("screen.simplymore.reforming_remnant.accept"),
                this::accept
        ).dimensions(centerX - halfButton, buttonLine, BUTTON_WIDTH, ARROW_SIZE)
        .build();

        nextButton = ButtonWidget.builder(
                        Text.literal("→"),
                        handler::next
                ).dimensions(centerX + halfButton + SPACING, buttonLine, ARROW_SIZE, ARROW_SIZE)
                .build();

        this.addDrawableChild(nextButton);
        this.addDrawableChild(prevButton);
        this.addDrawableChild(acceptButton);
    }

    public void accept(ButtonWidget button) {
        handler.accept();
        this.close();
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {}
}
