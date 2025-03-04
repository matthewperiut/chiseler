package com.periut.chiselmachine.neoforge;

import net.neoforged.fml.common.Mod;

import com.periut.chiselmachine.ChiselMachine;

@Mod(ChiselMachine.MOD_ID)
public final class ExampleModNeoForge {
    public ExampleModNeoForge() {
        // Run our common setup.
        ChiselMachine.init();
    }
}
