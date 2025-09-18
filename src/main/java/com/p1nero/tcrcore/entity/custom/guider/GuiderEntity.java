package com.p1nero.tcrcore.entity.custom.guider;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.p1nero.dialog_lib.api.NpcDialogueEntity;
import com.p1nero.dialog_lib.api.component.DialogueComponentBuilder;
import com.p1nero.dialog_lib.api.component.TreeNode;
import com.p1nero.dialog_lib.api.goal.LookAtConservingPlayerGoal;
import com.p1nero.dialog_lib.client.screen.DialogueScreenBuilder;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.datagen.TCRAdvancementData;
import com.p1nero.tcrcore.item.TCRItems;
import com.p1nero.tcrcore.save_data.TCRLevelSaveData;
import com.p1nero.tcrcore.utils.ItemUtil;
import com.p1nero.tcrcore.utils.WaypointUtil;
import com.p1nero.tcrcore.utils.WorldUtil;
import dev.ftb.mods.ftbquests.item.FTBQuestsItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import xaero.hud.minimap.waypoint.WaypointColor;
import yesman.epicfight.api.utils.math.Vec2i;

public class GuiderEntity extends PathfinderMob implements NpcDialogueEntity, GeoEntity {

    protected static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    @Nullable
    private Player conversingPlayer;

    public GuiderEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float value) {
        if (source.getEntity() instanceof ServerPlayer serverPlayer) {
            //彩蛋对话
            if (this.getConversingPlayer() == null) {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.putBoolean("from_hurt", true);
                this.sendDialogTo(serverPlayer, compoundTag);
                this.setConversingPlayer(serverPlayer);
            } else {
                return false;
            }
            source.getEntity().hurt(this.damageSources().indirectMagic(this, this), value * 0.5F);
            EntityType.LIGHTNING_BOLT.spawn(serverPlayer.serverLevel(), serverPlayer.getOnPos(), MobSpawnType.MOB_SUMMONED);
        }
        return source.isCreativePlayer();
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0f)
                .add(Attributes.ATTACK_DAMAGE, 1.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 114514f)
                .build();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new LookAtConservingPlayerGoal<>(this));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double p_21542_) {
        return false;
    }

    @Override
    protected @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        if (player instanceof ServerPlayer serverPlayer) {
            CompoundTag tag = new CompoundTag();
            tag.putBoolean("finished", TCRLevelSaveData.get(serverPlayer.serverLevel()).isAllFinish());
            if (player.getItemInHand(hand).is(TCRItems.ANCIENT_ORACLE_FRAGMENT.get())) {
                tag.putBoolean("is_oracle", true);
            }
            this.sendDialogTo(serverPlayer, tag);
        }
        return InteractionResult.sidedSuccess(level().isClientSide);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public DialogueScreenBuilder getDialogueBuilder(CompoundTag compoundTag) {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (localPlayer == null) {
            return null;
        }
        DialogueScreenBuilder treeBuilder = new DialogueScreenBuilder(this);
        DialogueComponentBuilder dBuilder = new DialogueComponentBuilder(this);
        if (compoundTag.getBoolean("from_hurt")) {
            treeBuilder.start(5).addFinalChoice(6);
            return treeBuilder;
        }
        if(compoundTag.getBoolean("finished")) {
            treeBuilder.start(8)
                    .addChoice(12, 11)
                    .addChoice(13, 12)
                    .addChoice(14, 13)
                    .addFinalChoice(15, 3, (dialogueScreen -> {
                        //TODO 渲染黑屏+字幕
                    }));
            return treeBuilder;
        }

        //正式起航，改变一下对话
        if(PlayerDataManager.mapMarked.get(localPlayer)) {
            TreeNode root = new TreeNode(dBuilder.ans(8), dBuilder.optWithBrackets(0));//开场白 | 返回

            TreeNode ans1 = new TreeNode(dBuilder.ans(9), dBuilder.optWithBrackets(10))
                    .addChild(root);

            TreeNode ans2 = new TreeNode(dBuilder.ans(10), dBuilder.optWithBrackets(11))
                    .addChild(root);

            root.addChild(ans1).addChild(ans2);

            treeBuilder.setAnswerRoot(root);
            return treeBuilder;
        }
         if (compoundTag.getBoolean("is_oracle")) {
            treeBuilder.start(7).addFinalChoice(9, 2);
        } else {
            TreeNode root = new TreeNode(dBuilder.ans(0), dBuilder.optWithBrackets(0));//开场白 | 返回

            TreeNode ans1 = new TreeNode(dBuilder.ans(1), dBuilder.optWithBrackets(1))
                    .addChild(root);

            TreeNode ans2 = new TreeNode(dBuilder.ans(2), dBuilder.optWithBrackets(3))
                    .addChild(root);

            TreeNode ans3 = new TreeNode(dBuilder.ans(3), dBuilder.optWithBrackets(4))
                    .addChild(root);

            if (PlayerDataManager.pillagerKilled.get(localPlayer)) {
                ans3 = new TreeNode(dBuilder.ans(4), dBuilder.optWithBrackets(7))
                        .addLeaf(dBuilder.optWithBrackets(5), (byte) 1);
                root.addChild(new TreeNode(dBuilder.ans(6), dBuilder.optWithBrackets(8))
                        .addChild(root));
            }

            root.addChild(ans1).addChild(ans2).addChild(ans3);

            treeBuilder.setAnswerRoot(root);
        }
        return treeBuilder;
    }

    @Override
    public void handleNpcInteraction(ServerPlayer player, int code) {
        if (code == 3) {
            //TODO 进入战灵维度
        }
        if (code == 2) {
            //揭示预言，即解锁新玩法。根据记录的id解锁，初始阶段0， 1解锁时装和武器 2解锁盔甲和boss图鉴，3解锁附魔地狱末地，具体在FTB看
            int stage = PlayerDataManager.stage.getInt(player);
            TCRAdvancementData.finishAdvancement("stage" + (stage + 1), player);
            PlayerDataManager.stage.put(player, stage + 1D);
            if(stage + 1 <= 3) {
                player.displayClientMessage(TCRCoreMod.getInfo("unlock_new_ftb_page"), false);
            }
            level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.PLAYERS, 1.0F, 1.0F);
            if (player.getMainHandItem().is(TCRItems.ANCIENT_ORACLE_FRAGMENT.get())) {
                player.getMainHandItem().shrink(1);
            } else if (player.getOffhandItem().is(TCRItems.ANCIENT_ORACLE_FRAGMENT.get())){
                player.getOffhandItem().shrink(1);
            }
        }
        if (code == 1) {
            if (!PlayerDataManager.mapMarked.get(player)) {
                ItemUtil.addItem(player, FTBQuestsItems.BOOK.get(), 1, true);//给任务书
                ItemUtil.addItem(player, AquamiraeItems.SHELL_HORN.get(), 1, true);//给号角
                //地图上标记位置
                Vec2i cursed = WorldUtil.getNearbyStructurePos(player, "aquamirae:ship");//船长
                if (cursed != null) {
                    WaypointUtil.sendWaypoint(player, TCRCoreMod.getInfoKey("cursed_pos"), new BlockPos(cursed.x, 64, cursed.y), WaypointColor.BLUE);
                }
                Vec2i desert = WorldUtil.getNearbyStructurePos(player, "block_factorys_bosses:sandworm_nest");//沙漠蠕虫巢穴
                if (desert != null) {
                    WaypointUtil.sendWaypoint(player, TCRCoreMod.getInfoKey("desert_pos"), new BlockPos(desert.x, 64, desert.y), WaypointColor.YELLOW);
                }
                Vec2i flame = WorldUtil.getNearbyStructurePos(player, "block_factorys_bosses:dragon_tower");//龙之塔
                if (flame != null) {
                    WaypointUtil.sendWaypoint(player, TCRCoreMod.getInfoKey("flame_pos"), new BlockPos(flame.x, 256, flame.y), WaypointColor.RED);
                }

                Vec2i abyss = WorldUtil.getNearbyStructurePos(player, WorldUtil.COVES);//隐秘水湾
                if (abyss != null) {
                    WaypointUtil.sendWaypoint(player, TCRCoreMod.getInfoKey("abyss_pos"), new BlockPos(abyss.x, 64, abyss.y), WaypointColor.DARK_BLUE);
                }

                Vec2i storm = WorldUtil.getNearbyStructurePos(player, WorldUtil.SKY_ISLAND);//天空岛
                if (storm != null) {
                    WaypointUtil.sendWaypoint(player, TCRCoreMod.getInfoKey("storm_pos"), new BlockPos(storm.x, 230, storm.y), WaypointColor.AQUA);
                }

                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.END_PORTAL_SPAWN, player.getSoundSource(), 1.0F, 1.0F);
                TCRAdvancementData.finishAdvancement("mark_map", player);
                PlayerDataManager.mapMarked.put(player, true);
            }

            player.connection.send(new ClientboundSetTitleTextPacket(TCRCoreMod.getInfo("press_to_open_map")));
        }
        if(!PlayerDataManager.pillagerKilled.get(player)) {
            DialogueComponentBuilder dBuilder = new DialogueComponentBuilder(this);
            player.displayClientMessage(dBuilder.buildDialogue(this, dBuilder.ans(3)), false);
        }
        this.setConversingPlayer(null);
    }

    @Override
    public void setConversingPlayer(@Nullable Player player) {
        this.conversingPlayer = player;
    }

    @Override
    public @Nullable Player getConversingPlayer() {
        return conversingPlayer;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, this::deployAnimController));
    }

    protected <E extends GuiderEntity> PlayState deployAnimController(final AnimationState<E> state) {
        return state.setAndContinue(IDLE);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
