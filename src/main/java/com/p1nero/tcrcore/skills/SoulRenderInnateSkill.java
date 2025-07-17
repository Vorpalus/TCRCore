package com.p1nero.tcrcore.skills;

import com.p1nero.tcrcore.gameassets.TCRAnimations;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class SoulRenderInnateSkill extends TCRWeaponInnateSkillBase {
    public SoulRenderInnateSkill(SkillBuilder<? extends Skill> builder) {
        super(builder);
    }

    @Override
    public void executeSkill1(ServerPlayerPatch serverPlayerPatch, SkillContainer container) {
//        serverPlayerPatch.playAnimationSynchronized(TCRAnimations.CERAUNUS_SKILL1, 0.15F);
        System.out.println(2);//TODO 突刺，路径留下戟 meen charge2 抄戟
    }

    @Override
    public void executeSkill2(ServerPlayerPatch serverPlayerPatch, SkillContainer container) {
        System.out.println(2);//TODO 蓄力，周围释放冲击波给敌人上灾变眩晕 meen charge1，抄战锤
    }

    @Override
    public void executeSkill3(ServerPlayerPatch serverPlayerPatch, SkillContainer container) {
        System.out.println(3);//TODO 枪的大招，周围生戟 meen charge all 抄戟和boss
    }
}
