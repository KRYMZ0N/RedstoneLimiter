package me.krymz0n.limitredstone;

import me.krymz0n.limitredstone.listener.Redstone;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {


    @Override
    public void onEnable() {
        saveDefaultConfig();
        System.out.println("Enabling!");

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);
        pm.registerEvents(new Redstone(this), this);

    }

    @Override
    public void onDisable() {
        System.out.println("Disabling!");
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

