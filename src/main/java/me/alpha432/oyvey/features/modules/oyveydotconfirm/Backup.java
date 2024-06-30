package me.alpha432.oyvey.features.modules.oyveydotconfirm;

import me.alpha432.oyvey.features.commands.Command;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.manager.PositionManager;

public class Backup extends Module{

    public Backup() {
        super("Backupcaller", "", Module.Category.OYVEYDOTCONFIRM, true, false, false);
    }

    @Override
    public void onEnable() {
        String message = String.format("I need Backup X: %.1f Y: %.1f Z: %.1f", mc.player.getX(), mc.player.getY(), mc.player.getZ());
        Command.serverSendMessage(message);
        this.disable();
    }

}
