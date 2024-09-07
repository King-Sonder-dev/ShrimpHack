package aids.dev.shrimphack.util.World;

import net.minecraft.client.network.OtherClientPlayerEntity;

import java.util.Objects;
import java.util.UUID;

import static aids.dev.shrimphack.features.modules.Module.mc;

public class PlayerEntityCopy extends OtherClientPlayerEntity {
    public PlayerEntityCopy() {
        super(Objects.requireNonNull(mc.world), Objects.requireNonNull(mc.player).getGameProfile());

        copyFrom(mc.player);
        getPlayerListEntry();
        dataTracker.set(PLAYER_MODEL_PARTS, mc.player.getDataTracker().get(PLAYER_MODEL_PARTS));
        setUuid(UUID.randomUUID());
    }

    public void spawn() {
        if (mc.world == null) return;

        unsetRemoved();
        mc.world.addEntity(this);
    }

    public void deSpawn() {
        if (mc.world == null) return;

        mc.world.removeEntity(this.getId(), RemovalReason.DISCARDED);
    }
}