package aids.dev.shrimphack.util.World;

import aids.dev.shrimphack.util.traits.Util;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.Objects;

public class WorldUtil implements Util {
    private static MinecraftClient mc = MinecraftClient.getInstance();

    public static boolean isClickable(Block block) {
        return block instanceof CraftingTableBlock
                || block instanceof AnvilBlock
                || block instanceof ButtonBlock
                || block instanceof AbstractPressurePlateBlock
                || block instanceof BlockWithEntity
                || block instanceof BedBlock
                || block instanceof FenceGateBlock
                || block instanceof DoorBlock
                || block instanceof NoteBlock
                || block instanceof TrapdoorBlock;
    }

    public static boolean place(BlockPos blockPos, Hand hand, int slot, boolean swingHand, boolean checkEntities) {
        if (slot < 0 || slot > 8) return false;
        if (!canPlace(blockPos, checkEntities)) return false;

        Vec3d hitPos = Vec3d.ofCenter(blockPos);

        BlockPos neighbour;
        Direction side = getPlaceSide(blockPos);

        if (side == null) {
            side = Direction.UP;
            neighbour = blockPos;
        } else {
            neighbour = blockPos.offset(side);
            hitPos = hitPos.add(side.getOffsetX() * 0.5, side.getOffsetY() * 0.5, side.getOffsetZ() * 0.5);
        }

        BlockHitResult bhr = new BlockHitResult(hitPos, side.getOpposite(), neighbour, false);

        mc.player.getInventory().selectedSlot = slot;

        interact(bhr, hand, swingHand);

        return true;
    }

    public static void interact(BlockHitResult blockHitResult, Hand hand, boolean swing) {
        boolean wasSneaking = mc.player.input.sneaking;
        mc.player.input.sneaking = false;

        ActionResult result = mc.interactionManager.interactBlock(mc.player, hand, blockHitResult);

        if (result.shouldSwingHand()) {
            if (swing) mc.player.swingHand(hand);
            else mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(hand));
        }

        mc.player.input.sneaking = wasSneaking;
    }

    public static void interactBlock(BlockHitResult blockHitResult, Hand hand, boolean swing) {
        PlayerInteractBlockC2SPacket interactBlockC2SPacket = new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, blockHitResult, 0);
        Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(interactBlockC2SPacket);
    }


    public static boolean canPlace(BlockPos blockPos, boolean checkEntities) {
        if (blockPos == null) return false;

        // Check y level
        if (!World.isValid(blockPos)) return false;

        if (!mc.world.getBlockState(blockPos).isReplaceable()) return false;

        return !checkEntities || mc.world.canPlace(Blocks.OBSIDIAN.getDefaultState(), blockPos, ShapeContext.absent());
    }

    public static boolean canPlace(BlockPos blockPos) {
        return canPlace(blockPos, true);
    }

    public static Direction getPlaceSide(BlockPos blockPos) {
        for (Direction side : Direction.values()) {
            BlockPos neighbor = blockPos.offset(side);
            BlockState state = mc.world.getBlockState(neighbor);

            if (state.isAir() || isClickable(state.getBlock())) continue;

            if (!state.getFluidState().isEmpty()) continue;

            return side;
        }

        return null;
    }

    public static Vec3d getLegitLookPos(BlockPos pos, Direction dir, boolean raycast, int res) {
        return getLegitLookPos(new Box(pos), dir, raycast, res, 0.01);
    }

    public static Vec3d getLegitLookPos(Box box, Direction dir, boolean raycast, int res, double extrude) {
        Vec3d eyePos = mc.player.getEyePos();
        Vec3d blockPos = new Vec3d(box.minX, box.minY, box.minZ).add(
                (dir == Direction.WEST ? -extrude : dir.getOffsetX() * box.getLengthX() + extrude),
                (dir == Direction.DOWN ? -extrude : dir.getOffsetY() * box.getLengthY() + extrude),
                (dir == Direction.NORTH ? -extrude : dir.getOffsetZ() * box.getLengthZ() + extrude));

        for (double i = 0; i <= 1; i += 1d / (double) res) {
            for (double j = 0; j <= 1; j += 1d / (double) res) {
                Vec3d lookPos = blockPos.add(
                        (dir.getAxis() == Direction.Axis.X ? 0 : i * box.getLengthX()),
                        (dir.getAxis() == Direction.Axis.Y ? 0 : dir.getAxis() == Direction.Axis.Z ? j * box.getLengthY() : i * box.getLengthY()),
                        (dir.getAxis() == Direction.Axis.Z ? 0 : j * box.getLengthZ()));

                if (eyePos.distanceTo(lookPos) > 4.55)
                    continue;

                if (raycast) {
                    if (mc.world.raycast(new RaycastContext(eyePos, lookPos,
                            RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, mc.player)).getType() == HitResult.Type.MISS) {
                        return lookPos;
                    }
                } else {
                    return lookPos;
                }
            }
        }

        return null;
    }

    public static void facePosAuto(double x, double y, double z) {
        facePos(x, y, z);
    }

    public static void facePos(double x, double y, double z) {
        float[] rot = getViewingRotation(mc.player, x, y, z);

        mc.player.setYaw(mc.player.getYaw() + MathHelper.wrapDegrees(rot[0] - mc.player.getYaw()));
        mc.player.setPitch(mc.player.getPitch() + MathHelper.wrapDegrees(rot[1] - mc.player.getPitch()));
    }

    public static float[] getViewingRotation(Entity entity, double x, double y, double z) {
        double diffX = x - entity.getX();
        double diffY = y - entity.getEyeY();
        double diffZ = z - entity.getZ();

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        return new float[] {
                (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90f,
                (float) -Math.toDegrees(Math.atan2(diffY, diffXZ)) };
    }
}