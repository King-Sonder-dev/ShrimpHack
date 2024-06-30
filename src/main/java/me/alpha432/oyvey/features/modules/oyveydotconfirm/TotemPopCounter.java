package me.alpha432.oyvey.features.modules.oyveydotconfirm;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.commands.Command;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

import java.util.HashMap;
import java.util.Map;

public class TotemPopCounter extends Module {

    private final Map<PlayerEntity, Integer> popCounts;
    private int selfPopCount;

    public TotemPopCounter() {
        super("TotemPopCounter", "Counts the number of totem pops for you and other players", Category.OYVEYDOTCONFIRM, true, false, false);
        this.popCounts = new HashMap<>();
        this.selfPopCount = 0;
    }

    @Override
    public void onEnable() {
        popCounts.clear();
        selfPopCount = 0;
    }

    @Override
    public void onUpdate() {
        PlayerEntity player = mc.player;
        if (player != null) {
            if (player.isUsingItem() && player.getStackInHand(Hand.MAIN_HAND).getItem() == Items.TOTEM_OF_UNDYING) {
                if (player == mc.player) {
                    selfPopCount++;
                    Command.sendMessage("You popped a totem! Total pops: " + selfPopCount);
                } else {
                    int currentPops = popCounts.getOrDefault(player, 0) + 1;
                    popCounts.put(player, currentPops);
                    Command.sendMessage("Player " + player.getName().getString() + " popped a totem! Total pops: " + currentPops);
                }
            }

            // Check if the player has died
            if (player.isDead()) {
                if (player == mc.player) {
                    if (selfPopCount > 0) {
                        Command.sendMessage("You died after popping " + selfPopCount + " totems!");
                        selfPopCount = 0;
                    }
                } else {
                    int pops = popCounts.getOrDefault(player, 0);
                    if (pops > 0) {
                        Command.sendMessage("Player " + player.getName().getString() + " has died after popping " + pops + " totems!");
                        popCounts.remove(player);
                    }
                }
            }
        }
    }

    @Override
    public void onDisable() {
        popCounts.clear();
        selfPopCount = 0;
    }
}