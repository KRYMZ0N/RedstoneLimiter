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
        System.out.println("Enabling!");

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);
        pm.registerEvents(new Redstone(this), this);

    }

    @Override
    public void onDisable() {
        System.out.println("Disabling!");
    }

    public int count(Chunk c, Material m) {
        int count = 0;
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 256; y++) {
                for (int z = 0; z < 16; z++) {
                    if (c.getBlock(x, y , z).getType() == m) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
