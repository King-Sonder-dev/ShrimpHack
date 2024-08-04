package me.alpha432.oyvey.mixin.cape;

import me.alpha432.oyvey.features.modules.client.Cape;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerListEntry.class)
public abstract class PlayerListEntryMixin {
    private static final Identifier Future = new Identifier("minecraft", "textures/capes/future.png");
    private static final Identifier Rusherhack = new Identifier("minecraft", "textures/capes/rusherhack.png");
    private static final Identifier Hyper = new Identifier("minecraft", "textures/capes/hyper.png");
    private static final Identifier Cobalt = new Identifier("minecraft", "textures/capes/cobalt.png");
    private static final Identifier FOUNDER = new Identifier("minecraft", "textures/capes/founder.png");

    private static final Identifier HIGHLAND = new Identifier("minecraft", "textures/capes/highland.png");
    private static final Identifier MINECON2011 = new Identifier("minecraft", "textures/capes/minecon2011.png");
    private static final Identifier MINECON2012 = new Identifier("minecraft", "textures/capes/minecon2012.png");
    private static final Identifier MINECON2013 = new Identifier("minecraft", "textures/capes/minecon2013.png");
    private static final Identifier MINECON2015 = new Identifier("minecraft", "textures/capes/minecon2015.png");
    private static final Identifier MINECON2016 = new Identifier("minecraft", "textures/capes/minecon2016.png");
    private static final Identifier MOJANG = new Identifier("minecraft", "textures/capes/mojang.png");
    private static final Identifier MOJANG_CLASSIC = new Identifier("minecraft", "textures/capes/mojang_classic.png");
    private static final Identifier MOJANG_STUDIOS = new Identifier("minecraft", "textures/capes/mojang_studios.png");
    private static final Identifier PHOBOS = new Identifier("minecraft", "textures/capes/phobos.png");
    private static final Identifier RUSHERHACKEK = new Identifier("minecraft", "textures/capes/rusherhackek.png");
    private static final Identifier SNOWMAN = new Identifier("minecraft", "textures/capes/snowman.png");

    @Inject(method = "getSkinTextures", at = @At("TAIL"), cancellable = true)
    private void getSkinTextures(CallbackInfoReturnable<SkinTextures> cir) {
        if (!Cape.getInstance().isOn()) return;

        SkinTextures oldTextures = cir.getReturnValue();
        Identifier capeTexture;

        if (Cape.getInstance().cape.getValue() == Cape.CapeMode.FUTURE) {
            capeTexture = Future;
            SkinTextures Textures = new SkinTextures(oldTextures.texture(), oldTextures.textureUrl(), capeTexture, capeTexture, oldTextures.model(), oldTextures.secure());
            cir.setReturnValue(Textures);
        }
        if (Cape.getInstance().cape.getValue() == Cape.CapeMode.RUSHERHACK) {
            capeTexture = Rusherhack;
            SkinTextures Textures = new SkinTextures(oldTextures.texture(), oldTextures.textureUrl(), capeTexture, capeTexture, oldTextures.model(), oldTextures.secure());
            cir.setReturnValue(Textures);
        }
        if (Cape.getInstance().cape.getValue() == Cape.CapeMode.HYPER) {
            capeTexture = Hyper;
            SkinTextures Textures = new SkinTextures(oldTextures.texture(), oldTextures.textureUrl(), capeTexture, capeTexture, oldTextures.model(), oldTextures.secure());
            cir.setReturnValue(Textures);
        }
        if (Cape.getInstance().cape.getValue() == Cape.CapeMode.COBALT) {
            capeTexture = Cobalt;
            SkinTextures Textures = new SkinTextures(oldTextures.texture(), oldTextures.textureUrl(), capeTexture, capeTexture, oldTextures.model(), oldTextures.secure());
            cir.setReturnValue(Textures);
        }
        if (Cape.getInstance().cape.getValue() == Cape.CapeMode.FOUNDER) {
            capeTexture = FOUNDER;
            SkinTextures Textures = new SkinTextures(oldTextures.texture(), oldTextures.textureUrl(), capeTexture, capeTexture, oldTextures.model(), oldTextures.secure());
            cir.setReturnValue(Textures);
        }
        if (Cape.getInstance().cape.getValue() == Cape.CapeMode.HIGHLAND) {
            capeTexture = HIGHLAND;
            SkinTextures Textures = new SkinTextures(
                    oldTextures.texture(),
                    oldTextures.textureUrl(),
                    capeTexture,
                    capeTexture,
                    oldTextures.model(),
                    oldTextures.secure()
            );
            cir.setReturnValue(Textures);
        }

        if (Cape.getInstance().cape.getValue() == Cape.CapeMode.MINECON2011) {
            capeTexture = MINECON2011;
            SkinTextures Textures = new SkinTextures(
                    oldTextures.texture(),
                    oldTextures.textureUrl(),
                    capeTexture,
                    capeTexture,
                    oldTextures.model(),
                    oldTextures.secure()
            );
            cir.setReturnValue(Textures);
        }

        if (Cape.getInstance().cape.getValue() == Cape.CapeMode.MINECON2012) {
            capeTexture = MINECON2012;
            SkinTextures Textures = new SkinTextures(
                    oldTextures.texture(),
                    oldTextures.textureUrl(),
                    capeTexture,
                    capeTexture,
                    oldTextures.model(),
                    oldTextures.secure()
            );
            cir.setReturnValue(Textures);
        }

        if (Cape.getInstance().cape.getValue() == Cape.CapeMode.MINECON2013) {
            capeTexture = MINECON2013;
            SkinTextures Textures = new SkinTextures(
                    oldTextures.texture(),
                    oldTextures.textureUrl(),
                    capeTexture,
                    capeTexture,
                    oldTextures.model(),
                    oldTextures.secure()
            );
            cir.setReturnValue(Textures);
        }

        if (Cape.getInstance().cape.getValue() == Cape.CapeMode.MINECON2015) {
            capeTexture = MINECON2015;
            SkinTextures Textures = new SkinTextures(
                    oldTextures.texture(),
                    oldTextures.textureUrl(),
                    capeTexture,
                    capeTexture,
                    oldTextures.model(),
                    oldTextures.secure()
            );
            cir.setReturnValue(Textures);
        }

        if (Cape.getInstance().cape.getValue() == Cape.CapeMode.MINECON2016) {
            capeTexture = MINECON2016;
            SkinTextures Textures = new SkinTextures(
                    oldTextures.texture(),
                    oldTextures.textureUrl(),
                    capeTexture,
                    capeTexture,
                    oldTextures.model(),
                    oldTextures.secure()
            );
            cir.setReturnValue(Textures);
        }

        if (Cape.getInstance().cape.getValue() == Cape.CapeMode.MOJANG) {
            capeTexture = MOJANG;
            SkinTextures Textures = new SkinTextures(
                    oldTextures.texture(),
                    oldTextures.textureUrl(),
                    capeTexture,
                    capeTexture,
                    oldTextures.model(),
                    oldTextures.secure()
            );
            cir.setReturnValue(Textures);
        }

        if (Cape.getInstance().cape.getValue() == Cape.CapeMode.MOJANG_CLASSIC) {
            capeTexture = MOJANG_CLASSIC;
            SkinTextures Textures = new SkinTextures(
                    oldTextures.texture(),
                    oldTextures.textureUrl(),
                    capeTexture,
                    capeTexture,
                    oldTextures.model(),
                    oldTextures.secure()
            );
            cir.setReturnValue(Textures);
        }

        if (Cape.getInstance().cape.getValue() == Cape.CapeMode.MOJANG_STUDIOS) {
            capeTexture = MOJANG_STUDIOS;
            SkinTextures Textures = new SkinTextures(
                    oldTextures.texture(),
                    oldTextures.textureUrl(),
                    capeTexture,
                    capeTexture,
                    oldTextures.model(),
                    oldTextures.secure()
            );
            cir.setReturnValue(Textures);
        }

        if (Cape.getInstance().cape.getValue() == Cape.CapeMode.PHOBOS) {
            capeTexture = PHOBOS;
            SkinTextures Textures = new SkinTextures(
                    oldTextures.texture(),
                    oldTextures.textureUrl(),
                    capeTexture,
                    capeTexture,
                    oldTextures.model(),
                    oldTextures.secure()
            );
            cir.setReturnValue(Textures);
        }

        if (Cape.getInstance().cape.getValue() == Cape.CapeMode.RUSHERHACKEK) {
            capeTexture = RUSHERHACKEK;
            SkinTextures Textures = new SkinTextures(
                    oldTextures.texture(),
                    oldTextures.textureUrl(),
                    capeTexture,
                    capeTexture,
                    oldTextures.model(),
                    oldTextures.secure()
            );
            cir.setReturnValue(Textures);
        }

        if (Cape.getInstance().cape.getValue() == Cape.CapeMode.SNOWMAN) {
            capeTexture = SNOWMAN;
            SkinTextures Textures = new SkinTextures(
                    oldTextures.texture(),
                    oldTextures.textureUrl(),
                    capeTexture,
                    capeTexture,
                    oldTextures.model(),
                    oldTextures.secure()
            );
            cir.setReturnValue(Textures);
        }
    }
}