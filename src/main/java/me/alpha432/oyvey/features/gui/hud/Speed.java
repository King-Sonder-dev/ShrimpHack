package me.alpha432.oyvey.features.gui.hud;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.event.impl.Render2DEvent;
import me.alpha432.oyvey.features.settings.Setting;

import static me.alpha432.oyvey.util.traits.Util.mc;

public class Speed {
    public final Setting<Boolean> speed = new Setting<>("speed", false);
    public final Setting<Integer> getys = new Setting<>("Yspeed", 2, 0, 485, v -> speed.getValue());
    public final Setting<Integer> getxs = new Setting<>("Xspeed", 2, 0, 710, v -> speed.getValue());

    public void render(int color, Render2DEvent event) {
        if (speed.getValue()) {
            event.getContext().drawTextWithShadow(
                    mc.textRenderer,
                    "Speed: " + OyVey.speedManager.getSpeedMpS(),
                    this.getxs.getPlannedValue(), this.getys.getPlannedValue(), color);
        }
    }
}
