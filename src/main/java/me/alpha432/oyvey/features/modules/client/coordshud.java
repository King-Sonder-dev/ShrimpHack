package me.alpha432.oyvey.features.modules.client;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.event.impl.Render2DEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import me.alpha432.oyvey.manager.PositionManager;

public class coordshud extends Module {
    public coordshud() {
        super("coords", "hud", Category.CLIENT, true, false, false);
    }
    public Setting<Integer> gety = this.register(new Setting<>("Y", 485, 0, 485));
    public Setting<Integer> getx = this.register(new Setting<>("X", 2, 0, 710));
    public Setting<Boolean> shadow = this.register(new Setting<>("Shadow", true));

    @Override public void onRender2D(Render2DEvent event) {
        String coordinates = String.format("X: %.1f Y: %.1f Z: %.1f", mc.player.getX(), mc.player.getY(), mc.player.getZ());
        int color = OyVey.colorManager.getColorAsInt();
        if (shadow.getValue()) {
            event.getContext().drawTextWithShadow(
                mc.textRenderer,
                coordinates,
                this.getx.getPlannedValue(), this.gety.getPlannedValue(),
                color);
        } else {
            event.getContext().drawTextWithShadow(
                    mc.textRenderer,
                    coordinates,
                    this.getx.getPlannedValue(), this.gety.getPlannedValue(),
                    color);

        }
    }

}