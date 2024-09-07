package aids.dev.shrimphack.mixin.velocity;

import aids.dev.shrimphack.features.modules.player.velocity.Velocity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Iterator;

@Mixin(FlowableFluid.class)
public class MixinFlowableFluid {
    @Redirect(method = "getVelocity", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;hasNext()Z", ordinal = 0))
    private boolean getVelocityHook(Iterator<Direction> var9) {
        if (Velocity.getInstance().isEnabled() && Velocity.getInstance().water.getValue()) {
            return false;
        }
        return var9.hasNext();
    }
}