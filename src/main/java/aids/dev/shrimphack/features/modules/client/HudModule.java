package aids.dev.shrimphack.features.modules.client;

import aids.dev.shrimphack.Shrimphack;
import aids.dev.shrimphack.event.impl.Render2DEvent;
import aids.dev.shrimphack.features.gui.hud.*;
import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.settings.Setting;

import java.util.ArrayList;
import java.util.List;

public class HudModule extends Module {
    private final List<Object> features = new ArrayList<>();

    public HudModule() {
        super("Hud", "hud", Category.CLIENT, true, false, false);

        // Initialize feature classes
        Watermark watermark = new Watermark();
        features.add(watermark);
        features.add(new Speed());
        features.add(new greeter(watermark));
        features.add(new coords());
        features.add(new ArrayListFeature());
        features.add(new Potions());

        // Register settings for each feature
        features.forEach(feature -> {
            for (java.lang.reflect.Field field : feature.getClass().getDeclaredFields()) {
                if (Setting.class.isAssignableFrom(field.getType())) {
                    try {
                        Setting<?> setting = (Setting<?>) field.get(feature);
                        this.register(setting);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onRender2D(Render2DEvent event) {
        int color = Shrimphack.colorManager.getColorAsInt();
        features.forEach(feature -> {
            try {
                java.lang.reflect.Method renderMethod = feature.getClass().getMethod("render", int.class, Render2DEvent.class);
                renderMethod.invoke(feature, color, event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
