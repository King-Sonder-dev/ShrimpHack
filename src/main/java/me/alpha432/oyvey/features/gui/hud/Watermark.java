package me.alpha432.oyvey.features.gui.hud;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.event.impl.Render2DEvent;
import me.alpha432.oyvey.features.settings.Setting;
import me.alpha432.oyvey.util.models.Timer;

import static me.alpha432.oyvey.util.traits.Util.mc;

public class Watermark {

    public final Setting<Boolean> watermarkbutton = new Setting<>("Watermark", false);
    public final Setting<String> watermark = new Setting<>("Watermark", "Oyvey++", v -> watermarkbutton.getValue());
    public final Setting<Integer> gety = new Setting<>("Y", 2, 0, 485, v -> watermarkbutton.getValue());
    public final Setting<Integer> getx = new Setting<>("X", 2, 0, 710, v -> watermarkbutton.getValue());
    public final Setting<Watermarkmode> mode = new Setting<>("Mode", Watermarkmode.NONE, v -> watermarkbutton.getValue());
    public final Setting<Integer> typingSpeed = new Setting<>("Typing Speed", 5, 1, 20, v -> watermarkbutton.getValue());
    public final Setting<TextMode> textMode = new Setting<>("TextMode", TextMode.OYVEY, v -> watermarkbutton.getValue());

    private final Timer timer = new Timer();
    private int typingIndex = 0;
    private boolean isDeleting = false;
    private String currentText = "";

    public String getWatermark() {
        switch (textMode.getValue()) {
            case FUTURE:
                return "Future v2.13.5-extern+274.ba4c68c147";
            case DOTGOD:
                return "DotGod.CC";
            case PHOBOS:
                return "Phobos.eu";
            case TROLLGOD:
                return "Trollgod.cc 1.5.4";
            case OYVEY:
                return this.watermark.getValue() + " " + OyVey.VERSION;
            case MIO:
                return "Mio v2.0.2";
            case MIONIGHTLY:
                return "Mio v2.0.2-nightly";
            case MIODOTME:
                return "Mioclient.me";
            case SNOWBETA:
                return "Snow 4.4-beta";
            default:
                return watermark.getValue();
        }
    }

    public void render(int color, Render2DEvent event) {
        if (watermarkbutton.getValue()) {
            String displayText = getWatermark();

            switch (mode.getValue()) {
                case TYPING:
                    if (timer.passedMs(typingSpeed.getValue() * 100)) {
                        typingIndex++;
                        if (typingIndex > displayText.length()) {
                            typingIndex = 0;
                        }
                        timer.reset();
                    }
                    displayText = displayText.substring(0, Math.min(typingIndex, displayText.length()));
                    break;

                case TYPEDEL:
                    if (timer.passedMs(typingSpeed.getValue() * 100)) {
                        if (!isDeleting) {
                            typingIndex++;
                            if (typingIndex > displayText.length()) {
                                isDeleting = true;
                                typingIndex = displayText.length();
                            }
                        } else {
                            typingIndex--;
                            if (typingIndex <= 0) {
                                isDeleting = false;
                                typingIndex = 0;
                            }
                        }
                        timer.reset();
                    }
                    displayText = displayText.substring(0, typingIndex);
                    break;

                case NONE:
                default:
                    displayText = displayText;
                    break;
            }

            event.getContext().drawTextWithShadow(
                    mc.textRenderer,
                    displayText,
                    this.getx.getPlannedValue(), this.gety.getPlannedValue(),
                    color);
        }
    }

    public enum Watermarkmode {
        NONE,
        TYPING,
        TYPEDEL
    }

    public enum TextMode {
        OYVEY,
        TROLLGOD,
        FUTURE,
        DOTGOD,
        PHOBOS,
        MIO,
        MIONIGHTLY,
        MIODOTME,
        SNOWBETA
    }
}
