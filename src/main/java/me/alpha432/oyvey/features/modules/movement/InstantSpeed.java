package me.alpha432.oyvey.features.modules.movement;

import com.google.common.eventbus.Subscribe;
import me.alpha432.oyvey.event.impl.MoveEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import me.alpha432.oyvey.manager.HoleManager;
import me.alpha432.oyvey.util.BlockUtil;
import me.alpha432.oyvey.util.EntityUtil;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

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