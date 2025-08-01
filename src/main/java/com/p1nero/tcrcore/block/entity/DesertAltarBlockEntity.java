package com.p1nero.tcrcore.block.entity;

import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.state.BlockState;

public class DesertAltarBlockEntity extends AbstractAltarBlockEntity {
    public DesertAltarBlockEntity(BlockPos pos, BlockState blockState) {
        super(TCRBlockEntities.DESERT_ALTAR_BLOCK_ENTITY.get(), pos, blockState, ModItems.DESERT_EYE.get());
    }

    @Override
    protected ParticleOptions getSpawnerParticle() {
        return ModParticle.SANDSTORM.get();
    }
}
