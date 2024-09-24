package aids.dev.shrimphack.util.World;

import static aids.dev.shrimphack.util.traits.Util.mc;

public class SpeedUtils {
    public static boolean anyMovementKeys() {
        return mc.player.input.pressingForward || mc.player.input.pressingBack || mc.player.input.pressingLeft || mc.player.input.pressingRight || mc.player.input.jumping || mc.player.input.sneaking;
    }
}
