package com.p1nero.tcrcore.mixin;

import com.github.L_Ender.cataclysm.init.ModItems;
import com.p1nero.dialog_lib.DialogueLib;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.capability.TCRCapabilityProvider;
import com.p1nero.tcrcore.utils.ItemUtil;
import com.yungnickyoung.minecraft.yungsapi.criteria.SafeStructureLocationPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillager {

    @Shadow public abstract VillagerData getVillagerData();

    public VillagerMixin(EntityType<? extends AbstractVillager> p_35267_, Level p_35268_) {
        super(p_35267_, p_35268_);
    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void tcr$mobInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (player instanceof ServerPlayer serverPlayer && hand == InteractionHand.MAIN_HAND) {
            this.playAmbientSound();
            TCRCapabilityProvider.getTCRPlayer(serverPlayer).setCurrentTalkingEntity(this);
            ItemStack mainHand = player.getMainHandItem();
            if (mainHand.is(Items.EMERALD) || mainHand.is(ItemTags.FISHES) || mainHand.is(Blocks.MAGMA_BLOCK.asItem())) {
                if (!PlayerDataManager.stormEyeTraded.get(player) && mainHand.is(Items.EMERALD) && new SafeStructureLocationPredicate(ResourceKey.create(Registries.STRUCTURE, ResourceLocation.parse("trek:overworld/very_rare/floating_farm_large"))).matches(serverPlayer.serverLevel(), this.getX(), this.getY(), this.getZ())) {
                    ItemUtil.addItem(player, ModItems.STORM_EYE.get(), 1, true);
                    PlayerDataManager.stormEyeTraded.put(player, true);
                } else if (!PlayerDataManager.flameEyeTraded.get(player) && mainHand.is(Blocks.MAGMA_BLOCK.asItem()) && new SafeStructureLocationPredicate(ResourceKey.create(Registries.STRUCTURE, ResourceLocation.parse("lios_outlandish_villages:spiral_tower_village_sea"))).matches(serverPlayer.serverLevel(), this.getX(), this.getY(), this.getZ())) {
                    ItemUtil.addItem(player, ModItems.FLAME_EYE.get(), 1, true);
                    PlayerDataManager.flameEyeTraded.put(player, true);
                } else if (!PlayerDataManager.abyssEyeTraded.get(player) && mainHand.is(ItemTags.FISHES) && new SafeStructureLocationPredicate(ResourceKey.create(Registries.STRUCTURE, ResourceLocation.parse("trek:overworld/very_rare/coves"))).matches(serverPlayer.serverLevel(), this.getX(), this.getY(), this.getZ())) {
                    ItemUtil.addItem(player, ModItems.ABYSS_EYE.get(), 1, true);
                    PlayerDataManager.abyssEyeTraded.put(player, true);
                } else {
                    CompoundTag tag = new CompoundTag();
                    tag.putBoolean("wrong_place", true);
                    DialogueLib.sendDialog((Villager) (Object) this, tag, serverPlayer);
                    cir.setReturnValue(InteractionResult.SUCCESS);
                    return;
                }
                mainHand.shrink(1);
                CompoundTag tag = new CompoundTag();
                tag.putBoolean("from_trade", true);
                DialogueLib.sendDialog((Villager) (Object) this, tag, serverPlayer);
                cir.setReturnValue(InteractionResult.SUCCESS);
            } else {
                DialogueLib.sendDialog((Villager) (Object) this, serverPlayer);
                cir.setReturnValue(InteractionResult.SUCCESS);
            }
        }
        cir.setReturnValue(InteractionResult.PASS);
    }
}
