package com.periut.chiselmachine;

public interface EnergyStorage {
    void setEnergy(long amount);
    void insertEnergy(long amount);
    long getEnergy();
    long getMaxEnergy();
}
