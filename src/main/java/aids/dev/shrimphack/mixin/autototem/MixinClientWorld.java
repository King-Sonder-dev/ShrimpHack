package aids.dev.shrimphack.mixin.autototem;

import aids.dev.shrimphack.event.impl.autototem.EventEntitySpawn;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static aids.dev.shrimphack.util.traits.Util.EVENT_BUS;


@Mixin(ClientWorld.class)
public class MixinClientWorld {
    @Inject(method = "addEntity", at = @At("HEAD"), cancellable = true)
    public void onAddEntity(Entity entity, CallbackInfo ci) {
        EventEntitySpawn ees = new EventEntitySpawn(entity);
        EVENT_BUS.post(ees);
        if (ees.isCancelled()) {
            ci.cancel();
        }
    }
}
