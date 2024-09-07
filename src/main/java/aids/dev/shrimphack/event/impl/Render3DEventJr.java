package aids.dev.shrimphack.event.impl;

import aids.dev.shrimphack.event.Event;
import net.minecraft.client.util.math.MatrixStack;

public class Render3DEventJr extends Event {
    private final MatrixStack matrix;
    private final float delta;

    public Render3DEventJr(MatrixStack matrix, float delta) {
        this.matrix = matrix;
        this.delta = delta;
    }

    public MatrixStack getMatrix() {
        return matrix;
    }

    public float getDelta() {
        return delta;
    }
}