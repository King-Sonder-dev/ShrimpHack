package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.event.impl.Render3DEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.modules.client.ClickGui;
import me.alpha432.oyvey.features.settings.Setting;
import me.alpha432.oyvey.util.ColorUtil;
import me.alpha432.oyvey.util.RenderUtil;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BurrowESP extends Module {

    private static BurrowESP INSTANCE = new BurrowESP();
    public Setting<Integer> range = register(new Setting<>("Range", 20, 5, 50));
    public Setting<Boolean> self = register(new Setting<>("Self", true));
    public Setting<Boolean> text = register(new Setting<>("Text", true));
    public Setting<String> textString = register(new Setting<>("TextString", "BURROW", v -> this.text.getValue()));
    public Setting<Boolean> rainbow = register(new Setting<>("Rainbow", false));
    public Setting<Integer> red = register(new Setting<>("Red", 0, 0, 255, v -> !this.rainbow.getValue()));
    public Setting<Integer> green = register(new Setting<>("Green", 255, 0, 255, v -> !this.rainbow.getValue()));
    public Setting<Integer> blue = register(new Setting<>("Blue", 0, 0, 255, v -> !this.rainbow.getValue()));
    public Setting<Integer> alpha = register(new Setting<>("Alpha", 0, 0, 255));
    public Setting<Integer> outlineAlpha = register(new Setting<>("OL-Alpha", 0, 0, 255));

    private final List<BlockPos> posList = new ArrayList<>();

    private final RenderUtil renderUtil = new RenderUtil();

    public BurrowESP() {
        super("BurrowESP", "BURROWESP", Category.RENDER, true, false, false);
        this.setInstance();
    }

    public static BurrowESP getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BurrowESP();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    public void onTick() {
        posList.clear();
        MinecraftClient mc = MinecraftClient.getInstance();
        ClientPlayerEntity player = mc.player;
        if (player == null || mc.world == null) return;

        for (PlayerEntity otherPlayer : mc.world.getPlayers()) {
            BlockPos blockPos = new BlockPos((int) Math.floor(otherPlayer.getX()), (int) Math.floor(otherPlayer.getY() + 0.2), (int) Math.floor(otherPlayer.getZ()));
            if ((mc.world.getBlockState(blockPos).getBlock() == Blocks.ENDER_CHEST || mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN) && blockPos.getSquaredDistance(player.getPos()) <= this.range.getValue()) {
                if (!(blockPos.getSquaredDistance(player.getPos()) <= 1.5) || this.self.getValue()) {
                    posList.add(blockPos);
                }
            }
        }
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        for (BlockPos blockPos : posList) {
            String s = textString.getValue().toUpperCase();
            if (this.text.getValue()) {
                this.renderUtil.drawText(blockPos, s, rainbow.getValue() ? ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()) : new Color(red.getValue(), green.getValue(), blue.getValue(), outlineAlpha.getValue()));
            }
            RenderUtil.drawBoxESP(blockPos, rainbow.getValue() ? ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()) : new Color(red.getValue(), green.getValue(), blue.getValue(), outlineAlpha.getValue()), 1.5F, true, true, alpha.getValue());
        }
    }
}
