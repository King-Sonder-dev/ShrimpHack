package aids.dev.shrimphack.manager;

import aids.dev.shrimphack.event.impl.Render2DEvent;
import aids.dev.shrimphack.event.impl.Render3DEvent;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import aids.dev.shrimphack.features.Feature;
import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.modules.chat.*;
import aids.dev.shrimphack.features.modules.chat.AutoBait;
import aids.dev.shrimphack.features.modules.chat.chatmodifier.ChatModifier;
import aids.dev.shrimphack.features.modules.client.*;
import aids.dev.shrimphack.features.modules.client.RPC.DiscordRPC;
import aids.dev.shrimphack.features.modules.combat.*;
import aids.dev.shrimphack.features.modules.exploit.HitboxDesyncModule;
import aids.dev.shrimphack.features.modules.exploit.PingSpoof;
import aids.dev.shrimphack.features.modules.exploit.RaytraceBypass;
import aids.dev.shrimphack.features.modules.exploit.iPaperDupe;
import aids.dev.shrimphack.features.modules.misc.*;
import aids.dev.shrimphack.features.modules.misc.Fakeplayer.FakePlayer;
import aids.dev.shrimphack.features.modules.misc.autoauth.AutoAuth;
import aids.dev.shrimphack.features.modules.movement.*;
import aids.dev.shrimphack.features.modules.movement.flight.Flight;
import aids.dev.shrimphack.features.modules.movement.noslow.NoSlow;
import aids.dev.shrimphack.features.modules.oyveydotconfirm.HudModuleOld;
import aids.dev.shrimphack.features.modules.oyveydotconfirm.KenCarsonModule;
import aids.dev.shrimphack.features.modules.player.*;
import aids.dev.shrimphack.features.modules.player.fastuse.FastUse;
import aids.dev.shrimphack.features.modules.player.hotbarreplenish.HotbarReplenish;
import aids.dev.shrimphack.features.modules.player.middleclick.MiddleClick;
import aids.dev.shrimphack.features.modules.player.throwpearl.ThrowPearl;
import aids.dev.shrimphack.features.modules.player.velocity.Velocity;
import aids.dev.shrimphack.features.modules.render.*;
import aids.dev.shrimphack.features.modules.render.cameraclip.NoCameraClip;
import aids.dev.shrimphack.features.modules.render.fov.FOV;
import aids.dev.shrimphack.util.traits.Jsonable;
import aids.dev.shrimphack.util.traits.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager implements Jsonable, Util {

    public List<Module> modules = new ArrayList<>();
    public List<Module> sortedModules = new ArrayList<>();
    public List<String> sortedModulesABC = new ArrayList<>();



    public void init() {
        modules.add(new StrengthDetect());
        modules.add(new AntiWeb());
        modules.add(new RaytraceBypass());
        modules.add(new Surround());
        modules.add(new CrystalAura());
        modules.add(new iPaperDupe());
        modules.add(new AntiAim());
        modules.add(new Options());
        modules.add(new Autoez());
        modules.add(new HUD());
        modules.add(new KenCarsonModule());
        modules.add(new CAMessage());
        modules.add(new FCAIALM());
        modules.add(new DiscordRPC());
        modules.add(new HudModule());
        modules.add(new CrystalPredict());
        modules.add(new ClickGui());
        modules.add(new Stairs());
        modules.add(new HoleESP());
        modules.add(new BreakESP());
        modules.add(new PvpInfoModule());
        modules.add(new GhastSpawnNotifier());
        modules.add(new ForceSneak());
        modules.add(new PingSpoof());
        modules.add(new AutoPhase());
        modules.add(new WordGuard());
        modules.add(new MSChecker());
        modules.add(new FastLatency());
        modules.add(new NoFall());
        modules.add(new OnGroundSpeed());
        modules.add(new InstantSpeed());
        modules.add(new TPSChecker());
        modules.add(new TextRadar());
        modules.add(new NoInterpolation());
        modules.add(new PearlNotify());
        modules.add(new PopCounter());
        modules.add(new HitboxDesyncModule());
        modules.add(new Backup());
        modules.add(new AutoMeow());
        modules.add(new BurrowESP());
        modules.add(new VisualRange());
        modules.add(new Criticals());
        modules.add(new MCF());
        modules.add(new Step());
        modules.add(new ReverseStep());
        modules.add(new FastPlace());
        modules.add(new VelocityExplosion());
        modules.add(new NotificationModule());
        modules.add(new ModuleTools());
        modules.add(new AutoBait());
        modules.add(new NoSlow());
        modules.add(new FakePlayer());
        modules.add(new Velocity());
        modules.add(new XCarry());
        modules.add(new ViewLock());
        modules.add(new AutoRespawn());
        modules.add(new MiddleClick());
        modules.add(new HotbarReplenish());
        modules.add(new FastUse());
        modules.add(new HandModifier());
        modules.add(new AspectRatio());
        modules.add(new FOV());
        modules.add(new NoCameraClip());
        modules.add(new InvMove());
        modules.add(new AutoWalk());
        modules.add(new Flight());
        modules.add(new PortalChat());
        modules.add(new EntityNotifier());
        modules.add(new AutoFrameDupe());
        modules.add(new AutoBedDupe());
        modules.add(new AutoAuth());
        modules.add(new Cape());
        modules.add(new ChatModifier());
        modules.add(new Popcounterplus());
        modules.add(new Poplag());
        modules.add(new ThrowPearl());
        modules.add(new HudModuleOld());
        modules.add(new FullBright());
        modules.add(new ChatSuffix());
    }

    public Module getModuleByName(String name) {
        for (Module module : this.modules) {
            if (!module.getName().equalsIgnoreCase(name)) continue;
            return module;
        }
        return null;
    }

    public <T extends Module> T getModuleByClass(Class<T> clazz) {
        for (Module module : this.modules) {
            if (!clazz.isInstance(module)) continue;
            return (T) module;
        }
        return null;
    }


    public void enableModule(Class<Module> clazz) {
        Module module = this.getModuleByClass(clazz);
        if (module != null) {
            module.enable();
        }
    }

    public void disableModule(Class<Module> clazz) {
        Module module = this.getModuleByClass(clazz);
        if (module != null) {
            module.disable();
        }
    }

    public void enableModule(String name) {
        Module module = this.getModuleByName(name);
        if (module != null) {
            module.enable();
        }
    }

    public void disableModule(String name) {
        Module module = this.getModuleByName(name);
        if (module != null) {
            module.disable();
        }
    }

    public boolean isModuleEnabled(String name) {
        Module module = this.getModuleByName(name);
        return module != null && module.isOn();
    }

    public boolean isModuleEnabled(Class<Module> clazz) {
        Module module = this.getModuleByClass(clazz);
        return module != null && module.isOn();
    }

    public Module getModuleByDisplayName(String displayName) {
        for (Module module : this.modules) {
            if (!module.getDisplayName().equalsIgnoreCase(displayName)) continue;
            return module;
        }
        return null;
    }

    public ArrayList<Module> getEnabledModules() {
        ArrayList<Module> enabledModules = new ArrayList<>();
        for (Module module : this.modules) {
            if (!module.isEnabled()) continue;
            enabledModules.add(module);
        }
        return enabledModules;
    }

    public ArrayList<String> getEnabledModulesName() {
        ArrayList<String> enabledModules = new ArrayList<>();
        for (Module module : this.modules) {
            if (!module.isEnabled() || !module.isDrawn()) continue;
            enabledModules.add(module.getFullArrayString());
        }
        return enabledModules;
    }

    public ArrayList<Module> getModulesByCategory(Module.Category category) {
        ArrayList<Module> modulesCategory = new ArrayList<Module>();
        this.modules.forEach(module -> {
            if (module.getCategory() == category) {
                modulesCategory.add(module);
            }
        });
        return modulesCategory;
    }

    public List<Module.Category> getCategories() {
        return Arrays.asList(Module.Category.values());
    }

    public void onLoad() {
        this.modules.stream().filter(Module::listening).forEach(EVENT_BUS::register);
        this.modules.forEach(Module::onLoad);
    }

    public void onUpdate() {
        this.modules.stream().filter(Feature::isEnabled).forEach(Module::onUpdate);
    }

    public void onTick() {
        this.modules.stream().filter(Feature::isEnabled).forEach(Module::onTick);
    }

    public void onRender2D(Render2DEvent event) {
        this.modules.stream().filter(Feature::isEnabled).forEach(module -> module.onRender2D(event));
    }

    public void onRender3D(Render3DEvent event) {
        this.modules.stream().filter(Feature::isEnabled).forEach(module -> module.onRender3D(event));
    }

    public void sortModules(boolean reverse) {
        this.sortedModules = this.getEnabledModules().stream().filter(Module::isDrawn)
                .sorted(Comparator.comparing(module -> mc.textRenderer.getWidth(module.getFullArrayString()) * (reverse ? -1 : 1)))
                .collect(Collectors.toList());
    }

    public void sortModulesABC() {
        this.sortedModulesABC = new ArrayList<>(this.getEnabledModulesName());
        this.sortedModulesABC.sort(String.CASE_INSENSITIVE_ORDER);
    }

    public void onUnload() {
        this.modules.forEach(EVENT_BUS::unregister);
        this.modules.forEach(Module::onUnload);
    }

    public void onUnloadPost() {
        for (Module module : this.modules) {
            module.enabled.setValue(false);
        }
    }

    public void onKeyPressed(int eventKey) {
        if (eventKey <= 0) return;
        this.modules.forEach(module -> {
            if (module.getBind().getKey() == eventKey) {
                module.toggle();
            }
        });
    }

    @Override
    public JsonElement toJson() {
        JsonObject object = new JsonObject();
        for (Module module : modules) {
            object.add(module.getName(), module.toJson());
        }
        return object;
    }

    @Override
    public void fromJson(JsonElement element) {
        for (Module module : modules) {
            module.fromJson(element.getAsJsonObject().get(module.getName()));
        }
    }

    @Override
    public String getFileName() {
        return "modules.json";
    }
}