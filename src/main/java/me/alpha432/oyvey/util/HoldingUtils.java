package me.alpha432.oyvey.util;

import me.alpha432.oyvey.event.impl.EventBus;
import me.alpha432.oyvey.event.impl.PacketEvent;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;

import static me.alpha432.oyvey.util.traits.Util.mc;

public class HoldingUtils {

    private static int slot = 0;
    private static long modifyStartTime = 0;

    static {
        EventBus.getInstance().register(PacketEvent.Send.class, HoldingUtils::onSend);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private static void onSend(PacketEvent.Send event) {
        if (event.getPacket() instanceof UpdateSelectedSlotC2SPacket packet) {
            if (packet.getSelectedSlot() >= 0 && packet.getSelectedSlot() <= 8) {
                slot = packet.getSelectedSlot();
            }
        }
    }

    public static ItemStack getStack() {
        if (mc.player == null) {
            return null;
        }
        return mc.player.getInventory().getStack(slot);
    }

    public static int getSlot() {
        return slot;
    }

    public static boolean isHolding(Item... items) {
        ItemStack stack = getStack();
        if (stack == null) {
            return false;
        }
        for (Item item : items) {
            if (item.equals(stack.getItem())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isHolding(Item item) {
        ItemStack stack = getStack();
        if (stack == null) {
            return false;
        }
        return stack.getItem().equals(item);
    }

    public static void setModifyStartTime(long time) {
        modifyStartTime = time;
    }

    public static long getModifyStartTime() {
        return modifyStartTime;
    }
}
