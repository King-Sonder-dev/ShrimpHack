package aids.dev.shrimphack.features.modules.movement;

import aids.dev.shrimphack.event.impl.MoveEvent;
import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.util.Combat.EntityUtil;
import com.google.common.eventbus.Subscribe;
import net.minecraft.entity.effect.StatusEffects;

import java.util.Objects;

public class InstantSpeed extends Module {
    private static InstantSpeed INSTANCE = new InstantSpeed();

    public InstantSpeed() {
        super("InstantSpeed", "instant speed", Category.MOVEMENT, true, false, false);
        this.setInstance();
    }
    @Subscribe
    public void onMove(MoveEvent event) {
        double[] speed = EntityUtil.forward(InstantSpeed.getSpeed(true));
        event.setX(speed[0]);
        event.setZ(speed[1]);
    }

    public static InstantSpeed getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InstantSpeed();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    public static double getSpeed(boolean slowness) {
        int amplifier;
        double defaultSpeed = 0.2873;
        if (InstantSpeed.mc.player.hasStatusEffect(StatusEffects.SPEED)) {
            amplifier = Objects.requireNonNull(InstantSpeed.mc.player.getStatusEffect(StatusEffects.SPEED)).getAmplifier();
            defaultSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
        }
        if (slowness && InstantSpeed.mc.player.hasStatusEffect(StatusEffects.SLOWNESS)) {
            amplifier = Objects.requireNonNull(InstantSpeed.mc.player.getStatusEffect(StatusEffects.SLOWNESS)).getAmplifier();
            defaultSpeed /= 1.0 + 0.2 * (double)(amplifier + 1);
        }
        return defaultSpeed;
    }
}