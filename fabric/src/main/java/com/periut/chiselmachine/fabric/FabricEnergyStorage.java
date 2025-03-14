package com.periut.chiselmachine.fabric;

import com.periut.chiselmachine.EnergyStorage;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public class FabricEnergyStorage extends SimpleEnergyStorage implements EnergyStorage {
    public FabricEnergyStorage(long capacity, long maxInsert, long maxExtract) {
        super(capacity, maxInsert, maxExtract);
    }

    @Override
    public void setEnergy(long amount) {
        this.amount = amount;
    }

    @Override
    public void insertEnergy(long amount) {
        this.amount += amount;
    }

    @Override
    public long getEnergy() {
        return this.amount;
    }

    @Override
    public long getMaxEnergy() {
        return this.capacity;
    }
}
