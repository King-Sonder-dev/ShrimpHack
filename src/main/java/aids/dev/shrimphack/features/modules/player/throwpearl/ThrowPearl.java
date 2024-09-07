package aids.dev.shrimphack.features.modules.player.throwpearl;

import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.modules.player.middleclick.util.InventoryUtility;
import aids.dev.shrimphack.features.modules.player.middleclick.util.PlayerUtility;
import aids.dev.shrimphack.features.settings.Setting;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.util.Hand;

public class ThrowPearl extends Module {

    private final Setting<Boolean> noPlayerTrace = register(new Setting<>("NoPlayerTrace", true));

    public ThrowPearl() {
        super("ThrowPearl", "Throws a pearl when enabled", Category.PLAYER, true, false, false);
    }

    @Override
    public void onEnable() {
        if (!fullNullCheck()) {
            throwPearl();
            disable();  // Optionally disable the module after throwing the pearl
        }
    }

    private void throwPearl() {
        if (noPlayerTrace.getValue()) {
            // Check if the targeted entity is a player and if it should be ignored
            if (mc.targetedEntity != null && mc.targetedEntity instanceof ClientPlayerEntity) {
                return;
            }
        }

        // Find the ender pearl in the hotbar
        int pearlSlot = InventoryUtility.findItemInInventory(Items.ENDER_PEARL).slot();
        boolean offhand = mc.player.getOffHandStack().getItem() == Items.ENDER_PEARL;

        if (pearlSlot != -1 || offhand) {
            int oldSlot = mc.player.getInventory().selectedSlot;

            // Switch to the ender pearl slot if necessary
            if (!offhand) {
                mc.player.getInventory().selectedSlot = pearlSlot;
                sendPacket(new UpdateSelectedSlotC2SPacket(pearlSlot));
            }

            // Send the packet to throw the ender pearl
            sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, PlayerUtility.getWorldActionId(mc.world)));

            // Switch back to the original slot if necessary
            if (!offhand) {
                mc.player.getInventory().selectedSlot = oldSlot;
                sendPacket(new UpdateSelectedSlotC2SPacket(oldSlot));
            }
        }
    }
}
