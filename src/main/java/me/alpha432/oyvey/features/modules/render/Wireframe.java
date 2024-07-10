package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.features.modules.Module;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class Wireframe extends Module {

    public Wireframe() {
        super("Wireframe", "Outlines players and end crystals.", Category.RENDER, true, false, false);
    }

    @Override
    public void onEnable() {
        WorldRenderEvents.AFTER_ENTITIES.register(this::onAfterEntities);
    }

    @Override
    public void onDisable() {
    }

    private void onAfterEntities(WorldRenderContext context) {
        MatrixStack matrices = context.matrixStack();
        VertexConsumerProvider vertexConsumers = context.consumers();

        for (Entity entity : context.world().getEntities()) {
            if (entity instanceof PlayerEntity || entity.getType().getSpawnGroup() == net.minecraft.entity.SpawnGroup.MISC) {
                renderWireframe(matrices, vertexConsumers, entity);
            }
        }
    }

    private void renderWireframe(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Entity entity) {
        Box box = entity.getBoundingBox().expand(0.1);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getLines());
        renderBox(matrices, vertexConsumer, box);
    }

    private void renderBox(MatrixStack matrices, VertexConsumer vertexConsumer, Box box) {
        Vec3d min = new Vec3d(box.minX, box.minY, box.minZ);
        Vec3d max = new Vec3d(box.maxX, box.maxY, box.maxZ);

        matrices.push();
        matrices.translate(min.x, min.y, min.z);
        matrices.scale((float) (max.x - min.x), (float) (max.y - min.y), (float) (max.z - min.z));

        MatrixStack.Entry entry = matrices.peek();
        drawLine(entry, vertexConsumer, 0, 0, 0, 1, 0, 0);
        drawLine(entry, vertexConsumer, 1, 0, 0, 1, 1, 0);
        drawLine(entry, vertexConsumer, 1, 1, 0, 0, 1, 0);
        drawLine(entry, vertexConsumer, 0, 1, 0, 0, 0, 0);
        drawLine(entry, vertexConsumer, 0, 0, 1, 1, 0, 1);
        drawLine(entry, vertexConsumer, 1, 0, 1, 1, 1, 1);
        drawLine(entry, vertexConsumer, 1, 1, 1, 0, 1, 1);
        drawLine(entry, vertexConsumer, 0, 1, 1, 0, 0, 1);
        drawLine(entry, vertexConsumer, 0, 0, 0, 0, 0, 1);
        drawLine(entry, vertexConsumer, 1, 0, 0, 1, 0, 1);
        drawLine(entry, vertexConsumer, 1, 1, 0, 1, 1, 1);
        drawLine(entry, vertexConsumer, 0, 1, 0, 0, 1, 1);
        matrices.pop();
    }

    private void drawLine(MatrixStack.Entry entry, VertexConsumer vertexConsumer, float x1, float y1, float z1, float x2, float y2, float z2) {
        vertexConsumer.vertex(entry.getPositionMatrix(), x1, y1, z1).color(0, 255, 0, 255).normal(entry.getNormalMatrix(), 0, 0, 0).next();
        vertexConsumer.vertex(entry.getPositionMatrix(), x2, y2, z2).color(0, 255, 0, 255).normal(entry.getNormalMatrix(), 0, 0, 0).next();
    }
}