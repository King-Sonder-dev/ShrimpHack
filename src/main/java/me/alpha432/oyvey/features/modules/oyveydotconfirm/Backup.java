package me.alpha432.oyvey.features.modules.oyveydotconfirm;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.manager.PositionManager;
import me.alpha432.oyvey.util.ChatUtil;

public class Backup extends Module{

    public Backup() {
        super("Backupcaller", "", Module.Category.OYVEYDOTCONFIRM, true, false, false);
    }

    @Override
    public void onEnable(){
        ChatUtil.serverSendMessage("I need Backup " + mc.player.getX() + " " + mc.player.getY() + " " + mc.player.getZ());
this.disable();
    }

}
