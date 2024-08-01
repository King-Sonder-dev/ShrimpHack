package me.alpha432.oyvey.features.modules.chat;

import me.alpha432.oyvey.features.commands.Command;
import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.entity.Entity;

import static me.alpha432.oyvey.features.modules.combat.autocrystal.AutoCrystal.target;
import static me.alpha432.oyvey.util.Jewedutil.isBurrowed;

public class BurrowNotifier extends Module {

    public BurrowNotifier() {
        super("BurrowNotifier", "Notifies when a player is burrowed.", Category.CHAT, true, false, false);
    }

    @Override
    public void onEnable() {
        checkBurrow();
        this.disable();
    }

    private void checkBurrow() {


        if (isBurrowed(target)) {
                Command.serverSendMessage(mc.player.getName().getString() + " is burrowed!");
            }
        }
    }