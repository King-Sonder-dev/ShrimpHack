package me.alpha432.oyvey.features.modules.client;

import me.alpha432.oyvey.features.modules.Module;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;

public class FastLatency extends Module {

    private int tickCounter = 0;
    private static final int PING_INTERVAL = 10; // Check ping every 10 ticks (0.5 seconds)

    public FastLatency() {
        super("FastLatency", "Checks your ping more frequently.", Category.CLIENT, true, false, false);
    }

    @Override
    public void onEnable() {
        ClientTickEvents.END_CLIENT_TICK.register(this::onEndClientTick);
    }

    @Override
    public void onDisable() {
    }

    private void onEndClientTick(MinecraftClient client) {
        tickCounter++;
        if (tickCounter >= PING_INTERVAL) {
            tickCounter = 0;
            checkPing(client);
        }
    }

    private void checkPing(MinecraftClient client) {
        ClientPlayerEntity player = client.player;
        if (player != null) {
            ServerInfo currentServer = client.getCurrentServerEntry();
            if (currentServer != null) {
                int ping = client.getNetworkHandler().getPlayerListEntry(player.getUuid()).getLatency();
            }
        }
    }
}