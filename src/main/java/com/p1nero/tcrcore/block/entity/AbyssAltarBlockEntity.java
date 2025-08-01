package com.p1nero.tcrcore.block.entity;

import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.state.BlockState;

public class AbyssAltarBlockEntity extends AbstractAltarBlockEntity {
    public AbyssAltarBlockEntity(BlockPos pos, BlockState blockState) {
        super(TCRBlockEntities.ABYSS_ALTAR_BLOCK_ENTITY.get(), pos, blockState, ModItems.ABYSS_EYE.get());
    }

    @Override
    protected ParticleOptions getSpawnerParticle() {
        return ModParticle.SOUL_LAVA.get();
    }
}
