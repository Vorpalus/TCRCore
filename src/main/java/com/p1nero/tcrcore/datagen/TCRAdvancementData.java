package com.p1nero.tcrcore.datagen;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.p1nero.tcrcore.TCRCoreMod;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TCRAdvancementData extends ForgeAdvancementProvider {

    public static final String PRE = "advancement." + TCRCoreMod.MOD_ID + ".";
    public TCRAdvancementData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper helper) {
        super(output, registries, helper, List.of(new TCRAdvancements()));

    }

    public static class TCRAdvancements implements AdvancementGenerator {
        private Consumer<Advancement> consumer;
        private ExistingFileHelper helper;

        @SuppressWarnings("unused")
        @Override
        public void generate(HolderLookup.@NotNull Provider provider, @NotNull Consumer<Advancement> consumer, @NotNull ExistingFileHelper existingFileHelper) {
            this.consumer = consumer;
            this.helper = existingFileHelper;

            Advancement root = Advancement.Builder.advancement()
                    .display(ModItems.STORM_EYE.get(),
                            Component.translatable(PRE + TCRCoreMod.MOD_ID),
                            Component.translatable(PRE + TCRCoreMod.MOD_ID + ".desc"),
                            ResourceLocation.fromNamespaceAndPath(Cataclysm.MODID,"textures/block/azure_seastone_bricks.png"),
                            FrameType.TASK, false, false, false)
                    .addCriterion(TCRCoreMod.MOD_ID, new ImpossibleTrigger.TriggerInstance())
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, TCRCoreMod.MOD_ID), existingFileHelper);

            Advancement kill_pillager = registerAdvancement(root, "kill_pillager", FrameType.CHALLENGE, Items.GOAT_HORN, true, true, true, KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(EntityTypeTags.RAIDERS)));

            Advancement storm_eye = registerAdvancement(kill_pillager, "storm_eye", FrameType.CHALLENGE, ModItems.MUSIC_DISC_SCYLLA.get(), true, true, true, InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.STORM_EYE.get()));
            Advancement abyss_eye = registerAdvancement(kill_pillager, "abyss_eye", FrameType.CHALLENGE, ModItems.ABYSS_EYE.get(), true, true, true, InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ABYSS_EYE.get()));
            Advancement flame_eye = registerAdvancement(kill_pillager, "flame_eye", FrameType.CHALLENGE, ModItems.FLAME_EYE.get(), true, true, true, InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.FLAME_EYE.get()));
            Advancement desert_eye = registerAdvancement(kill_pillager, "desert_eye", FrameType.CHALLENGE, ModItems.REMNANT_SKULL.get(), true, true, true, InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.DESERT_EYE.get()));
            Advancement cursed_eye = registerAdvancement(kill_pillager, "cursed_eye", FrameType.CHALLENGE, AquamiraeItems.SHIP_GRAVEYARD_ECHO.get(), true, true, true, InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CURSED_EYE.get()));

            Advancement flame_kill = registerAdvancement(flame_eye, "flame_kill", FrameType.CHALLENGE, ModItems.THE_INCINERATOR.get(), true, true, false, KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(ModEntities.IGNIS.get())));
            Advancement abyss_kill = registerAdvancement(abyss_eye, "abyss_kill", FrameType.CHALLENGE, ModItems.TIDAL_CLAWS.get(), true, true, false, KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(ModEntities.THE_LEVIATHAN.get())));
            Advancement storm_kill = registerAdvancement(storm_eye, "storm_kill", FrameType.CHALLENGE, ModItems.CERAUNUS.get(), true, true, false, KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(ModEntities.SCYLLA.get())));
            Advancement desert_kill = registerAdvancement(desert_eye, "desert_kill", FrameType.CHALLENGE, ModItems.ANCIENT_SPEAR.get(), true, true, false, KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(ModEntities.ANCIENT_REMNANT.get())));
            Advancement cursed_kill = registerAdvancement(cursed_eye, "cursed_kill", FrameType.CHALLENGE, ModItems.SOUL_RENDER.get(), true, true, false, KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(ModEntities.MALEDICTUS.get())));

        }

        public Advancement registerItemAdvancement(Advancement parent, ItemLike display) {
            String disc = "item." + display.asItem();
            ItemStack itemStack = display.asItem().getDefaultInstance();
            MutableComponent desc = Component.translatable(PRE + disc + ".desc");
//            List<Component> descList = new ArrayList<>();
//            itemStack.getItem().appendHoverText(itemStack, null, descList, TooltipFlag.NORMAL);
//            for(Component component : descList){
//                desc.append("\n").append(component);
//            }
            return Advancement.Builder.advancement()
                    .parent(parent)
                    .display(display,
                            display.asItem().getName(itemStack),
                            desc,
                            null,
                            FrameType.GOAL, true, true, false)
                    .addCriterion(disc, InventoryChangeTrigger.TriggerInstance.hasItems(itemStack.getItem()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, disc), helper);
        }

        public Advancement registerAdvancement(Advancement parent, String name, FrameType type, ItemLike display, boolean showToast, boolean announceToChat, boolean hidden) {
            return Advancement.Builder.advancement()
                    .parent(parent)
                    .display(display,
                            Component.translatable(PRE + name),
                            Component.translatable(PRE + name + ".desc"),
                            null,
                            type, showToast, announceToChat, hidden)
                    .addCriterion(name, new ImpossibleTrigger.TriggerInstance())
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, name), helper);
        }

        public Advancement registerAdvancement(Advancement parent, String name, FrameType type, ItemLike display, boolean showToast, boolean announceToChat, boolean hidden, CriterionTriggerInstance triggerInstance) {
            return Advancement.Builder.advancement()
                    .parent(parent)
                    .display(display,
                            Component.translatable(PRE + name),
                            Component.translatable(PRE + name + ".desc"),
                            null,
                            type, showToast, announceToChat, hidden)
                    .addCriterion(name, triggerInstance)
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, name), helper);
        }

        public Advancement registerAdvancement(Advancement parent, String name, FrameType type, ItemLike display, CriterionTriggerInstance instance) {
            return Advancement.Builder.advancement()
                    .parent(parent)
                    .display(display,
                            Component.translatable(PRE + name),
                            Component.translatable(PRE + name + ".desc"),
                            null,
                            type, true, true, true)
                    .addCriterion(name, instance)
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, name), helper);
        }

        public Advancement registerAdvancement(Advancement parent, String name, FrameType type, ItemLike display) {
            return registerAdvancement(parent, name, type, display, true, true, true);
        }

    }

    public static void finishAdvancement(Advancement advancement, ServerPlayer serverPlayer) {
        AdvancementProgress progress = serverPlayer.getAdvancements().getOrStartProgress(advancement);
        if (!progress.isDone()) {
            for (String criteria : progress.getRemainingCriteria()) {
                serverPlayer.getAdvancements().award(advancement, criteria);
            }
        }
    }

    public static void finishAdvancement(String name, ServerPlayer serverPlayer) {
        Advancement advancement = serverPlayer.server.getAdvancements().getAdvancement(ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, name));
        if (advancement == null) {
            TCRCoreMod.LOGGER.error("advancement:\"{}\" is null!", name);
            return;
        }
        finishAdvancement(advancement, serverPlayer);
    }

    public static boolean isDone(String name, ServerPlayer serverPlayer) {
        Advancement advancement = serverPlayer.server.getAdvancements().getAdvancement(ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, name));
        if (advancement == null) {
            TCRCoreMod.LOGGER.info("advancement:\"{}\" is null!", name);
            return false;
        }
        return isDone(advancement, serverPlayer);
    }

    public static boolean isDone(Advancement advancement, ServerPlayer serverPlayer) {
        AdvancementProgress advancementProgress = serverPlayer.getAdvancements().getOrStartProgress(advancement);
        return advancementProgress.isDone();
    }

}
