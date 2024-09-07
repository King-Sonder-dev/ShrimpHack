package aids.dev.shrimphack.features.modules.chat;

import aids.dev.shrimphack.features.commands.Command;
import aids.dev.shrimphack.features.modules.Module;
import me.alpha432.oyvey.OyVey;
import net.minecraft.client.MinecraftClient;

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
