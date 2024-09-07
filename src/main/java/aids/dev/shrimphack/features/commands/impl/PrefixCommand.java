package aids.dev.shrimphack.features.commands.impl;

import aids.dev.shrimphack.features.commands.Command;
import me.alpha432.oyvey.OyVey;
import net.minecraft.util.Formatting;

public class PrefixCommand
        extends Command {
    public PrefixCommand() {
        super("prefix", new String[]{"<char>"});
    }

    @Override
    public void execute(String[] commands) {
        if (commands.length == 1) {
            Command.sendMessage(Formatting.GREEN + "Current prefix is " + OyVey.commandManager.getPrefix());
            return;
        }
        OyVey.commandManager.setPrefix(commands[0]);
        Command.sendMessage("Prefix changed to " + Formatting.GRAY + commands[0]);
    }
}