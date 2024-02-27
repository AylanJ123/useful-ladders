package com.aylanj123.usefulladders.items;

import com.aylanj123.usefulladders.Config;
import com.aylanj123.usefulladders.Helpers;
import com.aylanj123.usefulladders.LangKeys;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class RopeLadder extends Item {
    public RopeLadder(Properties properties) {
        super(properties
                .rarity(Rarity.COMMON)
                .stacksTo(64)
        );
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getLevel().isClientSide) return InteractionResult.SUCCESS;
        Level level = pContext.getLevel();
        Direction horizontalOpposite = pContext.getHorizontalDirection().getOpposite();
        Direction face = pContext.getClickedFace();
        BlockPos aimedPos = pContext.getClickedPos();
        BlockPos pos = aimedPos.relative(face);

        Direction ladderDirection = face == Direction.UP || face == Direction.DOWN ? horizontalOpposite : face;
        BlockState ladderState = Blocks.LADDER.defaultBlockState()
            .setValue(LadderBlock.FACING, ladderDirection);
        Boolean aimedAtLadder = level.getBlockState(aimedPos).getBlock() == Blocks.LADDER;
        if (!aimedAtLadder && Blocks.LADDER.canSurvive(ladderState, level, pos)) {
            BlockState stateToReplace = level.getBlockState(pos);
            if (stateToReplace.getBlock() == Blocks.WATER)
                ladderState.setValue(LadderBlock.WATERLOGGED, true);
            level.setBlock(pos, ladderState, Block.UPDATE_ALL_IMMEDIATE);
            level.scheduleTick(pos, Fluids.WATER, 1);
            pContext.getLevel().playSound(null, pos, SoundEvents.LADDER_PLACE, SoundSource.BLOCKS, 1, 1);
            consume(pContext, pos);
        }
        else if (aimedAtLadder) {
            Helpers.RecursiveResult result = Helpers.placeLadder(aimedPos,level,
                    level.getBlockState(aimedPos), Direction.DOWN
            );
            if (result.success) consume(pContext, result.finalPos);
        }
        return InteractionResult.CONSUME;
    }

    private void consume(UseOnContext cx, BlockPos pos) {
        ItemStack stack = cx.getItemInHand();
        stack.setCount(stack.getCount() - 1);
        Random rdm = new Random();
        if (rdm.nextFloat() < Config.recoveryChance) {
            if (cx.getPlayer() != null && cx.getPlayer().isCreative()) return;
            cx.getLevel().playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, ((cx.getLevel().random.nextFloat() - cx.getLevel().random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            ItemStack string = new ItemStack(Items.STRING, 1);
            if (Config.inventoryRecovery && cx.getPlayer() != null)
                cx.getPlayer().getInventory().add(string);
            else {
                ItemEntity entity = new ItemEntity(cx.getLevel(), pos.getX(), pos.getY(), pos.getZ(), string);
                cx.getLevel().addFreshEntity(entity);
            }
        }
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        return Component.translatable(LangKeys.ITEM_ROPE_LADDER.key());
    }

}
