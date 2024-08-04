package me.alpha432.oyvey.features.modules.chat;

import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PearlNotify extends Module {
    private final HashMap<PlayerEntity, UUID> list;
    private Entity enderPearl;
    private final Map<String, Setting<Boolean>> moduleSettings = new HashMap<>();
    private final Map<Module, Boolean> moduleStates = new HashMap<>();
    private boolean flag;
    public final Setting<MessageStyle> messageStyle = this.register(new Setting<>("MessageStyle", MessageStyle.NONE));
    public Setting<Boolean> bold = this.register(new Setting<>("Boldsn0w", true, v -> this.messageStyle.getValue() == MessageStyle.SN0W));

    public PearlNotify() {
        super("PearlNotify", "Notify pearl throws.", Category.CHAT, true, false, false);
        this.list = new HashMap<>();
        init();
    }

    private void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> onTick());
    }

    @Override
    public void onEnable() {
        this.flag = true;
    }

    @Override
    public void onTick() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.world == null || mc.player == null) {
            return;
        }
        this.enderPearl = null;

        // Search for an Ender Pearl entity
        for (Entity e : mc.world.getEntities()) {
            if (e.getType() == EntityType.ENDER_PEARL) {
                this.enderPearl = e;
                break;
            }
        }

        if (this.enderPearl == null) {
            this.flag = true;
            return;
        }

        // Find the closest player to the Ender Pearl
        PlayerEntity closestPlayer = null;
        for (PlayerEntity entity : mc.world.getPlayers()) {
            if (closestPlayer == null) {
                closestPlayer = entity;
            } else if (closestPlayer.squaredDistanceTo(this.enderPearl) > entity.squaredDistanceTo(this.enderPearl)) {
                closestPlayer = entity;
            }
        }

        // Do not notify if the player is the one who threw the pearl
        if (closestPlayer == mc.player) {
            this.flag = false;
        }

        // Send a notification if a valid player is found and they are not a friend
        if (closestPlayer != null && this.flag && !OyVey.friendManager.isFriend(closestPlayer.getName().getString())) {
            String facing = this.enderPearl.getHorizontalFacing().asString();
            if (facing.equals("west")) {
                facing = "east";
            } else if (facing.equals("east")) {
                facing = "west";
            }

            String message = getFormattedMessage(closestPlayer.getName().getString(), facing);
            mc.inGameHud.getChatHud().addMessage(Text.of(message));
            this.flag = false;
        }
    }

    private String getFormattedMessage(String playerName, String facing) {
        String prefix = "";
        switch (this.messageStyle.getValue()) {
            case FUTURE:
                prefix = Formatting.RED + "[Future] " + Formatting.RESET + playerName + " has just thrown a pearl heading " + facing + ".";
                break;
            case PHOBOS:
                prefix = " " + Formatting.GOLD + playerName + Formatting.RED + " has just thrown a pearl heading " + facing + ".";
                break;
            case DOTGOD:
                prefix = Formatting.DARK_PURPLE + "[" + Formatting.LIGHT_PURPLE + "DotGod.CC" + Formatting.DARK_PURPLE + "] " + Formatting.DARK_AQUA + playerName + Formatting.LIGHT_PURPLE + " has just thrown a pearl heading " + facing;
                break;
            case SN0W:
                prefix = Formatting.BLUE + "[" + Formatting.AQUA + "❄" + Formatting.BLUE + "] " + Formatting.RESET + (this.bold.getValue() ? Formatting.BOLD : "") + playerName + Formatting.RESET + " has just thrown a pearl heading " + facing + "!";
                break;
            case SNOW:
                prefix = Formatting.GRAY + "[" + Formatting.AQUA + "Snow" + Formatting.RESET + Formatting.GRAY + "]" + Formatting.RESET + Formatting.GRAY + " [" + Formatting.DARK_AQUA + "PearlNotify" + Formatting.RESET + Formatting.GRAY + "] " + Formatting.RESET + playerName + " has just thrown a pearl heading " + facing;
                break;
            case NONE:
                prefix = Formatting.WHITE + playerName + " has been " + Formatting.GREEN + facing + Formatting.WHITE + ".";
                break;
            default:
                break;
        }
        return prefix;
    }

    public enum MessageStyle {
        NONE,
        FUTURE,
        PHOBOS,
        DOTGOD,
        SN0W,
        SNOW
    }
}
