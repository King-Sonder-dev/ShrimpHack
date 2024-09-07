package aids.dev.shrimphack.features.modules.client;

import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.settings.Setting;

public class Cape extends Module {
    private static Cape INSTANCE = new Cape();
        public final Setting<CapeMode> cape = this.register(new Setting<CapeMode>("CapeMode", CapeMode.NONE, "Cape."));

    public Cape() {
        super("Cape", "Cape", Category.CLIENT, true, false, false);
        this.setInstance();
    }

    public static Cape getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Cape();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    public enum CapeMode {
        FUTURE,
        RUSHERHACK,
        HYPER,
        COBALT,
        FOUNDER,
        HIGHLAND,
        CAPY,
        MINECON2011,
        MINECON2012,
        MINECON2013,
        MINECON2015,
        MINECON2016,
        MOJANG,
        MOJANG_CLASSIC,
        MOJANG_STUDIOS,
        PHOBOS,
        RUSHERHACKEK,
        SNOWMAN,
        NONE
    }
}
