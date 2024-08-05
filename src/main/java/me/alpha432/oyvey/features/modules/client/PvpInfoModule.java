package me.alpha432.oyvey.features.modules.client;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.event.impl.Render2DEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.modules.movement.InstantSpeedPlus;
import me.alpha432.oyvey.features.settings.Setting;
import me.alpha432.oyvey.util.BlockUtil;
import me.alpha432.oyvey.util.EntityUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class PvpInfoModule extends Module {
    // Settings for PvpInfo
    private final Setting<TextMode> textMode = this.register(new Setting<>("TextMode", TextMode.OYVEY));

    public final Setting<String> custom = new Setting<>("Custom", "Oyvey.pub");

    public final Setting<Integer> gety = this.register(new Setting<>("Y", 205, 0, 485));
    public final Setting<Integer> getx = this.register(new Setting<>("X", 0, 0, 710));
    private final Setting<Boolean> showExp = this.register(new Setting<>("ShowExp", true));
    private final Setting<Boolean> showCrystals = this.register(new Setting<>("ShowCrystals", true));
    private final Setting<Boolean> showPLR = this.register(new Setting<>("ShowPLR", true));
    private final Setting<Boolean> showSafety = this.register(new Setting<>("ShowSafety", true));

    private final Setting<Boolean> showPing = this.register(new Setting<>("ShowPing", true));
    private final Setting<Boolean> showTotems = this.register(new Setting<>("ShowTotems", true));

    public PvpInfoModule() {
        super("PvpInfo", "Displays PvP related information", Category.CLIENT, true, false, false);
    }

    public String getWatermark() {
        switch (textMode.getValue()) {
            case FUTURE:
                return "Future v2.13.5";
            case FUTUREBETA:
                return "Future v2.13.5-extern+274.ba4c68c147";
            case DOTGOD:
                return "DotGod.CC";
            case PHOBOS:
                return "Phobos.eu";
            case TROLLGOD:
                return "Trollgod.CC";
            case OYVEY:
                return OyVey.NAME + " " + OyVey.VERSION;
            case OYVEYDOTPUB:
                return "Oyvey.pub";
            case MIO:
                return "Mio v2.0.2";
            case MIONIGHTLY:
                return "Mio v2.0.2-nightly";
            case MIODOTME:
                return "Mioclient.me";
            case SNOWBETA:
                return "Snow 4.4-beta";
            case NUTGOD:
                return "Nutgod.cc";
            case GONDAL:
                return "Gondal.club";
            case MCDONALDS:
                return "McDonal Client";
            case RATWARE:
                return "Ratware";
            case EARTHHACK:
                return  "3arthh4ck";
            case AUTOWINCC:
                return "Autowin.cc";
            case SN0W:
                return "Sn0w";
            case ONEHACK:
                return "1hack.org";
            case SKULLHACK:
                return "Skullhack";
            case PUTAHACKNN:
                return "Putahack.nn";
            case FLORADOTNET:
                return "Flora.net";
            case FLORADOTNETDEV:
                return "FLora.net v2.0.0-DEV";
            case CLOWNGOD:
                return "Clowngod.cc";
            case TATERGOD:
                return "TaterGOD.cc";
            case ZIPCLUB:
                return "Zip.club";
            case BUTTERFLY:
                return "butterfly v2.3.3";
            case PASTBETA:
                return "Past v3.11-beta+2.5fda9d5127+";
            default:
                return custom.getValue();
        }
    }

    @Override
    public void onRender2D(Render2DEvent event) {
        int x = this.getx.getPlannedValue();
        int y = this.gety.getPlannedValue();
        int color = OyVey.colorManager.getColorAsInt();
        String displayText = getWatermark();

        event.getContext().drawTextWithShadow(mc.textRenderer, displayText, x, y, color);
        y += mc.textRenderer.fontHeight;

        // Render "Exp" first
        if (showExp.getValue()) {
            String expText = "Exp: " + getExpCount();
            event.getContext().drawTextWithShadow(mc.textRenderer, expText, x, y, color);
            y += mc.textRenderer.fontHeight;
        }

        // Render "Crystals" second
        if (showCrystals.getValue()) {
            String crystalsText = "Crystals: " + getCrystalCount();
            event.getContext().drawTextWithShadow(mc.textRenderer, crystalsText, x, y, color);
            y += mc.textRenderer.fontHeight;
        }

        // Render "PLR"
        if (showPLR.getValue()) {
            String plrText = "PLR";
            int plrColor = getPLRColor();
            event.getContext().drawTextWithShadow(mc.textRenderer, plrText, x, y, plrColor);
            y += mc.textRenderer.fontHeight;
        }

        // Render "Safety"
        if (showSafety.getValue()) {
            String safetyText = getSafetyText();
            int safetyColor = getSafetyColor();
            event.getContext().drawTextWithShadow(mc.textRenderer, safetyText, x, y, safetyColor);
            y += mc.textRenderer.fontHeight;
        }

        // Render "Ping"
        if (showPing.getValue()) {
            String pingText = getPing() + " Ms";
            event.getContext().drawTextWithShadow(mc.textRenderer, pingText, x, y, getPingColor());
            y += mc.textRenderer.fontHeight;
        }

        // Render "Totems"
        if (showTotems.getValue()) {
            String totemsText = "Totems: " + getTotemCount();
            event.getContext().drawTextWithShadow(mc.textRenderer, totemsText, x, y, color);
            y += mc.textRenderer.fontHeight;
        }
    }

    private int getExpCount() {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            int count = 0;
            for (ItemStack stack : player.getInventory().main) {
                if (stack.getItem() == Items.EXPERIENCE_BOTTLE) {
                    count += stack.getCount();
                }
            }
            return count;
        }
        return 0;
    }

    private int getCrystalCount() {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            int count = 0;
            for (ItemStack stack : player.getInventory().main) {
                if (stack.getItem() == Items.END_CRYSTAL) {
                    count += stack.getCount();
                }
            }
            return count;
        }
        return 0;
    }

    private int getPLRColor() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            Box box = player.getBoundingBox().expand(5, 5, 5);
            List<PlayerEntity> nearbyPlayers = player.getWorld().getEntitiesByClass(PlayerEntity.class, box, p -> p != player);
            if (nearbyPlayers.isEmpty()) {
                return 0xFF0000; // Red if there are no nearby players
            } else {
                boolean allFriends = true;
                for (PlayerEntity nearbyPlayer : nearbyPlayers) {
                    if (!OyVey.friendManager.isFriend(nearbyPlayer)) {
                        allFriends = false;
                        break;
                    }
                }
                return allFriends ? 0xFF0000 : 0x00FF00; // Red if all nearby players are friends, green if there's at least one who is not a friend
            }
        }
        return 0xFF0000; // Default to Red if player is null
    }

    private String getSafetyText() {
        return BlockUtil.isHole(EntityUtil.getPlayerPos()) ? "SAFE" : "UNSAFE";
    }

    private int getSafetyColor() {
        return BlockUtil.isHole(EntityUtil.getPlayerPos()) ? 0x00FF00 : 0xFF0000; // Green if in a hole, Red if not
    }

    private int getPing() {
        return OyVey.serverManager.getPing();
    }

    private int getTotemCount() {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            int count = 0;
            // Check the main inventory
            for (ItemStack stack : player.getInventory().main) {
                if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
                    count += stack.getCount();
                }
            }
            ItemStack offhandStack = player.getOffHandStack();
            if (offhandStack.getItem() == Items.TOTEM_OF_UNDYING) {
                count += offhandStack.getCount();
            }
            return count;
        }
        return 0;
    }

    private int getPingColor() {
        int ping = getPing();
        if (ping <= 50) {
            return 0x00FF00; // Green
        } else if (ping <= 75) {
            return 0xFFFF00; // Yellow
        } else {
            return 0xFF0000; // Red
        }
    }

    public enum TextMode {
        OYVEY,
        OYVEYDOTPUB,
        BUTTERFLY,
        PASTBETA,
        ZIPCLUB,
        TATERGOD,
        CLOWNGOD,
        FLORADOTNET,
        FLORADOTNETDEV,
        PUTAHACKNN,
        SKULLHACK,
        ONEHACK,
        SN0W,
        AUTOWINCC,
        EARTHHACK,
        RATWARE,
        MCDONALDS,
        GONDAL,
        NUTGOD,
        TROLLGOD,
        FUTURE,
        FUTUREBETA,
        DOTGOD,
        PHOBOS,
        MIO,
        MIONIGHTLY,
        MIODOTME,
        SNOWBETA
    }
}
