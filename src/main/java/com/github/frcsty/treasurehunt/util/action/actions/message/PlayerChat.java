package com.github.frcsty.treasurehunt.util.action.actions.message;

import com.github.frcsty.treasurehunt.util.action.Action;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class PlayerChat implements Action {
    private final Plugin plugin;

    public PlayerChat(final Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getID() {
        return "chat";
    }

    @Override
    public void run(final Player player, final String data) {
        Bukkit.getScheduler().runTask(plugin, () -> player.chat(data));
    }
}
