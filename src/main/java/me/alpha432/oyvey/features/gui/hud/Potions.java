package me.alpha432.oyvey.features.gui.hud;

import me.alpha432.oyvey.event.impl.Render2DEvent;
import me.alpha432.oyvey.features.settings.Setting;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static me.alpha432.oyvey.util.traits.Util.mc;

public class Potions {
    public final Setting<Boolean> Potions = new Setting<>("Potions", false);
    public final Setting<Boolean> colorSync = new Setting<>("ColorSync", false, v -> Potions.getValue());
    public final Setting<Boolean> renderTextUp = new Setting<>("Chatfix", true, v -> Potions.getValue());
    public final Setting<Integer> getypotions = new Setting<>("YPotions", 2, 0, 485, v -> Potions.getValue());
    public final Setting<Integer> getxpotions = new Setting<>("XPotions", 2, 0, 800, v -> Potions.getValue());

    private String getColoredPotionString(StatusEffectInstance effect) {
        StatusEffect potion = effect.getEffectType();
        return potion.getName().getString() + " " + (effect.getAmplifier() + 1) + " " + "\u00a7f" + StatusEffectUtil.getDurationText(effect, 1, mc.world.getTickManager().getTickRate()).getString();
    }
    public void render(int color, Render2DEvent event) {
        if (Potions.getValue()) {
            List<StatusEffectInstance> effects = new ArrayList<>(mc.player.getStatusEffects());
            effects.sort(Comparator.comparing(StatusEffectInstance::getAmplifier)); // Sort by amplifier ascending

            int width = mc.getWindow().getScaledWidth();
            int height = mc.getWindow().getScaledHeight();
            int yOffset = getypotions.getPlannedValue();
            int textHeight = mc.textRenderer.fontHeight + 2; // Adjust this value to control the vertical spacing between potion entries



            int i = (mc.currentScreen instanceof ChatScreen && renderTextUp.getValue()) ? 13 : (renderTextUp.getValue() ? -2 : 0);
            if (renderTextUp.getValue()) {
                for (StatusEffectInstance potionEffect : effects) {
                    String str = getColoredPotionString(potionEffect);
                    int textColor = colorSync.getValue() ? potionEffect.getEffectType().getColor() : color;
                    i += 10;
                    event.getContext().drawTextWithShadow(mc.textRenderer, Text.of(str), (width - 2 - getStringWidth(str)), (height - i), color);
                }
            } else {
                for (StatusEffectInstance potionEffect : effects) {
                    String str = getColoredPotionString(potionEffect);
                    int textColor = colorSync.getValue() ? potionEffect.getEffectType().getColor() : color;
                    i += 10;
                    event.getContext().drawTextWithShadow(mc.textRenderer, Text.of(str), (width - 2 - mc.textRenderer.getWidth(str)), (height - i), textColor);
                }
            }
        }
    }
    private int getStringWidth(String str) {
        return mc.textRenderer.getWidth(str);
    }

}