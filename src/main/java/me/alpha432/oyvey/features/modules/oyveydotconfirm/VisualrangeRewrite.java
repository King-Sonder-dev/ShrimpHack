package me.alpha432.oyvey.features.modules.oyveydotconfirm;


import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.event.impl.EntityAddedEvent;
import me.alpha432.oyvey.event.impl.EntityRemovedEvent;
import me.alpha432.oyvey.features.settings.Setting;
import me.alpha432.oyvey.util.ChatUtil;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

import static me.alpha432.oyvey.features.commands.Command.sendMessage;

public class VisualrangeRewrite extends Module {
    private static final ArrayList<String> entities = new ArrayList<>();
    private final Setting<Boolean> leave = register(new Setting<>("Leave",true));
    private final Setting<Boolean> enter = register(new Setting<>("Enter",true));


    private final Setting<Boolean> friends = register(new Setting<>("Friend",true));
    private final Setting<Boolean> soundpl = register(new Setting<>("Sound",true));
    private final Setting<Mode> mode = register(new Setting<>("Mode",Mode.Chat));
    public VisualrangeRewrite() {super("VisualRangeRewrite", "", Module.Category.OYVEYDOTCONFIRM, true, false, false);
    }

    @EventHandler
    public void EntityAddedEvent(EntityAddedEvent event) {
        if (!isValid(event.getEntity())) return;

        if (!entities.contains(event.getEntity().getName().getString()))
            entities.add(event.getEntity().getName().getString());
        else return;

        if (enter.getValue()) notify(event.getEntity(), true);
    }

    @EventHandler
    public void EntityAddedEvent(EntityRemovedEvent event) {
        if (!isValid(event.entity)) return;

        if (entities.contains(event.entity.getName().getString()))
            entities.remove(event.entity.getName().getString());
        else return;

        if (leave.getValue()) notify(event.entity, false);
    }

    public void notify(Entity entity, boolean enter) {
        String message = "";
        if (OyVey.friendManager.isFriend(entity.getName().getString()))
            message = Formatting.AQUA + entity.getName().getString();
        else message = Formatting.GRAY + entity.getName().getString();

        if (enter) message += Formatting.GREEN + " was found!";
        else message += Formatting.RED + " left to X:" + (int)entity.getX() + " Z:" + (int) entity.getZ();




        if (soundpl.getValue()) {
            try {
                if (enter)
                    mc.world.playSound(mc.player, mc.player.getBlockPos(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.BLOCKS, 1f, 1f);
                else
                    mc.world.playSound(mc.player, mc.player.getBlockPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 1f, 1f);
            } catch (Exception ignored) {
            }
        }
    }

    public boolean isValid(Entity entity) {
        if (!(entity instanceof PlayerEntity)) return false;
        return entity != mc.player && (!OyVey.friendManager.isFriend(entity.getName().getString()) || friends.getValue());
    }

    public enum Mode {
        Chat
    }
}
