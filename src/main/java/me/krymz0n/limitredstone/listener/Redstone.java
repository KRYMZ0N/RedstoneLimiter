package me.krymz0n.limitredstone.listener;

import me.krymz0n.limitredstone.Main;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.world.ChunkLoadEvent;

public class Redstone implements Listener {
    private final Main plugin;

    public Redstone(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent evt) {
        Chunk c = evt.getChunk();
        int cx = c.getX() << 4;
        int cz = c.getZ() << 4;
        for (int x = cx; x < cx + 16; x++) {
            for (int z = cz; z < cz + 16; z++) {
                for (int y = 0; y < 255; y++) {
                    if (plugin.getConfig().getBoolean("LimitRedstoneOnChunkLoad") && !evt.isNewChunk() && !(c.getBlock(x, y, z) == null)) {
                        for (int i = plugin.count(c, Material.REDSTONE); i > plugin.getConfig().getInt("MaxRedstonePerChunk"); i++) {
                            c.getBlock(x, y, z).setType(Material.AIR);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent evt) {
        Chunk c = evt.getBlock().getChunk();
        if (plugin.getConfig().getBoolean("LimitRedstonePlaced")) {
            if (evt.getBlock().getType() == (Material.REDSTONE) && !(evt.getBlock() == null)) {
                if (plugin.count(c, Material.REDSTONE) > plugin.getConfig().getInt("MaxRedstonePerChunk")) {
                    evt.setCancelled(true);
                }
            }
        }
    }
}
