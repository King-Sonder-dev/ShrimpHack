package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.commands.Command;
import me.alpha432.oyvey.features.settings.Setting;
import net.minecraft.util.Formatting;

public class CAMessage extends Module {
    
    enum ClientName {Future, Gamesense, Impact, Kamiblue, Konas, Lambda, Newbase, Opfern, Oyvey, Phobos, Pyro, Rusherhack, Snow, Viknet, Wurstplus2}
    Setting<ClientName> clientname = register(new Setting("Client", ClientName.Future));
    
    public CAMessage() {
        super("CAMessage", "shows notifications for different CAs", Category.COMBAT, true, false, false);
    }

    @Override
    public void onEnable() {
        String onMessage = "";
        switch (clientname.getValue()) {
            case Future: {
                onMessage = Formatting.RED + "[Future] " + Formatting.GRAY + "AutoCrystal toggled " + Formatting.GREEN + "on" + Formatting.GRAY + ".";
                break;
            }
            case Gamesense: {
                onMessage = Formatting.GRAY + "[" + Formatting.WHITE + "Game" + Formatting.GREEN + "Sense" + Formatting.GRAY + "] " + Formatting.GREEN + "AutoCrystal turned ON!";
                break;
            }
            case Impact: {
                onMessage = Formatting.DARK_GRAY + "[" + Formatting.BLUE + "Impact" + Formatting.DARK_GRAY + "] " + Formatting.GRAY + "Toggled " + Formatting.BLUE + "Crystal Aura " + Formatting.GRAY + "[" + Formatting.GREEN + "ON" + Formatting.GRAY + "]";
                break;
            }
            case Kamiblue: {
                onMessage = Formatting.GRAY + "[" + Formatting.BLUE + "\u30ab\u30df\u30d6\u30eb" + Formatting.GRAY + "] " + Formatting.WHITE + "CrystalAura " + Formatting.GREEN + "enabled";
                break;
            }
            case Konas: {
                onMessage = Formatting.DARK_GRAY + "[" + Formatting.LIGHT_PURPLE + "Konas" + Formatting.DARK_GRAY + "] " + Formatting.WHITE + "AutoCrystal has been enabled";
                break;
            }
            case Lambda: {
                onMessage = Formatting.GRAY + "[" + Formatting.BLUE + "\u03bb" + Formatting.GRAY + "] " + Formatting.WHITE + "CrystalAura " + Formatting.GREEN + "enabled";
                break;
            }
            case Newbase: {
                onMessage = Formatting.BOLD + "AutoCrystal " + Formatting.RESET + Formatting.GREEN + "enabled.";
                break;
            }
            case Opfern: {
                onMessage = Formatting.GRAY + "[" + Formatting.RED + "\u1d0f\u1d18\ua730\u1d07\u0280\u0274" + Formatting.GRAY + "] " + Formatting.RED + "AutoCrystal " + Formatting.GREEN + "Enabled!";
                break;
            }
            case Oyvey: {
                onMessage = Formatting.BLUE + "<OyVey> " + Formatting.GREEN + "AutoCrystal toggled on.";
                break;
            }
            case Phobos: {
                onMessage = Formatting.BLUE + "<Phobos.eu> " + Formatting.GREEN + "AutoCrystal enabled.";
                break;
            }
            case Pyro: {
                onMessage = Formatting.DARK_RED + "" + Formatting.BOLD + "[" + Formatting.RESET + "" + Formatting.DARK_RED + "Pyro" + Formatting.BOLD + "] " + Formatting.RESET + "" + Formatting.GREEN + "AutoCrystal has been enabled.";
                break;
            }
            case Rusherhack: {
                onMessage = Formatting.WHITE + "[" + Formatting.GREEN + "rusherhack" + Formatting.WHITE + "] AutoCrystal has been enabled";
                break;
            }
            case Snow: {
                onMessage = Formatting.BLUE + "[" + Formatting.AQUA + "Snow" + Formatting.BLUE + "] [" + Formatting.DARK_AQUA + "SnowAura" + Formatting.BLUE + "] " + Formatting.GREEN + "enabled";
                break;
            }
            case Viknet: {
                onMessage = Formatting.GRAY + "VikNet " + Formatting.DARK_GRAY + "\u27ab " + Formatting.WHITE + "VikNetAura " + Formatting.DARK_GREEN + "ON";
                break;
            }
            case Wurstplus2: {
                onMessage = Formatting.GOLD + "Wurst+ 2 " + Formatting.GRAY + "> " + Formatting.WHITE + "we " + Formatting.GREEN + "gaming";
                break;
            }
        }
        Command.sendSilentMessage(onMessage);
    }
    
    @Override
    public void onDisable() {
        String offMessage = "";
        switch (clientname.getValue()) {
            case Future: {
                offMessage = Formatting.RED + "[Future] " + Formatting.GRAY + "AutoCrystal toggled " + Formatting.RED + "off" + Formatting.GRAY + ".";
                break;
            }
            case Gamesense: {
                offMessage = Formatting.GRAY + "[" + Formatting.WHITE + "Game" + Formatting.GREEN + "Sense" + Formatting.GRAY + "] " + Formatting.RED + "AutoCrystal turned OFF!";
                break;
            }
            case Impact: {
                offMessage = Formatting.DARK_GRAY + "[" + Formatting.BLUE + "Impact" + Formatting.DARK_GRAY + "] " + Formatting.GRAY + "Toggled " + Formatting.BLUE + "Crystal Aura " + Formatting.GRAY + "[" + Formatting.RED + "OFF" + Formatting.GRAY + "]";
                break;
            }
            case Kamiblue: {
                offMessage = Formatting.GRAY + "[" + Formatting.BLUE + "\u30ab\u30df\u30d6\u30eb" + Formatting.GRAY + "] " + Formatting.WHITE + "CrystalAura " + Formatting.RED + "disabled";
                break;
            }
            case Konas: {
                offMessage = Formatting.DARK_GRAY + "[" + Formatting.LIGHT_PURPLE + "Konas" + Formatting.DARK_GRAY + "] " + Formatting.WHITE + "AutoCrystal has been disabled";
                break;
            }
            case Lambda: {
                offMessage = Formatting.GRAY + "[" + Formatting.BLUE + "\u03bb" + Formatting.GRAY + "] " + Formatting.WHITE + "CrystalAura " + Formatting.RED + "disabled";
                break;
            }
            case Newbase: {
                offMessage = Formatting.BOLD + "AutoCrystal " + Formatting.RESET + Formatting.RED + "disabled.";
                break;
            }
            case Opfern: {
                offMessage = Formatting.GRAY + "[" + Formatting.RED + "\u1d0f\u1d18\ua730\u1d07\u0280\u0274" + Formatting.GRAY + "] " + Formatting.RED + "AutoCrystal Disabled!";
                break;
            }
            case Oyvey: {
                offMessage = Formatting.BLUE + "<OyVey> " + Formatting.RED + "AutoCrystal toggled off.";
                break;
            }
            case Phobos: {
                offMessage = Formatting.BLUE + "<Phobos.eu> " + Formatting.RED + "AutoCrystal disabled.";
                break;
            }
            case Pyro: {
                offMessage = Formatting.DARK_RED + "" + Formatting.BOLD + "[" + Formatting.RESET + "" + Formatting.DARK_RED + "Pyro" + Formatting.BOLD + "] " + Formatting.RESET + "" + Formatting.RED + "AutoCrystal has been disabled.";
                break;
            }
            case Rusherhack: {
                offMessage = Formatting.WHITE + "[" + Formatting.GREEN + "rusherhack" + Formatting.WHITE + "] AutoCrystal has been disabled";
                break;
            }
            case Snow: {
                offMessage = Formatting.BLUE + "[" + Formatting.AQUA + "Snow" + Formatting.BLUE + "] [" + Formatting.DARK_AQUA + "SnowAura" + Formatting.BLUE + "] " + Formatting.RED + "disabled";
                break;
            }
            case Viknet: {
                offMessage = Formatting.GRAY + "VikNet " + Formatting.DARK_GRAY + "\u27ab " +  Formatting.WHITE + "VikNetAura " + Formatting.DARK_RED + "OFF";
                break;
            }
            case Wurstplus2: {
                offMessage = Formatting.GOLD + "Wurst+ 2 " + Formatting.GRAY + "> " + Formatting.WHITE + "we aint " + Formatting.RED + "gaming " + Formatting.WHITE + "no more";
                break;
            }
        }
        Command.sendSilentMessage(offMessage);
    }
} 