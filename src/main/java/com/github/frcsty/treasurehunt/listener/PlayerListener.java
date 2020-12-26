package com.github.frcsty.treasurehunt.listener;

import com.github.frcsty.treasurehunt.TreasureHuntPlugin;
import com.github.frcsty.treasurehunt.game.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerListener implements Listener {

    private final TreasureHuntPlugin plugin;
    public PlayerListener(final TreasureHuntPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final GameManager manager = plugin.getManager();
        final Player player = event.getPlayer();

        if (manager.isGameStarted()) {
            manager.teleportToLobby(player);
            return;
        }

        manager.addPlayer(player);
    }

}
