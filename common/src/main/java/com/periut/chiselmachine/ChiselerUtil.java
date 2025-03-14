package com.periut.chiselmachine;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class ChiselerUtil {
    @ExpectPlatform
    static public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        throw new AssertionError();
    }
}
