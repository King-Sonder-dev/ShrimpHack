package aids.dev.shrimphack.features.modules.combat;

import aids.dev.shrimphack.Shrimphack;
import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.settings.Setting;

import aids.dev.shrimphack.manager.RotationManager;
import aids.dev.shrimphack.util.World.BlockUtil;
import aids.dev.shrimphack.util.World.InventoryUtility;
import aids.dev.shrimphack.util.World.InventoryUtils;
import aids.dev.shrimphack.util.jewedutils.BOInvUtils;

import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class AntiWeb extends Module {
    public Setting<Boolean> onlyinhole = this.register(new Setting<Boolean>("OnlyInHole", Boolean.valueOf(true), ""));
    public static final RotationManager Rotations = Shrimphack.rotationManager;

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