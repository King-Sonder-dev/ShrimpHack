package me.alpha432.oyvey.features.modules.client;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.event.impl.Render2DEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;
import java.util.stream.Collectors;

public class TextRadar extends Module {
    private final Setting<Integer> x = register(new Setting<>("X", 12, 0, 1000));
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

        int nameColor = OyVey.colorManager.getColorAsInt();

        for (PlayerEntity player : nearbyPlayers) {
            int healthColor = getHealthColor(player.getHealth(), player.getMaxHealth());
            int distanceColor = getDistanceColor(player.distanceTo(MinecraftClient.getInstance().player));

            String playerName = player.getName().getString();
            String playerHealth = String.format("%.1f", player.getHealth());
            String playerDistance = String.format("%.1f", player.distanceTo(MinecraftClient.getInstance().player));

            // Draw distance, name, and health with respective colors
            if (shadow.getValue()) {
                event.getContext().drawTextWithShadow(MinecraftClient.getInstance().textRenderer, playerDistance, x.getValue(), y.getValue() + yOffset, distanceColor);
                event.getContext().drawTextWithShadow(MinecraftClient.getInstance().textRenderer, playerName, x.getValue() + MinecraftClient.getInstance().textRenderer.getWidth(playerDistance) + 2, y.getValue() + yOffset, nameColor);
                event.getContext().drawTextWithShadow(MinecraftClient.getInstance().textRenderer, playerHealth, x.getValue() + MinecraftClient.getInstance().textRenderer.getWidth(playerDistance) + MinecraftClient.getInstance().textRenderer.getWidth(playerName) + 4, y.getValue() + yOffset, healthColor);
            } else {
                // You can remove this else block if drawText is not available.
                // You may also choose to use drawTextWithShadow for all cases.
            }

            yOffset += MinecraftClient.getInstance().textRenderer.fontHeight + 2; // Add some spacing between lines
        }
    }

    private int getHealthColor(float health, float maxHealth) {
        float ratio = health / maxHealth;

        int r = (int) (255 * (1 - ratio));
        int g = (int) (255 * ratio);
        int b = 0;

        return (r << 16) | (g << 8) | b | 0xFF000000; // Include alpha channel
    }

    private int getDistanceColor(float distance) {
        float maxDistance = 100; // Assuming max distance is 100
        float ratio = distance / maxDistance;

        int r = (int) (255 * (1 - ratio));
        int g = (int) (255 * ratio);
        int b = 0;

        return (r << 16) | (g << 8) | b | 0xFF000000; // Include alpha channel
    }
}
