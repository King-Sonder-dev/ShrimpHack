package aids.dev.shrimphack.features.modules;

import aids.dev.shrimphack.features.Feature;
import aids.dev.shrimphack.features.modules.combat.autototem.AutoTotem;
import aids.dev.shrimphack.features.settings.Bind;
import aids.dev.shrimphack.features.settings.Setting;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import aids.dev.shrimphack.Shrimphack;
import aids.dev.shrimphack.event.impl.ClientEvent;
import aids.dev.shrimphack.event.impl.Render2DEvent;
import aids.dev.shrimphack.event.impl.Render3DEvent;
import aids.dev.shrimphack.manager.ConfigManager;
import aids.dev.shrimphack.util.traits.Jsonable;
import net.minecraft.network.packet.Packet;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static aids.dev.shrimphack.features.commands.Command.sendMessage;

public class Module extends Feature implements Jsonable {
    private final String description;
    private final Category category;
    public Setting<Boolean> enabled = this.register(new Setting<>("Enabled", false));
    public Setting<Boolean> drawn = this.register(new Setting<>("Drawn", true));
    public Setting<Bind> bind = this.register(new Setting<>("Keybind", new Bind(-1)));
    public Setting<String> displayName;
    public boolean hasListener;
    public boolean alwaysListening;
    public boolean hidden;

    public Module(String name, String description, Category category, boolean hasListener, boolean hidden, boolean alwaysListening) {
        super(name);
        this.displayName = this.register(new Setting<>("DisplayName", name));
        this.description = description;
        this.category = category;
        this.hasListener = hasListener;
        this.hidden = hidden;
        this.alwaysListening = alwaysListening;
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void onToggle() {
    }

    public void onLoad() {
    }

    public void onTick() {
    }

    public void onUpdate() {
    }

    public void onRender2D(Render2DEvent event) {
    }

    public void onRender3D(Render3DEvent event) {
    }

    public void onUnload() {
    }

    public String getDisplayInfo() {
        return null;
    }

    public boolean isOn() {
        return this.enabled.getValue();
    }

    public boolean isOff() {
        return !this.enabled.getValue();
    }

    public void setEnabled(boolean enabled) {
        if (enabled) {
            this.enable();
        } else {
            this.disable();
        }
    }

    public void enable() {
        this.enabled.setValue(true);
        this.onToggle();
        this.onEnable();
        if (this.isOn() && this.hasListener && !this.alwaysListening) {
            EVENT_BUS.register(this);
        }
    }

    public void disable() {
        if (this.hasListener && !this.alwaysListening) {
            EVENT_BUS.unregister(this);
        }
        this.enabled.setValue(false);
        this.onToggle();
        this.onDisable();
    }

    public void toggle() {
        // Check if the chat is open
        if (mc.currentScreen instanceof net.minecraft.client.gui.screen.ChatScreen) {
            return;
        }


        ClientEvent event = new ClientEvent(!this.isEnabled() ? 1 : 0, this);
        EVENT_BUS.post(event);
        if (!event.isCancelled()) {
            this.setEnabled(!this.isEnabled());
        }
    }

    public String getDisplayName() {
        return this.displayName.getValue();
    }

    public void setDisplayName(String name) {
        Module module = Shrimphack.moduleManager.getModuleByDisplayName(name);
        Module originalModule = Shrimphack.moduleManager.getModuleByName(name);
        if (module == null && originalModule == null) {
            sendMessage(this.getDisplayName() + ", name: " + this.getName() + ", has been renamed to: " + name);
            this.displayName.setValue(name);
            return;
        }
        sendMessage(Formatting.RED + "A module of this name already exists.");
    }

    @Override public boolean isEnabled() {
        return isOn();
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isDrawn() {
        return this.drawn.getValue();
    }

    public void setDrawn(boolean drawn) {
        this.drawn.setValue(drawn);
    }

    public Category getCategory() {
        return this.category;
    }

    public String getInfo() {
        return null;
    }

    public Bind getBind() {
        return this.bind.getValue();
    }

    public void setBind(int key) {
        this.bind.setValue(new Bind(key));
    }

    public boolean listening() {
        return this.hasListener && this.isOn() || this.alwaysListening;
    }

    public String getFullArrayString() {
        return this.getDisplayName() + Formatting.GRAY + (this.getDisplayInfo() != null ? " [" + Formatting.WHITE + this.getDisplayInfo() + Formatting.GRAY + "]" : "");
    }

    @Override public JsonElement toJson() {
        JsonObject object = new JsonObject();
        for (Setting<?> setting : getSettings()) {
            try {
                if (setting.getValue() instanceof Bind bind) {
                    object.addProperty(setting.getName(), bind.getKey());
                } else {
                    object.addProperty(setting.getName(), setting.getValueAsString());
                }
            } catch (Throwable e) {
            }
        }
        return object;
    }

    @Override public void fromJson(JsonElement element) {
        JsonObject object = element.getAsJsonObject();
        String enabled = object.get("Enabled").getAsString();
        if (Boolean.parseBoolean(enabled)) this.toggle();
        for (Setting<?> setting : getSettings()) {
            try {
                Shrimphack.LOGGER.info("configging " + this.getName() + ", " + setting.getName());
                ConfigManager.setValueFromJson(this, setting, object.get(setting.getName()));
            } catch (Throwable throwable) {
            }
        }
    }

    public enum Category {
        CHAT("Chat"),
        COMBAT("Combat"),
        MISC("Misc"),
        RENDER("Render"),
        MOVEMENT("Movement"),
        PLAYER("Player"),
        EXPLOIT("Exploit"),
        CLIENT("Core"),
        SHRIMPDOTCONFIRM("Shrimp.Confirm");

        private final String name;

        Category(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
    protected void sendPacket(Packet<?> packet) {
        if (mc.getNetworkHandler() == null) return;

        mc.getNetworkHandler().sendPacket(packet);
    }
    public void disable(String reason) {
        sendMessage(reason);
        disable();
    }
    public static void clickSlot(int id) {
        if (id == -1 || mc.interactionManager == null || mc.player == null) return;
        mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, id, 0, SlotActionType.PICKUP, mc.player);
    }

    public static void clickSlot(int id, SlotActionType type) {
        if (id == -1 || mc.interactionManager == null || mc.player == null) return;
        mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, id, 0, type, mc.player);
    }
    public void debug(String message) {
        if (fullNullCheck() || !AutoTotem.getInstance().debug.getValue()) return;
        mc.player.sendMessage(Text.of(message), false);
    }
}
