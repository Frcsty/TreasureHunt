package com.github.frcsty.treasurehunt.listener;

import com.github.frcsty.treasurehunt.game.GameController;
import com.github.frcsty.treasurehunt.game.GameState;
import com.github.frcsty.treasurehunt.util.settings.MapSettings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerListener implements Listener {

    private final GameController controller;

    public PlayerListener(final GameController controller) {
        this.controller = controller;
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        if (!controller.getUserController().isJoined(player)) {
            controller.getUserController().addUser(player);
        }

        player.teleport(MapSettings.getMapLobbyLocation());
    }

}
