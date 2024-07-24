package me.alpha432.oyvey.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RotationUtil {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static Vec3d getEyesPos() {
        return new Vec3d(mc.player.getX(), mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()), mc.player.getZ());
    }
    public static float normalizeAngle(float angle, int i) {
        while (angle < 0) {
            angle += 360;
        }
        while (angle >= 360) {
            angle -= 360;
        }
        return angle;
    }
    public static double[] calculateLookAt(double px, double py, double pz, Entity me) {
        double dirx = me.getX() - px;
        double diry = me.getY() - py;
        double dirz = me.getZ() - pz;
        double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
        double pitch = Math.asin(diry / len);
        double yaw = Math.atan2(dirz / len, dirx / len);
        pitch = pitch * 180.0 / Math.PI;
        yaw = yaw * 180.0 / Math.PI;
        return new double[]{yaw + 90.0, pitch};
    }

    public static float[] getLegitRotations(Vec3d vec) {
        Vec3d eyesPos = getEyesPos();
        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{mc.player.getYaw() + MathHelper.wrapDegrees(yaw - mc.player.getYaw()), mc.player.getPitch() + MathHelper.wrapDegrees(pitch - mc.player.getPitch())};
    }

    public static void faceYawAndPitch(float yaw, float pitch) {
        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(yaw, pitch, mc.player.isOnGround()));
    }

    public static void faceVector(Vec3d vec, boolean normalizeAngle) {
        float[] rotations = getLegitRotations(vec);
        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(rotations[0], normalizeAngle ? (float) normalizeAngle((int) rotations[1], 360) : rotations[1], mc.player.isOnGround()));
    }

    public static void faceEntity(Entity entity) {
        float[] angle = MathUtil.calcAngle(getEyesPos(), entity.getPos());
        faceYawAndPitch(angle[0], angle[1]);
    }

    public static float[] getAngle(Entity entity) {
        return MathUtil.calcAngle(getEyesPos(), entity.getPos());
    }

    public static int getDirection4D() {
        return MathHelper.floor((mc.player.getYaw() * 4.0f / 360.0f) + 0.5) & 3;
    }

    public static String getDirection4D(boolean northRed) {
        int dirnumber = getDirection4D();
        if (dirnumber == 0) {
            return "South (+Z)";
        } else if (dirnumber == 1) {
            return "West (-X)";
        } else if (dirnumber == 2) {
            return (northRed ? "§c" : "") + "North (-Z)";
        } else if (dirnumber == 3) {
            return "East (+X)";
        } else {
            return "Loading...";
        }
    }
}
