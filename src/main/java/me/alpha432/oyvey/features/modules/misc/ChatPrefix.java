package me.alpha432.oyvey.features.modules.misc;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.commands.Command;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

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
            String newMessage = message + " | oyvey";
            Command.serverSendMessage(newMessage);
        }
    }
}