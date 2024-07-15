package me.alpha432.oyvey.features.modules.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import me.alpha432.oyvey.features.settings.Setting;
import me.alpha432.oyvey.features.commands.Command;
import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.util.Formatting;

import java.util.HashMap;

public class PopCounterRewrite extends Module {

    public static HashMap<String, Integer> TotemPopContainer = new HashMap<>();
    private static PopCounterRewrite INSTANCE = new PopCounterRewrite();
    public Setting<PopNotifier> popNotifier = this.register(new Setting<>("PopNotifier", PopNotifier.NONE));

    public PopCounterRewrite() {
        super("PopCounterRewrite", "Counts other players totem pop.", Category.CLIENT, true, false, false);
        this.setInstance();
    }

    public static PopCounterRewrite getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PopCounterRewrite();
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

    public String death1(PlayerEntity player) {
        int l_Count = TotemPopContainer.get(player.getName().getString());
        TotemPopContainer.remove(player.getName().getString());
        if (l_Count == 1) {
            if (!this.isEnabled()) {
                return " " + Formatting.WHITE + player.getName().getString() + " popped " + Formatting.GREEN + l_Count + Formatting.WHITE + " Totems.";
            }
            switch (this.popNotifier.getValue()) {
                case FUTURE: {
                    String text = Formatting.RED + "[Future] " + Formatting.GREEN + player.getName().getString() + Formatting.GRAY + " died after popping " + Formatting.GREEN + l_Count + Formatting.GRAY + " totem.";
                    return text;
                }
                case PHOBOS: {
                    String text = " " + Formatting.GOLD + player.getName().getString() + Formatting.RED + " died after popping " + Formatting.GOLD + l_Count + Formatting.RED + " totem.";
                    return text;
                }
                case DOTGOD: {
                    String text = Formatting.DARK_PURPLE + "[" + Formatting.LIGHT_PURPLE + "DotGod.CC" + Formatting.DARK_PURPLE + "] " + Formatting.LIGHT_PURPLE + player.getName().getString() + " died after popping " + Formatting.GREEN + l_Count + Formatting.LIGHT_PURPLE + " time!";
                    return text;
                }
                case NONE: {
                    return " " + Formatting.WHITE + player.getName().getString() + " popped " + Formatting.GREEN + l_Count + Formatting.WHITE + " Totems.";
                }
            }
        } else {
            if (!this.isEnabled()) {
                return " " + Formatting.WHITE + player.getName().getString() + " popped " + Formatting.GREEN + l_Count + Formatting.WHITE + " Totems.";
            }
            switch (this.popNotifier.getValue()) {
                case FUTURE: {
                    String text = Formatting.RED + "[Future] " + Formatting.GREEN + player.getName().getString() + Formatting.GRAY + " died after popping " + Formatting.GREEN + l_Count + Formatting.GRAY + " totems.";
                    return text;
                }
                case PHOBOS: {
                    String text = " " + Formatting.GOLD + player.getName().getString() + Formatting.RED + " died after popping " + Formatting.GOLD + l_Count + Formatting.RED + " totems.";
                    return text;
                }
                case DOTGOD: {
                    String text = Formatting.DARK_PURPLE + "[" + Formatting.LIGHT_PURPLE + "DotGod.CC" + Formatting.DARK_PURPLE + "] " + Formatting.LIGHT_PURPLE + player.getName().getString() + " died after popping " + Formatting.GREEN + l_Count + Formatting.LIGHT_PURPLE + " times!";
                    return text;
                }
                case NONE: {
                    return " " + Formatting.WHITE + player.getName().getString() + " popped " + Formatting.GREEN + l_Count + Formatting.WHITE + " Totems.";
                }
            }
        }
        return null;
    }

    public void onDeath(PlayerEntity player) {
        if (PopCounterRewrite.fullNullCheck()) {
            return;
        }
        if (getInstance().isDisabled())
            return;
        if (MinecraftClient.getInstance().player.equals(player)) {
            return;
        }
        if (TotemPopContainer.containsKey(player.getName().getString())) {
            Command.sendSilentMessage(death1(player));
        }
    }

    public String pop(PlayerEntity player) {
        int l_Count = 1;
        if (TotemPopContainer.containsKey(player.getName().getString())) {
            l_Count = TotemPopContainer.get(player.getName().getString());
            TotemPopContainer.put(player.getName().getString(), ++l_Count);
        } else {
            TotemPopContainer.put(player.getName().getString(), l_Count);
        }
        if (l_Count == 1) {
            if (ModuleTools.getInstance().isEnabled()) {
                return " " + Formatting.WHITE + player.getName().getString() + " popped " + Formatting.GREEN + l_Count + Formatting.WHITE + " Totems.";
            }
            switch (this.popNotifier.getValue()) {
                case FUTURE: {
                    String text = Formatting.RED + "[Future] " + Formatting.GREEN + player.getName().getString() + Formatting.GRAY + " just popped " + Formatting.GREEN + l_Count + Formatting.GRAY + " totem.";
                    return text;
                }
                case PHOBOS: {
                    String text = " " + Formatting.GOLD + player.getName().getString() + Formatting.RED + " popped " + Formatting.GOLD + l_Count + Formatting.RED + " totem.";
                    return text;
                }
                case DOTGOD: {
                    String text = Formatting.DARK_PURPLE + "[" + Formatting.LIGHT_PURPLE + "DotGod.CC" + Formatting.DARK_PURPLE + "] " + Formatting.LIGHT_PURPLE + player.getName().getString() + " has popped " + Formatting.RED + l_Count + Formatting.LIGHT_PURPLE + " time in total!";
                    return text;
                }
                case NONE: {
                    return " " + Formatting.WHITE + player.getName().getString() + " popped " + Formatting.GREEN + l_Count + Formatting.WHITE + " Totems.";
                }
            }
        } else {
            if (!this.isEnabled()) {
                return " " + Formatting.WHITE + player.getName().getString() + " popped " + Formatting.GREEN + l_Count + Formatting.WHITE + " Totems.";
            }
            switch (this.popNotifier.getValue()) {
                case FUTURE: {
                    String text = Formatting.RED + "[Future] " + Formatting.GREEN + player.getName().getString() + Formatting.GRAY + " just popped " + Formatting.GREEN + l_Count + Formatting.GRAY + " totems.";
                    return text;
                }
                case PHOBOS: {
                    String text = " " + Formatting.GOLD + player.getName().getString() + Formatting.RED + " popped " + Formatting.GOLD + l_Count + Formatting.RED + " totems.";
                    return text;
                }
                case DOTGOD: {
                    String text = Formatting.DARK_PURPLE + "[" + Formatting.LIGHT_PURPLE + "DotGod.CC" + Formatting.DARK_PURPLE + "] " + Formatting.LIGHT_PURPLE + player.getName().getString() + " has popped " + Formatting.RED + l_Count + Formatting.LIGHT_PURPLE + " times in total!";
                    return text;
                }
                case NONE: {
                    return " " + Formatting.WHITE + player.getName().getString() + " popped " + Formatting.GREEN + l_Count + Formatting.WHITE + " Totems.";
                }
            }
        }
        return "";
    }

    public void onTotemPop(PlayerEntity player) {
        if (PopCounterRewrite.fullNullCheck()) {
            return;
        }
        if (getInstance().isDisabled())
            return;
        if (MinecraftClient.getInstance().player.equals(player)) {
            return;
        }
        Command.sendSilentMessage(pop(player));
    }

    public static enum PopNotifier {
        NONE,
        PHOBOS,
        FUTURE,
        DOTGOD;
    }
}
