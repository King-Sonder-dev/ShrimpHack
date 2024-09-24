package aids.dev.shrimphack.util.World;

import net.minecraft.item.ItemStack;

import static aids.dev.shrimphack.util.traits.Util.mc;

public class invutilo {
    public static int findHotbar(Class clazz) {
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack == ItemStack.EMPTY) continue;
            if (clazz.isInstance(stack.getItem())) {
                return i;
            }
            return i;
        }
        return -1;
    }
}
