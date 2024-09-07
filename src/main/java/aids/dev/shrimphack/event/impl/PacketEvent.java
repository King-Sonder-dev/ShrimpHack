package aids.dev.shrimphack.event.impl;

import aids.dev.shrimphack.event.Event;
import net.minecraft.network.packet.Packet;

import java.util.HashSet;
import java.util.Set;

import static aids.dev.shrimphack.util.traits.Util.mc;


public abstract class PacketEvent extends Event {

    private final Packet<?> packet;
    private static final Set<Packet<?>> PACKET_CACHE = new HashSet<>();


    public PacketEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }

    public static class Receive extends PacketEvent {
        public Receive(Packet<?> packet) {
            super(packet);
        }
    }

    public static class Send extends PacketEvent {
        public Send(Packet<?> packet) {
            super(packet);
        }
    }
    public static void sendPacket(final Packet<?> p) {
        if (mc.getNetworkHandler() != null) {
            PACKET_CACHE.add(p);
            mc.getNetworkHandler().sendPacket(p);
        }
    }


        @Cancelable
        public static class Inbound extends PacketEvent {
            /**
             * @param packet
             */
            public Inbound(Packet<?> packet) {
                super(packet);
            }
        }

        /**
         *
         *
         */
        @Cancelable
        public static class Outbound extends PacketEvent {
            //
            private final boolean cached;

            /**
             * @param packet
             */
            public Outbound(Packet<?> packet) {
                super(packet);
                this.cached = isCached(packet);
            }

            /**
             * @return
             */
            public boolean isClientPacket() {
                return cached;
            }
        }
    public boolean isCached(Packet<?> p) {
        return PACKET_CACHE.contains(p);
    }

    }
