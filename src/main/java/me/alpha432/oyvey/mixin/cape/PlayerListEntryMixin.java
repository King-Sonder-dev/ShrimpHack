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
    private static final Identifier CAPY = new Identifier("minecraft", "textures/capes/capy.png");
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

        capeTexture = switch (Cape.getInstance().cape.getValue()) {
            case FUTURE -> Future;
            case RUSHERHACK -> Rusherhack;
            case HYPER -> Hyper;
            case COBALT -> Cobalt;
            case FOUNDER -> FOUNDER;
            case HIGHLAND -> HIGHLAND;
            case CAPY -> CAPY;
            case MINECON2011 -> MINECON2011;
            case MINECON2012 -> MINECON2012;
            case MINECON2013 -> MINECON2013;
            case MINECON2015 -> MINECON2015;
            case MINECON2016 -> MINECON2016;
            case MOJANG -> MOJANG;
            case MOJANG_CLASSIC -> MOJANG_CLASSIC;
            case MOJANG_STUDIOS -> MOJANG_STUDIOS;
            case PHOBOS -> PHOBOS;
            case RUSHERHACKEK -> RUSHERHACKEK;
            case SNOWMAN -> SNOWMAN;
            case NONE -> null;
        };

        if (capeTexture != null) {
            SkinTextures Textures = new SkinTextures (
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