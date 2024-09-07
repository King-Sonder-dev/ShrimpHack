package aids.dev.shrimphack.manager;

import com.google.common.eventbus.Subscribe;
import aids.dev.shrimphack.Shrimphack;
import aids.dev.shrimphack.event.Stage;
import aids.dev.shrimphack.event.impl.*;
import aids.dev.shrimphack.features.Feature;
import aids.dev.shrimphack.features.commands.Command;
import aids.dev.shrimphack.features.modules.client.Options;
import aids.dev.shrimphack.util.models.Timer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.util.Formatting;

import static aids.dev.shrimphack.util.traits.Util.EVENT_BUS;
import static aids.dev.shrimphack.util.traits.Util.mc;

public class EventManager extends Feature {
    private final Timer logoutTimer = new Timer();


    public void init() {
        EVENT_BUS.register(this);
    }

    public void onUnload() {
        EVENT_BUS.unregister(this);
    }

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        Options optionsModule = Shrimphack.moduleManager.getModuleByClass(Options.class);
        if (optionsModule != null && optionsModule.title.getValue()) {
            mc.getWindow().setTitle("Shrimphack | " + mc.getCurrentFps());
        }
        if (!fullNullCheck()) {
//            Shrimphack.inventoryManager.update();
            Shrimphack.moduleManager.onUpdate();
            Shrimphack.moduleManager.sortModules(true);
            onTick();
//            if ((HUD.getInstance()).renderingMode.getValue() == HUD.RenderingMode.Length) {
//                Shrimphack.moduleManager.sortModules(true);
//            } else {
//                Shrimphack.moduleManager.sortModulesABC();
//            }
        }
    }

    public void onTick() {
        if (fullNullCheck())
            return;
        Shrimphack.moduleManager.onTick();
        for (PlayerEntity player : mc.world.getPlayers()) {
            if (player == null || player.getHealth() > 0.0F)
                continue;
            EVENT_BUS.post(new DeathEvent(player));
        }
    }

    @Subscribe
    public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent event) {
        if (fullNullCheck())
            return;
        if (event.getStage() == Stage.PRE) {
            Shrimphack.speedManager.updateValues();
            Shrimphack.rotationManager.updateRotations();
            Shrimphack.positionManager.updatePosition();
        }
        if (event.getStage() == Stage.POST) {
            Shrimphack.rotationManager.restoreRotations();
            Shrimphack.positionManager.restorePosition();
        }
    }

    @Subscribe
    public void onPacketReceive(PacketEvent.Receive event) {
        Shrimphack.serverManager.onPacketReceived();
        if (event.getPacket() instanceof WorldTimeUpdateS2CPacket)
            Shrimphack.serverManager.update();

    }

    @Subscribe
    public void onWorldRender(Render3DEvent event) {
        Shrimphack.moduleManager.onRender3D(event);
    }

    @Subscribe public void onRenderGameOverlayEvent(Render2DEvent event) {
        Shrimphack.moduleManager.onRender2D(event);
    }

    @Subscribe public void onKeyInput(KeyEvent event) {
        Shrimphack.moduleManager.onKeyPressed(event.getKey());
    }

    @Subscribe public void onChatSent(ChatEvent event) {
        if (event.getMessage().startsWith(Command.getCommandPrefix())) {
            event.cancel();
            try {
                if (event.getMessage().length() > 1) {
                    Shrimphack.commandManager.executeCommand(event.getMessage().substring(Command.getCommandPrefix().length() - 1));
                } else {
                    Command.sendMessage("Please enter a command.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Command.sendMessage(Formatting.RED + "An error occurred while running this command. Check the log!");
            }
        }
    }
}