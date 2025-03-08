package com.periut.chiselmachine.fabric.client;

import com.periut.chiselmachine.fabric.ChiselerScreen;
import com.periut.chiselmachine.fabric.ChiselMachineFabric;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public final class ChiselMachineFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        HandledScreens.register(ChiselMachineFabric.CHISELER_SCREEN_HANDLER, ChiselerScreen::new);
    }
}
