package aids.dev.shrimphack.features.modules.chat;

import aids.dev.shrimphack.event.impl.TotemPopEvent;
import aids.dev.shrimphack.features.commands.Command;
import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.settings.Setting;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;
import meteordevelopment.orbit.EventHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PopCounter extends Module {

    public static Map<String, Integer> TotemPopContainer = new ConcurrentHashMap<>();
    private static PopCounter INSTANCE = new PopCounter();
    public Setting<PopNotifier> popNotifier = this.register(new Setting<>("PopNotifier", PopNotifier.NONE));
    public Setting<Boolean> bold = this.register(new Setting<>("Boldsn0w", true, v -> this.popNotifier.getValue() == PopNotifier.SN0W));

    public PopCounter() {
        super("PopCounter", "Counts other players totem pops.", Category.CHAT, true, false, false);
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
            Command.sendSilentMessage(message);
        } else {
            String message = getPopMessage(playerName, pops);
            Command.sendSilentMessage(message);
        }
    }


    @Override
    public void onUpdate() {
        assert mc.world != null;
        for (PlayerEntity player : mc.world.getPlayers()) {
            if (player.getHealth() > 0 || !TotemPopContainer.containsKey(player.getName().getString()))
                continue;

            String playerName = player.getName().getString();
            int pops = TotemPopContainer.get(playerName);

            if (player == mc.player) {
                String message = getSelfDeathMessage(playerName, pops);
                Command.sendSilentMessage(message);
            } else {
                String message = getDeathMessage(playerName, pops);
                Command.sendSilentMessage(message);
            }
            TotemPopContainer.remove(playerName);
        }
    }



    private String getPopMessage(String playerName, int pops) {
        switch (this.popNotifier.getValue()) {
            case FUTURE:
                return Formatting.RED + "[Future] " + Formatting.GREEN + playerName + Formatting.GRAY + " just popped " + Formatting.GREEN + pops + Formatting.GRAY + " totems.";
            case PHOBOS:
                return " " + Formatting.GOLD + playerName + Formatting.RED + " popped " + Formatting.GOLD + pops + Formatting.RED + " totems.";
            case DOTGOD:
                return Formatting.DARK_PURPLE + "[" + Formatting.LIGHT_PURPLE + "DotGod.CC" + Formatting.DARK_PURPLE + "] " + Formatting.LIGHT_PURPLE + playerName + " has popped " + Formatting.RED + pops + Formatting.LIGHT_PURPLE + " time in total!";
            case SN0W:
                return Formatting.BLUE + "[" + Formatting.AQUA + "❄" + Formatting.BLUE + "] " + Formatting.RESET + (this.bold.getValue() ? Formatting.BOLD : "") + playerName + " has" + Formatting.RESET + " popped " + pops + (pops == 1 ? " totem" : " totems") + "!";
            case SNOW:
                return Formatting.GRAY + "[" + Formatting.AQUA + "Snow" + Formatting.GRAY + "] " + "[" + Formatting.DARK_BLUE + "Popcounter" + "]" + playerName + " popped " + pops + " totems!";
            case NONE:
            default:
                return " " + Formatting.WHITE + playerName + " popped " + Formatting.GREEN + pops + Formatting.WHITE + " Totems.";
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
            case SN0W:
                return Formatting.BLUE + "[" + Formatting.AQUA + "❄" + Formatting.BLUE + "] " + Formatting.RESET + (this.bold.getValue() ? Formatting.BOLD : "") + playerName + Formatting.RESET + " died after popping " + pops + (pops == 1 ? " totem" : " totems") + "!";
            case SNOW:
                return Formatting.GRAY + "[" + Formatting.AQUA + "Snow" + Formatting.GRAY + "] " + "[" + Formatting.DARK_BLUE + "Popcounter" + "]" + Formatting.RED + playerName + " popped " + Formatting.GREEN + pops + Formatting.RED + " totems!";
            case NONE:
            default:
                return " " + Formatting.WHITE + playerName + " died after popping " + Formatting.GREEN + pops + Formatting.WHITE + " Totems!";
        }
    }

    private String getSelfPopMessage(int pops) {
        switch (this.popNotifier.getValue()) {
            case FUTURE:
                return Formatting.RED + "[Future] " + Formatting.GREEN + "You" + Formatting.GRAY + " just popped " + Formatting.GREEN + pops + Formatting.GRAY + " totem.";
            case PHOBOS:
                return " " + Formatting.GOLD + "You" + Formatting.RED + " popped " + Formatting.GOLD + pops + Formatting.RED + " totem.";
            case DOTGOD:
                return Formatting.DARK_PURPLE + "[" + Formatting.LIGHT_PURPLE + "DotGod.CC" + Formatting.DARK_PURPLE + "] " + Formatting.LIGHT_PURPLE + "You" + " popped " + Formatting.RED + pops + Formatting.LIGHT_PURPLE + " time in total!";
            case SN0W:
                return Formatting.BLUE + "[" + Formatting.AQUA + "❄" + Formatting.BLUE + "] " + Formatting.RESET + (this.bold.getValue() ? Formatting.BOLD : "") + "You have" + Formatting.RESET + " popped " + pops + (pops == 1 ? " totem" : " totems") + "!";
            case SNOW:
                return Formatting.GRAY + "[" + Formatting.AQUA + "Snow" + Formatting.GRAY + "] " + "[" + Formatting.DARK_BLUE + "Popcounter" + "]" + "You" + " popped " + pops + " totems!";
            case NONE:
            default:
                return Formatting.AQUA + "You popped " + Formatting.GREEN + pops + Formatting.AQUA + " totem" + (pops > 1 ? "s" : "") + "!";
        }
    }

    private String getSelfDeathMessage(String playerName, int pops) {
        switch (this.popNotifier.getValue()) {
            case FUTURE:
                return Formatting.RED + "[Future] " + Formatting.GREEN + "you" + Formatting.GRAY + " died after popping " + Formatting.GREEN + pops + Formatting.GRAY + " totems.";
            case PHOBOS:
                return " " + Formatting.GOLD + "you" + Formatting.RED + " died after popping " + Formatting.GOLD + pops + Formatting.RED + " totems.";
            case DOTGOD:
                return Formatting.DARK_PURPLE + "[" + Formatting.LIGHT_PURPLE + "DotGod.CC" + Formatting.DARK_PURPLE + "] " + Formatting.LIGHT_PURPLE + "you" + " died after popping " + Formatting.GREEN + pops + Formatting.LIGHT_PURPLE + " times!";
            case SN0W:
                return Formatting.BLUE + "[" + Formatting.AQUA + "❄" + Formatting.BLUE + "] " + Formatting.RESET + (this.bold.getValue() ? Formatting.BOLD : "") + "you" + Formatting.RESET + " died after popping " + pops + (pops == 1 ? " totem" : " totems") + "!";
            case SNOW:
                return Formatting.GRAY + "[" + Formatting.AQUA + "Snow" + Formatting.GRAY + "] " + "[" + Formatting.DARK_BLUE + "Popcounter" + "]" + Formatting.RED + "You" + " popped " + Formatting.GREEN + pops + Formatting.RED + " totems!";
            case NONE:
            default:
                return Formatting.AQUA + "You died after popping " + Formatting.GREEN + pops + Formatting.AQUA + " totem" + (pops > 1 ? "s" : "") + "!";
        }
    }

    public static enum PopNotifier {
        NONE,
        SN0W,
        SNOW,
        PHOBOS,
        FUTURE,
        DOTGOD;
    }
}