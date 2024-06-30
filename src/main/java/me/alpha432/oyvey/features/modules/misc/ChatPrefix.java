package me.alpha432.oyvey.features.modules.misc;

import me.alpha432.oyvey.features.commands.Command;
import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ChatPrefix extends Module {
    public ChatPrefix() {
        super("ChatPrefix", "Appends | oyvey to every chat message", Category.MISC, true, false, false);
    }

    @Override
    public void onEnable() {
        // No specific initialization needed for this module
    }

    @Override
    public void onDisable() {
        // No specific cleanup needed for this module
    }

    public void onSendMessage(String message) {
        if (isEnabled()) {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            if (player != null) {
                String newMessage = message + " | oyvey";
                Command.serverSendMessage(newMessage);
            }
        }
    }
}