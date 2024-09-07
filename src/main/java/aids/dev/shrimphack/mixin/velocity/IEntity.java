package aids.dev.shrimphack.mixin.velocity;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public interface IEntity {

    List<Vec3d> getPrevPositions();

    BlockPos cracked_ve$getVelocityBP();
}
