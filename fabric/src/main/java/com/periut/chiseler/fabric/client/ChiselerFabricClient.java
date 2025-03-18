package com.periut.chiseler.fabric.client;

import com.periut.chiseler.ChiselerBlocks;
import com.periut.chiseler.ChiselerScreen;
import com.periut.chiseler.fabric.ChiselerFabric;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;

public final class ChiselerFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        HandledScreens.register(ChiselerFabric.CHISELER_SCREEN_HANDLER, ChiselerScreen::new);
        BlockRenderLayerMap.INSTANCE.putBlock(ChiselerBlocks.CHISELER, RenderLayer.getCutout());
    }
}
