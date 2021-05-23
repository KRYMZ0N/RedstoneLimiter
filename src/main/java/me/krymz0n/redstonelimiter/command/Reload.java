package me.krymz0n.redstonelimiter.command;

import me.krymz0n.redstonelimiter.Main;
import me.krymz0n.redstonelimiter.util.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reload implements CommandExecutor {
    private final Main plugin;

    public Reload(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args[0].toLowerCase().startsWith("reload")) {
            if (sender.isOp() || (!(sender instanceof Player))) {
                plugin.reloadConfig();
                sender.sendMessage(Utils.chat("&4RedstoneLimiter Config Reloaded!"));
                return true;
            } else {
                sender.sendMessage(Utils.chat("&cYou do not have permission to execute this command!"));
            }
        }
        if (args[0].toLowerCase().startsWith("ver")) {
            sender.sendMessage(Utils.chat( "&4This server is currently running HelpPlugin &0v&4 " + plugin.getDescription().getVersion()));
        }
        return false;
    }
}
