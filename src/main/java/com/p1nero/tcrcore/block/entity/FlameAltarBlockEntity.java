package com.p1nero.tcrcore.block.entity;

import com.github.L_Ender.cataclysm.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class FlameAltarBlockEntity extends AbstractAltarBlockEntity {
    public FlameAltarBlockEntity(BlockPos pos, BlockState blockState) {
        super(TCRBlockEntities.FLAME_ALTAR_BLOCK_ENTITY.get(), pos, blockState, ModItems.FLAME_EYE.get());
    }
}
