package me.alpha432.oyvey.features.modules.combat;

import com.google.common.eventbus.Subscribe;
import me.alpha432.oyvey.event.impl.PacketEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public class CrystalPredict extends Module {

    public Setting<Boolean> holding = this.register(new Setting<Boolean>("Holding", Boolean.valueOf(true), "only works when youre holding a crystal"));
    public Setting<Integer> offset = this.register(new Setting<Integer>("Offset", 1, 1, 20));
    public Setting<Integer> amount = this.register(new Setting<Integer>("Amount", 1, 1, 20));

    public CrystalPredict() {
        super("CrystalPredict", "fast crystal", Category.COMBAT, true, false, false);
    }
    
    @Subscribe
    public void onPacketSend(PacketEvent event) {
        if (!(event.getPacket() instanceof PlayerInteractBlockC2SPacket)) return;

        int id = 0;
        PlayerInteractBlockC2SPacket  packet = (PlayerInteractBlockC2SPacket ) event.getPacket();
        BlockPos position = packet.getBlockHitResult().getBlockPos();

        assert mc.world != null;
        if (!(mc.world.getBlockState(position).getBlock() == Blocks.BEDROCK || mc.world.getBlockState(position).getBlock() == Blocks.OBSIDIAN)) return;

        if (holding.getValue() && !(mc.player.getMainHandStack().getItem() == Items.END_CRYSTAL || mc.player.getOffHandStack().getItem() == Items.END_CRYSTAL)) return;
        
        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof EndCrystalEntity) {
                if (entity.getId() > id) id = entity.getId();
            }
        }
        for (int i = offset.getValue(); i < offset.getValue() + amount.getValue(); i++) {
            PlayerInteractEntityC2SPacket attackPacket = PlayerInteractEntityC2SPacket.attack(Objects.requireNonNull(mc.world.getEntityById(id + i)), mc.player.isSneaking());
            mc.player.networkHandler.sendPacket(attackPacket);
        }
    }

    @Override
    public String getDisplayInfo() {
        return offset.getValue() + ", " + amount.getValue();
    }
} 