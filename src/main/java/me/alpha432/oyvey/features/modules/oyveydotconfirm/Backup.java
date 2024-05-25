package me.alpha432.oyvey.features.modules.oyveydotconfirm;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.manager.PositionManager;
import me.alpha432.oyvey.util.ChatUtil;

public class Backup extends Module{

    public Backup() {
        super("Backupcaller", "", Module.Category.OYVEYDOTCONFIRM, true, false, false);
    }
    private double x;
    private double y;
    private double z;
    private double coords;
    public void updatePosition() {
        this.x = mc.player.getX();
        this.y = mc.player.getY();
        this.z = mc.player.getZ();
        this.coords = mc.player.getX(); mc.player.getY(); mc.player.getZ();
    }
    @Override
    public void onEnable(){
        ChatUtil.serverSendMessage("I need Backup " + mc.player.getX() + " " + mc.player.getY() + " " + mc.player.getZ());
this.disable();
    }

}
