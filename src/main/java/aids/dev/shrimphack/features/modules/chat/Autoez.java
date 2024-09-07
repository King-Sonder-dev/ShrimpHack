package aids.dev.shrimphack.features.modules.chat;

import aids.dev.shrimphack.features.commands.Command;
import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.settings.Setting;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.util.Formatting;
import java.util.*;

import com.google.common.eventbus.Subscribe;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.event.impl.PacketEvent;

public class Autoez extends Module {
    private final Object2IntMap<UUID> totemPopMap = new Object2IntOpenHashMap<>();
    private final Setting<Boolean> totemPops = this.register(new Setting<Boolean>("totemPops", true));
    private final Setting<Boolean> totemsIgnoreOthers = this.register(new Setting<Boolean>("totemsIgnoreOthers", false, v -> this.totemPops.getValue()));
    private final Setting<Boolean> totemsIgnoreOwn = this.register(new Setting<Boolean>("totemsIgnoreOwn", true, v -> this.totemPops.getValue()));
    private final Setting<Boolean> totemsIgnoreFriends = this.register(new Setting<Boolean>("totemsIgnoreFriends", false, v -> this.totemPops.getValue()));
    private final Setting<Boolean> popevent = this.register(new Setting<Boolean>("PopEvent", false));
    private final Setting<Boolean> ignoreFriends = this.register(new Setting<Boolean>("ignoreFriends", false, v -> this.popevent.getValue()));
    private final Setting<Boolean> popmsg = this.register(new Setting<Boolean>("MessagePop", false, v -> this.popevent.getValue()));
    public Setting<String> msgpop = this.register(new Setting<String>("PopMessage", "EZ pop no sweat", v -> this.popmsg.getValue()));
    private final Setting<Boolean> popez = this.register(new Setting<Boolean>("MessageEz", false, v -> this.popevent.getValue()));
    public Setting<String> msgez = this.register(new Setting<String>("MessageDeath", "Bad asl", v -> this.popez.getValue()));


    public Autoez() {
        super("AutoEZ", "ezzzz kits noobs", Category.CHAT, true, false, false);
    }

    @Subscribe//@EventHandler
    public void onReceivePacket(PacketEvent.Receive event) {
        ChatHud chatHud = mc.inGameHud.getChatHud();
        if (!totemPops.getValue()) return;
        if (!(event.getPacket() instanceof EntityStatusS2CPacket p)) return;

        if (p.getStatus() != 35) return;

        Entity entity = p.getEntity(mc.world);

        if (!(entity instanceof PlayerEntity)) return;

        if ((entity.equals(mc.player) && totemsIgnoreOwn.getValue())
            || (OyVey.friendManager.isFriend(((PlayerEntity) entity)) && totemsIgnoreOthers.getValue())
            || (!OyVey.friendManager.isFriend(((PlayerEntity) entity)) && totemsIgnoreFriends.getValue())
        ) return;

        synchronized (totemPopMap) {
            int pops = totemPopMap.getOrDefault(entity.getUuid(), 0);
            totemPopMap.put(entity.getUuid(), ++pops);
            Command.sendMessage(Formatting.GRAY + " popped " + entity.getName().getString() + " " + Formatting.GREEN + ( ( pops) > 1 ? pops + " " + Formatting.GRAY +"totems" : Formatting.GREEN + "1" + Formatting.GRAY + " totem"));//+ pops + strg);
            if (popevent.getValue()){
            if (!ignoreFriends.getValue() || (ignoreFriends.getValue() && !OyVey.friendManager.isFriend((PlayerEntity)entity))) {
                if (popmsg.getValue()){
                   this.sendPlayerMsg(entity.getName().getString() +" "+ (String)this.msgpop.getValue());
                }
                if (popez.getValue()){
                   this.sendPlayerMsg(entity.getName().getString() +" "+ (String)this.msgez.getValue());
                }
            }
        }
    }
}

    public void sendPlayerMsg(String message) {
        mc.inGameHud.getChatHud().addToMessageHistory(message);

        if (message.startsWith("")) mc.player.networkHandler.sendChatCommand(message.substring(1));
        else mc.player.networkHandler.sendChatMessage(message);
    }
}