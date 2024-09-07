package aids.dev.shrimphack.util.Misc;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;

public class UUIDUtil {

    public static String getPlayerUUID() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            GameProfile profile = client.player.getGameProfile();
            if (profile != null) {
                return profile.getId().toString(); // Returns UUID as a String
            }
        }
        return "Player not found";
    }
}
