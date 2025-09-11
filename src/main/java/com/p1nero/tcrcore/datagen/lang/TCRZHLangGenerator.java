package com.p1nero.tcrcore.datagen.lang;

import com.github.L_Ender.cataclysm.init.ModItems;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.block.TCRBlocks;
import com.p1nero.tcrcore.client.KeyMappings;
import com.p1nero.tcrcore.client.gui.BanPortalScreenHandler;
import com.p1nero.tcrcore.entity.TCREntities;
import com.p1nero.tcrcore.item.TCRItems;
import com.yesman.epicskills.registry.entry.EpicSkillsItems;
import net.kenddie.fantasyarmor.item.FAItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;

public class TCRZHLangGenerator extends TCRLangProvider {
    public TCRZHLangGenerator(PackOutput output) {
        super(output, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        this.add("itemGroup.tcr.items", "远梦之棺 —— 核心 物品");
        this.add("key.categories." + TCRCoreMod.MOD_ID, "远梦之棺 —— 核心");
        this.addKeyMapping(KeyMappings.RIPTIDE, "激流");

        this.add("unlock_tip.tcrcore.battleborn.water_avoid", "使用绿宝石向隐秘水湾的村民交易习得");
        this.add("unlock_tip.tcrcore.battleborn.fire_avoid", "击败Underworld Knight习得");
        this.addSkill("water_avoid", "避水咒", "可在水下自由呼吸！");
        this.addSkill("fire_avoid", "避火咒", "免疫火焰伤害！");

        this.add(TCRItems.ANCIENT_ORACLE_FRAGMENT.get(), "神谕残卷");
        this.addItemUsageInfo(TCRItems.ANCIENT_ORACLE_FRAGMENT.get(), "上面写着古老的神谕，回主城给守望者看看吧，说不定对冒险有帮助！");

        this.add(TCRBlocks.CURSED_ALTAR_BLOCK.get(), "诅咒祭坛");
        this.add(TCRBlocks.ABYSS_ALTAR_BLOCK.get(), "深渊祭坛");
        this.add(TCRBlocks.STORM_ALTAR_BLOCK.get(), "风暴祭坛");
        this.add(TCRBlocks.FLAME_ALTAR_BLOCK.get(), "烈焰祭坛");
        this.add(TCRBlocks.DESERT_ALTAR_BLOCK.get(), "沙漠祭坛");

        this.addInfo("reset_when_no_player", "当环境内没有玩家存在时，环境将会重置！");
        this.addInfo("on_full_set", "套装效果");
        this.addInfo("unlock_new_ftb_page", "解锁了新的任务界面，请打开§6[[任务书]§r查看");
        this.addInfo("unlock_new_skill_page", "解锁了新的技能书界面，按§6[K]§r查看");
        this.addInfo("unlock_water_avoid", "解锁了[%s]! 按§6[K]§r查看");
        this.addInfo("hit_barrier", "前面的区域，以后再来探索吧！");

        this.addInfo("death_info", "§6敌人过于强大时，可以尝试搭配不同技能组合！");
        this.addInfo("enter_dimension_tip", "潜行时右键祭坛核心以进入英灵幻境");
        this.addInfo("use_true_eye_tip", "请使用 [%s] 右键祭坛核心");

        this.addInfo("add_item_tip", "获得新物品：%s × %d！");
        this.addInfo("skill_point_lack", "释放该技能需 %d 技能点");
        this.addInfo("press_to_open_portal_screen", "长按[右键]以回到曾经点亮过的石碑！");
        this.addInfo("press_to_show_progress", "按下§6[L]键§f以查看指引！");
        this.addInfo("press_to_skill_tree", "按下§6[K]键§f以进行技能加点！");
        this.addInfo("riptide_tutorial", "在水中按下§6[闪避]键§f以释放§b激流");
        this.addInfo("dodge_tutorial", "按下§6[左ALT]§f以释放闪避技能");
        this.addInfo("weapon_innate_tutorial", "按下§6[R]键§f以释放武器技能");
        this.addInfo("weapon_innate_charge_tutorial", "§6[完美闪避]§c或§6[完美招架]§c可以对部分武器进行充能！");
        this.addInfo("perfect_dodge_tutorial", "§c抓住实机闪避以释放完美闪避！");
        this.addInfo("hurt_damage", "造成[ %.1f ]点伤害！");
        this.addInfo("parry_tutorial", "按下§6[右键]§f以进行格挡");
        this.addInfo("perfect_parry_tutorial", "§c抓住实机格挡以触发完美招架！");
        this.addInfo("you_pass", "§6你过关！！");

        this.addInfo("press_to_open_map", "按下§6[M]键§f以查看地图");

        this.addInfo("storm_pos", "风暴回响之所在：天空岛");
        this.addInfo("cursed_pos", "诅咒回响之所在：冰冻深海");
        this.addInfo("desert_pos", "沙漠回响之所在：沙海蠕虫之巢");
        this.addInfo("flame_pos", "烈焰回响之所在：龙之塔");
        this.addInfo("abyss_pos", "深渊回响之所在：隐秘水湾");

        this.addAdvancement(TCRCoreMod.MOD_ID, "远梦之棺", "梦开始的地方，前往主城寻找守护者。");
        this.addAdvancement(TCRCoreMod.MOD_ID + "_weapon", "王之宝库", "所有可获得的且进行过适配的武器或材料，可通过 [JEI] 查看获取方式及详细信息");
        this.addItemAdvDesc(ModItems.ATHAME.get(), "获取方式：[特定怪物掉落]    较高穿甲，匕首模板");
        this.addItemAdvDesc(ModItems.CORAL_SPEAR.get(), "获取方式：[特定怪物掉落]    三叉戟模板，较高穿甲，可抛掷");
        this.addItemAdvDesc(ModItems.CORAL_BARDICHE.get(), "获取方式：[特定怪物掉落]    三叉戟模板，较高穿甲，可附魔激流");
        this.addItemAdvDesc(ModItems.BLACK_STEEL_SWORD.get(), "获取方式：[合成]    剑模板");
        this.addItemAdvDesc(ModItems.KHOPESH.get(), "获取方式：[特定怪物掉落]    剑模板，较高穿甲");
        this.addItemAdvDesc(ModItems.ANCIENT_SPEAR.get(), "获取方式：[合成]    长枪模板，击中敌人释放沙暴");
        this.addItemAdvDesc(AquamiraeItems.DIVIDER.get(), "获取方式：[合成]    剑模板，减少目标5%的最大生命，可叠10层");
        this.addItemAdvDesc(AquamiraeItems.MAZE_ROSE.get(), "获取方式：[合成]    剑模板，右键召唤脉轮");
        this.addItemAdvDesc(AquamiraeItems.POISONED_CHAKRA.get(), "获取方式：[合成]    剑模板，右键召唤带毒脉轮");
        this.addItemAdvDesc(AquamiraeItems.WHISPER_OF_THE_ABYSS.get(), "获取方式：[合成]    长枪模板，减少目标10%的护甲值，可叠5层");
        this.addItemAdvDesc(AquamiraeItems.DAGGER_OF_GREED.get(), "获取方式：[战利品箱]    匕首模板，攻击村民概率获得绿宝石，且概率触发不祥征兆");
        this.addItemAdvDesc(AquamiraeItems.POISONED_BLADE.get(), "获取方式：[合成]    匕首模板，攻击附带中毒效果");
        this.addItemAdvDesc(AquamiraeItems.FIN_CUTTER.get(), "获取方式：[合成]    剑模板，每少一颗心增加15%伤害，最多增加150%");
        this.addItemAdvDesc(AquamiraeItems.TERRIBLE_SWORD.get(), "获取方式：[合成]    剑模板，50%暴击率，400%暴击伤害。如果你的攻击没有杀死敌人，你会受到1点伤害");
        this.addItemAdvDesc(AquamiraeItems.CORAL_LANCE.get(), "获取方式：[击败Cornelia概率掉落]    长枪模板，对海船墓地生物造成的伤害增加");
        this.addAdvancement(TCRCoreMod.MOD_ID + "_ingredient", "材料图鉴", "一些重要的材料的获取方式");
        this.addItemAdvDesc(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get(), "通过击杀该模组内除[金蛾]和[棘鱼]的生物概率掉落，其中只有击杀[鳗]可固定掉落，也可以通过打开[冰冻箱子]概率获得。");
        this.addItemAdvDesc(EpicSkillsItems.ABILIITY_STONE.get(), "击杀敌对生物或战利品箱概率获得，按[K]以进行技能加点");
        this.addItemAdvDesc(Items.AMETHYST_SHARD, "击杀敌对生物概率获得");
        this.addItemAdvDesc(AquamiraeItems.ABYSSAL_AMETHYST.get(), "击杀[迷宫之母]或合成获得");
        this.addItemAdvDesc(FAItems.MOON_CRYSTAL.get(), "击杀敌对生物概率获得，或合成获得");
        this.addItemAdvDesc(Items.IRON_INGOT, "击杀敌对生物概率获得");
        this.addItemAdvDesc(Items.GOLD_INGOT, "击杀掠夺者概率获得");
        this.addItemAdvDesc(Items.DIAMOND, "击杀掠夺者概率获得");
        this.addItemAdvDesc(Items.NETHERITE_INGOT, "击杀掠夺者概率获得");
        this.addAdvancement("find_ymsw", "隐秘水湾", "抵达隐秘水湾");
        this.addAdvancement("aquamirae_weapon", "海灵物语-武器", "");
        this.addAdvancement("cataclysm_weapon", "灾变-武器", "");
        this.addAdvancement("legend_weapon", "传奇武器", "");
        this.addAdvancement("ef_legacy", "史诗战斗-武器", "皆可通过合成获取，拥有不同的武器技能和不同的动作模板，可在JEI查看合成方式及技能信息");
        this.addAdvancement("kill_pillager", "投名状", "任务已经完成，该回去找守护神了。");
        this.addAdvancement("mark_map", "标记地点", "守护神帮你标出了神之眼散落的地方，现在动身去夺回它们吧！");
        this.addAdvancement("storm_eye", "风暴之眼", "§a§o当战火撕裂云层，她以风暴为阶梯，为子民筑起悬空净土");
        this.addAdvancement("abyss_eye", "深渊之眼", "§a§o深渊吞噬陆地时，祂将自己缝进海床，血肉化成气泡之城");
        this.addAdvancement("flame_eye", "烈焰之眼", "§a§o岩浆奔涌之地，祂剜出心脏，铸成永不熄灭的烽火台");
        this.addAdvancement("desert_eye", "沙漠之眼", "§a§o守卫者不是怪物，它们是子民自愿化身的活体墓碑");
        this.addAdvancement("cursed_eye", "诅咒之眼", "§a§o当背叛者刺穿她的脊柱，冻泪瞬间冰封三千幽灵船");

        this.addAdvancement("flame_kill", "伊格尼斯(Ignis)之魂", "击败伊格尼斯(Ignis)，获得炎葬");
        this.addAdvancement("storm_kill", "斯库拉(Scylla)之魂", "击败斯库拉(Scylla)，获得庭浪锚戟");
        this.addAdvancement("abyss_kill", "利维坦(Leviathan)之魂", "击败利维坦(Leviathan)，获得潮汐利爪");
        this.addAdvancement("desert_kill", "远古遗魂(Ancient Remnant)之魂", "击败远古遗魂(Ancient Remnant)，获得沙暴之怒");
        this.addAdvancement("cursed_kill", "玛莱迪克特斯(Maledictus)之魂", "击败玛莱迪克特斯(Maledictus)，获得断魂战戟");

        this.addAdvancement("stage1", "阶段1","");
        this.addAdvancement("stage2", "阶段2","");
        this.addAdvancement("stage3", "阶段3","");
        this.addAdvancement("stage4", "阶段4","");

        this.add(TCREntities.GUIDER.get(), "守望者");
        this.add(TCREntities.TUTORIAL_GOLEM.get(), "训练傀儡");

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
        this.addDialog(EntityType.VILLAGER, 7, "阿西噶哈雅酷那路~ wow~");
        this.addDialogChoice(EntityType.VILLAGER, -2, "[这位村民对该物品并没有兴趣...]");
        this.addDialogChoice(EntityType.VILLAGER, -1, "[收下]");
        this.addDialogChoice(EntityType.VILLAGER, 0, "[？？？]");
        this.addDialogChoice(EntityType.VILLAGER, 1, "[看来，当地的居民被侵蚀的不轻！]");
        this.addDialogChoice(EntityType.VILLAGER, 2, "[叽里咕噜说什么呢？]");
        this.addDialogChoice(EntityType.VILLAGER, 3, "[为什么和村民就语言不通了...]");
        this.addDialog(TCREntities.GUIDER.get(), 0, "所以说…你们是从世界之外…漂流来的？当你们准备降落的时候，有陌生的神明将你们拉入结界，然后你们便不省人事？");
        this.addDialogChoice(TCREntities.GUIDER.get(), 0, "返回");
        this.addDialogChoice(TCREntities.GUIDER.get(), 1, "你是何人？为何救我");
        this.addDialogChoice(TCREntities.GUIDER.get(), 2, "什么海底捞？");
        this.addDialogChoice(TCREntities.GUIDER.get(), 3, "这个世界怎么了？");
        this.addDialogChoice(TCREntities.GUIDER.get(), 4, "我该如何帮助你们？");
        this.addDialogChoice(TCREntities.GUIDER.get(), 5, "标记地点");
        this.addDialogChoice(TCREntities.GUIDER.get(), 6, "适才相戏耳！");
        this.addDialogChoice(TCREntities.GUIDER.get(), 7, "§a我已经击败过掠夺者了§f");
        this.addDialogChoice(TCREntities.GUIDER.get(), 8, "我去，你怎么变成美少女了");
        this.addDialogChoice(TCREntities.GUIDER.get(), 9, "揭晓神谕");
        this.addDialog(TCREntities.GUIDER.get(), 1, "我是此世界的守护神，那日天象异常，雷声四起，天有流星坠入海中，于是我在海底捞起了你们，想必你们就是古老预言中的天外勇者。");
        this.addDialog(TCREntities.GUIDER.get(), 2, "曾经，这里有个荣光的王国，英灵们守护着天地。直到有一天，黑潮降临世间，万物受到侵蚀，甚至部分村民黑化为灾厄村民。而众神不敌黑潮，化为英灵。依照古老预言所示，我将他们残存的力量封印于此，等待一位天外勇者，拯救我们的世界。");
        this.addDialog(TCREntities.GUIDER.get(), 3, "古老预言所示，待你点亮所有火种，便可重建神庙，获取英灵的力量，举行仪式，清洗黑潮！不过在这之前，先§6击杀一位灾厄村民§f再§f来找我吧。");
        this.addDialog(TCREntities.GUIDER.get(), 4, "看来阁下真是预言中的天外勇者！§b风暴之火种§f散落在天空岛的铁傀儡身上。§6烈焰之种§f需从龙之塔的炼狱魔龙口中夺回。§3深渊之眼§f需在[隐秘水湾]底下蛮屠巨兽（Bulldrogious）夺回。§2§f需前往寒冰迷宫奏响§6[号角]§f询问可妮莉娅(Cornelia)船长的灵魂。§e沙漠之眼§f需前往[蠕虫巢穴]击败§6[沙漠蠕虫]§f夺回！我已将他们散落的位置标注在地图之上了，小神便在此地等候，待你点亮所有火种，吾便启动黑潮清洗仪式！");
        this.addDialog(TCREntities.GUIDER.get(), 5, "阁下何故攻击我？");
        this.addDialog(TCREntities.GUIDER.get(), 6, "既然你已经证明了你的实力，我便卸下伪装，以真面目相待。");
        this.addDialog(TCREntities.GUIDER.get(), 7, "这是？神谕残卷！将它交给我吧，我将为你揭示它所展示的画卷。");

    }
}
