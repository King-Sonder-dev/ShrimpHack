package me.alpha432.oyvey.features.modules.oyveydotconfirm;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import me.alpha432.oyvey.util.MiningUtil;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import static me.alpha432.oyvey.util.traits.Util.mc;

public class SpeedMine extends Module {

    public enum RotationMode {
        NONE,
        SEMI,
        FULL
    }

    private final Setting<RotationMode> rotate = register(new Setting<>("Rotate", RotationMode.NONE));
    private final Map<BlockPos, Float> miningProgress = new HashMap<>();

    public SpeedMine() {
        super("SpeedMine", "Allows you to mine blocks silently using packets and swap tools silently.", Category.OYVEYDOTCONFIRM, true, false, false);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.world == null) return;

        BlockHitResult hitResult = (BlockHitResult) mc.player.raycast(5.0, 0.0f, false);
        if (hitResult == null || hitResult.getType() != BlockHitResult.Type.BLOCK) return;

        BlockPos targetBlockPos = hitResult.getBlockPos();

        BlockState targetBlockState = mc.world.getBlockState(targetBlockPos);

        silentlySwapTool(targetBlockState);
        handleRotation(targetBlockPos, targetBlockState);
        updateMiningProgress(targetBlockPos, targetBlockState);
    }

    private void silentlySwapTool(BlockState targetBlockState) {
        for (int i = 0; i < MiningUtil.getInventorySize(); i++) {
            ItemStack itemStack = mc.player.getInventory().getStack(i);
            if (itemStack.isSuitableFor(targetBlockState)) {
                mc.player.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, new BlockHitResult(Vec3d.ZERO, Direction.UP, BlockPos.ORIGIN, false), 0));
                return;
            }
        }
    }

    private void handleRotation(BlockPos targetBlockPos, BlockState targetBlockState) {
        switch (rotate.getValue()) {
            case NONE:
                break;
            case SEMI:
                if (targetBlockState.getHardness(mc.world, targetBlockPos) < 0.5f) {
                    rotateToBlock(targetBlockPos);
                }
                break;
            case FULL:
                rotateToBlock(targetBlockPos);
                break;
        }
    }

    private void rotateToBlock(BlockPos targetBlockPos) {
        Vec3d blockCenter = Vec3d.ofCenter(targetBlockPos);
        double diffX = blockCenter.x - mc.player.getX();
        double diffY = blockCenter.y - mc.player.getY();
        double diffZ = blockCenter.z - mc.player.getZ();
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float) -Math.toDegrees(Math.atan2(diffY, diffXZ));

        mc.player.setYaw(yaw);
        mc.player.setPitch(pitch);
    }

    private void updateMiningProgress(BlockPos targetBlockPos, BlockState targetBlockState) {
        float hardness = targetBlockState.getHardness(mc.world, targetBlockPos);
        float progress = miningProgress.getOrDefault(targetBlockPos, 0f) + 0.1f;

        if (progress >= hardness) {
            sendBlockBreakPacket(targetBlockPos);
            miningProgress.remove(targetBlockPos);
        } else {
            miningProgress.put(targetBlockPos, progress);
        }
    }

    private void sendBlockBreakPacket(BlockPos blockPos) {
        PlayerActionC2SPacket.Action action = PlayerActionC2SPacket.Action.START_DESTROY_BLOCK;
        Direction direction = Direction.UP;

        PlayerActionC2SPacket packet = new PlayerActionC2SPacket(action, blockPos, direction);
        mc.player.networkHandler.sendPacket(packet);
    }

    public void onRender(MatrixStack matrices, float tickDelta) {
        if (mc.world == null) return;

        BlockRenderManager blockRenderManager = mc.getBlockRenderManager();
        VertexConsumerProvider.Immediate vertexConsumerProvider = mc.getBufferBuilders().getEntityVertexConsumers();

        for (Map.Entry<BlockPos, Float> entry : miningProgress.entrySet()) {
            BlockPos blockPos = entry.getKey();
            float progress = entry.getValue();

            BlockState blockState = mc.world.getBlockState(blockPos);
            float hardness = blockState.getHardness(mc.world, blockPos);
            float progressRatio = progress / hardness;

            Color color = new Color(Color.HSBtoRGB(progressRatio / 3f, 1f, 1f));

            matrices.push();
            matrices.translate(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            blockRenderManager.renderBlock(blockState, blockPos, mc.world, matrices, vertexConsumerProvider.getBuffer(RenderLayer.getCutout()), false, mc.world.random);
            matrices.pop();
        }

        vertexConsumerProvider.draw();
    }
}