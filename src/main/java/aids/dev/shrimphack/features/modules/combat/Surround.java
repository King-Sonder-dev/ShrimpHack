package aids.dev.shrimphack.features.modules.combat;

import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.settings.Setting;
import aids.dev.shrimphack.util.World.InventoryUtils;
import aids.dev.shrimphack.util.World.WorldUtil;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.Set;

public class Surround extends Module {
    private final Setting<Boolean> center = this.register(new Setting<>("center", true));
    public Surround() {
        super("Surround", "", Category.COMBAT, true, false, false);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null) {
            return;
        }

        if (this.center.getValue()) {
            Vec3d centerPos = Vec3d.ofBottomCenter(mc.player.getBlockPos());
            mc.player.updatePosition(centerPos.x, centerPos.y, centerPos.z);
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(centerPos.x, centerPos.y, centerPos.z, mc.player.isOnGround()));
        }
//
//        if (jumpDisable.isEnabled() && mc.options.jumpKey.isPressed()) {
////            this.toggle();
//            Avalanche.getInstance().removeListener(this);
//        }
        place();
    }

    private static final Set<Block> SURROUND_BLOCKS = Sets.newHashSet(Blocks.OBSIDIAN, Blocks.ENDER_CHEST, Blocks.ANVIL, Blocks.CHIPPED_ANVIL, Blocks.DAMAGED_ANVIL, Blocks.CRYING_OBSIDIAN, Blocks.NETHERITE_BLOCK, Blocks.ANCIENT_DEBRIS, Blocks.RESPAWN_ANCHOR);

    private void place() {
        if (mc.player == null) {
            return;
        }

        int slot = InventoryUtils.getSlot(true,
                i -> SURROUND_BLOCKS.contains(Block.getBlockFromItem(mc.player.getInventory().getStack(i).getItem())));

        BlockPos playerPos = mc.player.getBlockPos();

        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos offset = new BlockPos(direction.getOffsetX(), 0, direction.getOffsetZ());
            WorldUtil.place(playerPos.add(offset), Hand.MAIN_HAND, slot, false, false);
        }
    }
}