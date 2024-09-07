package aids.dev.shrimphack.features.gui.hud;

import aids.dev.shrimphack.event.impl.Render2DEvent;
import aids.dev.shrimphack.features.settings.Setting;

import static aids.dev.shrimphack.util.traits.Util.mc;


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
