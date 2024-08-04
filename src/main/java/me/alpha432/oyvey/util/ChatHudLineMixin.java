/*
 * This file is part of the oyvey Client distribution (https://github.com/oyveyDevelopment/oyvey-client).
 * Copyright (c) oyvey Development.
 */

package me.alpha432.oyvey.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = ChatHudLine.class)
public abstract class ChatHudLineMixin implements IChatHudLine {
    @Shadow @Final private Text content;
    @Unique private int id;
    @Unique private GameProfile sender;

    @Override
    public String oyvey$getText() {
        return content.getString();
    }

    @Override
    public int oyvey$getId() {
        return id;
    }

    @Override
    public void oyvey$setId(int id) {
        this.id = id;
    }

    @Override
    public GameProfile oyvey$getSender() {
        return sender;
    }

    @Override
    public void oyvey$setSender(GameProfile profile) {
        sender = profile;
    }
}