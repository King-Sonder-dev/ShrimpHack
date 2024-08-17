package me.alpha432.oyvey.features.modules.player.speedmine;

import com.google.common.eventbus.Subscribe;
import me.alpha432.oyvey.event.impl.PacketEvent;
import me.alpha432.oyvey.event.impl.TickEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import me.alpha432.oyvey.mixin.AccessorClientPlayerInteractionManager;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;

import static net.minecraft.entity.effect.StatusEffects.HASTE;

public class MiningTS extends Module {
    private final Setting<Modes> mode = this.register(new Setting<Modes>("Mode", Modes.Normal, "Mode."));
    private final Setting<Integer> hasteAmplifier = register(new Setting<>("Haste Level", 2, 1, 20, v -> mode.getValue() == Modes.Haste));
    private final Setting<Boolean> grimBypass = register(new Setting<>("GrimBypass", true, v -> mode.getValue() == Modes.Damage));

    public MiningTS() {
        super("Speedmine", "", Module.Category.PLAYER, true, false, false);
    }

    @Subscribe
    public void onTickEvent(TickEvent.Post event) {
        System.out.println("received tick");
        if (!canUpdate()) return;

        if (mode.getValue() == Modes.Haste) {
            StatusEffectInstance haste = mc.player.getStatusEffect(HASTE);

            if (haste == null || haste.getAmplifier() <= hasteAmplifier.getValue() - 1) {
                mc.player.setStatusEffect(new StatusEffectInstance(HASTE, -1, hasteAmplifier.getValue() - 1, false, false, false), null);
            }
        }
        else if (mode.getValue() == Modes.Damage) {
            AccessorClientPlayerInteractionManager im = (AccessorClientPlayerInteractionManager) mc.interactionManager;
            float progress = im.getBreakingProgress();
            BlockPos pos = im.getCurrentBreakingBlockPos();

            if (pos == null || progress <= 0) return;
            if (progress + mc.world.getBlockState(pos).calcBlockBreakingDelta(mc.player, mc.world, pos) >= 0.7f)
                im.setCurrentBreakingProgress(1f);
        }
    }

    @Subscribe
    public void onPacket(PacketEvent.Send event) {
        System.out.println("sent packet");
        if (!(mode.getValue() == Modes.Damage) || !grimBypass.getValue()) return;

        // https://github.com/GrimAnticheat/Grim/issues/1296
        if (event.getPacket() instanceof PlayerActionC2SPacket packet && packet.getAction() == PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK) {
            mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, packet.getPos().up(), packet.getDirection()));
        }
    }

    @Override
    public void onDisable() {
        removeHaste();
    }

    public static boolean canUpdate() {
        return mc != null && mc.world != null && mc.player != null;
    }

    private void removeHaste() {
        StatusEffectInstance haste = mc.player.getStatusEffect(HASTE);
        if (haste != null && !haste.shouldShowIcon()) mc.player.removeStatusEffect(HASTE);
    }

    public enum Modes {
        Normal,
        Haste,
        Damage
    }
}