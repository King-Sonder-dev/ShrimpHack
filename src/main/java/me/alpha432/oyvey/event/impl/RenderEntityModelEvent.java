package me.alpha432.oyvey.event.impl;

import net.minecraft.client.model.Model;
import net.minecraft.entity.player.PlayerEntity;

public class RenderEntityModelEvent extends Event {
    private final Model modelBase;
    private final PlayerEntity entity;
    private final float limbSwing;
    private final float limbSwingAmount;
    private final float age;
    private final float headYaw;
    private final float headPitch;
    private final float scale;

    public RenderEntityModelEvent(int stage, Model modelBase, PlayerEntity entity, PlayerEntity entity1, float limbSwing, float limbSwingAmount, float age, float headYaw, float headPitch, float scale) {
        super(null);
        this.modelBase = modelBase;
        this.entity = entity1;
        this.limbSwing = limbSwing;
        this.limbSwingAmount = limbSwingAmount;
        this.age = age;
        this.headYaw = headYaw;
        this.headPitch = headPitch;
        this.scale = scale;
    }

    public Model getModelBase() {
        return modelBase;
    }

    public PlayerEntity getEntity() {
        return entity;
    }

    public float getLimbSwing() {
        return limbSwing;
    }

    public float getLimbSwingAmount() {
        return limbSwingAmount;
    }

    public float getAge() {
        return age;
    }

    public float getHeadYaw() {
        return headYaw;
    }

    public float getHeadPitch() {
        return headPitch;
    }

    public float getScale() {
        return scale;
    }
}
