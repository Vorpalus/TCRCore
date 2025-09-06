package com.p1nero.tcrcore.gameassets;

import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.skills.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.passive.PassiveSkill;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TCRSkills {

    public static Skill STEP;
    public static Skill WATER_AVOID;
    public static Skill FIRE_AVOID;
    @SubscribeEvent
    public static void buildSkills(SkillBuildEvent event) {
        SkillBuildEvent.ModRegistryWorker registryWorker = event.createRegistryWorker(TCRCoreMod.MOD_ID);
        STEP = registryWorker.build("step", DefaultDodgeSkill::new, DefaultDodgeSkill.createDodgeBuilder()
                .setAnimations(TCRAnimations.STEP_F, TCRAnimations.STEP_B, TCRAnimations.STEP_L, TCRAnimations.STEP_R));
        WATER_AVOID = registryWorker.build("water_avoid", SimpleSkill::new, Skill.createBuilder().setCategory(SkillCategories.PASSIVE).setResource(Skill.Resource.NONE));
        FIRE_AVOID = registryWorker.build("fire_avoid", FireAvoidSkill::new, Skill.createBuilder().setCategory(SkillCategories.PASSIVE).setResource(Skill.Resource.NONE));
    }
}
