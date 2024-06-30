package me.alpha432.oyvey.features.modules.client;

import me.alpha432.oyvey.event.impl.Render2DEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;
import java.util.stream.Collectors;

public class TextRadar extends Module {
    private final Setting<Integer> x = register(new Setting<>("X", 2, 0, 1000));
    private final Setting<Integer> y = register(new Setting<>("Y", 2, 0, 1000));
    private final Setting<Boolean> shadow = register(new Setting<>("Shadow", true));

    public TextRadar() {
        super("TextRadar", "Displays nearby players with health, name, and distance", Category.CLIENT, true, false, false);
    }

    @Override
    public void onRender2D(Render2DEvent event) {
        int yOffset = 0;

        List<PlayerEntity> nearbyPlayers = MinecraftClient.getInstance().world.getPlayers().stream()
                .filter(player -> player != MinecraftClient.getInstance().player && player.distanceTo(MinecraftClient.getInstance().player) < 100) // Adjust distance as needed
                .collect(Collectors.toList());

        for (PlayerEntity player : nearbyPlayers) {
            int healthColor = getHealthColor(player.getHealth());
            int distanceColor = getDistanceColor(player.distanceTo(MinecraftClient.getInstance().player));

            String playerInfo = String.format("%s - %.1f HP - %.1f blocks", player.getName().getString(), player.getHealth(), player.distanceTo(MinecraftClient.getInstance().player));

            if (shadow.getValue()) {
                event.getContext().drawTextWithShadow(MinecraftClient.getInstance().textRenderer, playerInfo, x.getValue(), y.getValue() + yOffset, healthColor);
            } else {
                event.getContext().drawTextWithShadow(MinecraftClient.getInstance().textRenderer, playerInfo, x.getValue(), y.getValue() + yOffset, healthColor);
            }

            yOffset += MinecraftClient.getInstance().textRenderer.fontHeight + 2; // Add some spacing between lines
        }
    }

    private int getHealthColor(float health) {
        int maxHealth = 20; // Assuming max health is 20
        float ratio = health / maxHealth;

        int r = (int) (255 * (1 - ratio));
        int g = (int) (255 * ratio);
        int b = 0;

        return (r << 16) | (g << 8) | b | 0xFF000000; // Include alpha channel
    }

    private int getDistanceColor(float distance) {
        float maxDistance = 100; // Assuming max distance is 100
        float ratio = distance / maxDistance;

        int r = (int) (255 * ratio);
        int g = (int) (255 * (1 - ratio));
        int b = 0;

        return (r << 16) | (g << 8) | b | 0xFF000000; // Include alpha channel
    }
}