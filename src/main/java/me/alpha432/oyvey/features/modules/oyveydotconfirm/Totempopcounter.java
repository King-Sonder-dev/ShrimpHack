package me.alpha432.oyvey.features.modules.oyveydotconfirm;

import me.alpha432.oyvey.event.impl.TotemPopEvent;
import me.alpha432.oyvey.features.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Totempopcounter extends Module {
    public Totempopcounter() {
        super("TotemPopCounter", "", Module.Category.OYVEYDOTCONFIRM, true, false, false);
    }

}
