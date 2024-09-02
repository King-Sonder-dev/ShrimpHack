package me.alpha432.oyvey.features.modules.chat;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.event.impl.EventHandler;
import me.alpha432.oyvey.features.commands.Command;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.modules.misc.Fakeplayer.utils.ChatUtils;
import me.alpha432.oyvey.manager.FriendManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.message.MessageType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking.PlayChannelHandler;

import java.util.stream.Stream;

public class AutoReply extends Module {
    private final MinecraftClient mc = MinecraftClient.getInstance();

    public AutoReply() {
        super("AutoReply", "", Category.CHAT, true, false, false);
    }

    private boolean isWhisperMessage(String message) {
        return Stream.of("whispers:", "whispers to you:", "says:").anyMatch(message::contains);
    }

    private boolean isFriend(String name) {
        // Implement your friend checking logic here
        return OyVey.friendManager.isFriend(name);
    }

    private void sendReply(String name, String message) {
        String reply;
        if (Stream.of("coords", "coord", "wya").anyMatch(message::contains)) {
            reply = getCoords();
        } else {
            reply = "<Coords>";
        }

        if (reply.equalsIgnoreCase("<Coords>")) {
            return;
        }

        Command.serverSendMessage("/msg " + name + " " + reply);
    }

    private String getCoords() {
        return "XYZ: " + (int)mc.player.getX() + ", " + (int)mc.player.getY() + ", " + (int)mc.player.getZ();
    }
}
