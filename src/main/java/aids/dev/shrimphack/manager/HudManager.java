package aids.dev.shrimphack.manager;

import com.google.common.eventbus.Subscribe;
import aids.dev.shrimphack.event.impl.PacketEvent;
import aids.dev.shrimphack.event.impl.autototem.EventTick;
import aids.dev.shrimphack.features.modules.client.hud.utils.MathUtility;
import aids.dev.shrimphack.features.modules.client.hud.utils.Timer;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayDeque;

import static aids.dev.shrimphack.util.traits.Util.mc;

public class HudManager {
    public float currentPlayerSpeed;
    private final Timer timeDelay = new Timer();
    private final ArrayDeque<Float> tpsResult = new ArrayDeque<>(20);
    private long time;
    private long tickTime;
    private float tps;

    //@EventHandler
    @Subscribe
    public void onTick(EventTick e) {
        currentPlayerSpeed = (float) Math.hypot(mc.player.getX() - mc.player.prevX, mc.player.getZ() - mc.player.prevZ);
    }

    public float getTPS() {
        return round2(tps);
    }

    public float getTPS2() {
        return round2(20.0f * ((float) tickTime / 1000f));
    }

    public static float round2(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    //@EventHandler
    @Subscribe
    public void onPacketReceive(PacketEvent.@NotNull Receive event) {
        if (!(event.getPacket() instanceof ChatMessageS2CPacket)) {
            timeDelay.reset();
        }
        if (event.getPacket() instanceof WorldTimeUpdateS2CPacket) {
            if (time != 0L) {
                tickTime = System.currentTimeMillis() - time;

                if (tpsResult.size() > 20)
                    tpsResult.poll();

                tpsResult.add(20.0f * (1000.0f / (float) (tickTime)));

                float average = 0.0f;

                for (Float value : tpsResult) average += MathUtility.clamp(value, 0f, 20f);

                tps = average / (float) tpsResult.size();
            }
            time = System.currentTimeMillis();
        }
    }


    public static int getPing() {
        if (mc.getNetworkHandler() == null || mc.player == null) return 0;

        //if (FastLatency.instance.isEnabled())
        //    return FastLatency.instance.resolvedPing;

        PlayerListEntry playerListEntry = mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid());
        if (playerListEntry == null) return 0;
        return playerListEntry.getLatency();
    }

    public int getfps() {
        mc.getCurrentFps();
        return 0;
    }
}
