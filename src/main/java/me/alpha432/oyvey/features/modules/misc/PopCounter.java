package me.alpha432.oyvey.features.modules.misc;

import me.alpha432.oyvey.features.commands.Command;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;
import me.alpha432.oyvey.event.impl.TotemPopEvent;
import meteordevelopment.orbit.EventHandler;

import java.util.HashMap;

public class PopCounter extends Module {

    public static HashMap<String, Integer> TotemPopContainer = new HashMap<>();
    private static PopCounter INSTANCE = new PopCounter();
    public Setting<PopNotifier> popNotifier = this.register(new Setting<>("PopNotifier", PopNotifier.NONE));

    public PopCounter() {
        super("PopCounter", "Counts other players totem pops.", Category.MISC, true, false, false);
        this.setInstance();
    }

    public static PopCounter getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PopCounter();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        TotemPopContainer.clear();
    }

    @EventHandler
    public void onTotemPop(@NotNull TotemPopEvent event) {
        PlayerEntity player = event.getEntity();
        String playerName = player.getName().getString();
        int pops = TotemPopContainer.getOrDefault(playerName, 0) + 1;
        TotemPopContainer.put(playerName, pops);

        if (player == mc.player) {
            String message = getSelfPopMessage(pops);
            Command.sendMessage(message);
        } else {
            String message = getPopMessage(playerName, pops);
            Command.sendMessage(message);
        }
    }

    @Override
    public void onUpdate() {
        for (PlayerEntity player : mc.world.getPlayers()) {
            if (player.getHealth() > 0 || !TotemPopContainer.containsKey(player.getName().getString()))
                continue;

            String playerName = player.getName().getString();
            int pops = TotemPopContainer.get(playerName);

            if (player == mc.player) {
                String message = getSelfDeathMessage(pops);
                Command.sendMessage(message);
            } else {
                String message = getDeathMessage(playerName, pops);
                Command.sendMessage(message);
            }
            TotemPopContainer.remove(playerName);
        }
    }

    private String getPopMessage(String playerName, int pops) {
        switch (this.popNotifier.getValue()) {
            case FUTURE:
                return Formatting.RED + "[Future] " + Formatting.GREEN + playerName + Formatting.GRAY + " just popped " + Formatting.GREEN + pops + Formatting.GRAY + " totem.";
            case PHOBOS:
                return " " + Formatting.GOLD + playerName + Formatting.RED + " popped " + Formatting.GOLD + pops + Formatting.RED + " totem.";
            case DOTGOD:
                return Formatting.DARK_PURPLE + "[" + Formatting.LIGHT_PURPLE + "DotGod.CC" + Formatting.DARK_PURPLE + "] " + Formatting.LIGHT_PURPLE + playerName + " has popped " + Formatting.RED + pops + Formatting.LIGHT_PURPLE + " time in total!";
            case NONE:
            default:
                return " " + Formatting.WHITE + playerName + " popped " + Formatting.GREEN + pops + Formatting.WHITE + " Totem.";
        }
    }

    private String getDeathMessage(String playerName, int pops) {
        switch (this.popNotifier.getValue()) {
            case FUTURE:
                return Formatting.RED + "[Future] " + Formatting.GREEN + playerName + Formatting.GRAY + " died after popping " + Formatting.GREEN + pops + Formatting.GRAY + " totems.";
            case PHOBOS:
                return " " + Formatting.GOLD + playerName + Formatting.RED + " died after popping " + Formatting.GOLD + pops + Formatting.RED + " totems.";
            case DOTGOD:
                return Formatting.DARK_PURPLE + "[" + Formatting.LIGHT_PURPLE + "DotGod.CC" + Formatting.DARK_PURPLE + "] " + Formatting.LIGHT_PURPLE + playerName + " died after popping " + Formatting.GREEN + pops + Formatting.LIGHT_PURPLE + " times!";
            case NONE:
            default:
                return " " + Formatting.WHITE + playerName + " died after popping " + Formatting.GREEN + pops + Formatting.WHITE + " Totems!";
        }
    }

    private String getSelfPopMessage(int pops) {
        return Formatting.AQUA + "You popped " + Formatting.GREEN + pops + Formatting.AQUA + " totem" + (pops > 1 ? "s" : "") + "!";
    }

    private String getSelfDeathMessage(int pops) {
        return Formatting.AQUA + "You died after popping " + Formatting.GREEN + pops + Formatting.AQUA + " totem" + (pops > 1 ? "s" : "") + "!";
    }

    public static enum PopNotifier {
        NONE,
        PHOBOS,
        FUTURE,
        DOTGOD;
    }
}