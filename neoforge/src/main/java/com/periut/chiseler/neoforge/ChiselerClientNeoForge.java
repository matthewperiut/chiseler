package com.periut.chiseler.neoforge;

import com.periut.chiseler.Chiseler;
import com.periut.chiseler.ChiselerScreen;
import net.minecraft.block.Block;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import static com.periut.chiseler.neoforge.ChiselerNeoForge.CHISELER_BLOCK;
import static com.periut.chiseler.neoforge.ChiselerNeoForge.CHISELER_SCREEN_HANDLER;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = Chiseler.MOD_ID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = Chiseler.MOD_ID, value = Dist.CLIENT)
public class ChiselerClientNeoForge {
    public ChiselerClientNeoForge(ModContainer container) {

    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        RenderLayers.setRenderLayer(CHISELER_BLOCK.get(), BlockRenderLayer.CUTOUT);
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(CHISELER_SCREEN_HANDLER.get(), ChiselerScreen::new);
    }
}
