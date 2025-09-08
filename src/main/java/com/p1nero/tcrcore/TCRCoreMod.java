package com.p1nero.tcrcore;

import com.mojang.logging.LogUtils;
import com.p1nero.tcrcore.block.TCRBlocks;
import com.p1nero.tcrcore.block.entity.TCRBlockEntities;
import com.p1nero.tcrcore.entity.TCREntities;
import com.p1nero.tcrcore.item.TCRItemTabs;
import com.p1nero.tcrcore.item.TCRItems;
import com.p1nero.tcrcore.network.TCRPacketHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.List;

@Mod(TCRCoreMod.MOD_ID)
public class TCRCoreMod {

    public static final String MOD_ID = "tcrcore";
    public static final Logger LOGGER = LogUtils.getLogger();
    private static boolean isCheatMod = false;

    public TCRCoreMod(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();
        bus.addListener(this::commonSetup);
        bus.addListener(this::addPackFindersEvent);
        TCREntities.REGISTRY.register(bus);
        TCRBlocks.REGISTRY.register(bus);
        TCRBlockEntities.REGISTRY.register(bus);
        TCRItems.REGISTRY.register(bus);
        TCRItemTabs.REGISTRY.register(bus);
        context.registerConfig(ModConfig.Type.COMMON, TCRConfig.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        TCRPacketHandler.register();
        List<String> cheatModList = List.of("tacz", "projecte");
        cheatModList.forEach(s -> {
            if(ModList.get().isLoaded(s)){
                isCheatMod = true;
            }
        });
    }

    public static boolean isCheatMod() {
        return isCheatMod;
    }

    private void addPackFindersEvent(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            String name = "tcr_assets";
            var resourcePath = ModList.get().getModFileById(MOD_ID).getFile().findResource("packs/" + name);
            var pack = Pack.readMetaAndCreate(name, Component.literal("The Casket of Reveries Assets - Override"), true,
                    (path) -> new PathPackResources(path, resourcePath, false), PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN);
            event.addRepositorySource((packConsumer) -> packConsumer.accept(pack));
        }
        if (event.getPackType() == PackType.SERVER_DATA) {
            String name = "tcr_data";
            var resourcePath = ModList.get().getModFileById(MOD_ID).getFile().findResource("packs/" + name);
            var pack = Pack.readMetaAndCreate(name, Component.literal("The Casket of Reveries Data - Override"), true,
                    (path) -> new PathPackResources(path, resourcePath, false), PackType.SERVER_DATA, Pack.Position.TOP, PackSource.WORLD);
            event.addRepositorySource((packConsumer) -> packConsumer.accept(pack));

        }
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
