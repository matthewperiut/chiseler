package com.periut.chiseler;

import com.periut.cryonicconfig.CryonicConfig;
import net.minecraft.util.Identifier;

public final class Chiseler {
    public static final String MOD_ID = "chiseler";
    public static final Identifier CHISELER_ID = Identifier.of(Chiseler.MOD_ID, "chiseler");
    public static int energyPerBlock = 12;

    public static void init() {
        energyPerBlock = CryonicConfig.getConfig(MOD_ID).getInt("energyPerBlock", energyPerBlock);
    }
}
