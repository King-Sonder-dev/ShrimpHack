package aids.dev.shrimphack.features.modules.client;

import aids.dev.shrimphack.Shrimphack;
import aids.dev.shrimphack.features.commands.Command;
import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.settings.Setting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.Map;

public class NotificationModule extends Module {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private final Map<String, Setting<Boolean>> moduleSettings = new HashMap<>();
    private final Map<Module, Boolean> moduleStates = new HashMap<>();
    public final Setting<NotificationStyle> notificationStyle = this.register(new Setting<>("NotificationStyle", NotificationStyle.NONE));
    public final Setting<Boolean> bold = this.register(new Setting<>("Bold", false));

    public NotificationModule() {
        super("NotificationModule", "Sends notifications for selected modules.", Category.CLIENT, true, false, false);
        initializeModuleSettings();
    }

    private void initializeModuleSettings() {
        for (Module module : Shrimphack.moduleManager.modules) {
            if (!module.getName().equals(this.getName())) {
                Setting<Boolean> setting = this.register(new Setting<>(module.getName(), false));
                moduleSettings.put(module.getName(), setting);
            }
        }
    }

    @Override
    public void onEnable() {
        moduleStates.clear();
        updateModuleStates();
    }

    @Override
    public void onUpdate() {
        for (Map.Entry<String, Setting<Boolean>> entry : moduleSettings.entrySet()) {
            if (entry.getValue().getValue()) {
                checkModuleState(entry.getKey(), entry.getValue().getValue());
            }
        }
    }

    private void updateModuleStates() {
        for (Map.Entry<String, Setting<Boolean>> entry : moduleSettings.entrySet()) {
            if (entry.getValue().getValue()) {
                Module module = getModuleByName(entry.getKey());
                moduleStates.put(module, module.isEnabled());
            }
        }
    }

    private void checkModuleState(String moduleName, boolean notify) {
        Module module = getModuleByName(moduleName);
        boolean currentState = module.isEnabled();

        if (moduleStates.containsKey(module)) {
            boolean previousState = moduleStates.get(module);

            if (previousState != currentState) {
                sendChatNotification(moduleName, currentState);
                moduleStates.put(module, currentState);
            }
        } else {
            moduleStates.put(module, currentState);
        }
    }

    private void sendChatNotification(String moduleName, boolean enabled) {
        String message = getFormattedMessage(moduleName, enabled);
        Command.sendSilentMessage(message);
    }

    private String getFormattedMessage(String moduleName, boolean enabled) {
        String action = enabled ? Formatting.GREEN + "enabled" : Formatting.RED + "disabled";
        String futureaction = enabled ? Formatting.GREEN + "on" : Formatting.RED + "off";
        switch (this.notificationStyle.getValue()) {
            case FUTURE:
                return Formatting.RED + "[Future] " + Formatting.RESET + moduleName + " toggled " + futureaction + Formatting.RESET + ".";
            case PHOBOS:
                return " " + Formatting.GOLD + moduleName + Formatting.RED + " has been " + action + ".";
            case DOTGOD:
                return Formatting.DARK_PURPLE + "[" + Formatting.LIGHT_PURPLE + "DotGod.CC" + Formatting.DARK_PURPLE + "] " + Formatting.DARK_AQUA + moduleName + Formatting.LIGHT_PURPLE + " was " + action;
            case SN0W:
                return Formatting.BLUE + "[" + Formatting.AQUA + "❄" + Formatting.BLUE + "] " + Formatting.RESET + (this.bold.getValue() ? Formatting.BOLD : "") + moduleName + Formatting.RESET + " has been " + action + "!";
            case SNOW:
                return Formatting.GRAY + "[" + Formatting.AQUA + "Snow" + Formatting.GRAY + "]" + Formatting.GRAY + " [" + Formatting.DARK_AQUA + moduleName  + Formatting.GRAY + "] " + action;
            case TROLLGOD:
                return Formatting.DARK_PURPLE + "[" + Formatting.LIGHT_PURPLE + "TrollGod" + Formatting.DARK_PURPLE + "] " + Formatting.LIGHT_PURPLE + moduleName + Formatting.LIGHT_PURPLE + " was " + action;
            case EARTH:
                return Formatting.BOLD + moduleName + Formatting.RESET + " " + action;
            case NONE:
            default:
                return  Formatting.DARK_AQUA + moduleName + Formatting.RESET + " has been " + Formatting.BOLD + action;
        }
    }

    private Module getModuleByName(String name) {
        return Shrimphack.moduleManager.getModuleByName(name);
    }

    public enum NotificationStyle {
        NONE,
        FUTURE,
        PHOBOS,
        DOTGOD,
        SN0W,
        SNOW,
        TROLLGOD,
        EARTH
    }
}