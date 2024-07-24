package me.alpha432.oyvey.util;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Set;

public class BlockUtil {

    // Define your set of unsafe blocks
    private static final Set<Block> unSafeBlocks = new HashSet<>();

    // Example unsafe blocks initialization (adjust as needed)
    static {
        unSafeBlocks.add(Blocks.BEDROCK);
        unSafeBlocks.add(Blocks.OBSIDIAN);
        unSafeBlocks.add(Blocks.ENDER_CHEST);
        unSafeBlocks.add(Blocks.ANVIL);

        // Add more blocks as needed
    }

    public static boolean isBlockUnSafe(Block block) {
        return unSafeBlocks.contains(block);
    }

    public static boolean isPosInFov(BlockPos pos) {
        int dirnumber = RotationUtil.getDirection4D();
        double playerX = MinecraftClient.getInstance().player.getX();
        double playerZ = MinecraftClient.getInstance().player.getZ();

        switch (dirnumber) {
            case 0:
                return pos.getZ() - playerZ >= 0.0;
            case 1:
                return pos.getX() - playerX <= 0.0;
            case 2:
                return pos.getZ() - playerZ <= 0.0;
            case 3:
                return pos.getX() - playerX >= 0.0;
            default:
                return false;
        }
    }
}
