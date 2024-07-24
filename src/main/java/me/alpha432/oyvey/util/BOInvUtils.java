package me.alpha432.oyvey.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.PickFromInventoryC2SPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;

import static me.alpha432.oyvey.util.traits.Util.mc;

@SuppressWarnings("DataFlowIssue")
public class BOInvUtils {
    private static int[] slots;
    public static int pickSlot = -1;

    public static boolean pickSwitch(int slot) {
        if (slot >= 0) {
            HoldingUtils.setModifyStartTime(System.currentTimeMillis());
            pickSlot = slot;
            mc.getNetworkHandler().sendPacket(new PickFromInventoryC2SPacket(slot));
            return true;
        }
        return false;
    }

    public static void pickSwapBack() {
        if (pickSlot >= 0) {
            mc.getNetworkHandler().sendPacket(new PickFromInventoryC2SPacket(pickSlot));
            pickSlot = -1;
        }
    }

    // Credits to rickyracuun
    public static boolean invSwitch(int slot) {
        if (slot >= 0) {
            ScreenHandler handler = mc.player.currentScreenHandler;
            Int2ObjectArrayMap<ItemStack> stack = new Int2ObjectArrayMap<>();
            stack.put(slot, handler.getSlot(slot).getStack());

            mc.getNetworkHandler().sendPacket(new ClickSlotC2SPacket(handler.syncId,
                    handler.getRevision(), mc.player.getInventory().size() + HoldingUtils.getSlot(),
                    slot, SlotActionType.SWAP, handler.getSlot(slot).getStack(), stack)
            );
            syncSelectedSlot();
            slots = new int[]{slot, HoldingUtils.getSlot()};
            return true;
        }
        return false;
    }

    public static void swapBack() {
        ScreenHandler handler = mc.player.currentScreenHandler;
        Int2ObjectArrayMap<ItemStack> stack = new Int2ObjectArrayMap<>();
        stack.put(slots[0], handler.getSlot(slots[0]).getStack());

        mc.getNetworkHandler().sendPacket(new ClickSlotC2SPacket(handler.syncId,
                handler.getRevision(), mc.player.getInventory().size() + slots[1],
                slots[0], SlotActionType.SWAP, handler.getSlot(slots[0]).getStack().copy(), stack)
        );
        syncSelectedSlot();
    }

    private static void syncSelectedSlot() {
        // Use standard Minecraft methods to synchronize the selected slot if needed
        int selectedSlot = HoldingUtils.getSlot();
        mc.player.getInventory().selectedSlot = selectedSlot;
    }
}
