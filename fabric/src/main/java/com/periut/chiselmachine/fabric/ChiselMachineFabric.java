package com.periut.chiselmachine.fabric;

import com.periut.chiselmachine.ChiselMachine;
import com.periut.chiselmachine.ChiselMachineBlocks;
import com.periut.chiselmachine.ChiselerBlockEntity;
import com.periut.chiselmachine.ChiselerScreenHandler;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import team.reborn.energy.api.EnergyStorage;

import static com.periut.chiselmachine.ChiselMachine.CHISELER_ID;

public final class ChiselMachineFabric implements ModInitializer {

    public static final BlockEntityType<ChiselerBlockEntity> CHISELER_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, CHISELER_ID, FabricBlockEntityTypeBuilder.<ChiselerBlockEntity>create(FabricChiselerBlockEntity::new, ChiselMachineBlocks.CHISELER).build());;


    public static final ScreenHandlerType<ChiselerScreenHandler> CHISELER_SCREEN_HANDLER = new ScreenHandlerType<>(ChiselerScreenHandler::new, FeatureSet.empty());
    @Override
    public void onInitialize() {
        ChiselMachine.init();
        Registry.register(Registries.SCREEN_HANDLER, CHISELER_ID, CHISELER_SCREEN_HANDLER);
        EnergyStorage.SIDED.registerForBlockEntity((chiselerBlockEntity, direction) -> (EnergyStorage) chiselerBlockEntity.energyStorage, CHISELER_BLOCK_ENTITY);
    }
}
