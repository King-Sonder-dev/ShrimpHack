package aids.dev.shrimphack.features.modules.render;

import aids.dev.shrimphack.Shrimphack;
import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.settings.Setting;
import aids.dev.shrimphack.manager.MineManager;
import aids.dev.shrimphack.event.impl.Render3DEvent;
import aids.dev.shrimphack.util.Renders.FadeUtils;
import aids.dev.shrimphack.util.Renders.Render3DUtil;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;

import java.awt.*;
import java.util.HashMap;

public class BreakESP extends Module {
	public static BreakESP INSTANCE;


	public Setting<Integer> red = this.register(new Setting<>("Red", 145, 0, 255));
	public Setting<Integer> green = this.register(new Setting<>("Green", 160, 0, 255));
	public Setting<Integer> blue = this.register(new Setting<>("Blue", 255, 0, 255));
	public Setting<Integer> alpha = this.register(new Setting<>("HoverAlpha", 247, 0, 255));
	private final Setting<Boolean> outline = this.register(new Setting<>("Outline", false));

	private final Setting<Boolean> box = this.register(new Setting<>("Box", true));
	public Setting<Integer> animationTime = this.register(new Setting<>("AnimationTime", 500, 0, 2000));
	private final Setting<FadeUtils.Quad> quad = this.register(new Setting<>("Quad", FadeUtils.Quad.In));

	public BreakESP() {
		super("BreakESP", "it says it in the name nigger", Category.RENDER, true, false, false);
	}

	@Override
	public void onRender3D(Render3DEvent event) {
		for (MineManager.BreakData breakData : new HashMap<>(Shrimphack.mineManager.breakMap).values()) {
			if (breakData == null || breakData.getEntity() == null) continue;
			double size = 0.5 * (1 - breakData.fade.getQuad(quad.getValue()));
			Render3DUtil.draw3DBox(event.getMatrixStack(), new Box(breakData.pos).shrink(size, size, size).shrink(-size, -size, -size),red.getValue(), green.getValue(), blue.getValue(), alpha.getValue(), outline.getValue(), box.getValue());
			Render3DUtil.drawText3D(breakData.getEntity().getName().getString(), breakData.pos.toCenterPos().add(0, 0.1, 0), -1);
			Render3DUtil.drawText3D(Text.of(mc.world.isAir(breakData.pos) ? "Broken" : "Breaking"), breakData.pos.toCenterPos().add(0, -0.1, 0), 0, 0, 1, new Color(0, 255, 51));
		}
	}
}