package me.alpha432.oyvey.features.modules.client;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;

public class Options extends Module {
    public Options() {
        super("Options", "Change settings", Module.Category.CLIENT, true, false, false);
    }
    public final Setting<Boolean> mainmenu = this.register(new Setting<>("Mainmenu", false));
    public final Setting<Boolean> title = this.register(new Setting<>("Title", false));


}
