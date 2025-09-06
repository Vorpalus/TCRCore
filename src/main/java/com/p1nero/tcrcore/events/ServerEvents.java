package com.p1nero.tcrcore.events;

import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.utils.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;


@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID)
public class ServerEvents {

    /**
     * 默认出生在主城特定位置
     */
    @SubscribeEvent
    public static void onWorldLoad(LevelEvent.CreateSpawnPosition e) {
        if (e.getLevel() instanceof ServerLevel serverLevel && serverLevel.dimension() == ServerLevel.OVERWORLD) {
            serverLevel.setDefaultSpawnPos(new BlockPos(WorldUtil.START_POS), 1.0F);
            e.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event) {
        copyDuelDirectory(event.getServer());
    }

    public static void copyDuelDirectory(MinecraftServer server) {
        Path gameDir = FMLPaths.GAMEDIR.get();
        Path sourceDir = gameDir.resolve("mainland");
        Path savesDir = gameDir.resolve("saves");

        if (!Files.isDirectory(sourceDir)) {
            System.err.println("源目录不存在: " + sourceDir);
            return;
        }

        if(server.isDedicatedServer()) {
            copyToDimensions(gameDir.resolve(server.getWorldData().getLevelName()), sourceDir);
        } else {
            try (Stream<Path> saveFolders = Files.list(savesDir)) {
                saveFolders.filter(Files::isDirectory)
                        .forEach(saveFolder -> copyToDimensions(saveFolder, sourceDir));
            } catch (IOException e) {
                TCRCoreMod.LOGGER.error("TCR: Failed to copy dimension!", e);
            }
        }
    }

    private static void copyToDimensions(Path saveFolder, Path sourceDir) {
        Path markDir = saveFolder.resolve("mark");

        // 若已处理，则跳过
        if (Files.exists(markDir)) {
            return;
        }

        try {
            // 递归复制目录
            copyDirectoryRecursive(sourceDir, saveFolder);
            // 标记存在
            Files.createDirectories(markDir);
        } catch (IOException e) {
            TCRCoreMod.LOGGER.error("TCR: Failed to copy dimension! 复制到 {} 失败！", markDir, e);
        }
    }

    private static void copyDirectoryRecursive(Path source, Path target) throws IOException {
        Files.walkFileTree(source, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path relativePath = source.relativize(dir);
                Path destPath = target.resolve(relativePath);
                Files.createDirectories(destPath); // 创建目标目录
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path destFile = target.resolve(source.relativize(file));
                Files.copy(file, destFile, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
