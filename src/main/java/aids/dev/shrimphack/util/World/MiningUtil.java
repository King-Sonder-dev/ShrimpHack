package aids.dev.shrimphack.util.World;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import static aids.dev.shrimphack.util.traits.Util.mc;

public class MiningUtil {

    /**
     * Finds the appropriate tool in the player's inventory for the given block state.
     *
     * @param targetBlockState The block state to find a suitable tool for.
     * @return The index of the tool in the player's inventory, or -1 if no suitable tool is found.
     */
    public static int findSuitableTool(BlockState targetBlockState) {
        for (int i = 0; i < mc.player.getInventory().size(); i++) {
            ItemStack itemStack = mc.player.getInventory().getStack(i);
            if (itemStack.isSuitableFor(targetBlockState)) {
                return i;
            }
        }
        return -1;
    }
    public static int getInventorySize() {
        return mc.player.getInventory().size();
    }

    /**
     * Calculates the rotation needed to face the given block position.
     *
     * @param targetBlockPos The block position to face.
     */

}