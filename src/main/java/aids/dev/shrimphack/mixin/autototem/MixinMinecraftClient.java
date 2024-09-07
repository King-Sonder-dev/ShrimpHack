package aids.dev.shrimphack.mixin.autototem;

import aids.dev.shrimphack.event.impl.autototem.EventPostTick;
import aids.dev.shrimphack.event.impl.autototem.EventTick;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static aids.dev.shrimphack.features.modules.Module.fullNullCheck;
import static aids.dev.shrimphack.util.traits.Util.EVENT_BUS;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Inject(method = "tick", at = @At("HEAD"))
    void preTickHook(CallbackInfo ci) {
        if (!fullNullCheck()) EVENT_BUS.post(new EventTick());
    }

    @Inject(method = "tick", at = @At("RETURN"))
    void postTickHook(CallbackInfo ci) {
        if (!fullNullCheck()) EVENT_BUS.post(new EventPostTick());
    }
}
