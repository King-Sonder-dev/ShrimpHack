package aids.dev.shrimphack.event.impl;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;

public class MoveEvent {
    private int stage;
    private MovementType type;
    private double x;
    private double y;
    private double z;

    public MoveEvent(int stage, MovementType type, double x, double y, double z) {
        this.stage = stage;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getStage() {
        return this.stage;
    }

    public MovementType getType() {
        return this.type;
    }

    public void setType(MovementType type) {
        this.type = type;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public static final Event<MoveEventCallback> EVENT = EventFactory.createArrayBacked(MoveEventCallback.class,
            (listeners) -> (entity, stage, type, x, y, z) -> {
                for (MoveEventCallback listener : listeners) {
                    listener.onMove(entity, stage, type, x, y, z);
                }
            });

    @FunctionalInterface
    public interface MoveEventCallback {
        void onMove(Entity entity, int stage, MovementType type, double x, double y, double z);
    }
}