package dev.the456gamer.simplejump;

import java.util.List;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class SimpleJumpPlugin extends JavaPlugin {
    public void sendConfigMessage(Audience audience, String configName) {
        String configPath = "lang." + configName;

        String miniMessage = getConfig().getString(configPath);


        if (miniMessage == null) {
            getLogger().warning("Config message " + configPath + " not found");
            return;
        } else if (miniMessage.isEmpty()) {
            // skipping empty message
            return;
        }

        Component msg = MiniMessage.builder().build().deserialize(miniMessage);
        audience.sendMessage(msg);

    }

    @Override
    public void onEnable() {

        this.saveDefaultConfig();
        this.reloadConfig();

        getServer().getPluginManager().addPermission(new Permission("simplejump.simplejump",
            "Allows the player to use the simplejump command", PermissionDefault.OP));
        getServer().getPluginManager().addPermission(
            new Permission("simplejump.reload", "Allows an admin to reload the SJ config",
                PermissionDefault.OP));

        getServer().getCommandMap().register("simplejump", new SimpleJumpCommand(this));

        getServer().getCommandMap().register("simplejump",
            new Command("sjreload", "Reloads the SJ config", "/sjreload", List.of()) {

                {
                    this.setPermission("simplejump.reload");
                }

                @Override
                public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel,
                    @NotNull String[] args) {
                    SimpleJumpPlugin.this.saveDefaultConfig();
                    SimpleJumpPlugin.this.reloadConfig();
                    sendConfigMessage(sender, "reloaded-config");
                    return true;
                }
            });

        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
