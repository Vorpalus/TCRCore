package com.p1nero.tcrcore.skills;

import net.minecraft.network.FriendlyByteBuf;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

/**
 * 技能基本类，按123按键触发
 */
public abstract class WeaponInnateSkillBase extends Skill {
    public WeaponInnateSkillBase(SkillBuilder<? extends Skill> builder) {
        super(builder);
    }

    @Override
    public boolean isExecutableState(PlayerPatch<?> executor) {
        return super.isExecutableState(executor) && !executor.getEntityState().inaction();
    }

    @Override
    public void executeOnServer(SkillContainer container, FriendlyByteBuf args) {
        int skillId = args.readInt();
        ServerPlayerPatch serverPlayerPatch = container.getServerExecutor();
        switch (skillId) {
            case 1 -> executeSkill1(serverPlayerPatch, container);
            case 2 -> executeSkill2(serverPlayerPatch, container);
            case 3 -> executeSkill3(serverPlayerPatch, container);
        }
    }

    protected void executeSkill1(ServerPlayerPatch serverPlayerPatch, SkillContainer container) {

    }

    protected void executeSkill2(ServerPlayerPatch serverPlayerPatch, SkillContainer container) {

    }

    protected void executeSkill3(ServerPlayerPatch serverPlayerPatch, SkillContainer container) {

    }

}
