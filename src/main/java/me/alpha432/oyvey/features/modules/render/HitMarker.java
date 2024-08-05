package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.event.impl.PacketEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import me.alpha432.oyvey.util.models.Timer;
import me.alpha432.oyvey.event.impl.EventHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Identifier;

public class HitMarker extends Module {
    public HitMarker() {
        super("HitMarker", "", Module.Category.RENDER, true, false, false);
    }
    public Setting<Integer> time = this.register(new Setting<Integer>("Show Time", 3, 0, 60));

    private final Identifier marker = new Identifier("boyvey", "hitmarker.png");
    public Timer timer = new Timer();
    public int ticks=114514;
    @Override
    public void onEnable() {
        ticks=114514;
        timer.reset();
    }
    public void onRender2D(DrawContext drawContext, float tickDelta) {
        if(timer.passedMs(1/20)) {
            timer.reset();
            if (ticks <= time.getValue()) {
                ++ticks;
               drawContext.drawTexture(marker,mc.getWindow().getScaledWidth()/2-8,mc.getWindow().getScaledHeight()/2-8,0,0,0,16,16,16,16);
            }
        }
    }

    @EventHandler
    public void onpacket(PacketEvent.Send event){
        if(event.getPacket() instanceof PlayerInteractEntityC2SPacket){
            ticks=0;

        }
    }
}