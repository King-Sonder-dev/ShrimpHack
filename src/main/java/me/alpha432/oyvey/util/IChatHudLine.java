

package me.alpha432.oyvey.util;

import com.mojang.authlib.GameProfile;

public interface IChatHudLine {
    String oyvey$getText();

    int oyvey$getId();

    void oyvey$setId(int id);

    GameProfile oyvey$getSender();

    void oyvey$setSender(GameProfile profile);
}