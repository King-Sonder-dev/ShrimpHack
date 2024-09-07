package aids.dev.shrimphack.features.modules.misc;

import aids.dev.shrimphack.Shrimphack;
import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.settings.Setting;
import aids.dev.shrimphack.manager.RotationManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class AntiAim extends Module {
    private final Setting<Integer> yawOffset = this.register(new Setting<>("YawOffset", 0, -180, 180));
    private final Setting<Integer> pitchOffset = this.register(new Setting<>("PitchOffset", 0, -90, 90));
    private final Setting<Boolean> jitter = this.register(new Setting<>("Jitter", false));
    private final Setting<Boolean> blockCheck = this.register(new Setting<>("BlockCheck", true));
    private final Setting<Boolean> jumpRotate = this.register(new Setting<>("JumpRotate", true));
    private final Setting<Boolean> jumpMoveSpin = this.register(new Setting<>("JumpMoveSpin", true));

    public AntiAim() {
        super("AntiAim", "Advanced anti-aim functionality", Category.MISC, true, false, false);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.world == null) return;

        float yaw = mc.player.getYaw() + yawOffset.getValue();
        float pitch = mc.player.getPitch() + pitchOffset.getValue();

        if (jitter.getValue()) {
            yaw += (mc.player.age % 2 == 0 ? -1 : 1) * (Math.random() * 10);
        }

        if (blockCheck.getValue()) {
            Vec3d playerPos = mc.player.getPos();
            BlockPos blockPos = new BlockPos((int) playerPos.x, (int) (playerPos.y - 1), (int) playerPos.z);

            if (!mc.world.getBlockState(blockPos).isAir()) {
                yaw = getBlockRotationYaw(yaw);
            }
        }

        if (jumpRotate.getValue() && !mc.player.isOnGround()) {
            yaw += 15;
            if (yaw > 360) {
                yaw -= 360;
            }
        }

        if (jumpMoveSpin.getValue() && !mc.player.isOnGround() && mc.player.forwardSpeed != 0) {
            yaw = (mc.player.age * 10) % 360;
        }

        RotationManager rotationManager = Shrimphack.rotationManager;
        rotationManager.setRotation(yaw, pitch, true);
    }

    private float getBlockRotationYaw(float currentYaw) {
        BlockPos pos = new BlockPos((int) mc.player.getPos().x, (int) (mc.player.getPos().y - 1), (int) mc.player.getPos().z);
        if (!mc.world.getBlockState(pos).isAir()) {
            return currentYaw + 180;
        }
        return currentYaw;
    }
}