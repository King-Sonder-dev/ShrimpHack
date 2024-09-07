package aids.dev.shrimphack.features.modules.player;

import aids.dev.shrimphack.event.impl.PacketEvent;
import aids.dev.shrimphack.features.modules.Module;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;

import com.google.common.eventbus.Subscribe;


public class XCarry extends Module {
            private static XCarry INSTANCE = new XCarry();
    public XCarry() {
        super("XCarry", "XCarry", Category.PLAYER, true, false, false);
        this.setInstance();
    }

    public static XCarry getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new XCarry();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Subscribe//@Override
    public void onPacketSend(PacketEvent.Send e) {
        if (e.getPacket() instanceof CloseHandledScreenC2SPacket) e.cancel();
    }
}
