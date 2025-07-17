package com.p1nero.tcrcore.gameassets;

import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.skills.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TCRSkills {

    public static Skill STEP;
    public static Skill CERAUNUS_INNATE;
    public static Skill WRATH_OF_THE_DESERT_INNATE;
    public static Skill THE_INCINERATOR_INNATE;
    public static Skill SOUL_RENDER_INNATE;
    @SubscribeEvent
    public static void buildSkills(SkillBuildEvent event) {
        SkillBuildEvent.ModRegistryWorker registryWorker = event.createRegistryWorker(TCRCoreMod.MOD_ID);
        STEP = registryWorker.build("step", DefaultDodgeSkill::new, DefaultDodgeSkill.createDodgeBuilder()
                .setAnimations(TCRAnimations.STEP_F, TCRAnimations.STEP_B, TCRAnimations.STEP_L, TCRAnimations.STEP_R));

        CERAUNUS_INNATE = registryWorker.build("ceraunus_innate", CeraunusInnateSkill::new, TCRWeaponInnateSkillBase.createBuilder());
        WRATH_OF_THE_DESERT_INNATE = registryWorker.build("wrath_of_the_desert_innate", Wrath_of_the_desertInnateSkill::new, TCRWeaponInnateSkillBase.createBuilder());
        SOUL_RENDER_INNATE = registryWorker.build("soul_render_innate", SoulRenderInnateSkill::new, TCRWeaponInnateSkillBase.createBuilder());
        THE_INCINERATOR_INNATE = registryWorker.build("the_incinerator_innate", TheIncineratorInnateSkill::new, TCRWeaponInnateSkillBase.createBuilder());
    }
}
