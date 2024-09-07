package aids.dev.shrimphack.features.modules.player.middleclick.util;

import aids.dev.shrimphack.mixin.noslow.IClientWorldMixin;
import net.minecraft.client.network.PendingUpdateManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;


public final class PlayerUtility {

    public static int getWorldActionId(ClientWorld world) {
        PendingUpdateManager pum = getUpdateManager(world);
        int p = pum.getSequence();
        pum.close();
        return p;
    }

    public static float calculatePercentage(@NotNull ItemStack stack) {
        float durability = stack.getMaxDamage() - stack.getDamage();
        return (durability / (float) stack.getMaxDamage()) * 100F;
    }

    private static PendingUpdateManager getUpdateManager(ClientWorld world) {
        return ((IClientWorldMixin) world).acquirePendingUpdateManager();
    }

}
