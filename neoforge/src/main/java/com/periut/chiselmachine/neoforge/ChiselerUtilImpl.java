package com.periut.chiselmachine.neoforge;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class ChiselerUtilImpl {
    static public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new NeoForgeChiselerBlockEntity(pos, state);
    }
}
