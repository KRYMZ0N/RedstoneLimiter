package me.krymz0n.limitredstone.listener;

import me.krymz0n.limitredstone.Main;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

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
                        System.out.println("Prevented" + p.getName() + " from placing more redstone than allowed");
                    }
                }
            }
        }
    }
}
