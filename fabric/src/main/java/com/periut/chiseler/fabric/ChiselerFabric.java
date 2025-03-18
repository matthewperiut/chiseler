package com.periut.chiseler.fabric;

import com.periut.chisel.Chisel;
import com.periut.chisel.item.ChiselItem;
import com.periut.chisel.registry.ItemGroupRegistry;
import com.periut.chiseler.Chiseler;
import com.periut.chiseler.ChiselerBlocks;
import com.periut.chiseler.ChiselerBlockEntity;
import com.periut.chiseler.ChiselerScreenHandler;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import team.reborn.energy.api.EnergyStorage;

import static com.periut.chiseler.Chiseler.CHISELER_ID;

public final class ChiselerFabric implements ModInitializer {

    public static final BlockEntityType<ChiselerBlockEntity> CHISELER_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, CHISELER_ID, FabricBlockEntityTypeBuilder.<ChiselerBlockEntity>create(FabricChiselerBlockEntity::new, ChiselerBlocks.CHISELER).build());;


    public static final ScreenHandlerType<ChiselerScreenHandler> CHISELER_SCREEN_HANDLER = new ScreenHandlerType<>(ChiselerScreenHandler::new, FeatureSet.empty());
    @Override
    public void onInitialize() {
        Chiseler.init();
        Registry.register(Registries.SCREEN_HANDLER, CHISELER_ID, CHISELER_SCREEN_HANDLER);
        EnergyStorage.SIDED.registerForBlockEntity((chiselerBlockEntity, direction) -> (EnergyStorage) chiselerBlockEntity.energyStorage, CHISELER_BLOCK_ENTITY);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(content -> {
            content.add(ChiselerBlocks.CHISELER);
        });
    }
}
