package me.alpha432.oyvey;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

import me.alpha432.oyvey.features.modules.client.RPC.ImageMode;
import static me.alpha432.oyvey.util.traits.Util.mc;

public class DiscordRP {

    public DiscordRichPresence discordRichPresence = new DiscordRichPresence();
    private Thread presenceThread;

    public void start(int delay, ImageMode imageMode) {
        DiscordEventHandlers eventHandlers = new DiscordEventHandlers();
        String discordID = "1262827280686780607";
        DiscordRPC.INSTANCE.Discord_Initialize(discordID, eventHandlers, true, null);
        discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;

        // Line 1: Use Minecraft username
        discordRichPresence.details = String.valueOf(mc.player.getName().getString());

        // Set images based on the mode
        switch (imageMode) {
            case TIREDBK:
                discordRichPresence.largeImageKey = "tiredbk";
                break;
            case CO6:
                discordRichPresence.largeImageKey = "co6";
                break;
            case NONE:
            default:
                discordRichPresence.largeImageKey = "";
                discordRichPresence.smallImageKey = "";
                break;
        }

        discordRichPresence.largeImageText = "UID: 1";

        discordRichPresence.partyId = "ae488379-351d-4a4f-ad32-2b9b01c91657";
        discordRichPresence.partySize = 1;
        discordRichPresence.partyMax = 10;
        discordRichPresence.joinSecret = "MTI4NzM0OjFpMmhuZToxMjMxMjM= ";
        // Initialize the presence
        DiscordRPC.INSTANCE.Discord_UpdatePresence(discordRichPresence);

        // Start a new thread to update the status periodically
        Thread presenceThread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    // Update state message periodically
                    discordRichPresence.state = !mc.isInSingleplayer() ? "on " + mc.player.getServer().getServerIp() : "in singleplayer";
                    DiscordRPC.INSTANCE.Discord_UpdatePresence(discordRichPresence);
                    Thread.sleep(delay);  // Use the delay from the setting

                    // Change the state message
                    discordRichPresence.state = "Owning " + mc.player.getServer().getServerIp() + " spawn";
                    DiscordRPC.INSTANCE.Discord_UpdatePresence(discordRichPresence);
                    Thread.sleep(delay);  // Use the delay from the setting

                    discordRichPresence.state = "Every opp shot!";
                    DiscordRPC.INSTANCE.Discord_UpdatePresence(discordRichPresence);
                    Thread.sleep(delay);  // Use the delay from the setting

                    discordRichPresence.state = String.valueOf(mc.player.getName().getString()) + " is my daddy :3";
                    DiscordRPC.INSTANCE.Discord_UpdatePresence(discordRichPresence);
                    Thread.sleep(delay);  // Use the delay from the setting

                    discordRichPresence.state = "UwU :3";
                    DiscordRPC.INSTANCE.Discord_UpdatePresence(discordRichPresence);
                    Thread.sleep(delay);  // Use the delay from the setting

                    discordRichPresence.state = "Meow";
                    DiscordRPC.INSTANCE.Discord_UpdatePresence(discordRichPresence);
                    Thread.sleep(delay);  // Use the delay from the setting
                }
            } catch (InterruptedException ignored) {
                // Handle interruption
            }
        });
        presenceThread.start();
    }

    public void stop() {
        if (presenceThread != null && presenceThread.isAlive()) {
            presenceThread.interrupt();
        }
        DiscordRPC.INSTANCE.Discord_Shutdown();
        DiscordRPC.INSTANCE.Discord_ClearPresence();
    }
}