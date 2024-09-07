package aids.dev.shrimphack.features.modules.player;

import aids.dev.shrimphack.Shrimphack;
import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.settings.Setting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class AutoPhase extends Module {

    private final Setting<Boolean> rotateDown = register(new Setting<>("RotateDown", false));
    private final Setting<Integer> delay = register(new Setting<>("Delay", 0, 0, 5));
    private int tickCounter = 0;

    public AutoPhase() {
        super("AutoPhase", "6b6bleh", Category.PLAYER, true, false, false);
    }

    @Override
    public void onUpdate() {
        MinecraftClient mc = MinecraftClient.getInstance();
        ClientPlayerEntity player = mc.player;

        if (player == null) return;

        if (tickCounter >= delay.getValue() * 20) { // Convert delay from seconds to ticks (20 ticks per second)
            placeItemsAtFeet(player);
            throwPearlSilently(player);
            tickCounter = 0;
        } else {
            tickCounter++;
        }
    }



    private void placeItemsAtFeet(ClientPlayerEntity player) {
        BlockPos feetPos = player.getBlockPos();
        placeItem(player, feetPos, Items.COBWEB);
        placeItem(player, feetPos, Items.STONE_BUTTON);
        placeItem(player, feetPos, Items.OAK_PRESSURE_PLATE);
        // Add more items as needed
    }

    private void placeItem(ClientPlayerEntity player, BlockPos pos, Item item) {
        ItemStack itemStack = new ItemStack(item);
        if (player.getInventory().contains(itemStack)) {
            int itemSlot = player.getInventory().getSlotWithStack(itemStack);
            player.getInventory().selectedSlot = itemSlot;

            Vec3d hitVec = new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
            BlockHitResult hitResult = new BlockHitResult(hitVec, Direction.UP, pos, false);
            player.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, hitResult, 0));
        }
    }

    private void throwPearlSilently(ClientPlayerEntity player) {
        if (rotateDown.getValue()) {
            Shrimphack.rotationManager.setYaw(Shrimphack.rotationManager.getYaw()); // Adjust yaw if needed
            Shrimphack.rotationManager.setPitch(90); // Rotate down
        }

        ItemStack pearlStack = new ItemStack(Items.ENDER_PEARL);
        if (player.getInventory().contains(pearlStack)) {
            player.getInventory().selectedSlot = player.getInventory().getSlotWithStack(pearlStack);

            Vec3d throwPos = findNearestCornerOrEdge(player);
            if (throwPos != null) {
                player.networkHandler.sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 0));
            }
        }
    }

    private Vec3d findNearestCornerOrEdge(ClientPlayerEntity player) {
        BlockPos playerPos = player.getBlockPos();
        Vec3d playerVec = player.getPos();

        // Check for corners and edges around the player
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (Math.abs(x) + Math.abs(y) + Math.abs(z) == 1 || Math.abs(x) + Math.abs(y) + Math.abs(z) == 2) {
                        BlockPos pos = playerPos.add(x, y, z);
                        Vec3d cornerVec = new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                        if (playerVec.distanceTo(cornerVec) < 2.0) {
                            return cornerVec;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String getDisplayInfo() {
        int remainingTicks = delay.getValue() * 20 - tickCounter;
        if (remainingTicks <= 0) {
            return "\u00A7aREADY"; // Green color code
        } else {
            double remainingSeconds = remainingTicks / 20.0;
            if (remainingSeconds <= 1.0) {
                return String.format("\u00A7c%.1f", remainingSeconds); // Red color code
            } else {
                return String.format("%.1f", remainingSeconds);
            }
        }
    }
}