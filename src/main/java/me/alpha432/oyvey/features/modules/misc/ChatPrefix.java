package me.alpha432.oyvey.features.modules.misc;

import me.alpha432.oyvey.features.modules.Module;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.util.Identifier;

public class ChatPrefix extends Module {

    private static final Identifier CHAT_PACKET_ID = new Identifier("minecraft", "chat");

    public ChatPrefix() {
        super("ChatPrefix", "Appends | oyvey to every chat message", Category.MISC, true, false, false);
    }

    @Override
    public void onEnable() {
        ClientPlayNetworking.registerGlobalReceiver(CHAT_PACKET_ID, this::onChatMessageSend);
    }

    @Override
    public void onDisable() {
        ClientPlayNetworking.unregisterGlobalReceiver(CHAT_PACKET_ID);
    }

    private void onChatMessageSend(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        ChatMessageC2SPacket packet = new ChatMessageC2SPacket(buf);
        String originalMessage = packet.chatMessage();
        if (!originalMessage.endsWith(" | oyvey")) {
            String newMessage = originalMessage + " | oyvey";
            buf.writeString(newMessage);
        }
    }
}
