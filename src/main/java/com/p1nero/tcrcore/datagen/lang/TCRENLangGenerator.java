package com.p1nero.tcrcore.datagen.lang;

import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.client.KeyMappings;
import com.p1nero.tcrcore.client.gui.BanPortalScreenHandler;
import com.p1nero.tcrcore.entity.TCREntities;
import net.minecraft.data.PackOutput;

public class TCRENLangGenerator extends TCRLangProvider {
    public TCRENLangGenerator(PackOutput output) {
        super(output, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add("key.categories." + TCRCoreMod.MOD_ID, "The Casket of Reveries - Core");
        this.addKeyMapping(KeyMappings.RIPTIDE, "Riptide");
        this.addInfo("skill_point_lack", "%d skill points are required");
        this.add(TCREntities.GUIDER.get(), "The Isle's Vigil");

        BanPortalScreenHandler.onGenerateEN(this);
    }
}
