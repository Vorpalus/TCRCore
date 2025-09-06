package com.p1nero.tcrcore.datagen;

import com.yesman.epicskills.common.data.SkillTreeProvider;
import net.minecraft.data.PackOutput;
import yesman.epicfight.main.EpicFightMod;

import java.util.function.Consumer;

public class TCRSkillTreeProvider extends SkillTreeProvider {
    public TCRSkillTreeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildSkillTreePages(Consumer<SkillTreePageBuilder> writer) {

    }
}
