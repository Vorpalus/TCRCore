package com.p1nero.tcrcore.client.sound;

import com.p1nero.tcrcore.TCRCoreMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TCRSounds {
    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TCRCoreMod.MOD_ID);

    public static final RegistryObject<SoundEvent> WIN_BGM = createEvent("bgm.tcr.win_bgm");
    public static final RegistryObject<SoundEvent> BOSS_FIGHT_BGM = createEvent("bgm.tcr.boss_fight_bgm");

    private static RegistryObject<SoundEvent> createEvent(String sound) {
        return REGISTRY.register(sound, () -> SoundEvent.createVariableRangeEvent(TCRCoreMod.prefix(sound)));
    }
}
