package me.alpha432.oyvey.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

import static me.alpha432.oyvey.util.BlockUtil.getState;
import static me.alpha432.oyvey.util.traits.Util.mc;

public class CombatUtil {
    public static BlockPos modifyPos;
    public static BlockState modifyBlockState = Blocks.AIR.getDefaultState();

    public static boolean isHard(BlockPos pos) {
        Block block = getState(pos).getBlock();
        return block == Blocks.OBSIDIAN || block == Blocks.NETHERITE_BLOCK || block == Blocks.ENDER_CHEST || block == Blocks.BEDROCK || block == Blocks.ANVIL;
    }
    public static boolean isBurrowed (PlayerEntity target) {
        if (target == null) return false;
        BlockPos blockPos = target.getBlockPos();
        Block block = Objects.requireNonNull(mc.world).getBlockState(blockPos).getBlock();
        return block == Blocks.OBSIDIAN || block == Blocks.BEDROCK || block == Blocks.REINFORCED_DEEPSLATE || block == Blocks.NETHERITE_BLOCK || block == Blocks.COBWEB;
    }
}
