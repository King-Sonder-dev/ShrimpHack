package aids.dev.shrimphack.features.modules.chat;

import aids.dev.shrimphack.features.commands.Command;
import aids.dev.shrimphack.features.modules.Module;

public class AutoMeow extends Module {

    public AutoMeow() {super("AutoMeow", "", Category.CHAT, true, false, false);
    }

    @Override
    public void onEnable() {
        Command.serverSendMessage("meow");
        this.disable();
    }



}
