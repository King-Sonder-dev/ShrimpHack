package me.alpha432.oyvey.features.modules.movement;


import com.google.common.eventbus.Subscribe;
import me.alpha432.oyvey.event.impl.TickEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import net.minecraft.util.math.MathHelper;

import static me.alpha432.oyvey.util.traits.Util.mc;


public class LongJump extends Module {
    private final Setting<Double> speed = register(new Setting<>("Speed", 1.0, 0.1, 3.0));

    public LongJump() {
        super("LongJump", "iLongJump", Module.Category.MOVEMENT, true, false, false);
    }
    @Subscribe
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        float yaw = (float) Math.toRadians(mc.player.getYaw());
        double vSpeed = speed.getValue() / 5;
        if (!mc.player.isOnGround()) {

            mc.player.addVelocity(-MathHelper.sin(yaw) * vSpeed, 0.0F, MathHelper.cos(yaw) * vSpeed);
        } else if (mc.player.isOnGround()) {
            mc.player.setVelocity(0, 0, 0);
        }
        this.enable();
    }
}
