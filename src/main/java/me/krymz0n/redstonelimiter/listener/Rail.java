package me.krymz0n.redstonelimiter.listener;

import me.krymz0n.redstonelimiter.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;

public class Rail implements Listener {
    private final HashSet<Location> clock = new HashSet<>();
    private final Main plugin;

    public Rail(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onRedstone(BlockRedstoneEvent evt) {
        Location loc = evt.getBlock().getLocation();
        if (plugin.getConfig().getBoolean("PreventRailUpdateLag"))
        if (evt.getBlock().getType() == Material.POWERED_RAIL || evt.getBlock().getType() == Material.ACTIVATOR_RAIL || evt.getBlock().getType() == Material.DETECTOR_RAIL) {
            if (clock.contains(loc)) {
                new BukkitRunnable() {
                    public void run() {
                            evt.getBlock().breakNaturally();
                    }
                }.runTask(plugin);
            } else {
                clock.add(loc);
                Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> clock.remove(loc), plugin.getConfig().getInt("DelayOfRailClock") * 20L);
            }
        }
    }
}
