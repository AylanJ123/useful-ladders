package com.aylanj123.usefulladders;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class Helpers {

    public static void placeLadder(BlockPos pos, Level level, BlockState state, Direction direction) {
        BlockPos newPos = pos.relative(direction);
        BlockState newPosState = level.getBlockState(newPos);
        if (newPosState != state) {
            if (
                    newPosState == Blocks.AIR.defaultBlockState()
                            && Blocks.LADDER.canSurvive(state, level, newPos)
            ) level.setBlock(newPos, state, Block.UPDATE_ALL);
        } else placeLadder(newPos, level, state, direction);
    }

}
