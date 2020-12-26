package com.github.frcsty.treasurehunt.listener;

import com.github.frcsty.treasurehunt.TreasureHuntPlugin;
import com.github.frcsty.treasurehunt.game.GameManager;
import com.github.frcsty.treasurehunt.game.LocationHolder;
import com.github.frcsty.treasurehunt.user.TreasurePlayerProfile;
import com.github.frcsty.treasurehunt.utils.Message;
import com.github.frcsty.treasurehunt.utils.Replace;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public final class TreasureOpenListener implements Listener {

    private final TreasureHuntPlugin plugin;

    public TreasureOpenListener(final TreasureHuntPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChestInteract(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final Block block = event.getClickedBlock();

        if (block == null) return;
        if (block.getType() != Material.CHEST) return;

        final GameManager manager = plugin.getManager();
        if (!manager.isGameStarted()) return;
        if (manager.getTreasureByLocation(createLocationHolder(block.getLocation())) == null) {
            System.out.println("Not a valid chest");
            return;
        }

        event.setCancelled(true);
        block.setType(Material.AIR);
        manager.spawnRandomTreasure();

        Message.broadcast(Replace.replaceString(
                plugin.getConfig().getString("message.playerFoundTreasure"),
                "{player-name}", player.getName()
        ));

        manager.removeTreasure(createLocationHolder(block.getLocation()));
        final TreasurePlayerProfile profile = manager.getProfile(player);
        profile.incrementTreasuresFound();

        manager.getJoinedPlayers().put(player.getUniqueId(), profile);
    }

    private LocationHolder createLocationHolder(final Location location) {
        return new LocationHolder(
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ()
        );
    }

}
