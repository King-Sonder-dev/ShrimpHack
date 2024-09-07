package aids.dev.shrimphack.event.impl.autototem;

import aids.dev.shrimphack.event.Event;
import net.minecraft.entity.Entity;

public class EventEntitySpawn extends Event {
    private final Entity entity;
    public EventEntitySpawn(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return this.entity;
    }
}
