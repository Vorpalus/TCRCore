package com.p1nero.tcrcore.block.entity;

import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.state.BlockState;

public class StormAltarBlockEntity extends AbstractAltarBlockEntity {
    public StormAltarBlockEntity(BlockPos pos, BlockState blockState) {
        super(TCRBlockEntities.STORM_ALTAR_BLOCK_ENTITY.get(), pos, blockState, ModItems.STORM_EYE.get());
    }

    @Override
    protected ParticleOptions getSpawnerParticle() {
        return ModParticle.SPARK.get();
    }
}
