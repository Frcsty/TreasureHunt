package com.github.frcsty.treasurehunt.util.action.actions.command;

import com.github.frcsty.treasurehunt.util.action.Action;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class PlayerCommand implements Action {
    private final Plugin plugin;

    public PlayerCommand(final Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getID() {
        return "player";
    }

    @Override
    public void run(final Player player, final String data) {
        Bukkit.getScheduler().runTask(plugin, () -> player.chat("/" + data));
    }
}
