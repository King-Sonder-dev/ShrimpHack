package aids.dev.shrimphack.event.impl;

import aids.dev.shrimphack.event.impl.Combineddeathevent.DeathEvent;
import aids.dev.shrimphack.event.impl.Combineddeathevent.TotemPopEvent;
import aids.dev.shrimphack.features.modules.chat.PopCounter;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;

import static aids.dev.shrimphack.util.traits.Util.mc;


public class ListenerTotems {

    @EventHandler
    public void onPacketReceive(PacketEvent.Receive event) {
        EntityStatusS2CPacket packet = (EntityStatusS2CPacket) event.getPacket();
        Entity entity = packet.getEntity(mc.world);
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            switch (packet.getStatus()) {
                case 3:
                    // Handle death event
                    mc.execute(() -> {
                        if (mc.world != null) {
                            int pops = PopCounter.TotemPopContainer.getOrDefault(player.getName().getString(), 0);
                            if (pops > 0) {
                                DeathEvent deathEvent = new DeathEvent(player);
                                EventBus.post(deathEvent);
                                PopCounter.TotemPopContainer.remove(player.getName().getString());
                            }
                        }
                    });
                    break;
                case 35:
                    // Handle totem pop event
                    mc.execute(() -> {
                        if (mc.world != null) {
                            int pops = PopCounter.TotemPopContainer.getOrDefault(player.getName().getString(), 0) + 1;
                            TotemPopEvent totemPopEvent = new TotemPopEvent(player, pops);
                            EventBus.post(totemPopEvent);
                            PopCounter.TotemPopContainer.put(player.getName().getString(), pops);
                        }
                    });
                    break;
                default:
                    // Do nothing
                    break;
            }
        }
    }
}
