package me.alpha432.oyvey.features.modules.client;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.event.impl.Render2DEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;

public class SpeedHud extends Module {
    public SpeedHud() {
        super("SpeedMeasure", "hud", Module.Category.CLIENT, true, false, false);
    }
    public Setting<Integer> gety = this.register(new Setting<>("Y", 2, 0, 485));
    public Setting<Integer> getx = this.register(new Setting<>("X", 2, 0, 710));

    public Setting<Integer> red = this.register(new Setting<>("Red", 0, 0, 255));
    public Setting<Integer> green = this.register(new Setting<>("Green", 0, 0, 255));
    public Setting<Integer> blue = this.register(new Setting<>("Blue", 255, 0, 255));
    public Setting<Integer> hoverAlpha = this.register(new Setting<>("Alpha", 180, 0, 255));
    public Setting<Integer> topRed = this.register(new Setting<>("SecondRed", 0, 0, 255));
    public Setting<Integer> topGreen = this.register(new Setting<>("SecondGreen", 0, 0, 255));
    public Setting<Integer> topBlue = this.register(new Setting<>("SecondBlue", 150, 0, 255));
    public Setting<Integer> alpha = this.register(new Setting<>("HoverAlpha", 240, 0, 255));
    public Setting<Boolean> rainbow = this.register(new Setting<>("Rainbow", false));
    @Override public void onRender2D(Render2DEvent event) {
        event.getContext().drawTextWithShadow(
                mc.textRenderer,
              "Speed: " + OyVey.speedManager.getSpeedMpS(),
                this.getx.getPlannedValue(), this.gety.getPlannedValue(),
                this.red.getPlannedValue() + this.green.getPlannedValue() + this.blue.getPlannedValue() + this.alpha.getPlannedValue());
    }
}
