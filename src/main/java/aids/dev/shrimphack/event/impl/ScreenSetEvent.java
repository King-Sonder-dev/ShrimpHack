package aids.dev.shrimphack.event.impl;


import aids.dev.shrimphack.event.Event;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

public class ScreenSetEvent extends Event {
    private static Screen screen = null;
    static MinecraftClient mc = MinecraftClient.getInstance();
    public ScreenSetEvent(Screen screen) {
        this.screen = screen;
    }
    public static Screen getScreen() {
        return screen;
    }
}