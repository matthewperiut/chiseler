package com.periut.chiseler.neoforge;

import com.periut.chiseler.ChiselerBlock;
import com.periut.chiseler.ChiselerScreen;
import com.periut.chiseler.ChiselerScreenHandler;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

import com.periut.chiseler.Chiseler;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.periut.chiseler.Chiseler.CHISELER_ID;

@Mod(Chiseler.MOD_ID)
public final class ChiselerNeoForge {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Chiseler.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Chiseler.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Chiseler.MOD_ID);
    public static final DeferredRegister<ScreenHandlerType<?>> SCREEN_HANDLER_TYPES = DeferredRegister.create(Registries.SCREEN_HANDLER, Chiseler.MOD_ID);

    public static final Supplier<ScreenHandlerType<ChiselerScreenHandler>> CHISELER_SCREEN_HANDLER = SCREEN_HANDLER_TYPES.register("chiseler", () -> new ScreenHandlerType<>(ChiselerScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static final DeferredBlock<Block> CHISELER_BLOCK = BLOCKS.register("chiseler", registryName -> new ChiselerBlock(AbstractBlock.Settings.create().hardness(3.F).resistance(3.F).registryKey(RegistryKey.of(RegistryKeys.BLOCK, CHISELER_ID))));
    public static final DeferredItem<BlockItem> CHISELER_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("chiseler", CHISELER_BLOCK);

    public static final Supplier<BlockEntityType<NeoForgeChiselerBlockEntity>> CHISELER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "chiseler",
            // The block entity type.
            () -> new BlockEntityType<>(
                    // The supplier to use for constructing the block entity instances.
                    NeoForgeChiselerBlockEntity::new,
                    // A vararg of blocks that can have this block entity.
                    // This assumes the existence of the referenced blocks as DeferredBlock<Block>s.
                    CHISELER_BLOCK.get()
            )
    );

    public ChiselerNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        Chiseler.init();

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITY_TYPES.register(modEventBus);
        SCREEN_HANDLER_TYPES.register(modEventBus);

        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(this::buildContents);
    }


    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,           // The energy storage capability for blocks
                CHISELER_BLOCK_ENTITY.get(),  // Our chiseler BlockEntity type
                (blockEntity, side) -> {
                    // Provide the chiseler's IEnergyStorage instance (same for any side)
                    NeoForgeChiselerBlockEntity chiseler = (NeoForgeChiselerBlockEntity) blockEntity;
                    return chiseler.getEnergyStorage();
                }
        );
    }

    public void buildContents(BuildCreativeModeTabContentsEvent event) {
        // Is this the tab we want to add to?
        if (event.getTabKey() == ItemGroups.REDSTONE) {
            event.add(CHISELER_BLOCK_ITEM);
        }
    }
}