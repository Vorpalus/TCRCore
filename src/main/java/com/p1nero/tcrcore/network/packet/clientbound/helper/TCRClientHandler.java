package com.p1nero.tcrcore.network.packet.clientbound.helper;

import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.capability.TCRCapabilityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TCRClientHandler {

    public static void syncBoolData(String key, boolean isLocked, boolean value) {
        if(Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (isLocked) {
                PlayerDataManager.putData(player, key + "isLocked", true);
                return;
            }
            PlayerDataManager.putData(player, key, value);
            PlayerDataManager.putData(player, key + "isLocked", false);
        }
    }

    public static void syncDoubleData(String key, boolean isLocked, double value) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (isLocked) {
                PlayerDataManager.putData(player, key + "isLocked", true);
                return;
            }
            PlayerDataManager.putData(player, key, value);
            PlayerDataManager.putData(player, key + "isLocked", false);
        }
    }

    public static void syncStringData(String key, boolean isLocked, String value){
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (isLocked) {
                PlayerDataManager.putData(player, key + "isLocked", true);
                return;
            }
            PlayerDataManager.putData(player, key, value);
            PlayerDataManager.putData(player, key + "isLocked", false);
        }
    }

    public static void syncTCRPlayer(CompoundTag compoundTag) {
        if(Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            TCRCapabilityProvider.getTCRPlayer(Minecraft.getInstance().player).loadNBTData(compoundTag);
        }
    }

}
