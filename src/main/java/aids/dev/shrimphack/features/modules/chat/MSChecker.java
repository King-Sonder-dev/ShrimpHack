package aids.dev.shrimphack.features.modules.chat;

import aids.dev.shrimphack.features.commands.Command;
import aids.dev.shrimphack.features.modules.Module;
import me.alpha432.oyvey.OyVey;

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
