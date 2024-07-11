package me.alpha432.oyvey.event.impl;

import me.alpha432.oyvey.event.Event;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class Combineddeathevent {

    public static class DeathEvent extends Event {
        private final LivingEntity entity;

        public DeathEvent(LivingEntity entity) {
            this.entity = entity;
        }

        public LivingEntity getEntity() {
            return entity;
        }
    }

    public static class TotemPopEvent extends Event {
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
}
