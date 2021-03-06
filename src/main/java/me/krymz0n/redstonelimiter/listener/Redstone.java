package me.krymz0n.redstonelimiter.listener;

import me.krymz0n.redstonelimiter.Main;
import me.krymz0n.redstonelimiter.util.Utils;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.world.ChunkLoadEvent;

public class Redstone implements Listener {
    private final Main plugin;

    public Redstone(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent evt) {
        if (plugin.getConfig().getBoolean("LimitRedstonePlaced")) {
            Chunk c = evt.getBlock().getChunk();
            Player p = evt.getPlayer();
            if (evt.getBlock().getType().equals(Material.REDSTONE_WIRE)) {
                if (plugin.checkChunk(Material.REDSTONE_WIRE, c) > plugin.getConfig().getInt("MaxRedstonePerChunk")) {
                    evt.setCancelled(true);
                    if (plugin.getConfig().getBoolean("Debug")) {
                        System.out.println(Utils.chat("&4RedstoneLimiter &0Prevented &4" + p.getName() + " &0from placing more redstone than allowed"));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent evt) {
        if (plugin.getConfig().getBoolean("LimitRedstoneOnChunkLoad") && !evt.isNewChunk()) {
            Chunk c = evt.getChunk();
            int cx = c.getX() << 4;
            int cz = c.getZ() << 4;
            for (int x = cx; x < cx + 16; x++) {
                for (int z = cz; z < cz + 16; z++) {
                    for (int y = 0; y < 255; y++) {
                        if (c.getBlock(x, y, z).getType().equals(Material.REDSTONE_WIRE) && plugin.checkChunk(Material.REDSTONE_WIRE, c) > plugin.getConfig().getInt("MaxRedstonePerChunk") && !(c.getBlock(x, y, z).getType() == null)) {
                            c.getBlock(x, y, z).setType(Material.AIR);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockRedstoneEvent(BlockRedstoneEvent evt) {
        if (plugin.getConfig().getBoolean("DisableRedstoneAtMaxPerChunk")) {
            Chunk c = evt.getBlock().getChunk();
            if (evt.getNewCurrent() > evt.getOldCurrent()) {
                if (plugin.checkChunk(Material.REDSTONE_WIRE, c) > plugin.getConfig().getInt("MaxRedstonePerChunk")) {
                    int current = evt.getOldCurrent();
                    evt.setNewCurrent(current);
                }
            }
        }
    }
}
