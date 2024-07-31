package me.alpha432.oyvey.features.gui.hud;

import me.alpha432.oyvey.event.impl.Render2DEvent;
import me.alpha432.oyvey.features.settings.Setting;

import static me.alpha432.oyvey.util.traits.Util.mc;

public class greeter {
    public final Setting<Boolean> greeter = new Setting<>("Welcomer", false);
    public final Setting<Integer> getygreeter = new Setting<>("Ywelcomer", 2, 0, 485, v -> greeter.getValue());
    public final Setting<Integer> getxgreeter = new Setting<>("Xwelcomer", 387, 0, 710, v -> greeter.getValue());

    private final Watermark watermark;

    public greeter(Watermark watermark) {
        this.watermark = watermark;
    }

    public void render(int color, Render2DEvent event) {
        if (greeter.getValue()) {
            event.getContext().drawTextWithShadow(
                    mc.textRenderer,
                    "Welcome to " + watermark.getWatermark() + " " + mc.player.getName().getString(),
                    this.getxgreeter.getPlannedValue(), this.getygreeter.getPlannedValue(), color);
        }
    }
}
