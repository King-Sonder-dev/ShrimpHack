package aids.dev.shrimphack.features.modules.movement;

import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.settings.Setting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class OnGroundSpeed extends Module {

    private final Setting<Integer> speed = register(new Setting<>("Speed", 1, 1, 5));
    private final Setting<Boolean> strict = register(new Setting<>("Strict", true));

    public OnGroundSpeed() {
        super("OnGroundSpeed", "Increases movement speed on the ground.", Category.MOVEMENT, true, false, false);
    }

    @Override
    public void onUpdate() {
        MinecraftClient mc = MinecraftClient.getInstance();
        ClientPlayerEntity player = mc.player;

        if (player == null || mc.world == null) return;

        if (player.isOnGround() && (player.forwardSpeed != 0 || player.sidewaysSpeed != 0)) {
            double speedValue = speed.getValue() * 0.05; // Scale speed to a reasonable range
            Vec3d forward = new Vec3d(0, 0, speedValue).rotateY(-(float) Math.toRadians(player.getYaw()));
            Vec3d strafe = forward.rotateY((float) Math.toRadians(90));

            if (strict.getValue()) {
                // Strict mode: more cautious movement adjustments to avoid NCP detection
                if (player.forwardSpeed > 0 || player.sidewaysSpeed > 0) {
                    player.setVelocity(player.getVelocity().add(forward).add(strafe));
                }
            } else {
                // Normal mode: direct velocity adjustment
                player.setVelocity(player.getVelocity().add(forward).add(strafe));
            }
        }
    }
}