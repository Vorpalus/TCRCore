package com.p1nero.tcrcore.block.custom;

import com.p1nero.tcrcore.block.entity.AbstractAltarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractAltarBlock extends BaseEntityBlock {

    public AbstractAltarBlock(Properties properties) {
        super(properties);
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        BlockEntity entity = pLevel.getBlockEntity(pPos);
        if(entity instanceof AbstractAltarBlockEntity blockEntity && !pLevel.isClientSide){
            blockEntity.onPlayerInteract(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }


    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return AbstractAltarBlockEntity::tick;
    }
}
