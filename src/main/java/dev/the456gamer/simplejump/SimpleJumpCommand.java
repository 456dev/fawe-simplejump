package dev.the456gamer.simplejump;

import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.util.Location;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

class SimpleJumpCommand extends Command {

    private final SimpleJumpPlugin plugin;

    {
        this.setPermission("simplejump.simplejump");
    }

    public SimpleJumpCommand(SimpleJumpPlugin plugin) {
        super("simplejump", "Jump to the block you're looking at", "/simplejump", List.of());
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel,
        @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            plugin.sendConfigMessage(sender, "not-player");
            return true;
        }

        // convert bukkit player to worldedit player
        com.sk89q.worldedit.entity.Player wePlayer = new BukkitPlayer(player);
        Location targetPos = wePlayer.getBlockTrace(plugin.getConfig().getInt("options.range"));
        if (targetPos == null) {
            plugin.sendConfigMessage(sender, "not-looking-at-block");
        } else {
            wePlayer.findFreePosition(targetPos);
            plugin.sendConfigMessage(sender, "jump-success");
        }
        return true;
    }
}
