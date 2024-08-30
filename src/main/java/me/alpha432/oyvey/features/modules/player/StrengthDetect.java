package me.alpha432.oyvey.features.modules.player;

import me.alpha432.oyvey.features.commands.Command;
import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public class StrengthDetect extends Module {

    private final Set<PlayerEntity> str = Collections.newSetFromMap(new WeakHashMap());

    public StrengthDetect() {
        super("StrengthDetect", "i am amongus", Category.PLAYER, true, false, false);
    }

    @Override
    public void onUpdate() {
        for (PlayerEntity player : mc.world.getPlayers()) {
            if (player.equals(mc.player)) continue;
            if (player.hasStatusEffect(StatusEffects.STRENGTH) && !this.str.contains(player)) {
                Command.sendMessage(player.getName().getString() + " has strength");
                this.str.add(player);
            }

            if (!this.str.contains(player) || player.hasStatusEffect(StatusEffects.STRENGTH)) continue;
            Command.sendMessage(player.getName().getString() + " doesnt have strength");
            this.str.remove(player);
        }
    }
}