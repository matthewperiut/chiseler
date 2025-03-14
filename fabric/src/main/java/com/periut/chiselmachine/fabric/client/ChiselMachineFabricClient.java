package com.periut.chiselmachine.fabric.client;

import com.periut.chiselmachine.ChiselMachineBlocks;
import com.periut.chiselmachine.ChiselerScreen;
import com.periut.chiselmachine.fabric.ChiselMachineFabric;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;

public final class ChiselMachineFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        HandledScreens.register(ChiselMachineFabric.CHISELER_SCREEN_HANDLER, ChiselerScreen::new);
        BlockRenderLayerMap.INSTANCE.putBlock(ChiselMachineBlocks.CHISELER, RenderLayer.getCutout());
    }
}
