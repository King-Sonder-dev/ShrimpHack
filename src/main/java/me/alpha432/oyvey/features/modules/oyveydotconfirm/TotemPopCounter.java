package me.alpha432.oyvey.features.modules.oyveydotconfirm;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.commands.Command;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

import java.util.HashMap;
import java.util.Map;

public class TotemPopCounter extends Module {

    private Map<PlayerEntity, Integer> popCounts;
    private Map<PlayerEntity, String> lastMessages;

    public TotemPopCounter() {
        super("TotemPopCounter", "Counts the number of totem pops", Category.OYVEYDOTCONFIRM, true, false, false);
        this.popCounts = new HashMap<>();
        this.lastMessages = new HashMap<>();
    }

    @Override
    public void onEnable() {
        popCounts.clear();
        lastMessages.clear();
    }

    @Override
    public void onUpdate() {
        PlayerEntity player = mc.player;
        if (player != null) {
            if (player.isUsingItem() && player.getStackInHand(Hand.MAIN_HAND).getItem() == Items.TOTEM_OF_UNDYING) {
                int currentPops = popCounts.getOrDefault(player, 0) + 1;
                popCounts.put(player, currentPops);

                // Delete the previous message if it exists
                if (lastMessages.containsKey(player)) {
                    Command.sendSilentMessage(lastMessages.get(player));
                }

                // Send the new message
                String newMessage = "Player " + player.getName().getString() + " popped a totem! Total pops: " + currentPops;
                Command.sendMessage(newMessage);
                lastMessages.put(player, newMessage);
            }

            // Check if the player has died
            if (player.isDead()) {
                int pops = popCounts.getOrDefault(player, 0);
                if (pops > 0) {
                    Command.sendMessage("Player " + player.getName().getString() + " has died after popping " + pops + " totems!");
                    popCounts.remove(player);
                    lastMessages.remove(player);
                }
            }
        }
    }

    @Override
    public void onDisable() {
        popCounts.clear();
        lastMessages.clear();
    }
}