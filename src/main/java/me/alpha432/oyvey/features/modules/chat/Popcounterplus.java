package me.alpha432.oyvey.features.modules.chat;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import me.alpha432.oyvey.features.modules.chat.PopCounter;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import java.util.*;
import java.awt.*;
import com.google.common.eventbus.Subscribe;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.event.impl.PacketEvent;
import me.alpha432.oyvey.features.commands.Command;

public class Popcounterplus extends Module {
    private final Object2IntMap<UUID> totemPopMap = new Object2IntOpenHashMap<>();
    public Setting<PopNotifier> popNotifier = this.register(new Setting<>("PopNotifier", PopNotifier.NONE));
    public Setting<Boolean> bold = this.register(new Setting<>("Boldsn0w", true, v -> this.popNotifier.getValue() == PopNotifier.SN0W));
    private final Setting<Boolean> totemPops = this.register(new Setting<Boolean>("totemPops", true));

    private final Setting<Boolean> totemsIgnoreOthers = this.register(new Setting<Boolean>("totemsIgnoreOthers", false, v -> this.totemPops.getValue()));
    private final Setting<Boolean> totemsIgnoreOwn = this.register(new Setting<Boolean>("totemsIgnoreOthers", false, v -> this.totemPops.getValue()));
    private final Setting<Boolean> totemsIgnoreFriends = this.register(new Setting<Boolean>("totemsIgnoreOthers", false, v -> this.totemPops.getValue()));

    public Popcounterplus() {
        super("Popcounterplus", "ezzzz kits noobs", Module.Category.CHAT, true, false, false);
    }

    @Subscribe
    public void onReceivePacket(PacketEvent.Receive event) {
        ChatHud chatHud = mc.inGameHud.getChatHud();
        if (!totemPops.getValue()) return;
        if (!(event.getPacket() instanceof EntityStatusS2CPacket p)) return;

        if (p.getStatus() != 35) return;

        Entity entity = p.getEntity(mc.world);

        if (!(entity instanceof PlayerEntity)) return;

        if ((entity.equals(mc.player) && totemsIgnoreOwn.getValue())
                || (OyVey.friendManager.isFriend((PlayerEntity) entity) && totemsIgnoreOthers.getValue())
                || (!OyVey.friendManager.isFriend((PlayerEntity) entity) && totemsIgnoreFriends.getValue())
        ) return;

        synchronized (totemPopMap) {
            int pops = totemPopMap.getOrDefault(entity.getUuid(), 0);
            totemPopMap.put(entity.getUuid(), ++pops);
            String popMessage = (pops > 1 ? pops + " totems" : "1 totem");
            String playerName = entity.getName().getString();
            switch (this.popNotifier.getValue()) {
                case FUTURE:
                    Command.sendSilentMessage(Formatting.RED + "[Future] " + Formatting.GREEN + playerName + Formatting.GRAY + " just popped " + Formatting.GREEN + popMessage + Formatting.GRAY + " totems.");
                    break;
                case PHOBOS:
                    Command.sendSilentMessage(" " + Formatting.GOLD + playerName + Formatting.RED + " popped " + Formatting.GOLD + popMessage + Formatting.RED + " totems.");
                    break;
                case DOTGOD:
                    Command.sendSilentMessage(Formatting.DARK_PURPLE + "[" + Formatting.LIGHT_PURPLE + "DotGod.CC" + Formatting.DARK_PURPLE + "] " + Formatting.LIGHT_PURPLE + playerName + " has popped " + Formatting.RED + popMessage + Formatting.LIGHT_PURPLE + " time in total!");
                    break;
                case SN0W:
                    Command.sendSilentMessage(Formatting.BLUE + "[" + Formatting.AQUA + "❄" + Formatting.BLUE + "] " + Formatting.RESET + (this.bold.getValue() ? Formatting.BOLD : "") + playerName + " has" + Formatting.RESET + " popped " + popMessage + "!");
                    break;
                case SNOW:
                    Command.sendSilentMessage(Formatting.GRAY + "[" + Formatting.AQUA + "Snow" + Formatting.GRAY + "] " + "[" + Formatting.DARK_BLUE + "Popcounter" + "]" + playerName + " popped " + popMessage + " totems!");
                    break;
                case TROLLGOD:
                    Command.sendSilentMessage(Formatting.DARK_PURPLE + "[" + Formatting.LIGHT_PURPLE + "TrollGod" + Formatting.DARK_PURPLE + "] " + playerName + " popped their " + popMessage + " totems.");
                case NONE:
                default:
                    Command.sendSilentMessage(" " + Formatting.WHITE + entity.getName().getString() + " popped " + Formatting.GREEN + popMessage + Formatting.WHITE + " Totems.");
                    break;
            }
        }

    }
    public static enum PopNotifier {
        NONE,
        SN0W,
        SNOW,
        PHOBOS,
        FUTURE,
        DOTGOD,
        TROLLGOD
    }
}
