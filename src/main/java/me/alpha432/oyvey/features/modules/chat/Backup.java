package me.alpha432.oyvey.features.modules.chat;

import me.alpha432.oyvey.features.commands.Command;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import me.alpha432.oyvey.manager.PositionManager;
import net.minecraft.client.MinecraftClient;

import javax.net.ssl.HttpsURLConnection;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Backup extends Module{
    public final Setting<Sendmode> sendmode = this.register(new Setting<>("MessageStyle", Sendmode.GLOBAL));

    public Backup() {
        super("Backupcaller", "", Category.CHAT, true, false, false);
    }

    @Override
    public void onEnable() {
        if (sendmode.getValue() == Sendmode.GLOBAL) {
            String message = String.format("I need Backup X: %.1f Y: %.1f Z: %.1f", mc.player.getX(), mc.player.getY(), mc.player.getZ());
            Command.serverSendMessage(message);
            this.disable();
        }
        if (sendmode.getValue() == Sendmode.IRC) {
            String username = MinecraftClient.getInstance().getSession().getUsername();
            String icon = "https://minotar.net/avatar/" + username + "/128.png";
            long posX = Math.round(mc.player.getX() / 10) * 10;
            long posZ = Math.round(mc.player.getZ() / 10) * 10;
            String server = mc.getCurrentServerEntry().address;

            String message = "{\"content\":\"<@&1269511091847954525> im on " + server + " at " + posX + " " + posZ + " get on\",\"username\":\"Oyvey++ - " + username + "\",\"avatar_url\":\"" + icon + "\"}";

            try {
                URL url = new URL("https://discord.com/api/webhooks/1269510788649979996/gKEFIJ6x_5ggV5zz2MxJgilKygU6wv8Ia-bxCVV5OWTlrso1Knccju6IH5S23cql2quY");
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.addRequestProperty("Content-Type", "application/json");
                connection.addRequestProperty("User-Agent", "Oyvey++");
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                OutputStream stream = connection.getOutputStream();
                stream.write(message.getBytes(StandardCharsets.UTF_8));
                stream.flush();
                stream.close();
                connection.getInputStream().close();
                connection.disconnect();
                Command.sendMessage("Message sent!");
            } catch (Exception e) {
                Command.sendMessage("Failed to send :(");
            }
            this.disable();
        }
    }
    public enum Sendmode {
        GLOBAL,
        IRC
    }
}
