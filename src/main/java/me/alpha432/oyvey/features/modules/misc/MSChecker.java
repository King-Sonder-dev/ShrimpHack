package me.alpha432.oyvey.features.modules.misc;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.features.commands.Command;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;

public class MSChecker extends Module {

    public MSChecker() {
        super("MSChecker", "", Category.MISC, true, false, false);
    }


    @Override
    public void onEnable() {
        Command.sendMessage("MS: " + OyVey.serverManager.getPing());
        this.disable();
    }
}
