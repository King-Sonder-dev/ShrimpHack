package aids.dev.shrimphack.util.jewedutils;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

import static aids.dev.shrimphack.util.traits.Util.mc;

public class Jewedutil {
    public static boolean isBurrowed (PlayerEntity target) {
        if (target == null) return false;
        BlockPos blockPos = target.getBlockPos();
        Block block = Objects.requireNonNull(mc.world).getBlockState(blockPos).getBlock();
        return block == Blocks.OBSIDIAN || block == Blocks.BEDROCK || block == Blocks.REINFORCED_DEEPSLATE || block == Blocks.NETHERITE_BLOCK || block == Blocks.COBWEB;
    }
}
