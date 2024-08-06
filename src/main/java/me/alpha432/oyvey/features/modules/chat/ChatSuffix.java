package me.alpha432.oyvey.features.modules.chat;

import com.google.common.eventbus.Subscribe;
import me.alpha432.oyvey.event.impl.PacketEvent;
import me.alpha432.oyvey.features.commands.Command;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;

import java.util.Objects;

public class ChatSuffix extends Module {
    private static ChatSuffix INSTANCE = new ChatSuffix();
    public static String SUFFIX = "";
    private String string;


    public ChatSuffix() {super("ChatSuffix", "append a string to your message", Category.CHAT, true, false, false); }

    public Setting<Suffix> suffix = this.register(new Setting<Suffix>("Suffix", Suffix.NONE, "Your Suffix."));
    public Setting<String> custom = this.register(new Setting("Custom", "Oyvey++", v -> this.suffix.getValue() == Suffix.CUSTOM));

    @Override
    public void onEnable() {
        Command.serverSendMessage("meow");
        this.disable();
    }

    public static ChatSuffix getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChatSuffix();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Subscribe
    public void onPacketSend(PacketEvent.Send event) {
        if (event.getPacket() instanceof ChatMessageC2SPacket packet) {
            if (packet.chatMessage().startsWith("/") || packet.chatMessage().startsWith("+")) {
                return;
            }
            if (Objects.equals(packet.chatMessage(), string)) {
                return;
            }

            switch (this.suffix.getValue()) {
                case FUTURE: {
                    SUFFIX = " \u00bb " + this.convertToUnicode("future v2.13.5");
                    break;
                }
                case ABYSS: {
                    SUFFIX = " \u00bb " + this.convertToUnicode("abyss v4.0");
                    break;
                }
                case PYRO: {
                    SUFFIX = this.convertToUnicode(" » ҉ ᴘʏʀᴏ ᴄʟɪᴇɴᴛ ҉");
                    break;
                }
                case RUSHERHACK: {
                    SUFFIX = " \u00bb " + this.convertToUnicode("Rusherhack");
                    break;
                }
                case PHOBOS: {
                    SUFFIX = " \u00bb " + this.convertToUnicode("phobos.eu");
                    break;
                }
                case EARTHACK: {
                    SUFFIX = " \u00bb " + this.convertToUnicode("earthack v1.7 - 1.20.4");
                    break;
                }
                case PUTAHACK: {
                    SUFFIX = " \u00bb " + this.convertToUnicode("Putahack.nn");
                    break;
                }
                case BOZE: {
                    SUFFIX = " \u00bb " + this.convertToUnicode("boze.dev");
                    break;
                }
                case LEUXBACKDOOR: {
                    SUFFIX = " \u00bb " + this.convertToUnicode("leuxbackdoor v0.9.9 - 1.20.4");
                    break;
                }
                case OYVEY: {
                    SUFFIX = " \u00bb " + this.convertToUnicode("oyvey 0.8");
                    break;
                }
                case SNOW: {
                    SUFFIX = " \u00bb " + this.convertToUnicode("snow v5.1.1");
                    break;
                }
                case OCTOHACK: {
                    SUFFIX = " \u23d0 " + this.convertToUnicode("ΞＯᴄᴛ๏ɦΛᴄᏦΞ");
                    break;
                }
                case OCTOHACKPLUS: {
                    SUFFIX = " \u23d0 " + this.convertToUnicode("✳ΞＯᴄᴛ๏ɦΛᴄᏦ ₱ⱠᑘֆΞ✳");
                    break;
                }
                case NEPTUNE: {
                    SUFFIX = " \u23d0 " + this.convertToUnicode(" ̷Ｎєρｔυｎᴇ̷");
                    break;
                }
                case VONWARE: {
                    SUFFIX = " \u23d0 " + this.convertToUnicode("✴Ｖ♡ｎｗᗩⱤε.ᴅᴇᴠ✴");
                    break;
                }
                case HEPHAESTUS: {
                    SUFFIX = " \u23d0 " + this.convertToUnicode("НεᎮнᗩεѕƭυѕ");
                    break;
                }
                case TROLL: {
                    SUFFIX = " \u23d0 " + this.convertToUnicode("eeᴇᴇʀʜᴇᴄᴋ ɪ.2.1 ⏐ ᴘᴇʀʏᨃs ᴘᴏʙᴏʙⅽ ⏐ ᴋᴀm1 ᴀⅰvⅽ55 ⏐ ᴘʜᴏʙᴇⅽ ⏐ mᴇɢʏn ɪⅽ ᴀⅰᴛ ⏐ 3vᴛ ⏐ ЕЕЕuropa ⏐ (っ•ω•)っ ♥ ⏐ SMP WITHOUT AUXOL! ♥ ⏐ Oguzmad.inc\n");
                    break;
                }
                case CUSTOM: {
                    SUFFIX = " \u00bb " + this.convertToUnicode(this.custom.getValue());
                    break;
                }
                case NONE: {
                    SUFFIX = "";
                    break;
                }
            }
            event.setCancelled(true);
            mc.player.networkHandler.sendChatMessage(packet.chatMessage() + SUFFIX);
        }
    }

    private String convertToUnicode(String base) {
        String new_base = base;

        new_base = new_base.replace("a", "\u1d00");
        new_base = new_base.replace("b", "\u0299");
        new_base = new_base.replace("c", "\u1d04");
        new_base = new_base.replace("d", "\u1d05");
        new_base = new_base.replace("e", "\u1d07");
        new_base = new_base.replace("f", "\u0493");
        new_base = new_base.replace("g", "\u0262");
        new_base = new_base.replace("h", "\u029c");
        new_base = new_base.replace("i", "\u026a");
        new_base = new_base.replace("j", "\u1d0a");
        new_base = new_base.replace("k", "\u1d0b");
        new_base = new_base.replace("l", "\u029f");
        new_base = new_base.replace("m", "\u1d0d");
        new_base = new_base.replace("n", "\u0274");
        new_base = new_base.replace("o", "\u1d0f");
        new_base = new_base.replace("p", "\u1d18");
        new_base = new_base.replace("q", "\u01eb");
        new_base = new_base.replace("r", "\u0280");
        new_base = new_base.replace("s", "\u0455");
        new_base = new_base.replace("t", "\u1d1b");
        new_base = new_base.replace("u", "\u1d1c");
        new_base = new_base.replace("v", "\u1d20");
        new_base = new_base.replace("w", "\u1d21");
        new_base = new_base.replace("x", "\u0445");
        new_base = new_base.replace("y", "\u028f");
        new_base = new_base.replace("z", "\u1d22");

        return new_base;
    }

    public enum Suffix {
        NONE,
        FUTURE,
        ABYSS,
        PYRO,
        RUSHERHACK,
        PHOBOS,
        EARTHACK,
        PUTAHACK,
        BOZE,
        LEUXBACKDOOR,
        OYVEY,
        SNOW,
        OCTOHACK,
        OCTOHACKPLUS,
        NEPTUNE,
        VONWARE,
        VONWAREBETA,
        HEPHAESTUS,
        TROLL,
        CUSTOM;
    }
}
