package me.alpha432.oyvey.features.modules.movement;

import me.alpha432.oyvey.event.impl.MoveEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.util.EntityUtil;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.MovementType;

import java.util.Objects;

public class InstantSpeedPlus extends Module {
    private static InstantSpeedPlus INSTANCE = new InstantSpeedPlus();

    public InstantSpeedPlus() {
        super("InstantSpeed", "instant speed", Category.MOVEMENT, true, false, false);
        this.setInstance();
    }

    @Override
    public void onEnable() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (mc.player != null) {
                double[] speed = EntityUtil.forward(getSpeed(true));
                MoveEvent moveEvent = new MoveEvent(0, MovementType.SELF, speed[0], 0, speed[1]);
                onMove(moveEvent);
            }
        });
    }

    public void onMove(MoveEvent event) {
        event.setX(event.getX());
        event.setZ(event.getZ());
    }

    public static InstantSpeedPlus getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InstantSpeedPlus();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    public static double getSpeed(boolean slowness) {
        double defaultSpeed = 0.2873;
        PlayerEntity player = mc.player;

        assert player != null;
        if (player.hasStatusEffect(StatusEffects.SPEED)) {
            int amplifier = Objects.requireNonNull(player.getStatusEffect(StatusEffects.SPEED)).getAmplifier();
            defaultSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        if (slowness && player.hasStatusEffect(StatusEffects.SLOWNESS)) {
            int amplifier = Objects.requireNonNull(player.getStatusEffect(StatusEffects.SLOWNESS)).getAmplifier();
            defaultSpeed /= 1.0 + 0.2 * (amplifier + 1);
        }
        return defaultSpeed;
    }
}