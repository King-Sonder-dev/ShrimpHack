package aids.dev.shrimphack.features.modules.render;

import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.settings.Setting;
import aids.dev.shrimphack.util.Renders.Render3DUtil;
import aids.dev.shrimphack.util.Renders.RenderUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


import java.util.List;

public class BurrowESP extends Module {
    private static BurrowESP INSTANCE = new BurrowESP();
    public Setting<Integer> range = register(new Setting("Range", 20, 5, 50));
    public Setting<Boolean> self = register(new Setting("Self", true));
    public Setting<Boolean> text = register(new Setting("Text", true));
    public Setting<String> textString = register(new Setting("TextString", "BURROW", v -> this.text.getValue()));
    public Setting<Boolean> rainbow = register(new Setting("Rainbow", false));
    public Setting<Integer> red = register(new Setting("Red", 0, 0, 255, v -> !this.rainbow.getValue()));
    public Setting<Integer> green = register(new Setting("Green", 255, 0, 255, v -> !this.rainbow.getValue()));
    public Setting<Integer> blue = register(new Setting("Blue", 0, 0, 255, v -> !this.rainbow.getValue()));
    public Setting<Integer> alpha = register(new Setting("Alpha", 0, 0, 255));
    public Setting<Integer> outlineAlpha = register(new Setting("OL-Alpha", 0, 0, 255));

    private RenderUtil renderUtil = new RenderUtil();
    private static final MinecraftClient mc = MinecraftClient.getInstance();


    public BurrowESP() {
        super("BurrowESP", "BURROWESP", Category.RENDER, true, false, false);
    }


    public static void onRenderTick() {
        if (mc.world == null || mc.player == null) return;

        List<AbstractClientPlayerEntity> players = mc.world.getPlayers();
        for (PlayerEntity player : players) {
            if (isPlayerBurrowed(player)) {
                renderBurrowedText(player);
            }
        }
    }

    private static boolean isPlayerBurrowed(PlayerEntity player) {
        BlockPos playerPos = player.getBlockPos();
        World world = player.getWorld();

        // Check if the player is inside a solid block
        return world.getBlockState(playerPos).isFullCube(world, playerPos);
    }

    private static void renderBurrowedText(PlayerEntity player) {
        Vec3d playerPos = player.getPos();
        Vec3d textPos = playerPos.add(0, player.getHeight() + 0.5, 0); // Position text above the player's head

        Render3DUtil.drawText3D("Burrowed", textPos, 0xFF0000); // Red color
    }
}