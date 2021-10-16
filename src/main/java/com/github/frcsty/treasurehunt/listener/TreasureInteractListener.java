package com.github.frcsty.treasurehunt.listener;

import com.github.frcsty.treasurehunt.game.GameController;
import com.github.frcsty.treasurehunt.game.GameState;
import com.github.frcsty.treasurehunt.message.MessageHandler;
import com.github.frcsty.treasurehunt.treasure.TreasureController;
import com.github.frcsty.treasurehunt.user.UserController;
import com.github.frcsty.treasurehunt.util.holder.TreasureHolder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public final class TreasureInteractListener implements Listener {

    private final GameController controller;

    public TreasureInteractListener(final GameController controller) {
        this.controller = controller;
    }

    @EventHandler
    public void onTreasureInteract(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final Block block = event.getClickedBlock();

        if (block == null) return;
        if (block.getType() != Material.PLAYER_HEAD) return;

        if (!GameState.isInMotion()) return;

        final Location location = block.getLocation();
        final TreasureController treasureController = controller.getTreasureController();
        if (treasureController.getTreasureByLocation(location) == null) return;

        event.setCancelled(true);

        block.setType(Material.AIR);
        treasureController.addRandomTreasure();

        final TreasureHolder treasure = treasureController.removeTreasureByLocation(location);
        final UserController userController = controller.getUserController();
        userController.incrementTreasureCountForUser(player, treasure.getTreasureType());
        MessageHandler.TREASURE_FOUND.executeForPlayer(player,
                "{player-name}", player.getName(),
                "{treasure-rarity}", treasure.getFormattedType(),
                "{treasure-points}", String.valueOf(treasure.getTreasureType().getPoints()),
                "{user-points}", String.valueOf(userController.getPointsForUser(player)),
                "{treasure-location}", String.format("%s %s %s", location.getBlockX(), location.getBlockY(), location.getBlockZ()),
                "{treasure-location-stringed}", String.format("%s;%s;%s", location.getBlockX(), location.getBlockY(), location.getBlockZ())
        );
    }

}
