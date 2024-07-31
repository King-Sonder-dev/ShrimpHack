package me.alpha432.oyvey.manager;

import me.alpha432.oyvey.features.Feature;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TotemPopManager extends Feature {
    private final Map<PlayerEntity, Integer> poplist = new ConcurrentHashMap<>();
    private final Set<PlayerEntity> toAnnounce = new HashSet<>();

    public void onTotemPop(PlayerEntity player) {
        this.popTotem(player);
        if (!player.equals(MinecraftClient.getInstance().player)) {
            this.toAnnounce.add(player);
        }
    }

    public void onDeath(PlayerEntity player) {
        if (this.getTotemPops(player) != 0 && !player.equals(MinecraftClient.getInstance().player)) {
            this.toAnnounce.remove(player);
        }
        this.resetPops(player);
    }

    public void onLogout(PlayerEntity player, boolean clearOnLogout) {
        if (clearOnLogout) {
            this.resetPops(player);
        }
    }

    public void onOwnLogout(boolean clearOnLogout) {
        if (clearOnLogout) {
            this.clearList();
        }
    }

    public void clearList() {
        this.poplist.clear();
    }

    public void resetPops(PlayerEntity player) {
        this.setTotemPops(player, 0);
    }

    public void popTotem(PlayerEntity player) {
        this.poplist.merge(player, 1, Integer::sum);
    }

    public void setTotemPops(PlayerEntity player, int amount) {
        this.poplist.put(player, amount);
    }

    public int getTotemPops(PlayerEntity player) {
        return this.poplist.getOrDefault(player, 0);
    }

    public Text getTotemPopString(PlayerEntity player) {
        int pops = this.getTotemPops(player);
        return Text.literal("\u00a7f" + (pops <= 0 ? "" : "-" + pops + " "));
    }
}