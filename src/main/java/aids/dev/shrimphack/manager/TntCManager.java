package aids.dev.shrimphack.manager;

import aids.dev.shrimphack.Shrimphack;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static aids.dev.shrimphack.util.traits.Util.mc;

public class TntCManager {
    public HashMap<String, Integer> popList = new HashMap<>();



    public @Nullable PlayerEntity getNearestTarget(float range) {
        return getTargets(range).stream().min(Comparator.comparing(t -> mc.player.distanceTo(t))).orElse(null);
    }

    public List<PlayerEntity> getTargets(float range) {
        return mc.world.getPlayers().stream()
                .filter(e -> !e.isDead())
                .filter(entityPlayer -> !Shrimphack.friendManager.isFriend(entityPlayer.getName().getString()))
                .filter(entityPlayer -> entityPlayer != mc.player)
                .filter(entityPlayer -> mc.player.squaredDistanceTo(entityPlayer) < range * range)
                .sorted(Comparator.comparing(e -> mc.player.distanceTo(e)))
                .collect(Collectors.toList());
    }
}
