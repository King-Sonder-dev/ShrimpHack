package me.alpha432.oyvey.features.modules.player.speedmine;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static me.alpha432.oyvey.util.traits.Util.mc;

public class MTSUtils {
    public static double progress = 0;
    public static List<BlockPos> getSphere(BlockPos centerPos, double radius, double height) {
        ArrayList<BlockPos> blocks = new ArrayList<>();

        for (int i = centerPos.getX() - (int) radius; i < centerPos.getX() + radius; i++) {
            for (int j = centerPos.getY() - (int) height; j < centerPos.getY() + height; j++) {
                for (int k = centerPos.getZ() - (int) radius; k < centerPos.getZ() + radius; k++) {
                    BlockPos pos = new BlockPos(i, j, k);

                    if (distanceTo(centerPos, pos) <= radius && !blocks.contains(pos)) blocks.add(pos);
                }
            }
        }

        return blocks;
    }

    public static double distanceTo(BlockPos blockPos1, BlockPos blockPos2) {
        double d = blockPos1.getX() - blockPos2.getX();
        double e = blockPos1.getY() - blockPos2.getY();
        double f = blockPos1.getZ() - blockPos2.getZ();
        return MathHelper.sqrt((float) (d * d + e * e + f * f));
    }

    public static double distanceTo(BlockPos blockPos1, PlayerEntity player) {
        if (blockPos1 == null || player == null) return 99;

        double d = blockPos1.getX() - player.getX();
        double e = blockPos1.getY() - player.getY();
        double f = blockPos1.getZ() - player.getZ();
        return MathHelper.sqrt((float) (d * d + e * e + f * f));
    }

    public static List<BlockPos> getBlocksAround(PlayerEntity player) {
        List<BlockPos> positions = new ArrayList<>();
        List<Entity> getEntityBoxes;

        for (BlockPos blockPos : getSphere(player.getBlockPos(), 3, 4)) {
            getEntityBoxes = mc.world.getOtherEntities(null, new Box(blockPos), entity -> entity == player);
            if (!getEntityBoxes.isEmpty()) continue;

            for (Direction direction : Direction.values()) {
                getEntityBoxes = mc.world.getOtherEntities(null, new Box(blockPos.offset(direction)), entity -> entity == player);
                if (!getEntityBoxes.isEmpty()) positions.add(blockPos);
            }
        }

        return positions;
    }

    public static boolean hasEntity(Box box) {
        return hasEntity(box, entity -> entity instanceof PlayerEntity || entity instanceof EndCrystalEntity || entity instanceof TntEntity);
    }

    public static boolean hasEntity(Box box, Predicate<Entity> predicate) {
        return !mc.world.getOtherEntities(null, box, predicate).isEmpty();
    }

    public static EndCrystalEntity getEntity(BlockPos blockPos) {
        if (blockPos == null) return null;

        return hasEntity(new Box(blockPos), entity -> entity instanceof EndCrystalEntity) ?
                (EndCrystalEntity) mc.world.getOtherEntities(null, new Box(blockPos), entity -> entity instanceof EndCrystalEntity).get(0) : null;
    }

    // Progress
    public static boolean canBreak(int slot, BlockPos blockPos) {
        if (progress >= 1) return true;

        BlockState blockState = mc.world.getBlockState(blockPos);

        progress += getBreakDelta(slot != 420 ? slot : mc.player.getInventory().selectedSlot, blockState);
        return false;
    }

    private static double getBreakDelta(int slot, BlockState state) {
        float hardness = state.getHardness(null, null);
        if (hardness == -1) return 0;
        else {
            return getBlockBreakingSpeed(slot, state) / hardness / (!state.isToolRequired() || mc.player.getInventory().main.get(slot).isSuitableFor(state) ? 30 : 100);
        }
    }

    private static double getBlockBreakingSpeed(int slot, BlockState block) {
        double speed = mc.player.getInventory().main.get(slot).getMiningSpeedMultiplier(block);

        if (speed > 1) {
            ItemStack tool = mc.player.getInventory().getStack(slot);

            int efficiency = EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, tool);

            if (efficiency > 0 && !tool.isEmpty()) speed += efficiency * efficiency + 1;
        }

        if (StatusEffectUtil.hasHaste(mc.player)) {
            speed *= 1 + (StatusEffectUtil.getHasteAmplifier(mc.player) + 1) * 0.2F;
        }

        if (mc.player.hasStatusEffect(StatusEffects.MINING_FATIGUE)) {
            float k = switch (mc.player.getStatusEffect(StatusEffects.MINING_FATIGUE).getAmplifier()) {
                case 0 -> 0.3F;
                case 1 -> 0.09F;
                case 2 -> 0.0027F;
                default -> 8.1E-4F;
            };

            speed *= k;
        }

        if (mc.player.isSubmergedIn(FluidTags.WATER) && !EnchantmentHelper.hasAquaAffinity(mc.player)) {
            speed /= 5.0F;
        }

        if (!mc.player.isOnGround()) {
            speed /= 5.0F;
        }

        return speed;
    }
}