package me.alpha432.oyvey.util.octo;

import net.minecraft.util.Formatting;

import java.util.Random;
import java.util.regex.Pattern;

public class TextUtilOcto {
    public static final String BLACK = String.valueOf(Formatting.BLACK);
    public static final String DARK_BLUE = String.valueOf(Formatting.DARK_BLUE);
    public static final String DARK_GREEN = String.valueOf(Formatting.DARK_GREEN);
    public static final String DARK_AQUA = String.valueOf(Formatting.DARK_AQUA);
    public static final String DARK_RED = String.valueOf(Formatting.DARK_RED);
    public static final String DARK_PURPLE = String.valueOf(Formatting.DARK_PURPLE);
    public static final String GOLD = String.valueOf(Formatting.GOLD);
    public static final String GRAY = String.valueOf(Formatting.GRAY);
    public static final String DARK_GRAY = String.valueOf(Formatting.DARK_GRAY);
    public static final String BLUE = String.valueOf(Formatting.BLUE);
    public static final String GREEN = String.valueOf(Formatting.GREEN);
    public static final String AQUA = String.valueOf(Formatting.AQUA);
    public static final String RED = String.valueOf(Formatting.RED);
    public static final String LIGHT_PURPLE = String.valueOf(Formatting.LIGHT_PURPLE);
    public static final String YELLOW = String.valueOf(Formatting.YELLOW);
    public static final String WHITE = String.valueOf(Formatting.WHITE);
    public static final String OBFUSCATED = String.valueOf(Formatting.OBFUSCATED);
    public static final String BOLD = String.valueOf(Formatting.BOLD);
    public static final String STRIKE = String.valueOf(Formatting.STRIKETHROUGH);
    public static final String UNDERLINE = String.valueOf(Formatting.UNDERLINE);
    public static final String ITALIC = String.valueOf(Formatting.ITALIC);
    public static final String RESET = String.valueOf(Formatting.RESET);
    private static final Random rand = new Random();

    public static String stripColor(String input) {
        if (input != null) {
            return Pattern.compile("(?i)\u00a7[0-9A-FK-OR]").matcher(input).replaceAll("");
        }
        return "";
    }

    public static String coloredString(String string, Color color) {
        String coloredString = string;
        switch (color) {
            case AQUA: {
                coloredString = Formatting.AQUA + coloredString + Formatting.RESET;
                break;
            }
            case WHITE: {
                coloredString = Formatting.WHITE + coloredString + Formatting.RESET;
                break;
            }
            case BLACK: {
                coloredString = Formatting.BLACK + coloredString + Formatting.RESET;
                break;
            }
            case DARK_BLUE: {
                coloredString = Formatting.DARK_BLUE + coloredString + Formatting.RESET;
                break;
            }
            case DARK_GREEN: {
                coloredString = Formatting.DARK_GREEN + coloredString + Formatting.RESET;
                break;
            }
            case DARK_AQUA: {
                coloredString = Formatting.DARK_AQUA + coloredString + Formatting.RESET;
                break;
            }
            case DARK_RED: {
                coloredString = Formatting.DARK_RED + coloredString + Formatting.RESET;
                break;
            }
            case DARK_PURPLE: {
                coloredString = Formatting.DARK_PURPLE + coloredString + Formatting.RESET;
                break;
            }
            case GOLD: {
                coloredString = Formatting.GOLD + coloredString + Formatting.RESET;
                break;
            }
            case DARK_GRAY: {
                coloredString = Formatting.DARK_GRAY + coloredString + Formatting.RESET;
                break;
            }
            case GRAY: {
                coloredString = Formatting.GRAY + coloredString + Formatting.RESET;
                break;
            }
            case BLUE: {
                coloredString = Formatting.BLUE + coloredString + Formatting.RESET;
                break;
            }
            case RED: {
                coloredString = Formatting.RED + coloredString + Formatting.RESET;
                break;
            }
            case GREEN: {
                coloredString = Formatting.GREEN + coloredString + Formatting.RESET;
                break;
            }
            case LIGHT_PURPLE: {
                coloredString = Formatting.LIGHT_PURPLE + coloredString + Formatting.RESET;
                break;
            }
            case YELLOW: {
                coloredString = Formatting.YELLOW + coloredString + Formatting.RESET;
            }
        }
        return coloredString;
    }

    public static String cropMaxLengthMessage(String s, int i) {
        String output = "";
        if (s.length() >= 256 - i) {
            output = s.substring(0, 256 - i);
        }
        return output;
    }

    public static String generateRandomHexSuffix(int n) {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(Integer.toHexString((rand.nextInt() + 11) * rand.nextInt()).substring(0, n));
        sb.append(']');
        return sb.toString();
    }

    public static String transformPlainToFancy(String input) {
        String output = input.toLowerCase();
        output = output.replace("a", "\u00e1\u00b4\u20ac");
        output = output.replace("b", "\u00ca\u2122");
        output = output.replace("c", "\u00e1\u00b4\u201e");
        output = output.replace("d", "\u00e1\u00b4\u2026");
        output = output.replace("e", "\u00e1\u00b4\u2021");
        output = output.replace("f", "\u00d2\u201c");
        output = output.replace("g", "\u00c9\u00a2");
        output = output.replace("h", "\u00ca\u0153");
        output = output.replace("i", "\u00c9\u00aa");
        output = output.replace("j", "\u00e1\u00b4\u0160");
        output = output.replace("k", "\u00e1\u00b4\u2039");
        output = output.replace("l", "\u00ca\u0178");
        output = output.replace("m", "\u00e1\u00b4\ufffd");
        output = output.replace("n", "\u00c9\u00b4");
        output = output.replace("o", "\u00e1\u00b4\ufffd");
        output = output.replace("p", "\u00e1\u00b4\u02dc");
        output = output.replace("q", "\u00c7\u00ab");
        output = output.replace("r", "\u00ca\u20ac");
        output = output.replace("s", "\u00d1\u2022");
        output = output.replace("t", "\u00e1\u00b4\u203a");
        output = output.replace("u", "\u00e1\u00b4\u0153");
        output = output.replace("v", "\u00e1\u00b4\u00a0");
        output = output.replace("w", "\u00e1\u00b4\u00a1");
        output = output.replace("x", "\u00d1\u2026");
        output = output.replace("y", "\u00ca\ufffd");
        output = output.replace("z", "\u00e1\u00b4\u00a2");
        return output;
    }

    public static enum Color {
        NONE,
        WHITE,
        BLACK,
        DARK_BLUE,
        DARK_GREEN,
        DARK_AQUA,
        DARK_RED,
        DARK_PURPLE,
        GOLD,
        GRAY,
        DARK_GRAY,
        BLUE,
        GREEN,
        AQUA,
        RED,
        LIGHT_PURPLE,
        YELLOW;

    }
}