package me.alpha432.oyvey.features.modules.client;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.event.impl.Render2DEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;

public class Welcomer extends Module {
    public Welcomer() {
        super("Welcomer", "hud", Module.Category.CLIENT, true, false, false);
    }
    public Setting<String> watermark = this.register(new Setting<>("Watermark", "Oyvey"));

    public Setting<Integer> red = this.register(new Setting<>("Red", 0, 0, 255));
    public Setting<Integer> green = this.register(new Setting<>("Green", 0, 0, 255));
    public Setting<Integer> blue = this.register(new Setting<>("Blue", 255, 0, 255));
    public Setting<Integer> alpha = this.register(new Setting<>("HoverAlpha", 240, 0, 255));
    @Override public void onRender2D(Render2DEvent event) {
        event.getContext().drawTextWithShadow(
                mc.textRenderer,
                "Welcome to " + this.watermark.getValue() + " " + mc.player.getName().getString(),
                400, 2,
                this.red.getPlannedValue() + this.green.getPlannedValue() + this.blue.getPlannedValue() + this.alpha.getPlannedValue());

    }
}

