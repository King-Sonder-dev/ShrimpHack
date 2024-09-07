package aids.dev.shrimphack.features.modules.player.middleclick.util;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static aids.dev.shrimphack.util.traits.Util.mc;


public record SearchInvResult(int slot, boolean found, ItemStack stack) {
    private static final SearchInvResult NOT_FOUND_RESULT = new SearchInvResult(-1, false, null);

    public static SearchInvResult notFound() {
        return NOT_FOUND_RESULT;
    }

    public static @NotNull SearchInvResult inOffhand(ItemStack stack) {
        return new SearchInvResult(999, true, stack);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isHolding() {
        if (mc.player == null) return false;

        return mc.player.getInventory().selectedSlot == slot;
    }

    public boolean isInHotBar() {
        return slot < 9;
    }
}
