package com.p1nero.tcrcore.mixin;

import com.p1nero.tudigong.client.screen.BiomeSearchScreen;
import com.p1nero.tudigong.client.screen.StructureSearchScreen;
import com.p1nero.tudigong.network.packet.client.SyncResourceKeysPacket;
import com.p1nero.tudigong.util.WorldUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Set;

@Mixin(SyncResourceKeysPacket.class)
public class SyncResourceKeysMixin {

    @Shadow(remap = false)
    @Final
    private boolean isStructure;

    @Shadow(remap = false)
    @Final
    private List<ResourceLocation> resourceLocations;

    @Inject(method = "execute", at = @At("HEAD"), cancellable = true, remap = false)
    private void tcr$execute(Player playerEntity, CallbackInfo ci) {
//        //限制可查询的结构，留关键结构就好，其余靠指南针
//        Set<ResourceLocation> resourceLocationSet = Set.of(
//                ResourceLocation.parse("aquamirae:ship"),
//                ResourceLocation.parse(com.p1nero.tcrcore.utils.WorldUtil.SKY_ISLAND),
//                ResourceLocation.parse(com.p1nero.tcrcore.utils.WorldUtil.COVES),
//                ResourceLocation.parse("minecraft:stronghold"),
//                ResourceLocation.parse("minecraft:stronghold_big"),
//                ResourceLocation.parse("minecraft:ancient_city"),
//                ResourceLocation.parse("ati_structures:warden_monument"),
//                ResourceLocation.parse("minecraft:ancient_city"));
//        if(Minecraft.getInstance().level != null) {
//            if(this.isStructure) {
//                StructureSearchScreen.STRUCTURE_NAME_MAP.clear();
//                this.resourceLocations.stream().filter(resourceLocation -> {
//                    return resourceLocation.getNamespace().equals("block_factorys_bosses")
//                            || resourceLocationSet.contains(resourceLocation)
//                            || resourceLocation.getNamespace().contains("village");
//                }).forEach((resourceLocation -> {
//                    StructureSearchScreen.STRUCTURE_NAME_MAP.put(resourceLocation, WorldUtil.getStructureName(resourceLocation));
//                }));
//            } else {
//                BiomeSearchScreen.BIOMIE_NAME_MAP.clear();
//                this.resourceLocations.forEach((resourceLocation -> {
//                    BiomeSearchScreen.BIOMIE_NAME_MAP.put(resourceLocation, WorldUtil.getBiomeName(resourceLocation));
//                }));
//            }
//        }
//        ci.cancel();
    }
}
