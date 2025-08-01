package com.p1nero.tcrcore.block.entity;

import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.state.BlockState;

public class CursedAltarBlockEntity extends AbstractAltarBlockEntity {
    public CursedAltarBlockEntity(BlockPos pos, BlockState blockState) {
        super(TCRBlockEntities.CURSED_ALTAR_BLOCK_ENTITY.get(), pos, blockState, ModItems.CURSED_EYE.get());
    }

    @Override
    protected ParticleOptions getSpawnerParticle() {
        return ModParticle.CURSED_FLAME.get();
    }
}
