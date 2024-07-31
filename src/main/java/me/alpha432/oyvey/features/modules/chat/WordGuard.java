package me.alpha432.oyvey.features.modules.chat;

import me.alpha432.oyvey.features.commands.Command;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import me.alpha432.oyvey.util.models.Timer;
import me.alpha432.oyvey.util.octo.TextUtilOcto;

public class WordGuard extends Module {

    public final Setting<Boolean> packetMessage = register(new Setting<>("Packet Message", true));
    public final Setting<Integer> afterMessageDelay = register(new Setting<>("Delay Seconds", 2, 1, 10));
    public final Setting<Integer> bypassCharacterAmount = register(new Setting<>("Bypass Character Amount", 4, 1, 15));
    public final Setting<Boolean> detectHacking = register(new Setting<>("Detect Cheats", true));
    public final Setting<Boolean> detectThreats = register(new Setting<>("Detect Threats", true));
    public final Setting<Boolean> detectSwearing = register(new Setting<>("Detect Swearing", true));
    private final Timer timer = new Timer();

    public WordGuard() {
        super("WordGuard", "Automatically sends a message if certain words are detected in chat.", Category.CHAT, true, false, false);
    }

    public void onChatSend(String message, boolean isSynchronous) {
        if (timer.passedS(afterMessageDelay.getValue())) {
            String lowerCaseMessage = message.toLowerCase();

            if (detectSwearing.getValue() && containsSwearWords(lowerCaseMessage)) {
                sendMessage("Hey! Sorry, but swearing is not allowed here!");
            } else if (detectThreats.getValue() && containsThreats(lowerCaseMessage)) {
                sendMessage("Hey! Sorry, but that word is not allowed here!");
            } else if (detectHacking.getValue() && containsHacking(lowerCaseMessage)) {
                sendMessage("Hey! Sorry, but cheating is not allowed here!");
            }
        }
    }

    private boolean containsSwearWords(String message) {
        String[] swearWords = {"fuck", "fucking", "bastard", "shit", "ass", "shitty", "faggot", "shitter", "shitbox", "cunt", "fucked", "rape", "raped", "nigger", "nigga", "shitass", "bitch", "retard", "retarded", "fag", "gay", "incel"};
        for (String swear : swearWords) {
            if (message.contains(swear)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsThreats(String message) {
        String[] threats = {"swat", "ddos", "kys"};
        for (String threat : threats) {
            if (message.contains(threat)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsHacking(String message) {
        String[] hacks = {"hack", "cheat", "exploit"};
        for (String hack : hacks) {
            if (message.contains(hack)) {
                return true;
            }
        }
        return false;
    }

    private void sendMessage(String baseMessage) {
        String message = "<WordGuard> " + baseMessage + " " + TextUtilOcto.generateRandomHexSuffix(bypassCharacterAmount.getValue());
        if (packetMessage.getValue()) {
            Command.serverSendMessage(message);
        } else {
            Command.serverSendMessage(message);
        }
        timer.reset();
    }
}