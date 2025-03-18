package com.periut.chiseler.neoforge;

import com.periut.chiseler.ChiselerBlockEntity;
import com.periut.chiseler.EnergyStorage;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class NeoForgeChiselerBlockEntity extends ChiselerBlockEntity {
    public NeoForgeChiselerBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    protected EnergyStorage createEnergyStorage() {
        return new NeoForgeEnergyStorage(1000);
    }

    public IEnergyStorage getEnergyStorage() {
        return (IEnergyStorage) energyStorage;
    }
}
