package me.alpha432.oyvey.features.modules.client;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.event.impl.Render2DEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Box;

import java.util.List;

public class PvpInfoModule extends Module {
    // Settings for PvpInfo
    public Setting<Integer> gety = this.register(new Setting<>("Y", 205, 0, 485));
    public Setting<Integer> getx = this.register(new Setting<>("X", 0, 0, 710));
    private final Setting<Boolean> showExp = this.register(new Setting<>("ShowExp", true));
    private final Setting<Boolean> showCrystals = this.register(new Setting<>("ShowCrystals", true));
    private final Setting<Boolean> showPLR = this.register(new Setting<>("ShowPLR", true));
    private final Setting<Boolean> showPing = this.register(new Setting<>("ShowPing", true));
    private final Setting<Boolean> showTotems = this.register(new Setting<>("ShowTotems", true));

    public PvpInfoModule() {
        super("PvpInfo", "Displays PvP related information", Category.CLIENT, true, false, false);
    }

    @Override
    public void onRender2D(Render2DEvent event) {
        int x = this.getx.getPlannedValue();
        int y = this.gety.getPlannedValue();
        int color = OyVey.colorManager.getColorAsInt();

        event.getContext().drawTextWithShadow(mc.textRenderer, "Oyvey.pub", x, y, color);
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

        if (showPLR.getValue()) {
            String plrText = "PLR";
            int plrColor = getPLRColor();
            event.getContext().drawTextWithShadow(mc.textRenderer, plrText, x, y, plrColor);
            y += mc.textRenderer.fontHeight;
        }

        if (showPing.getValue()) {
            String pingText = getPing() + " Ms";
            event.getContext().drawTextWithShadow(mc.textRenderer, pingText, x, y, getPingColor());
            y += mc.textRenderer.fontHeight;
        }

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
}