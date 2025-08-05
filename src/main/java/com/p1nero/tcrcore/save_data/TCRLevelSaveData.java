package com.p1nero.tcrcore.save_data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.jetbrains.annotations.NotNull;

public class TCRLevelSaveData extends SavedData {
    private boolean stormKill;
    private boolean desertKill;
    private boolean cursedKill;
    private boolean flameKill;
    private boolean abyssKill;
    public static final String NAME = "tcr_level_save_data";

    public static TCRLevelSaveData create() {
        return new TCRLevelSaveData();
    }

    public boolean isStormKill() {
        return stormKill;
    }

    public boolean isAbyssKill() {
        return abyssKill;
    }

    public boolean isCursedKill() {
        return cursedKill;
    }

    public boolean isDesertKill() {
        return desertKill;
    }

    public boolean isFlameKill() {
        return flameKill;
    }

    public void setAbyssKill(boolean abyssKill) {
        this.abyssKill = abyssKill;
    }

    public void setCursedKill(boolean cursedKill) {
        this.cursedKill = cursedKill;
    }

    public void setDesertKill(boolean desertKill) {
        this.desertKill = desertKill;
    }

    public void setFlameKill(boolean flameKill) {
        this.flameKill = flameKill;
    }

    public void setStormKill(boolean stormKill) {
        this.stormKill = stormKill;
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag pCompoundTag) {
        pCompoundTag.putBoolean("stormKill", stormKill);
        pCompoundTag.putBoolean("desertKill", desertKill);
        pCompoundTag.putBoolean("cursedKill", cursedKill);
        pCompoundTag.putBoolean("flameKill", flameKill);
        pCompoundTag.putBoolean("abyssKill", abyssKill);
        return pCompoundTag;
    }

    public void load(CompoundTag nbt) {
        this.stormKill = nbt.getBoolean("stormKill");
        this.desertKill = nbt.getBoolean("desertKill");
        this.cursedKill = nbt.getBoolean("cursedKill");
        this.flameKill = nbt.getBoolean("flameKill");
        this.abyssKill = nbt.getBoolean("abyssKill");
    }

    public static TCRLevelSaveData decode(CompoundTag tag){
        TCRLevelSaveData saveData = TCRLevelSaveData.create();
        saveData.load(tag);
        return saveData;
    }

    public static TCRLevelSaveData get(Level worldIn) {
        if (!(worldIn instanceof ServerLevel)) {
            throw new RuntimeException("Attempted to get the data from a client world. This is wrong.");
        }
        ServerLevel world = worldIn.getServer().getLevel(ServerLevel.OVERWORLD);
        DimensionDataStorage dataStorage = world.getDataStorage();
        return dataStorage.computeIfAbsent(TCRLevelSaveData::decode, TCRLevelSaveData::create, TCRLevelSaveData.NAME);
    }
}
