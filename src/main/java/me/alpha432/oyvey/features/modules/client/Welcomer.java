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
    public Setting<Integer> gety = this.register(new Setting<>("Y", 2, 0, 485));
    public Setting<Integer> getx = this.register(new Setting<>("X", 400, 0, 710));
    public Setting<Boolean> shadow = this.register(new Setting<>("Shadow", true));


    @Override public void onRender2D(Render2DEvent event) {
        // Combine the color values into an ARGB integer
        int color = OyVey.colorManager.getColorAsInt();

        if (shadow.getValue()) {
        event.getContext().drawTextWithShadow(                mc.textRenderer,
                "Welcome to " + this.watermark.getValue() + " " + mc.player.getName().getString(),
                this.getx.getPlannedValue(), this.gety.getPlannedValue(),color);
        } else {
            event.getContext().drawTextWithShadow(                mc.textRenderer,
                    "Welcome to " + this.watermark.getValue() + " " + mc.player.getName().getString(),
                    this.getx.getPlannedValue(), this.gety.getPlannedValue(),color);
        }

        }
}

