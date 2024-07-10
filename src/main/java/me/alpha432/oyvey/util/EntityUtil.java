package me.alpha432.oyvey.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class EntityUtil {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

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
}