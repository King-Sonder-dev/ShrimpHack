package aids.dev.shrimphack.mixin;

import aids.dev.shrimphack.Shrimphack;
import aids.dev.shrimphack.features.modules.client.Options;
import aids.dev.shrimphack.util.Text.ClientInfoInterface;
import aids.dev.shrimphack.util.Colors.ColorUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class MixinTitleScreen extends Screen implements ClientInfoInterface {

    protected MixinTitleScreen(Text title) {
        super(title);
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo cl) {
        Options optionsModule = Shrimphack.moduleManager.getModuleByClass(Options.class);
        if (optionsModule != null && optionsModule.mainmenu.getValue()) {
            context.drawTextWithShadow(textRenderer, Formatting.AQUA + clientName + " " + version + " By 1nject :3", 0, 1, ColorUtils.getRGBA());
        }
    }
}
