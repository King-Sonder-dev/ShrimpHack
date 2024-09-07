package aids.dev.shrimphack.features.commands.impl;

import aids.dev.shrimphack.Shrimphack;
import aids.dev.shrimphack.features.commands.Command;
import aids.dev.shrimphack.features.modules.Module;

public class ToggleCommand extends Command {
    public ToggleCommand() {
        super("toggle", new String[] {"<module>"});
    }

    @Override public void execute(String[] var1) {
        if (var1.length < 1 || var1[0] == null) {
            notFound();
            return;
        }
        Module mod = Shrimphack.moduleManager.getModuleByName(var1[0]);
        if (mod == null) {
            notFound();
            return;
        }
        mod.toggle();
    }

    private void notFound() {
        sendMessage("Module is not found.");
    }
}
