package com.p1nero.tcrcore.datagen.lang;

import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.client.KeyMappings;
import com.p1nero.tcrcore.client.gui.BanPortalScreenHandler;
import com.p1nero.tcrcore.entity.TCREntities;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;

public class TCRZHLangGenerator extends TCRLangProvider {
    public TCRZHLangGenerator(PackOutput output) {
        super(output, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        this.add("key.categories." + TCRCoreMod.MOD_ID, "远梦之棺 —— 核心");
        this.addKeyMapping(KeyMappings.RIPTIDE, "激流");

        this.addInfo("add_item_tip", "获得新物品：%s × %d！");
        this.addInfo("skill_point_lack", "释放该技能需 %d 技能点");
        this.addInfo("press_to_show_progress", "按下§6[L]键§f以查看指引！");
        this.addInfo("riptide_tutorial", "在水中按下§6[闪避]键§f以释放§b激流");

        this.addInfo("press_to_open_map", "按下§6[M]键§f以查看地图");

        this.addInfo("storm_pos", "风暴回响之所在：天空岛");
        this.addInfo("cursed_pos", "诅咒回响之所在：冰冻深海");
        this.addInfo("desert_pos", "沙漠回响之所在：海底神殿");
        this.addInfo("flame_pos", "烈焰回响之所在：螺旋塔村");
        this.addInfo("abyss_pos", "深渊回响之所在：隐秘水湾");

        this.addAdvancement(TCRCoreMod.MOD_ID, "远梦之棺", "梦开始的地方");
        this.addAdvancement("kill_pillager", "投名状", "任务已经完成，该回去找守护神了。");
        this.addAdvancement("storm_eye", "风暴的回响", "与[天空岛]的村民交易，夺回了风暴的回响。");//TODO 补全该地的故事
        this.addAdvancement("abyss_eye", "深渊的回响", "与[隐秘水湾]的村民交易，夺回了深渊的回响。");
        this.addAdvancement("flame_eye", "烈焰的回响", "与[螺旋塔村]的村民交易，夺回了烈焰的回响。");
        this.addAdvancement("desert_eye", "沙漠的回响", "击败[海底神殿]的远古守卫者，夺回了烈焰的回响。");
        this.addAdvancement("cursed_eye", "诅咒的回响", "击败可妮莉娅(Cornelia)船长的幽灵，夺回了诅咒的回响。");

        this.addAdvancement("flame_kill", "伊格尼斯(Ignis)之魂", "击败伊格尼斯(Ignis)");
        this.addAdvancement("storm_kill", "斯库拉(Scylla)之魂", "击败斯库拉(Scylla)");
        this.addAdvancement("abyss_kill", "利维坦(Leviathan)之魂", "击败利维坦(Leviathan)");
        this.addAdvancement("desert_kill", "远古遗魂(Ancient Remnant)之魂", "击败远古遗魂(Ancient Remnant)");
        this.addAdvancement("cursed_kill", "玛莱迪克特斯(Maledictus)之魂", "击败玛莱迪克特斯(Maledictus)");

        this.add(TCREntities.GUIDER.get(), "守望者");

        BanPortalScreenHandler.onGenerateZH(this);

        this.addDialog(EntityType.VILLAGER, -2, "曼波？");
        this.addDialog(EntityType.VILLAGER, -1, "！！！");
        this.addDialog(EntityType.VILLAGER, 0, "曼波，曼波，哦嘛吉利，曼波~");
        this.addDialog(EntityType.VILLAGER, 1, "砸布砸布~");
        this.addDialog(EntityType.VILLAGER, 2, "瓦一夏~曼波~");
        this.addDialog(EntityType.VILLAGER, 3, "南北绿豆~阿西噶阿西~");
        this.addDialog(EntityType.VILLAGER, 4, "哈基米南北绿豆~阿西噶阿西~");
        this.addDialog(EntityType.VILLAGER, 5, "叮咚鸡~叮咚鸡~");
        this.addDialog(EntityType.VILLAGER, 6, "有哒有哒~");
        this.addDialog(EntityType.VILLAGER, 7, "阿西噶哈雅酷那路~WOW~");
        this.addDialogChoice(EntityType.VILLAGER, -2, "[这位村民对该物品并没有兴趣...]");
        this.addDialogChoice(EntityType.VILLAGER, -1, "[收下]");
        this.addDialogChoice(EntityType.VILLAGER, 0, "[？？？]");
        this.addDialogChoice(EntityType.VILLAGER, 1, "[看来，当地的居民被侵蚀的不轻！]");
        this.addDialogChoice(EntityType.VILLAGER, 2, "[叽里咕噜说什么呢？]");
        this.addDialogChoice(EntityType.VILLAGER, 3, "[为什么和村民就语言不通了...]");
        this.addDialog(TCREntities.GUIDER.get(), 0, "所以说…你们是从世界之外…漂流来的？当你们准备离开的时候，有陌生的神明将你们拉入结界，然后你们便不省人事？");
        this.addDialogChoice(TCREntities.GUIDER.get(), 0, "返回");
        this.addDialogChoice(TCREntities.GUIDER.get(), 1, "你是何人？为何救我");
        this.addDialogChoice(TCREntities.GUIDER.get(), 2, "什么海底捞？");
        this.addDialogChoice(TCREntities.GUIDER.get(), 3, "这个世界怎么了？为何海天一色，不见边界？");
        this.addDialogChoice(TCREntities.GUIDER.get(), 4, "我该如何离开这个世界？");
        this.addDialogChoice(TCREntities.GUIDER.get(), 5, "标记地点");
        this.addDialogChoice(TCREntities.GUIDER.get(), 6, "适才相戏耳！");
        this.addDialogChoice(TCREntities.GUIDER.get(), 7, "已完成");
        this.addDialogChoice(TCREntities.GUIDER.get(), 8, "打通关了才发现守护神是女郎");//收集完所有回响之后
        this.addDialog(TCREntities.GUIDER.get(), 1, "我是本岛的小神，那日天象异常，雷声四起，神庙崩裂，天有流星坠入海中，于是我在海底捞起了你们。神庙崩裂，封印的魔神回响已四散，神已不再回应，而随着神庙崩塌，我也失去了力量，无法离开此地。救你是有代价的，我想这世上只有你们能助我寻回散落的魔神回响。");
        this.addDialog(TCREntities.GUIDER.get(), 2, "上古时代，各大魔神为宣誓主权互相残杀，受苦的都是凡人。最终海洋之主利维坦为平息这场战役，化尽自身力量，将世界淹没。此时有位神明出手，将各大魔神封印。那位神明创造了我，令我守护此地。而那位神明此后再没出现，这世界得以处于一段时间的稳定，直到你的到来。");
        this.addDialog(TCREntities.GUIDER.get(), 3, "待你替我寻回失落的回响，我便给予你突破天界的力量！不过在这之前，先§6击杀一位灾厄村民§f让§f我看看你的实力。老夫先静养片刻。");
        this.addDialog(TCREntities.GUIDER.get(), 4, "看来你已有了突破天穹的能力！§b斯库拉(Scylla)的回响§f散落在天空岛，使用§a[绿宝石]§f与[天空岛]的村民交易可换回。§6伊格尼斯(Ignis)的回响§f使用§c[岩浆块]§f与[螺旋塔村]的村民交易可换回。§3利维坦(Leviathan)的回响§f需前往[隐秘水湾]使用任意§a[鱼类]与村民交易可换回。§2玛莱迪克特斯(Maledictus)的回响§f需前往寒冰迷宫奏响§6[号角]§f询问可妮莉娅(Cornelia)船长的灵魂。§e远古遗魂(Ancient-Remnant)的回响§f需从海洋神殿的§6[远古守卫者]§f手中夺回！我已将他们散落的位置标注在地图之上了，我便在此地静候佳音！");
        this.addDialog(TCREntities.GUIDER.get(), 5, "就凭你也想偷袭老夫！");
    }
}
