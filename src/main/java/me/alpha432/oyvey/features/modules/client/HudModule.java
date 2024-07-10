package me.alpha432.oyvey.features.modules.client;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.event.impl.Render2DEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HudModule extends Module {
    public HudModule() {
        super("Hud", "hud", Category.CLIENT, true, false, false);
    }

    public final Setting<Boolean> arraylist = this.register(new Setting<>("arraylist", true));
    public Setting<Integer> getya = this.register(new Setting<>("Ya", 2, 0, 485));
    public Setting<String> watermark = this.register(new Setting<>("Watermark", "Oyvey++"));
    public Setting<Integer> gety = this.register(new Setting<>("Y", 2, 0, 485));
    public Setting<Integer> getx = this.register(new Setting<>("X", 2, 0, 710));
    private final Setting<Boolean> greeter = register(new Setting<>("Welcomer", true));
    public Setting<Integer> getygreeter = this.register(new Setting<>("Ywelcomer", 2, 0, 485));
    public Setting<Integer> getxgreeter = this.register(new Setting<>("Xwelcomer", 2, 0, 710));
    private final Setting<Boolean> coords = this.register(new Setting<>("Coords", true));
    public Setting<Integer> getycoords = this.register(new Setting<>("Ycoords", 2, 0, 485));
    public Setting<Integer> getxcoords = this.register(new Setting<>("Xcoords", 2, 0, 710));
    private final Setting<Boolean> speed = this.register(new Setting<>("speed", true));
    public Setting<Integer> getys = this.register(new Setting<>("Ycoords", 2, 0, 485));
    public Setting<Integer> getxs = this.register(new Setting<>("Xcoords", 2, 0, 710));

    // New setting for sorting order
    private final Setting<SortOrder> sortOrder = this.register(new Setting<>("SortOrder", SortOrder.SIZE_TOP));

    @Override
    public void onRender2D(Render2DEvent event) {
        String coordinates = String.format("X: %.1f Y: %.1f Z: %.1f", mc.player.getX(), mc.player.getY(), mc.player.getZ());

        // Combine the color values into an ARGB integer
        int color = OyVey.colorManager.getColorAsInt();

        event.getContext().drawTextWithShadow(
                mc.textRenderer,
                this.watermark.getValue() + " " + OyVey.VERSION,
                this.getx.getPlannedValue(), this.gety.getPlannedValue(),
                color);
        if (greeter.getValue()) {
            event.getContext().drawTextWithShadow(
                    mc.textRenderer,
                    "Welcome to " + this.watermark.getValue() + " " + mc.player.getName().getString(),
                    this.getxgreeter.getPlannedValue(), this.getygreeter.getPlannedValue(), color);
        }
        if (coords.getValue()) {
            event.getContext().drawTextWithShadow(
                    mc.textRenderer,
                    coordinates,
                    this.getxcoords.getPlannedValue(), this.getycoords.getPlannedValue(),
                    color);
        }
        if (speed.getValue()) {
            event.getContext().drawTextWithShadow(
                    mc.textRenderer,
                    "Speed: " + OyVey.speedManager.getSpeedMpS(),
                    this.getxs.getPlannedValue(), this.getys.getPlannedValue(), color);
        }
        if (arraylist.getValue()) {
            List<Module> enabledModules = OyVey.moduleManager.getEnabledModules();
            enabledModules.sort(Comparator.comparing(module -> {
                String displayInfo = module.getDisplayInfo();
                return module.getName() + (displayInfo != null ? " " + displayInfo : "");
            }, Comparator.comparingInt(String::length)));
            if (sortOrder.getValue() == SortOrder.SIZE_BOTTOM) {
                Collections.reverse(enabledModules);
            }
            int yOffset = this.getya.getPlannedValue();
            int maxY = mc.getWindow().getScaledHeight(); // Get the height of the screen
            for (Module module : enabledModules) {
                if (module.isDrawn()) {
                    String moduleName = module.getFullArrayString();
                    int textWidth = mc.textRenderer.getWidth(moduleName);
                    int xOffset = mc.getWindow().getScaledWidth() - textWidth; // Calculate X coordinate for right alignment

                    // Check if the next module would go off the screen
                    if (yOffset + mc.textRenderer.fontHeight > maxY) {
                        yOffset = this.getya.getPlannedValue(); // Reset to the top
                    }

                    event.getContext().drawTextWithShadow(
                            mc.textRenderer,
                            moduleName,
                            xOffset, yOffset, // Use calculated xOffset for right alignment
                            color);
                    yOffset += mc.textRenderer.fontHeight + 2; // Adjust spacing as needed
                }
            }
        }
    }

    // Enum for sorting order
    public enum SortOrder {
        SIZE_TOP,
        SIZE_BOTTOM
    }
}