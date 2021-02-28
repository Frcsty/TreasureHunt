package com.github.frcsty.treasurehunt.treasure;

import com.github.frcsty.treasurehunt.treasure.location.LocationProvider;
import com.github.frcsty.treasurehunt.treasure.texture.TextureController;
import com.github.frcsty.treasurehunt.treasure.type.TreasureType;
import com.github.frcsty.treasurehunt.util.holder.LocationHolder;
import com.github.frcsty.treasurehunt.util.holder.TreasureHolder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

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

    public Map<LocationHolder, TreasureHolder> getTreasures() {
        return this.treasures;
    }
}
