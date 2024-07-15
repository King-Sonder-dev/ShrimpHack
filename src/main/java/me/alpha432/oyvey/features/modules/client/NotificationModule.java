package me.alpha432.oyvey.features.modules.client;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import me.alpha432.oyvey.features.commands.Command;
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
        for (Module module : OyVey.moduleManager.modules) {
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
                return Formatting.RED + "[Future] " + Formatting.RESET + moduleName + " toggled " + futureaction + ".";
            case PHOBOS:
                return " " + Formatting.GOLD + moduleName + Formatting.RED + " has been " + action + ".";
            case DOTGOD:
                return Formatting.DARK_PURPLE + "[" + Formatting.LIGHT_PURPLE + "DotGod.CC" + Formatting.DARK_PURPLE + "] " + Formatting.DARK_AQUA + moduleName + Formatting.LIGHT_PURPLE + " was " + action;
            case SN0W:
                return Formatting.BLUE + "[" + Formatting.AQUA + "❄" + Formatting.BLUE + "] " + Formatting.RESET + (this.bold.getValue() ? Formatting.BOLD : "") + moduleName + Formatting.RESET + " has been " + action + "!";
            case SNOW:
                return "[" + Formatting.AQUA + "Snow" + Formatting.RESET + "]" + Formatting.RESET + " [" + Formatting.DARK_AQUA + moduleName  + Formatting.RESET+ "] " + action;
            case NONE:
            default:
                return " " + Formatting.WHITE + moduleName + " has been " + Formatting.GREEN + action + Formatting.WHITE + ".";
        }
    }

    private Module getModuleByName(String name) {
        return OyVey.moduleManager.getModuleByName(name);
    }

    public enum NotificationStyle {
        NONE,
        FUTURE,
        PHOBOS,
        DOTGOD,
        SN0W,
        SNOW
    }
}