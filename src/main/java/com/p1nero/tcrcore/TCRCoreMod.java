package com.p1nero.tcrcore;

import com.mojang.logging.LogUtils;
import com.p1nero.invincible.Config;
import com.p1nero.tcrcore.block.TCRBlocks;
import com.p1nero.tcrcore.block.entity.TCRBlockEntities;
import com.p1nero.tcrcore.entity.TCREntities;
import com.p1nero.tcrcore.item.TCRItemTabs;
import com.p1nero.tcrcore.item.TCRItems;
import com.p1nero.tcrcore.network.TCRPacketHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(TCRCoreMod.MOD_ID)
public class TCRCoreMod {

    public static final String MOD_ID = "tcrcore";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TCRCoreMod(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();
        bus.addListener(this::commonSetup);
        TCREntities.REGISTRY.register(bus);
        TCRBlocks.REGISTRY.register(bus);
        TCRBlockEntities.REGISTRY.register(bus);
        TCRItems.REGISTRY.register(bus);
        TCRItemTabs.REGISTRY.register(bus);
        context.registerConfig(ModConfig.Type.COMMON, TCRConfig.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        TCRPacketHandler.register();
    }

    public static MutableComponent getInfo(String key) {
        return Component.translatable("info.tcr." + key);
    }

    public static String getInfoKey(String key) {
        return "info.tcr." + key;
    }

    public static MutableComponent getInfo(String key, Object... objects) {
        return Component.translatable("info.tcr." + key, objects);
    }

}
