package aids.dev.shrimphack.features.modules.movement;

import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.settings.Setting;
import aids.dev.shrimphack.util.Math.StopWatch;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.math.BlockPos;

public class Stairs extends Module {
    private final Setting<Integer> delay = this.register(new Setting<>("delay", 100, 0, 1000));
    private final Setting<Boolean> whileSneaking = register(new Setting<>("WhileSneaking", true));

    private final BlockPos.Mutable pos = new BlockPos.Mutable();
    private final StopWatch timer = new StopWatch();
    private double currentY;
    private double lastY;

    public Stairs() {
        super("Stairs", "fastfastsaaaairrrrr..", Category.MOVEMENT, true, false, false);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null) return;

        if (mc.player.getY() != currentY || timer.passed(100)) {
            if (currentY != lastY) {
                lastY = currentY;
            }

            currentY = mc.player.getY();
        }

        if (timer.passed(delay.getValue())
                && mc.player.isOnGround()
                && mc.player.forwardSpeed > 0
                && lastY < currentY
                && !mc.player.isSpectator()
                && !mc.player.isRiding()
                && !mc.player.isHoldingOntoLadder()
                && (!mc.player.isSneaking() || whileSneaking.getValue())
                && checkForStairs()) {
            mc.player.jump();
            timer.reset();
        }
    }

    private boolean checkForStairs() {
        pos.set(mc.player.getX(), mc.player.getY(), mc.player.getZ());
        if (mc.world.getBlockState(pos).getBlock() instanceof StairsBlock) {
            return true;
        }

        pos.set(mc.player.getX(), mc.player.getY() - 1, mc.player.getZ());
        return mc.world.getBlockState(pos).getBlock() instanceof StairsBlock;
    }
}
