package aids.dev.shrimphack.features.commands.impl;

import aids.dev.shrimphack.Shrimphack;
import aids.dev.shrimphack.features.commands.Command;
import net.minecraft.util.Formatting;

public class HelpCommand
        extends Command {
    public HelpCommand() {
        super("help");
    }

    @Override
    public void execute(String[] commands) {
        HelpCommand.sendMessage("Commands: ");
        for (Command command : Shrimphack.commandManager.getCommands()) {
            StringBuilder builder = new StringBuilder(Formatting.GRAY.toString());
            builder.append(Shrimphack.commandManager.getPrefix());
            builder.append(command.getName());
            builder.append(" ");
            for (String cmd : command.getCommands()) {
                builder.append(cmd);
                builder.append(" ");
            }
            HelpCommand.sendMessage(builder.toString());
        }
    }
}