package com.periut.chiseler.fabric;

import com.periut.chiseler.ChiselerBlockEntity;
import com.periut.chiseler.EnergyStorage;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class FabricChiselerBlockEntity extends ChiselerBlockEntity {
    public FabricChiselerBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    protected EnergyStorage createEnergyStorage() {
        return new FabricEnergyStorage(1000, 50, 0) {
            @Override
            protected void onFinalCommit() {
                markDirty();
            }
        };
    }
}
