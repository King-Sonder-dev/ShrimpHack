package aids.dev.shrimphack.features.modules.movement.autosprint;

import aids.dev.shrimphack.event.impl.TickEvent;
import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.settings.Setting;
import com.google.common.eventbus.Subscribe;


public final class Sprint extends Module {

    private final Setting<Modes> Mode = this.register(new Setting<>("Mode", Modes.Legit));

    public enum Modes {
        Rage,
        Legit
    }

    public Sprint() {
        super("Sprint", "Sprint", Category.MISC, true, false, false);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (mc.world != null && mc.player != null) mc.player.setSprinting(false);
    }

    //@Override
    public String getMetaData() {
        return String.valueOf(Mode.getValue());
    }

    //@EventHandler
        @Subscribe
    private void OnPlayerUpdate(TickEvent event) {
        //if (event.isPre()) return;
        switch (this.Mode.getValue()) {
            case Rage -> {
                if (!(mc.player.getHungerManager().getFoodLevel() <= 6)) mc.player.setSprinting(true);
            }
            case Legit -> {
                if (mc.player.forwardSpeed > 0 && !(mc.player.getHungerManager().getFoodLevel() <= 6f)) mc.options.sprintKey.setPressed(true);
            }
        }
    }
}