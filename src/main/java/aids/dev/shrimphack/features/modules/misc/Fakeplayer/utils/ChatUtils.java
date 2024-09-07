package aids.dev.shrimphack.features.modules.misc.Fakeplayer.utils;


import aids.dev.shrimphack.Shrimphack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static aids.dev.shrimphack.util.traits.Util.mc;

public class ChatUtils {

    public static void sendMessage(String message) {
        mc.player.sendMessage(Text.of(Shrimphack.commandManager.getClientMessage() + " " + Formatting.GREEN + message));
    }

    public static void warningMessage(String message) {
        mc.player.sendMessage(Text.of(Shrimphack.commandManager.getClientMessage() + " " + Formatting.RED + message));
    }

}