package com.p1nero.tcrcore.save_data;

import com.p1nero.tcrcore.TCRCoreMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class TCRLevelSaveData extends SavedData {
    private BlockPos coversPos = BlockPos.ZERO;
    private boolean girlPlaced;
    private boolean stormFinish;
    private boolean desertFinish;
    private boolean cursedFinish;
    private boolean flameFinish;
    private boolean abyssFinish;
    private int progressCount;
    public static final String NAME = "tcr_level_save_data";
    private Level level;

    public void setLevel(Level level) {
        this.level = level;
    }

    public boolean isAllFinish() {
        return isAbyssFinish() && isStormFinish() && isCursedFinish() && isDesertFinish() && isFlameFinish();
    }

    public int getProgressCount() {
        return progressCount;
    }

    private void updateProgressCount() {
        progressCount = 0;
        if (stormFinish) progressCount++;
        if (desertFinish) progressCount++;
        if (cursedFinish) progressCount++;
        if (flameFinish) progressCount++;
        if (abyssFinish) progressCount++;

        if(isAllFinish()) {
            if(level instanceof ServerLevel serverLevel) {
                serverLevel.getServer().getPlayerList().getPlayers().forEach(player -> {
                    player.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.END_PORTAL_SPAWN), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 1.0F, 1.0F, player.getRandom().nextInt()));
                    player.connection.send(new ClientboundSetTitleTextPacket(TCRCoreMod.getInfo("finish_all_eye")));
                });
            }
        }
    }

    public static TCRLevelSaveData create() {
        return new TCRLevelSaveData();
    }

    public void setCoversPos(BlockPos coversPos) {
        this.coversPos = coversPos;
    }

    public BlockPos getCoversPos() {
        return coversPos;
    }

    public boolean isGirlPlaced() {
        return girlPlaced;
    }

    public void setGirlPlaced(boolean girlPlaced) {
        this.girlPlaced = girlPlaced;
        setDirty();
    }

    public boolean isStormFinish() {
        return stormFinish;
    }

    public boolean isAbyssFinish() {
        return abyssFinish;
    }

    public boolean isCursedFinish() {
        return cursedFinish;
    }

    public boolean isDesertFinish() {
        return desertFinish;
    }

    public boolean isFlameFinish() {
        return flameFinish;
    }

    public void setAbyssFinish(boolean abyssFinish) {
        this.abyssFinish = abyssFinish;
        updateProgressCount(); // 更新进度计数
        setDirty(); // 标记数据需要保存
    }

    public void setCursedFinish(boolean cursedFinish) {
        this.cursedFinish = cursedFinish;
        updateProgressCount(); // 更新进度计数
        setDirty(); // 标记数据需要保存
    }

    public void setDesertFinish(boolean desertFinish) {
        this.desertFinish = desertFinish;
        updateProgressCount(); // 更新进度计数
        setDirty(); // 标记数据需要保存
    }

    public void setFlameFinish(boolean flameFinish) {
        this.flameFinish = flameFinish;
        updateProgressCount(); // 更新进度计数
        setDirty(); // 标记数据需要保存
    }

    public void setStormFinish(boolean stormFinish) {
        this.stormFinish = stormFinish;
        updateProgressCount(); // 更新进度计数
        setDirty(); // 标记数据需要保存
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag pCompoundTag) {
        pCompoundTag.putInt("covesPosX", coversPos.getX());
        pCompoundTag.putInt("covesPosY", coversPos.getY());
        pCompoundTag.putInt("covesPosZ", coversPos.getZ());
        pCompoundTag.putBoolean("girlPlaced", girlPlaced);
        pCompoundTag.putBoolean("stormFinish", stormFinish);
        pCompoundTag.putBoolean("desertFinish", desertFinish);
        pCompoundTag.putBoolean("cursedFinish", cursedFinish);
        pCompoundTag.putBoolean("flameFinish", flameFinish);
        pCompoundTag.putBoolean("abyssFinish", abyssFinish);
        pCompoundTag.putInt("progressCount", progressCount); // 保存进度计数
        return pCompoundTag;
    }

    public void load(CompoundTag nbt) {
        this.coversPos = new BlockPos(nbt.getInt("covesPosX"), nbt.getInt("covesPosY"), nbt.getInt("covesPosZ"));
        this.girlPlaced = nbt.getBoolean("girlPlaced");
        this.stormFinish = nbt.getBoolean("stormFinish");
        this.desertFinish = nbt.getBoolean("desertFinish");
        this.cursedFinish = nbt.getBoolean("cursedFinish");
        this.flameFinish = nbt.getBoolean("flameFinish");
        this.abyssFinish = nbt.getBoolean("abyssFinish");
        this.progressCount = nbt.getInt("progressCount"); // 加载进度计数
    }

    public static TCRLevelSaveData decode(CompoundTag tag){
        TCRLevelSaveData saveData = TCRLevelSaveData.create();
        saveData.load(tag);
        return saveData;
    }

    public static TCRLevelSaveData get(ServerLevel worldIn) {
        ServerLevel world = worldIn.getServer().getLevel(ServerLevel.OVERWORLD);
        DimensionDataStorage dataStorage = world.getDataStorage();
        TCRLevelSaveData levelSaveData = dataStorage.computeIfAbsent(TCRLevelSaveData::decode, TCRLevelSaveData::create, TCRLevelSaveData.NAME);
        levelSaveData.setLevel(world);
        return levelSaveData;
    }
}