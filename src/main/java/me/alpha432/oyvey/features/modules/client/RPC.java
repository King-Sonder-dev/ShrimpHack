package me.alpha432.oyvey.features.modules.client;

import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.AddServerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;

import me.alpha432.oyvey.util.discord.DiscordEventHandlers;
import me.alpha432.oyvey.util.discord.DiscordRPC;
import me.alpha432.oyvey.util.discord.DiscordRichPresence;

import java.io.*;
import java.util.Objects;

public class RPC extends Module {
    public static DiscordRPC rpc = DiscordRPC.INSTANCE;
    public Setting<Mode> mode = this.register(new Setting<>("Picture", Mode.Normal));
    public Setting<Boolean> showIP = this.register(new Setting<>("ShowIP", true));
    public Setting<sMode> smode = this.register(new Setting<>("StateMode", sMode.Stats));
    public Setting<String> state = this.register(new Setting<>("State", "Oyvey++ - Beta"));
    public Setting<Boolean> nickname = this.register(new Setting<>("Nickname", true));
    public static DiscordRichPresence presence = new DiscordRichPresence();
    public static boolean started;
    static String String1 = "none";
    public static Thread thread;
    //public static RPC instance;
    private static RPC instance = new RPC();

    public RPC() {
        super("DiscordRPC", "RPC Custom", Module.Category.CLIENT, true, false, false);
       // instance = this;
       this.setInstance();
    }

    public static RPC getInstance() {
        if (instance == null) {
            instance = new RPC();
        }
        return instance;
    }

    private void setInstance() {
        instance = this;
    }



    public static void readFile() {
        try {
            File file = new File("OyveyPlusPlus/discord/RPC.txt");
            if (file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    while (reader.ready()) {
                        String1 = reader.readLine();
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    public static void WriteFile(String url1, String url2) {
        File file = new File("OyveyPlusPlus/discord/RPC.txt");
        try {
            file.createNewFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(url1 + "SEPARATOR" + url2 + '\n');
            } catch (Exception ignored) {
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onDisable() {
        started = false;
        if (thread != null && !thread.isInterrupted()) {
            thread.interrupt();
        }
        rpc.Discord_Shutdown();
    }

    @Override
    public void onUpdate() {
        startRpc();
    }

    public void startRpc() {
        if (isDisabled()) return;
        if (!started) {
            started = true;
            DiscordEventHandlers handlers = new DiscordEventHandlers();
            rpc.Discord_Initialize("1262827280686780607", handlers, true, "");
            presence.startTimestamp = (System.currentTimeMillis() / 1000L);
            presence.largeImageText = OyVey.NAME + " " + OyVey.VERSION + " by " + "1nject :3 ";
            rpc.Discord_UpdatePresence(presence);

            thread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    rpc.Discord_RunCallbacks();

                    presence.details = getDetails();

                    switch (smode.getValue()) {
                        case Stats ->
                                presence.state = "Hacks: " + OyVey.moduleManager.getEnabledModules().size() + " / " + OyVey.moduleManager.modules.size();
                        case Custom -> presence.state = state.getValue();
                        case Version -> presence.state = OyVey.NAME + " Beta";
                    }

                    if (nickname.getValue()) {
                        presence.smallImageText = "User - " + mc.getSession().getUsername();
                        presence.smallImageKey = "https://minotar.net/helm/" + mc.getSession().getUsername() + "/100.png";
                    } else {
                        presence.smallImageText = "";
                        presence.smallImageKey = "";
                    }

                    presence.button_label_1 = "Website";
                    presence.button_url_1 = "Pornhub.com";

                    switch (mode.getValue()) {
                        case Normal -> presence.largeImageKey = "https://i.imgur.com/dicoRAD";
                        case KINGVON ->
                                presence.largeImageKey = "https://cdn.discordapp.com/attachments/1068946994871140442/1222595265236570164/caption-11.gif?ex=66afb791&is=66ae6611&hm=df7047c7ef093cc4cfefa115097acc59deca7d223680ceeaf159b3e524139a62&";
                        case Custom -> {
                            readFile();
                            presence.largeImageKey = String1.split("SEPARATOR")[0];
                            if (!Objects.equals(String1.split("SEPARATOR")[1], "none")) {
                                presence.smallImageKey = String1.split("SEPARATOR")[1];
                            }
                        }
                    }
                    rpc.Discord_UpdatePresence(presence);
                    try {
                        Thread.sleep(2000L);
                    } catch (InterruptedException ignored) {
                    }
                }
            }, "RPC-Handler");
            thread.start();
        }
    }

    private String getDetails() {
        String result = "";

        if (mc.currentScreen instanceof TitleScreen) {
            result = "In Main menu";
        } else if (mc.currentScreen instanceof MultiplayerScreen || mc.currentScreen instanceof AddServerScreen) {
            result = "Picks a server";
        } else if (mc.getCurrentServerEntry() != null) {
            result = (showIP.getValue() ? "Playing on " + mc.getCurrentServerEntry().address : "Playing on server");
        } else if (mc.isInSingleplayer()) {
            result =  "SinglePlayer";
        }
        return result;
    }



    private boolean isOn(int x, int z, int x1, int z1) {
        return mc.player.getX() > x && mc.player.getX() < x1 && mc.player.getZ() > z && mc.player.getZ() < z1;
    }

    public enum Mode {
        Custom,
        Normal, 
        KINGVON
    }

    public enum sMode {
        Custom, 
        Stats, 
        Version
    }
}