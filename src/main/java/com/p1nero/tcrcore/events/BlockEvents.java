package com.p1nero.tcrcore.events;

import com.p1nero.cataclysm_dimension.worldgen.CataclysmDimensions;
import com.p1nero.tcrcore.TCRCoreMod;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID)
public class BlockEvents {
    @SubscribeEvent
    public static void onBreakBlock(BlockEvent.BreakEvent event) {
        if(event.getPlayer() == null) {
            return;
        }
        if(CataclysmDimensions.LEVELS.contains(event.getPlayer().level().dimension())) {
            event.getPlayer().displayClientMessage(TCRCoreMod.getInfo("dim_block_no_interact"), true);
            event.setCanceled(true);
        }
    }

}
