package aids.dev.shrimphack.features.modules.chat;

import aids.dev.shrimphack.features.commands.Command;
import aids.dev.shrimphack.features.modules.Module;

import static me.alpha432.oyvey.features.modules.combat.autocrystal.AutoCrystal.target;
import static me.alpha432.oyvey.util.Jewedutil.isBurrowed;

public class BurrowNotifier extends Module {

    public BurrowNotifier() {
        super("BurrowNotifier", "Notifies when a player is burrowed.", Category.CHAT, true, false, false);
    }

    @Override
    public void onEnable() {
        checkBurrow();
    }

    private void checkBurrow() {


        if (isBurrowed(target)) {
                Command.serverSendMessage(mc.player.getName().getString() + " is burrowed!");
            }
        }
    }