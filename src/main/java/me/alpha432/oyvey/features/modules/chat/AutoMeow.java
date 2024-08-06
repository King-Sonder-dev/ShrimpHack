package me.alpha432.oyvey.features.modules.chat;

import me.alpha432.oyvey.features.commands.Command;
import me.alpha432.oyvey.features.modules.Module;

public class AutoMeow extends Module {

    public AutoMeow() {super("AutoMeow", "", Category.CHAT, true, false, false);
    }

    @Override
    public void onEnable() {
        Command.serverSendMessage("meow");
        this.disable();
    }



}
