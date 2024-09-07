package aids.dev.shrimphack.features.modules.movement.noslow.util;

import aids.dev.shrimphack.mixin.noslow.IClientWorldMixin;
import net.minecraft.client.network.PendingUpdateManager;
import net.minecraft.client.world.ClientWorld;



public final class PlayerUtility {
    public static int getWorldActionId(ClientWorld world) {
        PendingUpdateManager pum = getUpdateManager(world);
        int p = pum.getSequence();
        pum.close();
        return p;
    }
    private static PendingUpdateManager getUpdateManager(ClientWorld world) {
        return ((IClientWorldMixin) world).acquirePendingUpdateManager();
    }
}