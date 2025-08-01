package com.p1nero.tcrcore.block.custom;

import com.p1nero.tcrcore.block.entity.DesertAltarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DesertAltarBlock extends AbstractAltarBlock {
    public DesertAltarBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState blockState) {
        return new DesertAltarBlockEntity(pos, blockState);
    }
}
