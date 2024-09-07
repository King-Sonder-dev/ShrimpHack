package aids.dev.shrimphack.event.impl.framedupe;

import aids.dev.shrimphack.event.Event;
import net.minecraft.entity.Entity;
import net.minecraft.util.Hand;

public class InteractEntityEvent extends Event {
    private static final InteractEntityEvent INSTANCE = new InteractEntityEvent();

    public Entity entity;
    public Hand hand;

    public static InteractEntityEvent get(Entity entity, Hand hand) {
        INSTANCE.setCancelled(false);
        INSTANCE.entity = entity;
        INSTANCE.hand = hand;
        return INSTANCE;
    }
}