package aids.dev.shrimphack.event.impl;

import aids.dev.shrimphack.event.Event;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

public class Render2DEvent extends Event {
    private final DrawContext context;
    private final float delta;

    public Render2DEvent(DrawContext context, float delta) {
        this.context = context;
        this.delta = delta;
    }

    public DrawContext getContext() {
        return context;
    }

    public float getDelta() {
        return delta;
    }

    public MatrixStack getMatrixStack() {
        return context.getMatrices();
    }
}