package com.aylanj123.usefulladders;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class Helpers {

    public static RecursiveResult placeLadder(BlockPos pos, Level level, BlockState state, Direction direction) {
        BlockPos newPos = pos.relative(direction);
        BlockState newPosState = level.getBlockState(newPos);
        if (
                newPosState.getBlock() != Blocks.LADDER ||
                newPosState.getValue(LadderBlock.FACING) != state.getValue(LadderBlock.FACING)
        ) {
            boolean waterlogged = newPosState.getBlock() == Blocks.WATER;
            if ((
                    newPosState == Blocks.AIR.defaultBlockState()
                    || waterlogged
                )
                    && Blocks.LADDER.canSurvive(state, level, newPos)
            ) {
                state = waterlogged ?
                    state.setValue(LadderBlock.WATERLOGGED, true) :
                    state.setValue(LadderBlock.WATERLOGGED, false);
                level.setBlock(newPos, state, Block.UPDATE_ALL_IMMEDIATE);
                level.playSound(null, pos, SoundEvents.LADDER_PLACE, SoundSource.BLOCKS, 1, 0.8f);
                if (waterlogged) level.scheduleTick(pos, Fluids.WATER, 1);
                return new RecursiveResult(newPos, true);
            } else return new RecursiveResult(false);
        } else return placeLadder(newPos, level, state, direction);
    }

    public static class RecursiveResult {
        public final BlockPos finalPos;
        public final boolean success;

        public RecursiveResult(BlockPos finalPos, boolean success) {
            this.finalPos = finalPos;
            this.success = success;
        }
        public RecursiveResult(boolean success) {
            this.finalPos = BlockPos.ZERO;
            this.success = success;
        }
    }

}
