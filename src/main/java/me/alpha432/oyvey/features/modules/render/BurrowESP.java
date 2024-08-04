package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.event.impl.Render3DEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import me.alpha432.oyvey.util.RenderUtil;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

import java.awt.*;

public class BurrowESP extends Module {
    private final Setting<Integer> red = this.register(new Setting<Integer>("Red", 255, 0, 255));
    private final Setting<Integer> green = this.register(new Setting<Integer>("Green", 128, 0, 255));
    private final Setting<Integer> blue = this.register(new Setting<Integer>("Blue", 128, 0, 255));
    private final Setting<Integer> alpha = this.register(new Setting<Integer>("Alpha", 128, 0, 255));
    private final Setting<Integer> outlineAlpha = this.register(new Setting<Integer>("OutlineAlpha", 255, 0, 255));
    private final Setting<Float> outlineWidth = this.register(new Setting<Float>("OutlineWidth", Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(5.0f)));
    private final Setting<Integer> webRed = this.register(new Setting<Integer>("WebRed", 255, 0, 255));
    private final Setting<Integer> webGreen = this.register(new Setting<Integer>("WebGreen", 255, 0, 255));
    private final Setting<Integer> webBlue = this.register(new Setting<Integer>("WebBlue", 255, 0, 255));
    private final Setting<Integer> webAlpha = this.register(new Setting<Integer>("WebAlpha", 128, 0, 255));
    private final Setting<Integer> webOutlineAlpha = this.register(new Setting<Integer>("WebOutlineAlpha", 255, 0, 255));
    private final Setting<Float> webOutlineWidth = this.register(new Setting<Float>("WebOutlineWidth", Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(5.0f)));

    public BurrowESP() {
        super("BurrowESP", "Renders a player's burrow", Module.Category.RENDER, false, false, false);
    }
    
    @Override
    public void onRender3D(Render3DEvent event) {
        for (Entity player : mc.world.getEntities()) {
            if (player == mc.player) continue;
            BlockPos blockPos = new BlockPos((int) Math.floor(player.getBlockX()), (int) Math.floor(player.getBlockY() + 0.2f), (int) Math.floor(player.getBlockZ()));
            if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(blockPos).getBlock().equals(Blocks.OBSIDIAN) || mc.world.getBlockState(blockPos).getBlock().equals(Blocks.ENDER_CHEST)) {
                RenderUtil.drawBoxESP(blockPos, new Color(red.getValue(), green.getValue(), blue.getValue(), outlineAlpha.getValue()), outlineWidth.getValue(), true, true, alpha.getValue());
            }
            if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.COBWEB)) {
                RenderUtil.drawBoxESP(blockPos, new Color(webRed.getValue(), webGreen.getValue(), webBlue.getValue(), webOutlineAlpha.getValue()), webOutlineWidth.getValue(), true, true, webAlpha.getValue());
            }
        }
    }
}