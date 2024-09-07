package aids.dev.shrimphack.features.modules.movement.autosprint.util;


import static aids.dev.shrimphack.util.traits.Util.mc;

public final class MovementUtility {

    public static boolean isMoving() {
        return mc.player.input.movementForward != 0.0 || mc.player.input.movementSideways != 0.0;
    }

}
