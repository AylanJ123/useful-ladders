package com.aylanj123.usefulladders.items;

import com.aylanj123.usefulladders.LangKeys;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class RopeLadder extends Item {
    public RopeLadder(Properties properties) {
        super(properties
                .rarity(Rarity.COMMON)
                .stacksTo(128)
        );
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        level.setBlock(pos,
                Blocks.LADDER.defaultBlockState()
                        .setValue(LadderBlock.FACING, pContext.getClickedFace())
                        .setValue(LadderBlock.WATERLOGGED, false),
                Block.UPDATE_ALL
        );
        return InteractionResult.PASS;
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        return Component.translatable(LangKeys.ITEM_ROPE_LADDER.key());
    }

}
