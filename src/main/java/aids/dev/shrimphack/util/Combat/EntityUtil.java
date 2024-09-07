package aids.dev.shrimphack.util.Combat;

import aids.dev.shrimphack.Shrimphack;
import aids.dev.shrimphack.manager.RotationManager;
import aids.dev.shrimphack.util.World.BlockPosX;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

import static aids.dev.shrimphack.util.World.RotationUtil.getLegitRotations;

public class EntityUtil {

    private static final MinecraftClient mc = MinecraftClient.getInstance();
    public static boolean rotating = false;


    public static double[] forward(double speed) {
        ClientPlayerEntity player = mc.player;
        float forward = player.input.movementForward;
        float side = player.input.movementSideways;
        float yaw = player.prevYaw + (player.getYaw() - player.prevYaw) * mc.getTickDelta();

        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += (float) (forward > 0.0f ? -45 : 45);
            } else if (side < 0.0f) {
                yaw += (float) (forward > 0.0f ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            } else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }

        double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        double posX = (double) forward * speed * cos + (double) side * speed * sin;
        double posZ = (double) forward * speed * sin - (double) side * speed * cos;
        return new double[]{posX, posZ};
    }

    public static void swingArmNoPacket(Hand hand, LivingEntity entity) {
        ItemStack stack = entity.getStackInHand(hand);
        if (!stack.isEmpty() && stack.getItem().useOnEntity(stack, (PlayerEntity) entity, entity, hand) != null) {
            return;
        }
        if (!entity.isUsingItem() || entity.getItemUseTime() >= entity.getItemUseTimeLeft() / 2 || entity.getItemUseTime() < 0) {
            entity.getItemUseTimeLeft();
            entity.swingHand(hand);
        }
    }
    public static BlockPos getPlayerPos() {
        return new BlockPosX(mc.player.getPos());
    }
    public static void sendLook(PlayerMoveC2SPacket packet) {
        if (!packet.changesLook() || packet.getYaw(114514) == Shrimphack.rotationManager.lastYaw && packet.getPitch(114514) == Shrimphack.rotationManager.lastPitch) {
            return;
        }
        rotating = true;
        Shrimphack.rotationManager.setRotation(packet.getYaw(0), packet.getPitch(0), true);
        mc.player.networkHandler.sendPacket(packet);
        rotating = false;
    }

    public static void syncInventory() {
         mc.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(mc.player.currentScreenHandler.syncId));
    }
    public static Vec3d getEyesPos() {
        return mc.player.getEyePos();
    }

    public static boolean canSee(BlockPos pos, Direction side) {
        Vec3d testVec = pos.toCenterPos().add(side.getVector().getX() * 0.5, side.getVector().getY() * 0.5, side.getVector().getZ() * 0.5);
        HitResult result = mc.world.raycast(new RaycastContext(getEyesPos(), testVec, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, mc.player));
        return result == null || result.getType() == HitResult.Type.MISS;
    }
    public static void faceVector(Vec3d directionVec) {
        RotationManager.ROTATE_TIMER.reset();
        RotationManager.directionVec = directionVec;
        float[] angle = getLegitRotations(directionVec);
        if (angle[0] == Shrimphack.rotationManager.lastYaw && angle[1] == Shrimphack.rotationManager.lastPitch) return;
        sendLook(new PlayerMoveC2SPacket.LookAndOnGround(angle[0], angle[1], mc.player.isOnGround()));
    }

    public static void faceVectorNoStay(Vec3d directionVec) {
        float[] angle = getLegitRotations(directionVec);
        if (angle[0] == Shrimphack.rotationManager.lastYaw && angle[1] == Shrimphack.rotationManager.lastPitch) return;
        sendLook(new PlayerMoveC2SPacket.LookAndOnGround(angle[0], angle[1], mc.player.isOnGround()));
    }
}