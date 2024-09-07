package aids.dev.shrimphack.features.modules.chat;

import aids.dev.shrimphack.features.commands.Command;
import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.settings.Setting;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Formatting;

import java.util.HashSet;
import java.util.Set;

public class VisualRange extends Module {

    private Set<String> playersInRange;
    public Setting<EnterMessage> enterMessage = this.register(new Setting<>("Message", EnterMessage.NONE));
    public Setting<Boolean> bold = this.register(new Setting<>("Boldsn0w", true, v -> this.enterMessage.getValue() == EnterMessage.SN0W));

    public VisualRange() {
        super("VisualRange", "Notifies when players enter or leave your visual range", Category.CHAT, true, false, false);
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
                // Handle message formatting based on selected option
                String message = "";
                switch (this.enterMessage.getValue()) {
                    case FUTURE:
                        message = Formatting.RED + "[Future] " + Formatting.GRAY + playerName + Formatting.GREEN + " entered " + Formatting.GRAY + "your visual range";
                        break;
                    case PHOBOS:
                        message = " " + Formatting.GOLD + playerName + Formatting.RED + " entered your visual range";
                        break;
                    case DOTGOD:
                        message = Formatting.DARK_PURPLE + "[" + Formatting.LIGHT_PURPLE + "DotGod.CC" + Formatting.DARK_PURPLE + "] " + Formatting.LIGHT_PURPLE + playerName + Formatting.GREEN + " entered " + Formatting.LIGHT_PURPLE + "your visual range";
                        break;
                    case SN0W:
                        message = Formatting.BLUE + "[" + Formatting.AQUA + "❄" + Formatting.BLUE + "] " + Formatting.RESET + (this.bold.getValue() ? Formatting.BOLD : "") + playerName + (this.bold.getValue() ? Formatting.BOLD : "") + Formatting.RESET + Formatting.BLUE + " entered" + Formatting.AQUA + " your visual range";
                        break;
                    case SNOW:
                        message = Formatting.GRAY + "[" + Formatting.AQUA + "Snow" + Formatting.GRAY + "] " + "[" + Formatting.DARK_BLUE + "VisualRange" + "]" + playerName + "entered" + " visual range";
                        break;
                    case TROLLGOD:
                        message = Formatting.DARK_PURPLE + "[" + Formatting.LIGHT_PURPLE + "TrollGod" + Formatting.DARK_PURPLE + "] " + Formatting.LIGHT_PURPLE + "\"" + playerName + " entered your visual range";
                        break;
                    case NONE:
                    default:
                        message = Formatting.AQUA + playerName + Formatting.GREEN + " entered " + Formatting.GRAY + "your visual range";
                        break;
                }
                Command.sendSilentMessage(message);
            }
        }

        for (String playerName : playersInRange) {
            if (!currentPlayers.contains(playerName)) {
                String message = "";
                switch (this.enterMessage.getValue()) {
                    case FUTURE:
                        message = Formatting.RED + "[Future] " + Formatting.RESET + playerName + Formatting.RED + " left " + Formatting.RESET + "your visual range";
                        break;
                    case PHOBOS:
                        message = " " + Formatting.GOLD + playerName + Formatting.RED + " left your visual range";
                        break;
                    case DOTGOD:
                        message = Formatting.DARK_PURPLE + "[" + Formatting.LIGHT_PURPLE + "DotGod.CC" + Formatting.DARK_PURPLE + "] " + Formatting.LIGHT_PURPLE + playerName + Formatting.RED + " left " + Formatting.LIGHT_PURPLE + "your visual range";
                        break;
                    case SN0W:
                        message = Formatting.BLUE + "[" + Formatting.AQUA + "❄" + Formatting.BLUE + "] " + Formatting.RESET + (this.bold.getValue() ? Formatting.BOLD : "") + playerName + (this.bold.getValue() ? Formatting.BOLD : "") + Formatting.RESET + Formatting.BLUE + " left" + Formatting.AQUA + " your visual range";
                        break;
                    case SNOW:
                        message = Formatting.GRAY + "[" + Formatting.AQUA + "Snow" + Formatting.GRAY + "] " + "[" + Formatting.DARK_AQUA + "VisualRange" + "]" + playerName + " left" + "visual range";
                        break;
                    case TROLLGOD:
                        message = Formatting.DARK_PURPLE + "[" + Formatting.LIGHT_PURPLE + "TrollGod" + Formatting.DARK_PURPLE + "] " + Formatting.LIGHT_PURPLE + "\"" + playerName + " left your visual range";
                        break;
                    case NONE:
                    default:
                        message = Formatting.AQUA + playerName + Formatting.RED + " left " + Formatting.GRAY + "your visual range";
                        break;
                }
                Command.sendSilentMessage(message);
            }
        }

        playersInRange = currentPlayers;
    }

    public static enum EnterMessage {
        NONE,
        SN0W,
        SNOW,
        TROLLGOD,
        PHOBOS,
        FUTURE,
        DOTGOD;
    }
}
