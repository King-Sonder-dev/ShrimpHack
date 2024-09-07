package aids.dev.shrimphack.features.modules.misc;

import aids.dev.shrimphack.features.commands.Command;
import aids.dev.shrimphack.features.modules.Module;
import aids.dev.shrimphack.features.settings.Setting;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

public class GhastSpawnNotifier extends Module {

    public Setting<Boolean> playSound = this.register(new Setting<>("PlaySound", true));
    public Setting<Boolean> makeGhastGlow = this.register(new Setting<>("MakeGhastGlow", true));

    public GhastSpawnNotifier() {
        super("GhastFinder", "Notifies when a Ghast spawns.", Category.MISC, true, false, false);
    }

    @Override
    public void onEnable() {
        // Register the event listener
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof GhastEntity) {
                onGhastSpawn((GhastEntity) entity);
            }
        });
    }

    private void onGhastSpawn(GhastEntity ghast) {
        Vec3d position = ghast.getPos();
        long roundedX = Math.round(position.x);
        long roundedY = Math.round(position.y);
        long roundedZ = Math.round(position.z);
        Command.sendMessage("Ghast Detected at: " + roundedX + "x, " + roundedY + "y, " + roundedZ + "z.");

        if (playSound.getValue()) {
            // Play the sound
            MinecraftClient.getInstance().getSoundManager().play((SoundInstance) SoundEvents.ENTITY_GHAST_AMBIENT);
        }

        if (makeGhastGlow.getValue()) {
            // Make the Ghast glow
            ghast.setGlowing(true);
        }
    }
}