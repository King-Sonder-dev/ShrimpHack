package aids.dev.shrimphack.features.modules.render;

import aids.dev.shrimphack.event.impl.Render3DEvent;
import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.modules.client.ClickGui;
import aids.dev.shrimphack.features.settings.Setting;
import aids.dev.shrimphack.util.Colors.ColorUtil;
import aids.dev.shrimphack.util.Colors.ColorUtils;
import aids.dev.shrimphack.util.Renders.RenderUtil;
import aids.dev.shrimphack.util.World.BlockUtil;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

import java.awt.Color;

public class HoleESP extends Module {
    private final Setting<Boolean> renderOwn = register(new Setting<>("RenderOwn", true));
    private final Setting<Boolean> fov = register(new Setting<>("InFov", true));
    private final Setting<Boolean> rainbow = register(new Setting<>("Rainbow", false));
    private final Setting<Integer> range = register(new Setting<>("RangeX", 0, 0, 10));
    private final Setting<Integer> rangeY = register(new Setting<>("RangeY", 0, 0, 10));
    private final Setting<Boolean> box = register(new Setting<>("Box", true));
    private final Setting<Boolean> gradientBox = register(new Setting<>("Gradient", false, v -> box.getValue()));
    private final Setting<Boolean> invertGradientBox = register(new Setting<>("ReverseGradient", false, v -> gradientBox.getValue()));
    private final Setting<Boolean> outline = register(new Setting<>("Outline", true));
    private final Setting<Boolean> gradientOutline = register(new Setting<>("GradientOutline", false, v -> outline.getValue()));
    private final Setting<Boolean> invertGradientOutline = register(new Setting<>("ReverseOutline", false, v -> gradientOutline.getValue()));
    private final Setting<Double> height = register(new Setting<>("Height", 0.0, -2.0, 2.0));
    private final Setting<Integer> red = register(new Setting<>("Red", 0, 0, 255));
    private final Setting<Integer> green = register(new Setting<>("Green", 255, 0, 255));
    private final Setting<Integer> blue = register(new Setting<>("Blue", 0, 0, 255));
    private final Setting<Integer> alpha = register(new Setting<>("Alpha", 255, 0, 255));
    private final Setting<Integer> boxAlpha = register(new Setting<>("BoxAlpha", 125, 0, 255, v -> box.getValue()));
    private final Setting<Float> lineWidth = register(new Setting<>("LineWidth", 1.0f, 0.1f, 5.0f, v -> outline.getValue()));
    private final Setting<Boolean> safeColor = register(new Setting<>("BedrockColor", false));
    private final Setting<Integer> safeRed = register(new Setting<>("BedrockRed", 0, 0, 255, v -> safeColor.getValue()));
    private final Setting<Integer> safeGreen = register(new Setting<>("BedrockGreen", 255, 0, 255, v -> safeColor.getValue()));
    private final Setting<Integer> safeBlue = register(new Setting<>("BedrockBlue", 0, 0, 255, v -> safeColor.getValue()));
    private final Setting<Integer> safeAlpha = register(new Setting<>("BedrockAlpha", 255, 0, 255, v -> safeColor.getValue()));
    private final Setting<Boolean> customOutline = register(new Setting<>("CustomLine", false, v -> outline.getValue()));
    private final Setting<Integer> cRed = register(new Setting<>("OL-Red", 0, 0, 255, v -> customOutline.getValue() && outline.getValue()));
    private final Setting<Integer> cGreen = register(new Setting<>("OL-Green", 0, 0, 255, v -> customOutline.getValue() && outline.getValue()));
    private final Setting<Integer> cBlue = register(new Setting<>("OL-Blue", 255, 0, 255, v -> customOutline.getValue() && outline.getValue()));
    private final Setting<Integer> cAlpha = register(new Setting<>("OL-Alpha", 255, 0, 255, v -> customOutline.getValue() && outline.getValue()));
    private final Setting<Integer> safecRed = register(new Setting<>("OL-SafeRed", 0, 0, 255, v -> customOutline.getValue() && outline.getValue() && safeColor.getValue()));
    private final Setting<Integer> safecGreen = register(new Setting<>("OL-SafeGreen", 255, 0, 255, v -> customOutline.getValue() && outline.getValue() && safeColor.getValue()));
    private final Setting<Integer> safecBlue = register(new Setting<>("OL-SafeBlue", 0, 0, 255, v -> customOutline.getValue() && outline.getValue() && safeColor.getValue()));
    private final Setting<Integer> safecAlpha = register(new Setting<>("OL-SafeAlpha", 255, 0, 255, v -> customOutline.getValue() && outline.getValue() && safeColor.getValue()));

    private static HoleESP INSTANCE;

    private int currentAlpha;

    public HoleESP() {
        super("HoleESP", "Shows safe spots.", Category.RENDER, false, false, false);
        setInstance();
    }

    private void setInstance() {
        HoleESP.INSTANCE = this;
    }

    public static HoleESP getInstance() {
        if (HoleESP.INSTANCE == null) {
            HoleESP.INSTANCE = new HoleESP();
        }
        return HoleESP.INSTANCE;
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        MinecraftClient mc = MinecraftClient.getInstance();
        assert mc.cameraEntity != null;
        Vec3i playerPos = new Vec3i((int) mc.cameraEntity.getX(), (int) mc.cameraEntity.getY(), (int) mc.cameraEntity.getZ());

        for (int x = playerPos.getX() - range.getValue(); x < playerPos.getX() + range.getValue(); x++) {
            for (int z = playerPos.getZ() - range.getValue(); z < playerPos.getZ() + range.getValue(); z++) {
                for (int y = playerPos.getY() + rangeY.getValue(); y > playerPos.getY() - rangeY.getValue(); y--) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR)
                            && mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR)
                            && mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)
                            && (!pos.equals(new BlockPos((int) mc.player.getX(), (int) mc.player.getY(), (int) mc.player.getZ())) || renderOwn.getValue())) {
                        if (BlockUtil.isPosInFov(pos) || !fov.getValue()) {
                            if (mc.world.getBlockState(pos.north()).getBlock() == Blocks.BEDROCK
                                    && mc.world.getBlockState(pos.east()).getBlock() == Blocks.BEDROCK
                                    && mc.world.getBlockState(pos.west()).getBlock() == Blocks.BEDROCK
                                    && mc.world.getBlockState(pos.south()).getBlock() == Blocks.BEDROCK
                                    && mc.world.getBlockState(pos.down()).getBlock() == Blocks.BEDROCK) {
                                RenderUtil.drawBoxESP(pos,
                                        rainbow.getValue() ? ColorUtils.rainbow(ClickGui.getInstance().rainbowHue.getValue())
                                                : new Color(safeRed.getValue(), safeGreen.getValue(), safeBlue.getValue(), safeAlpha.getValue()),
                                        customOutline.getValue(),
                                        new Color(safecRed.getValue(), safecGreen.getValue(), safecBlue.getValue(), safecAlpha.getValue()),
                                        lineWidth.getValue(),
                                        outline.getValue(),
                                        box.getValue(),
                                        boxAlpha.getValue(),
                                        true,
                                        height.getValue(),
                                        gradientBox.getValue(),
                                        gradientOutline.getValue(),
                                        invertGradientBox.getValue(),
                                        invertGradientOutline.getValue(),
                                        currentAlpha);
                            } else if (BlockUtil.isBlockUnSafe(mc.world.getBlockState(pos.down()).getBlock())
                                    && BlockUtil.isBlockUnSafe(mc.world.getBlockState(pos.east()).getBlock())
                                    && BlockUtil.isBlockUnSafe(mc.world.getBlockState(pos.west()).getBlock())
                                    && BlockUtil.isBlockUnSafe(mc.world.getBlockState(pos.south()).getBlock())) {
                                if (BlockUtil.isBlockUnSafe(mc.world.getBlockState(pos.north()).getBlock())) {
                                    RenderUtil.drawBoxESP(pos,
                                            rainbow.getValue() ? ColorUtils.rainbow(ClickGui.getInstance().rainbowHue.getValue())
                                                    : new Color(red.getValue(), green.getValue(), blue.getValue(), alpha.getValue()),
                                            customOutline.getValue(),
                                            new Color(cRed.getValue(), cGreen.getValue(), cBlue.getValue(), cAlpha.getValue()),
                                            lineWidth.getValue(),
                                            outline.getValue(),
                                            box.getValue(),
                                            boxAlpha.getValue(),
                                            true,
                                            height.getValue(),
                                            gradientBox.getValue(),
                                            gradientOutline.getValue(),
                                            invertGradientBox.getValue(),
                                            invertGradientOutline.getValue(),
                                            currentAlpha);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
