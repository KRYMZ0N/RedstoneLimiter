package me.krymz0n.redstonelimiter;

import me.krymz0n.redstonelimiter.command.Reload;
import me.krymz0n.redstonelimiter.listener.Redstone;
import me.krymz0n.redstonelimiter.listener.Rail;
import me.krymz0n.redstonelimiter.util.Utils;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {


    @Override
    public void onEnable() {
        saveDefaultConfig();
        System.out.println(Utils.chat("&4Enabling RedstoneLimiter &0v" + this.getDescription().getVersion()));
        //Event Registrations!
        System.out.println(Utils.chat("&4Registering Events"));
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);
        pm.registerEvents(new Redstone(this), this);
        pm.registerEvents(new Rail(this), this);
        getCommand("rlimiter").setExecutor(new Reload(this));

    }

    @Override
    public void onDisable() {
        System.out.println("&4Disabling RedstoneLimiter &0v" + this.getDescription().getVersion());
    }

    public Integer checkChunk(Material m, Chunk c) {
        int count = 0;
        int cx = c.getX() << 4;
        int cz = c.getZ() << 4;

        for (int x = cx; x < cx + 16; x++) {
            for (int z = cz; z < cz + 16; z++) {
                for (int y = 0; y < 256; y++) {
                    if (c.getBlock(x, y, z).getType() == m) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}

