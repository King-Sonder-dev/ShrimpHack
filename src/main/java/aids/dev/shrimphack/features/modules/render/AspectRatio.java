package aids.dev.shrimphack.features.modules.render;

import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.settings.Setting;

public class AspectRatio extends Module {
        private static AspectRatio INSTANCE = new AspectRatio();
    public AspectRatio() {
        super("AspectRatio", "AspectRatio", Category.RENDER, true, false, false);
        this.setInstance();
    }

    public static AspectRatio getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AspectRatio();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    public Setting<Float> ratio = this.register(new Setting<>("Ratio", 1.78f, 0.1f, 5f));
}
