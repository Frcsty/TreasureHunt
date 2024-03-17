package com.github.frcsty.treasurehunt.treasure;

import com.github.frcsty.treasurehunt.TreasureHuntPlugin;
import com.github.frcsty.treasurehunt.treasure.location.LocationProvider;
import com.github.frcsty.treasurehunt.treasure.texture.TextureController;
import com.github.frcsty.treasurehunt.treasure.type.TreasureType;
import com.github.frcsty.treasurehunt.util.holder.LocationHolder;
import com.github.frcsty.treasurehunt.util.holder.TreasureHolder;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public final class TreasureController {

    private final Map<LocationHolder, TreasureHolder> treasures = new HashMap<>();

    public void addRandomTreasure() {
        final Location location = LocationProvider.getRandomLocation();
        final TreasureType.TreasureSubType type = TreasureType.getRandomTreasureType();

        final Block treasureBlock = TextureController.getTexturedBlock(location, type);

        this.treasures.put(
                new LocationHolder(location.getBlockX(), location.getBlockY(), location.getBlockZ()),
                new TreasureHolder(treasureBlock, type)
        );
    }

    public void addInitialTreasures(final int amount) {
        for (int i = 1; i <= amount; i++) {
            addRandomTreasure();
        }
    }

    public TreasureHolder removeTreasureByLocation(final Location location) {
        final LocationHolder holder = new LocationHolder(
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ()
        );

        return this.treasures.remove(holder);
    }

    public TreasureHolder getTreasureByLocation(final Location location) {
        final LocationHolder holder = new LocationHolder(
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ()
        );

        for (final LocationHolder locationHolder : this.treasures.keySet()) {
            if (locationHolder.hashCode() == holder.hashCode()) {
                return this.treasures.get(locationHolder);
            }
        }

        return null;
    }

    public void clearTreasures() {
        this.treasures.forEach((loc, treasure) ->
                treasure.getBlock().setType(Material.AIR)
        );

        this.treasures.clear();
    }

    public void initializeNotification(final TreasureHuntPlugin plugin) {
        final Map<LocationHolder, TreasureHolder> current = getTreasures();

        new BukkitRunnable() {
            @Override
            public void run() {
                getTreasures().forEach((location, treasure) -> {
                    if (current.get(location) == null) {
                        return;
                    }

                    treasure.getBlock().getWorld().spawnEntity(
                            treasure.getBlock().getLocation(),
                            EntityType.FIREWORK, CreatureSpawnEvent.SpawnReason.BUILD_SNOWMAN,
                            entity -> {
                                final Firework firework = (Firework) entity;

                                firework.getFireworkMeta().addEffect(FireworkEffect
                                        .builder()
                                        .trail(true)
                                        .withColor(Color.ORANGE)
                                        .with(FireworkEffect.Type.CREEPER)
                                        .build()
                                );
                            });
                });
            }
        }.runTaskTimer(plugin, 0, 20 * 60);
    }

    public Map<LocationHolder, TreasureHolder> getTreasures() {
        return this.treasures;
    }
}
