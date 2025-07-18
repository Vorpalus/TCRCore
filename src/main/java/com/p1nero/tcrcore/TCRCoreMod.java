package com.p1nero.tcrcore;

import com.mojang.logging.LogUtils;
import com.p1nero.tcrcore.network.TCRPacketHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(TCRCoreMod.MOD_ID)
public class TCRCoreMod {

    public static final String MOD_ID = "tcrcore";
    private static final Logger LOGGER = LogUtils.getLogger();

    public TCRCoreMod(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();
        bus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        TCRPacketHandler.register();
    }

}
