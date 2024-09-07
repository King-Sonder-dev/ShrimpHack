package aids.dev.shrimphack.features.gui.hud;

import aids.dev.shrimphack.event.impl.Render2DEvent;
import aids.dev.shrimphack.features.settings.Setting;

import static aids.dev.shrimphack.util.traits.Util.mc;


public class greeter {
    public final Setting<Boolean> greeter = new Setting<>("Welcomer", false);
    public final Setting<TextMode> textMode = new Setting<>("TextMode", TextMode.DEFAULT, v -> greeter.getValue());

    public final Setting<Integer> getygreeter = new Setting<>("Ywelcomer", 2, 0, 485, v -> greeter.getValue());
    public final Setting<Integer> getxgreeter = new Setting<>("Xwelcomer", 387, 0, 710, v -> greeter.getValue());

    private final Watermark watermark;

    public greeter(Watermark watermark) {
        this.watermark = watermark;
    }

    public String getWelcomer() {
        if (mc.player == null) {
            return ""; // Return an empty string or a default message if mc.player is not available
        }

        String NAME = mc.player.getName().getString();
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

    public void render(int color, Render2DEvent event) {
        String displayText = getWelcomer();

        if (greeter.getValue() && displayText != null) {
            event.getContext().drawTextWithShadow(
                    mc.textRenderer,
                    displayText,
                    this.getxgreeter.getPlannedValue(), this.getygreeter.getPlannedValue(),
                    color
            );
        }
    }

    public enum TextMode {
        DEFAULT,
        WECLOME,
        WECLOMETO,
        GOOFYAHHFACE
    }
}
