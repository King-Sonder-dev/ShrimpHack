package me.alpha432.oyvey.util;

import com.google.common.collect.Lists;
import me.alpha432.oyvey.util.traits.Util;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class TPUtil implements Util {
    public static ArrayList<Vec3> tp(Runnable runnable, Vec3d vec){
        List<Vec3> tpPath = PathUtils.computePath(vec);
        tpPath.forEach((vec3) -> mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(vec3.getX(), vec3.getY(), vec3.getZ(), false)));
        runnable.run();
        tpPath = Lists.reverse(tpPath);
        tpPath.forEach((vec3) -> mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(vec3.getX(), vec3.getY(), vec3.getZ(), false)));
        /*if (CombatSetting.INSTANCE.test.getValue()) {
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(0, -0.354844, 0, false));
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(0, +0.325488, 0, false));
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(0, -0.15441, 0, false));
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(0, -0.15444, 0, false));
        }

         */
        return new ArrayList<>(tpPath);
    }
}