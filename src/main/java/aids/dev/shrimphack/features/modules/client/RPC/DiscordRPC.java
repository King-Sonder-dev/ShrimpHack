package aids.dev.shrimphack.features.modules.client.RPC;

import aids.dev.shrimphack.DiscordRP;
import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.settings.Setting;


public class DiscordRPC extends Module {
    private final DiscordRP discordRP = new DiscordRP();
    private final Setting<Integer> delay = register(new Setting<>("Delay", 5000, 1000, 10000)); // Default 5 seconds, min 1 second, max 10 seconds
    public final Setting<ImageMode> imageMode = this.register(new Setting<>("Image", ImageMode.NONE)); // Image mode setting

    public DiscordRPC() {
        super("DiscordRPC", "Displays your status on Discord.", Category.CLIENT, true, false, false);
    }

    @Override
    public void onEnable() {
        discordRP.start(delay.getValue(), imageMode.getValue());
        super.onEnable();
    }

    @Override
    public void onDisable() {
        discordRP.stop();
        super.onDisable();
    }
}