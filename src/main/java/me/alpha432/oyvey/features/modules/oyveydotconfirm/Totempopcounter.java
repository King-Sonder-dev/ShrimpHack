package me.alpha432.oyvey.features.modules.oyveydotconfirm;

import me.alpha432.oyvey.event.impl.TotemPopEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.util.ChatUtil;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Totempopcounter extends Module {
    private int popCounter;

    public Totempopcounter() {
        super("TotemPopCounter", "", Module.Category.OYVEYDOTCONFIRM, true, false, false);
    }

    // event handler (idk what the exact event name is)
    @EventHandler
    private void popEvent(TotemPopEvent event) {
        popCounter++;
        ChatUtil.clientSendMessage("I just popped " + event.entity());
    };
}

