package aids.dev.shrimphack.features.modules.movement;

import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.settings.Setting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class NoFall extends Module {

    private final Setting<Integer> fallDistance = register(new Setting<>("FallDistance", 3, 1, 10));


    public NoFall() {
        super("NoFall", "Prevents fall damage.", Category.MOVEMENT, true, false, false);
    }

    @Override
    public void onUpdate() {
        MinecraftClient mc = MinecraftClient.getInstance();
        ClientPlayerEntity player = mc.player;

        if (player == null) return;

        if (player.fallDistance > fallDistance.getValue()) {
            player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
        }
    }
}