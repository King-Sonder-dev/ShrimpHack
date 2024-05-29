package me.alpha432.oyvey.features.modules.oyveydotconfirm;

import me.alpha432.oyvey.features.Feature;
import me.alpha432.oyvey.features.commands.Command;
import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;

import static me.alpha432.oyvey.OyVey.INSTANCE;

public class Totempopcounter extends Module {
    private int popCounter;
    public static HashMap<String, Integer> TotemPopContainer = new HashMap<>();
    private static Totempopcounter INSTANCE = new Totempopcounter();


    public Totempopcounter() {
        super("TotemPopCounter", "", Module.Category.OYVEYDOTCONFIRM, true, false, false);
        this.setInstance();

    }

    public static boolean fullNullCheck() {
        return Feature.mc.player == null || Feature.mc.world == null;
    }
    public static Totempopcounter getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Totempopcounter();
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

    public void onDeath(PlayerEntity player) {
        if (TotemPopContainer.containsKey(player.getName().getString())) {
            int l_Count = TotemPopContainer.get(player.getName().getString());
            TotemPopContainer.remove(player.getName().getString());
            if (l_Count == 1) {
                Command.sendMessage(player.getName() + " died after popping " +  l_Count + " Totem!");
            } else {
                Command.sendMessage(player.getName() + " died after popping " +  l_Count + " Totems!");
            }
        }
    }
    public void onTotemPop(PlayerEntity player) {
        if (fullNullCheck()) {
            return;
        }
        if (mc.player.equals(player)) {
            return;
        }
        int l_Count = 1;
        if (TotemPopContainer.containsKey(player.getName().getString())) {
            l_Count = TotemPopContainer.get(player.getName().getString());
            TotemPopContainer.put(String.valueOf(player.getName()), ++l_Count);
        } else {
            TotemPopContainer.put(String.valueOf(player.getName()), l_Count);
        }
        if (l_Count == 1) {
            Command.sendMessage(player.getName() + " popped " + l_Count + " Totem.");
        } else {
            Command.sendMessage(player.getName() + " popped " + l_Count + " Totems.");
        }
    }
}