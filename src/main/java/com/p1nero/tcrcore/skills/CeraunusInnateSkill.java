package com.p1nero.tcrcore.skills;

import com.p1nero.tcrcore.gameassets.TCRAnimations;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class CeraunusInnateSkill extends TCRWeaponInnateSkillBase {
    public CeraunusInnateSkill(SkillBuilder<? extends Skill> builder) {
        super(builder);
    }

    @Override
    public void executeSkill1(ServerPlayerPatch serverPlayerPatch, SkillContainer container) {
        serverPlayerPatch.playAnimationSynchronized(TCRAnimations.CERAUNUS_SKILL1, 0.15F);
    }

    @Override
    public void executeSkill2(ServerPlayerPatch serverPlayerPatch, SkillContainer container) {
        System.out.println(2);
    }

    @Override
    public void executeSkill3(ServerPlayerPatch serverPlayerPatch, SkillContainer container) {
        System.out.println(3);
    }
}
