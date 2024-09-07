package aids.dev.shrimphack.features.modules.combat;

import aids.dev.shrimphack.Shrimphack;
import aids.dev.shrimphack.event.impl.Render3DEventJr;
import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.settings.Setting;
import aids.dev.shrimphack.util.Combat.DamageUtil;
import aids.dev.shrimphack.util.Renders.RenderUtil;
import aids.dev.shrimphack.util.World.InventoryUtils;
import aids.dev.shrimphack.util.World.WorldUtil;
import com.google.common.collect.Streams;
import com.google.common.eventbus.Subscribe;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class CrystalAura extends Module {
    private final Setting<Boolean> breakCrystals = this.register(new Setting<>("Break Crystals", true));
    private final Setting<Boolean> placeCrystals = this.register(new Setting<>("Place Crystals", true));
    private final Setting<Boolean> swap = this.register(new Setting<>("Swap", true));
    private final Setting<Boolean> antiWeakness = this.register(new Setting<>("Anti Weakness", true));
    private final Setting<Boolean> rotate = this.register(new Setting<>("Rotate", true));
    private final Setting<Float> range = register(new Setting<>("Range", 6f, 0f, 6f, v -> true));
    private final Setting<Float> minHealth = register(new Setting<>("Min Health", 2f, 0f, 20f, v -> true));
    private final Setting<Float> minDamage = register(new Setting<>("Min Damage", 2f, 0f, 20f, v -> true));
    private final Setting<Float> minRatio = register(new Setting<>("Min Ratio", 0.5f, 0f, 6f, v -> true));
    private final Setting<Boolean> renderBlock = this.register(new Setting<>("Render Block", true));

    private BlockPos render = null;
    private LivingEntity currentTarget = null;

    public CrystalAura() {
        super("CrystalAura", "", Category.COMBAT, true, false, false);
    }

    @Subscribe
    public void onRender3D(Render3DEventJr event) {
        if (render == null || mc.world == null) {
            return;
        }
        if (this.renderBlock.getValue()) {
            Vec3d dimensions = new Vec3d(1, 1, 1);
            Color colorFill = new Color(Shrimphack.colorManager.getColorWithAlpha(100));
            Color colorOutline = new Color(Shrimphack.colorManager.getColorWithAlpha(20));
            RenderUtil.drawBoxFilled(event.getMatrix(), new Vec3d(render.getX(), render.getY(), render.getZ()), colorFill);
        }
    }

    @Override
    public void onUpdate() {
        if (nullCheck()) return;
        if (mc.player.isUsingItem() && mc.player.getMainHandStack().isFood()) {
            return;
        }

        List<LivingEntity> targets = Streams.stream(mc.world.getEntities())
                .filter(e -> e instanceof PlayerEntity)
                .filter(e -> e != mc.player)
                .map(e -> (LivingEntity) e)
                .toList();

        List<EndCrystalEntity> nearestCrystals = Streams.stream(mc.world.getEntities())
                .filter(e -> e instanceof EndCrystalEntity)
                .map(e -> (EndCrystalEntity) e)
                .sorted(Comparator.comparing(mc.player::distanceTo))
                .toList();
        currentTarget = targets.stream()
                .filter(target -> mc.player.distanceTo(target) <= range.getValue())
                .findFirst()
                .orElse(null);

        // break
        if (this.breakCrystals.getValue()) {
            for (EndCrystalEntity crystalEntity : nearestCrystals) {
                if (mc.player.distanceTo(crystalEntity) > this.range.getValue() || mc.world.getOtherEntities(null, new Box(crystalEntity.getPos(), crystalEntity.getPos()).expand(7), targets::contains).isEmpty()) continue;

                float damage = DamageUtil.getExplosionDamage(crystalEntity.getPos(), 6f, mc.player);
                if (DamageUtil.willGoBelowHealth(mc.player, damage, this.minHealth.getValue())) continue;

                int oldSlot = mc.player.getInventory().selectedSlot;
                if (this.breakCrystals.getValue() && mc.player.hasStatusEffect(StatusEffects.WEAKNESS)) {
                    InventoryUtils.selectSlot(false, true, Comparator.comparing(i -> DamageUtil.getItemAttackDamage(mc.player.getInventory().getStack(i))));
                }

                if (this.rotate.getValue()) {
                    Vec3d eyeVec = mc.player.getEyePos();
                    Vec3d v = new Vec3d(crystalEntity.getX(), crystalEntity.getY() + 0.5, crystalEntity.getZ());
                    for (Direction d : Direction.values()) {
                        Vec3d vd = WorldUtil.getLegitLookPos(crystalEntity.getBoundingBox(), d, true, 5, -0.001);
                        if (vd != null && eyeVec.distanceTo(vd) <= eyeVec.distanceTo(v)) {
                            v = vd;
                        }
                    }

                    WorldUtil.facePosAuto(v.x, v.y, v.z);
                }

                assert mc.interactionManager != null;
                mc.interactionManager.attackEntity(mc.player, crystalEntity);
                mc.player.swingHand(Hand.MAIN_HAND);

                InventoryUtils.selectSlot(oldSlot);
            }
        }

        // place
        if (this.placeCrystals.getValue()) {
            int crystalSlot = !swap.getValue()
                    ? (mc.player.getMainHandStack().getItem() == Items.END_CRYSTAL ? mc.player.getInventory().selectedSlot
                    : mc.player.getOffHandStack().getItem() == Items.END_CRYSTAL ? 40
                    : -1)
                    : InventoryUtils.getSlot(true, i -> mc.player.getInventory().getStack(i).getItem() == Items.END_CRYSTAL);

            if (crystalSlot == -1) {
                return;
            }

            Map<BlockPos, Float> placeBlocks = new LinkedHashMap<>();
            Map.Entry<BlockPos, Float> bestBlock = null;

            for (Vec3d vec : getCrystalPoses()) {
                float playerDmg = DamageUtil.getExplosionDamage(vec, 6f, mc.player);
                if (DamageUtil.willKill(mc.player, playerDmg)) continue;

                for (LivingEntity entity : targets) {
                    float targetDmg = DamageUtil.getExplosionDamage(vec, 6f, entity);
                    if (DamageUtil.willPop(mc.player, playerDmg) && !DamageUtil.willPopOrKill(entity, targetDmg)) continue;

                    if (targetDmg >= minDamage.getValue()) {
                        float ratio = playerDmg == 0 ? targetDmg : targetDmg / playerDmg;

                        if (ratio > minRatio.getValue()) {
                            placeBlocks.put(BlockPos.ofFloored(vec).down(), ratio);
                            if (bestBlock == null || ratio > bestBlock.getValue()) {
                                bestBlock = new AbstractMap.SimpleEntry<>(BlockPos.ofFloored(vec).down(), ratio);
                            }
                        }
                    }
                }
            }

            placeBlocks = placeBlocks.entrySet().stream()
                    .sorted((b1, b2) -> Float.compare(b2.getValue(), b1.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new));

            int oldSlot = mc.player.getInventory().selectedSlot;

            if (bestBlock != null) {
                BlockPos block = bestBlock.getKey();
                Vec3d eyeVec = mc.player.getEyePos();

                Vec3d vec = Vec3d.ofCenter(block, 1);
                Direction dir = null;
                for (Direction d : Direction.values()) {
                    Vec3d vd = WorldUtil.getLegitLookPos(block, d, true, 5);
                    if (vd != null && eyeVec.distanceTo(vd) <= eyeVec.distanceTo(vec)) {
                        vec = vd;
                        dir = d;
                    }
                }

                if (dir == null) {
                    dir = Direction.UP;
                }

                if (this.rotate.getValue()) {
                    WorldUtil.facePosAuto(vec.x, vec.y, vec.z);
                }

                Hand hand = InventoryUtils.selectSlot(crystalSlot);

                if (hand == null) {
                    hand = Hand.MAIN_HAND;
                }

                try {
                    render = block;
                    WorldUtil.interactBlock(new BlockHitResult(vec, dir, block, false), hand, true);
                    InventoryUtils.selectSlot(oldSlot);
                } catch (Exception e1) {
                    InventoryUtils.selectSlot(oldSlot);
                }
            } else {
                for (Map.Entry<BlockPos, Float> e : placeBlocks.entrySet()) {
                    BlockPos block = e.getKey();

                    Vec3d eyeVec = mc.player.getEyePos();

                    Vec3d vec = Vec3d.ofCenter(block, 1);
                    Direction dir = null;
                    for (Direction d : Direction.values()) {
                        Vec3d vd = WorldUtil.getLegitLookPos(block, d, true, 5);
                        if (vd != null && eyeVec.distanceTo(vd) <= eyeVec.distanceTo(vec)) {
                            vec = vd;
                            dir = d;
                        }
                    }

                    if (this.rotate.getValue()) {
                        WorldUtil.facePosAuto(vec.x, vec.y, vec.z);
                    }

                    Hand hand = InventoryUtils.selectSlot(crystalSlot);

                    try {
                        render = block;
                        assert mc.interactionManager != null;
                        mc.interactionManager.interactBlock(mc.player, hand, new BlockHitResult(vec, dir, block, false));
                        InventoryUtils.selectSlot(oldSlot);
                    } catch (Exception e1) {
                        InventoryUtils.selectSlot(oldSlot);
                        return;
                    }
                }
            }
        }
    }

    public Set<Vec3d> getCrystalPoses() {
        Set<Vec3d> poses = new HashSet<>();
        int optimizedRange = 4; // Reduced range for efficiency
        BlockPos playerPos = BlockPos.ofFloored(mc.player.getEyePos());

        for (int x = -optimizedRange; x <= optimizedRange; x++) {
            for (int y = -optimizedRange; y <= optimizedRange; y++) {
                for (int z = -optimizedRange; z <= optimizedRange; z++) {
                    BlockPos basePos = playerPos.add(x, y, z);

                    // Check if the block at basePos is obsidian
                    if (!mc.world.getBlockState(basePos).getBlock().equals(Blocks.OBSIDIAN)) continue;

                    if (!canPlace(basePos)) continue;

                    Vec3d placementPos = Vec3d.of(basePos).add(0.5, 1, 0.5);
                    if (mc.player.getPos().distanceTo(placementPos) <= optimizedRange + 0.25) {
                        poses.add(placementPos);
                    }
                }
            }
        }
        return poses;
    }

    private boolean canPlace(BlockPos basePos) {
        BlockState baseState = mc.world.getBlockState(basePos);

        if (baseState.getBlock() != Blocks.BEDROCK && baseState.getBlock() != Blocks.OBSIDIAN) {
            return false;
        }

        BlockPos placePos = basePos.up();
        if (!mc.world.isAir(placePos) || !mc.world.isAir(placePos.up())) {
            return false;
        }

        return mc.world.getOtherEntities(null, new Box(placePos.toCenterPos(), placePos.up(1).toCenterPos())).isEmpty();
    }

    @Override
    public String getDisplayInfo() {
        return currentTarget != null ? currentTarget.getName().getString() : "No Target";
    }
}