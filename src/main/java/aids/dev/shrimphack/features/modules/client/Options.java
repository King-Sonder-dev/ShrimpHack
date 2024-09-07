package aids.dev.shrimphack.features.modules.client;

import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.settings.Setting;

public class Options extends Module {
    public Options() {
        super("Options", "Change settings", Category.CLIENT, true, false, false);
    }
    public final Setting<Boolean> mainmenu = this.register(new Setting<>("Mainmenu", false));
    public final Setting<Boolean> title = this.register(new Setting<>("Title", false));


}
