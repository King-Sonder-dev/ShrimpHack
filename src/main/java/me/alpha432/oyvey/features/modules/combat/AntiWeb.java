package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.modules.combat.autocrystal.utils.player.InteractionUtility;
import me.alpha432.oyvey.features.modules.combat.autocrystal.utils.player.InventoryUtility;
import me.alpha432.oyvey.features.modules.combat.tntaura.utils.PlayerUtility;
import me.alpha432.oyvey.features.modules.combat.tntaura.utils.SearchInvResult;
import me.alpha432.oyvey.features.settings.Setting;
import me.alpha432.oyvey.manager.RotationManager;
import me.alpha432.oyvey.util.BOInvUtils;
import me.alpha432.oyvey.util.BlockUtil;
import me.alpha432.oyvey.util.InvUtils;
import me.alpha432.oyvey.util.InventoryUtil;
import net.minecraft.block.BedBlock;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class AntiWeb extends Module {
    public Setting<Boolean> onlyinhole = this.register(new Setting<Boolean>("OnlyInHole", Boolean.valueOf(true), ""));
    public static final RotationManager Rotations = OyVey.rotationManager;

    public AntiWeb() {
        super("AntiWeb", "", Category.COMBAT, true, false, false);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        placeRedstoneTorch();
    }
    private void placeRedstoneTorch() {
        BlockPos playerFeet = new BlockPos((int) mc.player.getX(), (int) (mc.player.getY() - 1), (int) mc.player.getZ());
        int redstoneTorchSlot = InventoryUtility.findItemInHotBar(Items.REDSTONE_TORCH).slot();

        if (redstoneTorchSlot != -1) {
            BOInvUtils.invSwitch(redstoneTorchSlot);
            Direction feet = BlockUtil.getClickSide(playerFeet);
            if (feet != null) {
                BlockUtil.clickBlock(playerFeet, feet, true, Hand.MAIN_HAND, false);
                //BOInvUtils.swapBack();
            }
        }
    }

    private void sendMinePackets(BlockPos blockPos) {
        mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, blockPos, Direction.UP));
        mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, blockPos, Direction.UP));
    }

    private void sendStopPackets(BlockPos blockPos) {
        mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, blockPos, Direction.UP));
        mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
    }
}