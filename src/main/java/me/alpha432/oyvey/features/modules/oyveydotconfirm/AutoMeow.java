package me.alpha432.oyvey.features.modules.oyveydotconfirm;

import me.alpha432.oyvey.features.gui.OyVeyGui;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.util.ChatUtil;

import static me.alpha432.oyvey.util.traits.Util.mc;

public class AutoMeow extends Module {

    public AutoMeow() {super("AutoMeow", "", Module.Category.OYVEYDOTCONFIRM, true, false, false);
    }

    @Override
    public void onEnable() {
        ChatUtil.serverSendMessage("meow");
        this.disable();
    }



}
