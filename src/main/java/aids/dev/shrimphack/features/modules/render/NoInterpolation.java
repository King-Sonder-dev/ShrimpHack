package aids.dev.shrimphack.features.modules.render;

import aids.dev.shrimphack.features.modules.Module;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class NoInterpolation extends Module {

    public NoInterpolation() {
        super("Resolver", "Disables entity interpolation for a snappier feel.", Category.RENDER, true, false, false);
    }

    @Override
    public void onEnable() {
        ClientTickEvents.END_WORLD_TICK.register(this::onEndWorldTick);
    }

    @Override
    public void onDisable() {
        // No need to unregister the event listener
    }

    private void onEndWorldTick(net.minecraft.world.World world) {
        if (world != null) {
            for (Entity entity : world.getPlayers()) {
                if (entity instanceof LivingEntity && !(entity instanceof PlayerEntity)) {
                    updateEntityPosition(entity);
                }
            }
        }
    }

    private void updateEntityPosition(Entity entity) {
        Vec3d pos = entity.getPos();
        entity.updatePosition(pos.x, pos.y, pos.z);
    }
}