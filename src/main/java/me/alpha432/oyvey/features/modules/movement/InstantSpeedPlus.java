package me.alpha432.oyvey.features.modules.movement;

import me.alpha432.oyvey.event.impl.MoveEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import me.alpha432.oyvey.manager.HoleManager;
import me.alpha432.oyvey.util.EntityUtil;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public class InstantSpeedPlus extends Module {
    private static InstantSpeedPlus INSTANCE = new InstantSpeedPlus();

    private final Setting<Autoenable> autoEnable = this.register(new Setting<>("Autoenable", Autoenable.NONE));
    private final Setting<Integer> enableTime = this.register(new Setting<>("EnableTime", 5, 1, 10, v -> autoEnable.getValue() == Autoenable.TIME));
    private long startTime;
    private final HoleManager holeManager = new HoleManager();

    public enum Autoenable {
        NONE,
        HOLE,
        TIME
    }

    public InstantSpeedPlus() {
        super("InstantSpeed", "instant speed", Category.MOVEMENT, true, false, false);
        this.setInstance();
    }

    @Override
    public void onEnable() {
        if (autoEnable.getValue() == Autoenable.TIME) {
            startTime = System.currentTimeMillis();
        }

        ServerTickEvents.END_SERVER_TICK.register(this::onServerTick);
    }

    private void onServerTick(MinecraftServer server) {
        if (mc.player != null) {
            if (autoEnable.getValue() == Autoenable.HOLE) {
                BlockPos playerPos = new BlockPos((int) mc.player.getX(), (int) mc.player.getY(), (int) mc.player.getZ());
                if (holeManager.isHole(playerPos)) {
                    this.disable();
                }
            }

            if (autoEnable.getValue() == Autoenable.TIME) {
                if (System.currentTimeMillis() - startTime >= enableTime.getValue() * 1000) {
                    if (this.isEnabled()) {
                        this.disable();
                    } else {
                        this.enable();
                    }
                    startTime = System.currentTimeMillis();
                }
            }

            double[] speed = EntityUtil.forward(getSpeed(true));
            MoveEvent moveEvent = new MoveEvent(0, MovementType.SELF, speed[0], 0, speed[1]);
            onMove(moveEvent);
        }
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

    @Override
    public String getDisplayInfo() {
        if (autoEnable.getValue() == Autoenable.HOLE) {
            BlockPos playerPos = new BlockPos((int) mc.player.getX(), (int) mc.player.getY(), (int) mc.player.getZ());
            return holeManager.isHole(playerPos) ? "Off" : "On";
        }

        if (autoEnable.getValue() == Autoenable.TIME) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            long remainingTime = (enableTime.getValue() * 1000) - elapsedTime;
            return String.valueOf(Math.max(remainingTime / 1000, 0));
        }

        return null;
    }
}