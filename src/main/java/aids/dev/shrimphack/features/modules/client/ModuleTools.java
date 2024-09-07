package aids.dev.shrimphack.features.modules.client;

import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.settings.Setting;

public class ModuleTools extends Module {

    private static ModuleTools INSTANCE;

    public Setting<Notifier> notifier = register(new Setting("ModuleNotifier", Notifier.NONE));
    public Setting<PopNotifier> popNotifier = register(new Setting("PopNotifier", PopNotifier.NONE));


    public ModuleTools() {
        super("ModuleTools", "Change settings", Category.CLIENT, true, false, false);
        INSTANCE = this;
    }


    public static ModuleTools getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ModuleTools();
        }
        return INSTANCE;
    }


    public enum Notifier {
        NONE,
        SN0W,
        TROLLGOD,
        PHOBOS,
        FUTURE,
        DOTGOD;
    }

    public enum PopNotifier {
        NONE,
        SN0W,
        TROLLGOD,
        PHOBOS,
        FUTURE,
        DOTGOD;
    }


}