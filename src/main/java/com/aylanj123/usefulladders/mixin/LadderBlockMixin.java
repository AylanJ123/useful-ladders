package com.aylanj123.usefulladders.mixin;

import com.aylanj123.usefulladders.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.*;

@Mixin(LadderBlock.class)
public abstract class LadderBlockMixin {

    @Shadow @Final public static DirectionProperty FACING;
    @Shadow @Final public static BooleanProperty WATERLOGGED;
    @Shadow protected abstract boolean canAttachTo(BlockGetter pBlockReader, BlockPos pPos, Direction pDirection);

    @Unique
    private static final VoxelShape NEW_EAST_AABB = Block.box(1.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);
    @Unique
    private static final VoxelShape NEW_WEST_AABB = Block.box(13.0D, 0.0D, 0.0D, 15.0D, 16.0D, 16.0D);
    @Unique
    private static final VoxelShape NEW_SOUTH_AABB = Block.box(0.0D, 0.0D, 1.0D, 16.0D, 16.0D, 3.0D);
    @Unique
    private static final VoxelShape NEW_NORTH_AABB = Block.box(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 15.0D);

    /**
     * @author AylanJ123
     * @reason Modified the box of the ladders
     */
    @Overwrite
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            case NORTH -> NEW_NORTH_AABB;
            case SOUTH -> NEW_SOUTH_AABB;
            case WEST -> NEW_WEST_AABB;
            default -> NEW_EAST_AABB;
        };
    }

    /**
     * @author AylanJ123
     * @reason I need to check if support is behind, above or below
     */
    @Overwrite
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        boolean supportBehind = usefulLadders$hasSturdySupport(pState, pLevel, pPos);
        //This next part seems redundant and unnecessary, however, it might improve the
        //performance on many servers depending on the configs, won't affect much on other cases.
        //consider there might be up to 385 iterations per block updated on these ladders
        if (supportBehind) return true;
        if (Config.maxStress == 0 && Config.maxHang == 0) return false;
        if (Config.maxStress == 0) return usefulLadders$checkForHangingSupport(pState, pLevel, pPos);
        if (Config.maxHang == 0) return usefulLadders$checkForStressedSupport(pState, pLevel, pPos);
        if (Config.maxStress == -1)
            return usefulLadders$checkForHangingSupport(pState, pLevel, pPos)
                || usefulLadders$checkForStressedSupport(pState, pLevel, pPos);
        if (Config.maxHang == -1)
            return usefulLadders$checkForStressedSupport(pState, pLevel, pPos)
                || usefulLadders$checkForHangingSupport(pState, pLevel, pPos);
        if (Config.maxStress < Config.maxHang)
            return usefulLadders$checkForStressedSupport(pState, pLevel, pPos)
                || usefulLadders$checkForHangingSupport(pState, pLevel, pPos);
        else
            return usefulLadders$checkForHangingSupport(pState, pLevel, pPos)
                || usefulLadders$checkForStressedSupport(pState, pLevel, pPos);
    }

    /**
     * @author AylanJ123
     * @reason Need the ladders reacting to updates above or below, not only from behind
     */
    @Overwrite
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (
                (
                    pFacing.getOpposite() == pState.getValue(FACING)
                    || pFacing == Direction.DOWN
                    || pFacing == Direction.UP
                )
                && !pState.canSurvive(pLevel, pCurrentPos)
        )   return Blocks.AIR.defaultBlockState();
        else {
            if (pState.getValue(WATERLOGGED))
                pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
            return pState;
        }
    }
    
    @Unique
    public boolean usefulLadders$hasSturdySupport(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        return this.canAttachTo(level, pos.relative(direction.getOpposite()), direction);
    }
    
    @Unique
    public boolean usefulLadders$checkForHangingSupport(BlockState ladderState, LevelReader level, BlockPos lastPos) {
        return usefulLadders$checkForHangingSupport(0, ladderState, level, lastPos);
    }

    @Unique
    public boolean usefulLadders$checkForHangingSupport(int i, BlockState ladderState, LevelReader level, BlockPos lastPos) {
        if (Config.maxHang != -1 && ++i > Config.maxHang) return false;
        BlockPos posToCheck = lastPos.above();
        BlockState stateToCheck = level.getBlockState(posToCheck);
        if (
            stateToCheck.getBlock() != Blocks.LADDER ||
            stateToCheck.getValue(LadderBlock.FACING) != ladderState.getValue(LadderBlock.FACING)
        ) return false;
        if (!usefulLadders$hasSturdySupport(ladderState, level, posToCheck))
            return usefulLadders$checkForHangingSupport(i, ladderState, level, posToCheck);
        return true;
    }

    @Unique
    public boolean usefulLadders$checkForStressedSupport(BlockState ladderState, LevelReader level, BlockPos lastPos) {
        return usefulLadders$checkForStressedSupport(0, ladderState, level, lastPos);
    }

    @Unique
    public boolean usefulLadders$checkForStressedSupport(int i, BlockState ladderState, LevelReader level, BlockPos lastPos) {
        if (Config.maxStress != -1 && ++i > Config.maxStress) return false;
        BlockPos posToCheck = lastPos.below();
        BlockState stateToCheck = level.getBlockState(posToCheck);
        if (
            stateToCheck.getBlock() != Blocks.LADDER ||
            stateToCheck.getValue(LadderBlock.FACING) != ladderState.getValue(LadderBlock.FACING)
        ) return false;
        if (!usefulLadders$hasSturdySupport(ladderState, level, posToCheck))
            return usefulLadders$checkForStressedSupport(i, ladderState, level, posToCheck);
        return true;
    }

}
