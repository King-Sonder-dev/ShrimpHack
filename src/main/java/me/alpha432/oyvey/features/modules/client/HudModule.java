package me.alpha432.oyvey.features.modules.client;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.event.impl.Render2DEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import me.alpha432.oyvey.manager.PositionManager;

public class HudModule extends Module {
    public HudModule() {
        super("Hud", "hud", Category.CLIENT, true, false, false);
    }
    public Setting<String> watermark = this.register(new Setting<>("Watermark", "Oyvey"));
    public Setting<Boolean> coords = this.register(new Setting<>("Coords", false));
    public Setting<Boolean> welcomer = this.register(new Setting<>("Welcomer", false));

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
                this.watermark.getValue() + " " + OyVey.VERSION,
                2, 2,
                  this.red.getPlannedValue() + this.green.getPlannedValue() + this.blue.getPlannedValue() + this.alpha.getPlannedValue());
        }
    @Override
    public void onLoad() {
        OyVey.colorManager.setColor(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.hoverAlpha.getValue());
    }
}