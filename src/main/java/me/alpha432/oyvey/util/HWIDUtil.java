package me.alpha432.oyvey.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.NetworkInterface;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HWIDUtil {
    private static final String GITHUB_URL = "https://raw.githubusercontent.com/King-Sonder-dev/Oyvey-Hwid/main/Hwid%20list";

    public static String getHWID() {
        try {
            StringBuilder sb = new StringBuilder();
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface ni : interfaces) {
                byte[] mac = ni.getHardwareAddress();
                if (mac != null) {
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X", mac[i]));
                    }
                }
            }
            return hashString(sb.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String hashString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Set<String> getAuthorizedHWIDs() {
        Set<String> hwids = new HashSet<>();
        try {
            URL url = new URL(GITHUB_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            Pattern pattern = Pattern.compile("\"([a-fA-F0-9]{64})\"\\s*=\\s*\\S+");
            while ((inputLine = in.readLine()) != null) {
                Matcher matcher = pattern.matcher(inputLine.trim());
                if (matcher.matches()) {
                    hwids.add(matcher.group(1));
                }
            }
            in.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch authorized HWIDs from Pastebin", e);
        }
        return hwids;
    }
}
