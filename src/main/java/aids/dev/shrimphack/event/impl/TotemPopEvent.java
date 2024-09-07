package aids.dev.shrimphack.event.impl;

import aids.dev.shrimphack.event.Event;
import net.minecraft.entity.player.PlayerEntity;

public class TotemPopEvent extends Event {
    private final PlayerEntity entity;
    private int pops;

    public TotemPopEvent(PlayerEntity entity, int pops) {
        this.entity = entity;
        this.pops = pops;
    }

    public PlayerEntity getEntity() {
        return this.entity;
    }

    public int getPops() {
        return this.pops;
    }
}