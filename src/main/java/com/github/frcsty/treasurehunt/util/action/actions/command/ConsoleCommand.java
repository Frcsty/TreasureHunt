package com.github.frcsty.treasurehunt.util.action.actions.command;

import com.github.frcsty.treasurehunt.util.action.Action;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class ConsoleCommand implements Action {
    private final Plugin plugin;

    public ConsoleCommand(final Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getID() {
        return "console";
    }

    @Override
    public void run(final Player player, final String data) {
        Bukkit.getScheduler().runTask(plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), data));
    }
}
