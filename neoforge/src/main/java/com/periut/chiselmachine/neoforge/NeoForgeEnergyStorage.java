package com.periut.chiselmachine.neoforge;

import com.periut.chiselmachine.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class NeoForgeEnergyStorage implements IEnergyStorage, EnergyStorage {
    private int energy;
    private final int capacity;

    public NeoForgeEnergyStorage(int capacity) {
        this.capacity = capacity;
        this.energy = 0;
    }

    @Override
    public int getEnergyStored() {
        return energy;
    }

    @Override
    public int getMaxEnergyStored() {
        return capacity;
    }

    @Override
    public boolean canReceive() {
        return true;  // Accept energy from external sources
    }

    @Override
    public boolean canExtract() {
        return false; // Chiseler is not an energy source, so no extraction
    }

    @Override
    public int receiveEnergy(int amount, boolean simulate) {
        // Calculate how much can be received (don't exceed capacity)
        int space = capacity - energy;
        int energyReceived = Math.min(space, amount);
        if (!simulate) {
            energy += energyReceived;
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(int amount, boolean simulate) {
        // Disallow external extraction
        return 0;
    }

    @Override
    public void setEnergy(long amount) {
        this.energy = (int) amount;
    }

    @Override
    public void insertEnergy(long amount) {
        this.energy += (int) amount;
    }

    @Override
    public long getEnergy() {
        return energy;
    }

    @Override
    public long getMaxEnergy() {
        return capacity;
    }
}
