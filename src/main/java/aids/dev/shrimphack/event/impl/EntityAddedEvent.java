/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package aids.dev.shrimphack.event.impl;

import net.minecraft.entity.Entity;

public class EntityAddedEvent {
    private static final EntityAddedEvent INSTANCE = new EntityAddedEvent();

    public Entity entity;

    public static EntityAddedEvent get(Entity entity) {
        INSTANCE.entity = entity;
        return INSTANCE;
    }
    public Entity getEntity() {
        return this.entity;
    }
}