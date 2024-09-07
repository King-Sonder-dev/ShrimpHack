package aids.dev.shrimphack.features.gui.hud;

import aids.dev.shrimphack.Shrimphack;
import aids.dev.shrimphack.event.impl.Render2DEvent;
import aids.dev.shrimphack.features.settings.Setting;

import static aids.dev.shrimphack.util.traits.Util.mc;


public class Speed {
    public final Setting<Boolean> speed = new Setting<>("speed", false);
    public final Setting<Integer> getys = new Setting<>("Yspeed", 2, 0, 485, v -> speed.getValue());
    public final Setting<Integer> getxs = new Setting<>("Xspeed", 2, 0, 710, v -> speed.getValue());

    public void render(int color, Render2DEvent event) {
        if (speed.getValue()) {
            event.getContext().drawTextWithShadow(
                    mc.textRenderer,
                    "Speed: " + Shrimphack.speedManager.getSpeedKpH(),
                    this.getxs.getPlannedValue(), this.getys.getPlannedValue(), color);
        }
    }
}
