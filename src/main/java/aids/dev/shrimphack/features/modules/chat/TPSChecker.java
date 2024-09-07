package aids.dev.shrimphack.features.modules.chat;

import aids.dev.shrimphack.features.commands.Command;
import aids.dev.shrimphack.features.modules.Module;
import me.alpha432.oyvey.OyVey;


public class TPSChecker extends Module {
    public TPSChecker() {super("TPSChecker", "", Category.CHAT, true, false, false);
    }
    @Override
    public void onEnable() {
        Command.sendMessage("TPS: " + OyVey.serverManager.getTPS());
        this.disable();
    }
}



