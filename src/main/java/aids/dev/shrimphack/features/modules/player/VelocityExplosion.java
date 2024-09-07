package aids.dev.shrimphack.features.modules.player;

import aids.dev.shrimphack.event.impl.PacketEvent;
import aids.dev.shrimphack.features.modules.Module;
import com.google.common.eventbus.Subscribe;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;

public class VelocityExplosion extends Module {
    public VelocityExplosion() {
        super("VelocityExplosion", "", Category.PLAYER, true, false, false);
    }

    @Subscribe private void onPacketReceive(PacketEvent.Receive event) {
        if (event.getPacket() instanceof EntityVelocityUpdateS2CPacket || event.getPacket() instanceof ExplosionS2CPacket) event.cancel();
    }
}
