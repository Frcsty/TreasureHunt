package com.github.frcsty.treasurehunt.util.action.actions.command;

import com.github.frcsty.treasurehunt.util.action.Action;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;

public final class PermissionCommand implements Action {
    private final Plugin plugin;

    public PermissionCommand(final Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getID() {
        return "permission";
    }

    @Override
    public void run(final Player player, final String data) {
        if (!data.contains(" ")) {
            return;
        }

        final String[] parts = data.split(" ", 2);
        Bukkit.getScheduler().runTask(plugin, () -> {
            if (player.hasPermission(parts[0])) {
                player.chat("/" + parts[1]);
                return;
            }

            final PermissionAttachment attachment = player.addAttachment(plugin, parts[0], true);
            player.chat("/" + parts[1]);
            player.removeAttachment(attachment);
        });
    }
}
