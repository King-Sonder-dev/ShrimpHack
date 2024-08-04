package me.alpha432.oyvey.features.modules.misc;

import com.google.common.eventbus.Subscribe;
import me.alpha432.oyvey.event.impl.PacketEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import me.alpha432.oyvey.util.models.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;

public class FCAIALM extends Module {
    private static FCAIALM INSTANCE = new FCAIALM();
    enum CAMode {None, AP, One, Default}
    enum SurroundMode {AntiDie, AutoObsidian, Surround, Default}
    Setting<CAMode> camode = register(new Setting("CA", CAMode.Default));
    Setting<SurroundMode> surroundmode = register(new Setting("Surround", SurroundMode.Default));
    Setting<Boolean> doWatermark = register(new Setting("Watermark", false));
    Setting<String> cWatermark = register(new Setting("W", "Future v2.11.1", v -> doWatermark.getValue()));
    Setting<Boolean> noinfo = register(new Setting("NoInfo", false));
    boolean APstringAttacking = true;
    boolean APhasPlaced = false;
    boolean APhasAttacked = false;
    boolean APpacketAttacking = true;
    boolean APnothing = true;
    Timer APtimer = new Timer();

    public FCAIALM() {
        super("ClientChange", "very cool", Category.COMBAT, true, false, false);
        this.setInstance();
    }

    @Subscribe
    public void onPacketSend(PacketEvent.Send event) {

        if (event.getPacket() instanceof PlayerInteractBlockC2SPacket) {
            PlayerInteractBlockC2SPacket placePacket = (PlayerInteractBlockC2SPacket) event.getPacket();
            if (placePacket.getHand() == Hand.MAIN_HAND) {
                if (mc.player.getMainHandStack().getItem() == Items.END_CRYSTAL) {
                    APhasPlaced = true;
                    APpacketAttacking = false;
                }
            } else {
                if (mc.player.getMainHandStack().getItem() == Items.END_CRYSTAL) {
                    APhasPlaced = true;
                    APpacketAttacking = false;
                }
            }
        }

        if (event.getPacket() instanceof PlayerInteractEntityC2SPacket) {
            PlayerInteractEntityC2SPacket attackPacket = (PlayerInteractEntityC2SPacket) event.getPacket();
            int entityId = attackPacket.entityId;
            Entity entity = mc.world.getEntityById(entityId);
            if (entity instanceof EndCrystalEntity) {
                APhasAttacked = true;
                APpacketAttacking = true;
            }
        }
// for future net.futureclient.client
        if (event.getPacket().getClass().getName().contains("dev.boze")) {
            APhasAttacked = true;
            APpacketAttacking = true;
        }
    }

    @Override
    public void onUpdate() {
        if (APtimer.passedMs(250)) {
            if (APhasAttacked && APhasPlaced) {
                APnothing = false;
                APstringAttacking = !APstringAttacking;
            } else if (APhasAttacked || APhasPlaced) {
                APnothing = false;
                APstringAttacking = APpacketAttacking;
            } else {
                APnothing = true;
            }
            APhasPlaced = false;
            APhasAttacked = false;
            APtimer.reset();
        }
    }

    public String replaceWithAliases(String text) {
        if (!isOn()) return text;

        if (text.contains(" " + Formatting.GRAY + "[") && noinfo.getValue()) {
            return text.replaceAll(" " + Formatting.GRAY + "\\[.*]", "");
        }

        if (text.startsWith("AutoCrystal " + Formatting.GRAY + "[")) {

            switch (camode.getValue()) {
                case None: {
                    return "AutoCrystal";
                }
                case AP: {
                    if (APnothing) {
                        return "AutoCrystal";
                    } else {
                        return "AutoCrystal " + Formatting.GRAY + "[" + Formatting.WHITE + (APstringAttacking ? "Attack" : "Place") + Formatting.GRAY + "]";
                    }
                }
                case One: {
                    return text.replaceAll("\\,.*]", "]");
                }
                default: {
                    return text;
                }
            }
        }

        if (text.startsWith("FeetTrap " + Formatting.GRAY + "[")) {
            switch (surroundmode.getValue()) {
                case AntiDie: {
                    return "AntiDie";
                }
                case AutoObsidian: {
                    return "AutoObsidian";
                }
                case Surround: {
                    return "Surround";
                }
                default: {
                    return text;
                }
            }
        }

        if (text.startsWith("Boze ") && doWatermark.getValue()) {
            return cWatermark.getValue();
        }
        
        return text;
    }

    public static FCAIALM getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new FCAIALM();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }
}