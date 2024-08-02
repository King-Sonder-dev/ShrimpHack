package me.alpha432.oyvey.features.gui.hud;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.event.impl.Render2DEvent;
import me.alpha432.oyvey.features.settings.Setting;

import static me.alpha432.oyvey.util.traits.Util.mc;

public class greeter {
    public final Setting<Boolean> greeter = new Setting<>("Welcomer", false);
    public final Setting<TextMode> textMode = new Setting<>("TextMode", TextMode.DEFAULT, v -> greeter.getValue());

    public final Setting<Integer> getygreeter = new Setting<>("Ywelcomer", 2, 0, 485, v -> greeter.getValue());
    public final Setting<Integer> getxgreeter = new Setting<>("Xwelcomer", 387, 0, 710, v -> greeter.getValue());

    private final Watermark watermark;
    String NAME = mc.player.getName().getString();
    public String getWelcomer() {
        switch (textMode.getValue()) {
            case DEFAULT:
                return "Welcome to " + watermark.getWatermark() + " " + NAME;
            case WECLOME:
                return "Welcome " + NAME;
            case WECLOMETO:
                return "Welcome to " + watermark.getWatermark() + " :^)";
            case GOOFYAHHFACE:
                return ":^)";
        }
        return null;
    }

    public greeter(Watermark watermark) {
        this.watermark = watermark;
    }

    public void render(int color, Render2DEvent event) {
        String displayText = getWelcomer();

        if (greeter.getValue()) {
            event.getContext().drawTextWithShadow(
                    mc.textRenderer,
                    displayText,
            this.getxgreeter.getPlannedValue(), this.getygreeter.getPlannedValue(), color);
        }
    }
    public enum TextMode {
        DEFAULT,
        WECLOME,
        WECLOMETO,
        GOOFYAHHFACE
    }
}
