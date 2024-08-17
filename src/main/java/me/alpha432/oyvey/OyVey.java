package me.alpha432.oyvey;

import me.alpha432.oyvey.auth.WebhookInformer;
import me.alpha432.oyvey.manager.*;
import me.alpha432.oyvey.util.HWIDUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class OyVey implements ModInitializer, ClientModInitializer {
    public static final String NAME = "Oyvey++";
    public static final String VERSION = "0.8";

    public static float TIMER = 1f;
    public static final Logger LOGGER = LogManager.getLogger("OyVey++");
    public static final ModuleManager MODULE_MANAGER = new ModuleManager();
    public static ServerManager serverManager;
    public static ColorManager colorManager;
    public static RotationManager rotationManager;
    public static PositionManager positionManager;
    public static HoleManager holeManager;
    public static EventManager eventManager;
    public static SpeedManager speedManager;
    public static CommandManager commandManager;
    public static FriendManager friendManager;
    public static ModuleManager moduleManager;
    public static ConfigManager configManager;
    public static PlayerManager playerManager;
    public static AsyncManager asyncManager;
    public static TntCManager tntCManager;
    public static CombatManager combatManager;
    public static HudManager hudManager;
    public static MineManager mineManager;

    private static Set<String> AUTHORIZED_HWIDS;

    public static boolean isHWIDAuthorized() {
        if (AUTHORIZED_HWIDS == null) {
            AUTHORIZED_HWIDS = HWIDUtil.getAuthorizedHWIDs();
        }
        String hwid = HWIDUtil.getHWID();
        return AUTHORIZED_HWIDS.contains(hwid);
    }

    @Override
    public void onInitialize() {
        /* if (!isHWIDAuthorized()) {
            LOGGER.error("Unauthorized HWID! Shutting down.");
            WebhookInformer.sendLogginFail();
            System.exit(1);
        } */

        eventManager = new EventManager();
        serverManager = new ServerManager();
        rotationManager = new RotationManager();
        positionManager = new PositionManager();
        friendManager = new FriendManager();
        colorManager = new ColorManager();
        commandManager = new CommandManager();
        moduleManager = new ModuleManager();
        speedManager = new SpeedManager();
        holeManager = new HoleManager();
    }

    @Override
    public void onInitializeClient() {
        WebhookInformer.sendLaunch();
        eventManager.init();
        moduleManager.init();

        configManager = new ConfigManager();
        configManager.load();
        colorManager.init();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                configManager.save();
                WebhookInformer.sendExit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
    }
    public static OyVey INSTANCE;
}
