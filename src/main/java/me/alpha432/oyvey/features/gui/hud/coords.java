package me.alpha432.oyvey.features.gui.hud;

import me.alpha432.oyvey.event.impl.Render2DEvent;
import me.alpha432.oyvey.features.settings.Setting;
import static me.alpha432.oyvey.util.traits.Util.mc;

public class coords {
    public final Setting<Boolean> coords = new Setting<>("Coords", false);
    public final Setting<Integer> getycoords = new Setting<>("Ycoords", 2, 0, 485, v -> coords.getValue());
    public final Setting<Integer> getxcoords = new Setting<>("Xcoords", 2, 0, 710, v -> coords.getValue());

    public void render(int color, Render2DEvent event) {
        if (coords.getValue()) {
            String coordinates = String.format("X: %.1f Y: %.1f Z: %.1f", mc.player.getX(), mc.player.getY(), mc.player.getZ());
            event.getContext().drawTextWithShadow(
                    mc.textRenderer,
                    coordinates,
                    this.getxcoords.getPlannedValue(), this.getycoords.getPlannedValue(), color);
        }
    }
}
