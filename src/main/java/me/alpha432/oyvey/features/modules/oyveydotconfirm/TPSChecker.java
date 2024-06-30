package me.alpha432.oyvey.features.modules.oyveydotconfirm;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.features.commands.Command;
import me.alpha432.oyvey.features.modules.Module;


public class TPSChecker extends Module {
    public TPSChecker() {super("TPSChecker", "", Module.Category.OYVEYDOTCONFIRM, true, false, false);
    }
    @Override
    public void onEnable() {
        Command.sendMessage("TPS: " + OyVey.serverManager.getTPS());
        this.disable();
    }
}



