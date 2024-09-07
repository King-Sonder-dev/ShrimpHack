package aids.dev.shrimphack.features.modules.chat;

import aids.dev.shrimphack.Shrimphack;
import aids.dev.shrimphack.features.commands.Command;
import aids.dev.shrimphack.features.modules.Module;


public class TPSChecker extends Module {
    public TPSChecker() {super("TPSChecker", "", Category.CHAT, true, false, false);
    }
    @Override
    public void onEnable() {
        Command.sendMessage("TPS: " + Shrimphack.serverManager.getTPS());
        this.disable();
    }
}



