package me.alpha432.oyvey.features.modules.misc;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.commands.Command;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Formatting;

import java.util.HashSet;
import java.util.Set;

public class VisualRange extends Module {

    private Set<String> playersInRange;

    public VisualRange() {
        super("VisualRange", "Notifies when players enter or leave your visual range", Category.MISC, true, false, false);
        this.playersInRange = new HashSet<>();
    }

    @Override
    public void onUpdate() {
        if (mc.world == null || mc.player == null) {
            return;
        }

        Set<String> currentPlayers = new HashSet<>();

        for (PlayerEntity player : mc.world.getPlayers()) {
            if (player == mc.player) {
                continue;
            }

            String playerName = player.getName().getString();
            currentPlayers.add(playerName);

            if (!playersInRange.contains(playerName)) {
                Command.sendMessage(playerName + Formatting.GREEN + " entered " + "your visual range");
            }
        }

        for (String playerName : playersInRange) {
            if (!currentPlayers.contains(playerName)) {
                Command.sendMessage(playerName + " " + Formatting.RED + "left" + " your visual range");
            }
        }

        playersInRange = currentPlayers;
    }
}