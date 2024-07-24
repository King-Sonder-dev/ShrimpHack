package me.alpha432.oyvey.features.modules.misc;

import me.alpha432.oyvey.event.impl.ChatEvent;
import me.alpha432.oyvey.features.commands.Command;
import me.alpha432.oyvey.features.modules.Module;

public class ChatPrefix extends Module {


    public ChatPrefix() {
        super("ChatPrefix", "Appends | oyvey to every chat message", Category.MISC, true, false, false);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public void onMessage(ChatEvent chatEvent) {
        String message = chatEvent.getMessage();
        Command.serverSendMessage(message += " | <Oyvey>");
    }
}