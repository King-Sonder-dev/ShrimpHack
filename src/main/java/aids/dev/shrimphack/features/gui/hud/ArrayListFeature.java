package aids.dev.shrimphack.features.gui.hud;

import aids.dev.shrimphack.Shrimphack;
import aids.dev.shrimphack.event.impl.Render2DEvent;
import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.settings.Setting;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static aids.dev.shrimphack.util.traits.Util.mc;


public class ArrayListFeature {
    public final Setting<Boolean> arraylist = new Setting<>("arraylist", false);
    public final Setting<Integer> getya = new Setting<>("Ya", 485, 0, 485, v -> arraylist.getValue());
    public final Setting<SortOrder> sortOrder = new Setting<>("SortOrder", SortOrder.SIZE_TOP, v -> arraylist.getValue());

    public void render(int color, Render2DEvent event) {
        if (arraylist.getValue()) {
            List<Module> enabledModules = Shrimphack.moduleManager.getEnabledModules();
            enabledModules.sort(Comparator.comparing(module -> {
                String displayInfo = module.getDisplayInfo();
                return module.getName() + (displayInfo != null ? " " + displayInfo : "");
            }, Comparator.comparingInt(String::length)));
            if (sortOrder.getValue() == SortOrder.SIZE_BOTTOM) {
                Collections.reverse(enabledModules);
            }
            int yOffset = this.getya.getPlannedValue();
            int maxY = mc.getWindow().getScaledHeight();
            for (Module module : enabledModules) {
                if (module.isDrawn()) {
                    String moduleName = module.getFullArrayString();
                    int textWidth = mc.textRenderer.getWidth(moduleName);
                    int xOffset = mc.getWindow().getScaledWidth() - textWidth;

                    if (yOffset + mc.textRenderer.fontHeight > maxY) {
                        yOffset = this.getya.getPlannedValue();
                    }

                    event.getContext().drawTextWithShadow(
                            mc.textRenderer,
                            moduleName,
                            xOffset, yOffset,
                            color);
                    yOffset += mc.textRenderer.fontHeight + 2;
                }
            }
        }
    }

    public enum SortOrder {
        SIZE_TOP,
        SIZE_BOTTOM
    }
}
