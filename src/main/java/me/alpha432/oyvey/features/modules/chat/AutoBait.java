package me.alpha432.oyvey.features.modules.chat;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import me.alpha432.oyvey.features.commands.Command;
import me.alpha432.oyvey.util.BaitUtil;
import me.alpha432.oyvey.util.models.Timer;

import java.util.List;
import java.util.ArrayList;

public class AutoBait extends Module {

    private final Setting<Double> delay = this.register(new Setting<>("Delay", 0.25, 0.0, 10.0));
    private final Setting<Messagemode> mode = this.register(new Setting<>("Mode", Messagemode.SEX_SCRIPT));
    private final List<String> sexscript = new ArrayList<>(BaitUtil.SEX_SCRIPT);
    private final List<String> spike = new ArrayList<>(BaitUtil.SPIKE_AND_KIDS);
    private final List<String> pkBait = new ArrayList<>(BaitUtil.PKBAIT);
    private final Timer timer = new Timer();
    private int index;

    public AutoBait() {
        super("AutoBait", "Automatically spams a selected mode", Module.Category.CHAT, true, false, false);
    }

    @Override
    public void onEnable() {
        index = 0;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        index = 0;
        super.onDisable();
    }

    @Override
    public void onUpdate() {
        if (mc.world == null || mc.player == null) {
            return;
        }

        List<String> selectedList = getSelectedList();

        if (index >= selectedList.size()) {
            index = 0;
        }

        String phrase = selectedList.get(index);

        if (timer.passedS(delay.getValue())) {
            Command.serverSendMessage(phrase);
            System.out.println("Sending message: " + phrase + " at index: " + index); // Debugging output
            timer.reset();
            index++;
        }
    }

    private List<String> getSelectedList() {
        switch (mode.getValue()) {
            case SEX_SCRIPT:
                return sexscript;
            case SPIKE_AND_KIDS:
                return spike;
            case PKBAIT:
                return pkBait;
            default:
                return new ArrayList<>();
        }
    }

    public enum Messagemode {
        SEX_SCRIPT,
        SPIKE_AND_KIDS,
        PKBAIT
    }
}
