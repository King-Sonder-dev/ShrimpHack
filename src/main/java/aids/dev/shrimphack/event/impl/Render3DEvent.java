package aids.dev.shrimphack.event.impl;

import aids.dev.shrimphack.event.Event;
import net.minecraft.client.util.math.MatrixStack;

public class Render3DEvent extends Event {
        private final float partialTicks;
        private final MatrixStack matrixStack;


    public Render3DEvent(MatrixStack matrixStack, float partialTicks2) {
            this.partialTicks = partialTicks2;
            this.matrixStack = matrixStack;
        }

        public float getPartialTicks() {
            return partialTicks;
        }

        public MatrixStack getMatrixStack() {
            return matrixStack;
        }

        public static class EventRender3DNoBob extends Event {

            private final float partialTicks;
            private final MatrixStack matrixStack;

            public EventRender3DNoBob(MatrixStack matrixStack, float partialTicks2) {
                this.partialTicks = partialTicks2;
                this.matrixStack = matrixStack;
            }

            public float getPartialTicks() {
                return partialTicks;
            }

            public MatrixStack getMatrixStack() {
                return matrixStack;
            }

        }
    }
