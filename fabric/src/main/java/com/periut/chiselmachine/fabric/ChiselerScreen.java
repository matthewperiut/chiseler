package com.periut.chiselmachine.fabric;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.List;
import java.util.Optional;

public class ChiselerScreen extends HandledScreen<ChiselerScreenHandler> {
    // A path to the gui texture. In this example we use the texture from the dispenser
    private static final Identifier BURN_PROGRESS_TEXTURE = Identifier.ofVanilla("container/furnace/burn_progress");
    private static final Identifier TEXTURE = Identifier.of(ChiselMachine.MOD_ID, "textures/gui/container/chiseler.png");
    // For versions before 1.21:
    // private static final Identifier TEXTURE = new Identifier("minecraft", "textures/gui/container/dispenser.png");

    public ChiselerScreen(ChiselerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
//        FurnaceScreen
//        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
//        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        int size = MathHelper.ceil(((ChiselerScreenHandler)this.handler).getCookProgress() * 24.0F);
        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x, y, 0.0F, 0.0F, this.backgroundWidth, this.backgroundHeight, 256, 256);
        context.drawGuiTexture(RenderLayer::getGuiTextured, BURN_PROGRESS_TEXTURE, 24, 16, 0, 0, this.x + 76, this.y + 35, size, 16);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        float energyDiff = (1 - ((float) handler.getEnergy() / handler.getMaxEnergy())) * 48;
        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x + 10, y + 19 + (int)energyDiff, 177F, 0.0F, 12, 48 - (int)energyDiff, 256, 256);

        drawMouseoverTooltip(context, mouseX, mouseY);
        if (mouseX > 134 && mouseX < 147) {
            if (mouseY > 56 && mouseY < 105) {
                context.drawTooltip(this.textRenderer, getEnergyText(), Optional.empty(), mouseX, mouseY, null);
            }
        }
    }

    public List<Text> getEnergyText() {
        return List.of(Text.of(handler.getEnergy() + " RF / " + handler.getMaxEnergy() + " RF"));
    }

    @Override
    protected void init() {
        super.init();
        // Set the title location
        titleX = ((backgroundWidth - textRenderer.getWidth(title)) / 2);
    }
}
