package me.alpha432.oyvey.event.impl;

import me.alpha432.oyvey.features.modules.misc.ChatPrefix;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public class ChatMessageEvent {
    private static final ChatPrefix chatPrefixModule = new ChatPrefix();

    public static void onChatMessageSend(String message) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null && chatPrefixModule.isEnabled()) {
            chatPrefixModule.onSendMessage(message);
        }
    }
}