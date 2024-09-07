package aids.dev.shrimphack.features.modules.render.fullbright;

import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.settings.Setting;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;


public class Fullbright extends Module {

    public final Setting<Mode> mode = this.register(new Setting<>("Mode", Mode.NightVision));

    public Fullbright() {
        super("Fullbright", "Fullbright good", Category.MISC, true, false, false);
    }

    public enum Mode {
        NightVision, Gamma
    }

    //@Override
    public String getMetaData() {
        return String.valueOf(mode.getValue());
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (mc.player != null) {
            if (mode.getValue() == Mode.NightVision) {
                mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 999999999, 5));
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (mc.player != null) {
            if (mode.getValue() == Mode.NightVision) {
                mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
            }
        }
    }
}
