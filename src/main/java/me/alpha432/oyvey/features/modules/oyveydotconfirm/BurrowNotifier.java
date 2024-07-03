package me.alpha432.oyvey.features.modules.oyveydotconfirm;

import me.alpha432.oyvey.features.commands.Command;
import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BurrowNotifier extends Module {

    public BurrowNotifier() {
        super("BurrowNotifier", "Notifies when a player is burrowed.", Category.OYVEYDOTCONFIRM, true, false, false);
    }

    @Override
    public void onEnable() {
        checkBurrow();
        this.disable();
    }

    private void checkBurrow() {
        MinecraftClient mc = MinecraftClient.getInstance();
        World world = mc.world;
        if (world == null) return;

        for (PlayerEntity player : world.getPlayers()) {
            if (player == mc.player) continue; // Skip the local player

            BlockPos playerPos = player.getBlockPos();

            // Check if the player is burrowed (e.g., if the player's position is blocked by a solid block)
            if (world.getBlockState(playerPos).isSolidBlock(world, playerPos)) {
                Command.serverSendMessage(player.getName().getString() + " is burrowed!");
            }
        }
    }
}