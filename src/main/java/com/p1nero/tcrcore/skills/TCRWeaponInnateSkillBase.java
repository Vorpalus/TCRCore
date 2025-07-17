package com.p1nero.tcrcore.skills;

import com.p1nero.tcrcore.capability.TCRPlayer;
import net.minecraft.network.FriendlyByteBuf;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

/**
 * 技能基本类，按123按键触发
 */
public abstract class TCRWeaponInnateSkillBase extends Skill {
    public TCRWeaponInnateSkillBase(SkillBuilder<? extends Skill> builder) {
        super(builder);
    }

    public static SkillBuilder<Skill> createBuilder() {
        return new SkillBuilder<>().setCategory(SkillCategories.WEAPON_INNATE).setResource(Resource.NONE);
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
            case 1 -> {
                if (TCRPlayer.consumeSkillPoint(serverPlayerPatch.getOriginal(), 1)) {
                    executeSkill1(serverPlayerPatch, container);
                } else {
                    onSkillPointNotEnough(container, 1);
                }
            }
            case 2 -> {
                if (TCRPlayer.consumeSkillPoint(serverPlayerPatch.getOriginal(), 2)) {
                    executeSkill2(serverPlayerPatch, container);
                } else {
                    onSkillPointNotEnough(container, 2);
                }
            }
            case 3 -> {
                if (TCRPlayer.consumeSkillPoint(serverPlayerPatch.getOriginal(), 3)) {
                    executeSkill3(serverPlayerPatch, container);
                } else {
                    onSkillPointNotEnough(container, 3);
                }
            }
        }
    }

    public abstract void executeSkill1(ServerPlayerPatch serverPlayerPatch, SkillContainer container);

    public abstract void executeSkill2(ServerPlayerPatch serverPlayerPatch, SkillContainer container);

    public abstract void executeSkill3(ServerPlayerPatch serverPlayerPatch, SkillContainer container);

    public void onSkillPointNotEnough(SkillContainer container, int need) {

    }

}
