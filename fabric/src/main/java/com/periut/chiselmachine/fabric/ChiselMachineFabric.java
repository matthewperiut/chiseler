package com.periut.chiselmachine.fabric;

import com.periut.chiselmachine.*;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import static com.periut.chiselmachine.ChiselMachine.CHISELER_ID;

public final class ChiselMachineFabric implements ModInitializer {

    public static final BlockEntityType<ChiselerBlockEntity> COUNTER_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, CHISELER_ID, FabricBlockEntityTypeBuilder.<ChiselerBlockEntity>create(ChiselerBlockEntity::new, ChiselMachineBlocks.CHISELER).build());;


    public static final ScreenHandlerType<ChiselerScreenHandler> CHISELER_SCREEN_HANDLER = new ScreenHandlerType<>(ChiselerScreenHandler::new, FeatureSet.empty());
    @Override
    public void onInitialize() {
        ChiselMachine.init();
        Registry.register(Registries.SCREEN_HANDLER, CHISELER_ID, CHISELER_SCREEN_HANDLER);
    }
}
