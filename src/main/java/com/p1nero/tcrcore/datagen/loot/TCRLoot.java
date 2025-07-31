package com.p1nero.tcrcore.datagen.loot;

import com.p1nero.tcrcore.TCRCoreMod;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TCRLoot {
    // /give @p chest{BlockEntityTag:{LootTable:"duel_of_the_end:chests/flower_altar"}} 1
    // /give @p chest{BlockEntityTag:{LootTable:"duel_of_the_end:chests/flower_altar"},CustomName:'{"text":"Test Crate"}'} 1
    private static final Set<ResourceLocation> LOOT_TABLES = new HashSet<>();
    public static final Set<ResourceLocation> IMMUTABLE_LOOT_TABLES = Collections.unmodifiableSet(LOOT_TABLES);

    private static ResourceLocation register(String id) {
        return register(ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, id));
    }

    private static ResourceLocation register(ResourceLocation id) {
        if (LOOT_TABLES.add(id)) {
            return id;
        } else {
            throw new IllegalArgumentException(id + " is already a registered built-in loot table");
        }
    }
}
