package com.periut.chiseler;

public interface EnergyStorage {
    void setEnergy(long amount);
    void insertEnergy(long amount);
    long getEnergy();
    long getMaxEnergy();
}
