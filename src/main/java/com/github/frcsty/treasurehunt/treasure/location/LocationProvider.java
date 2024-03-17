package com.github.frcsty.treasurehunt.treasure.location;

import com.github.frcsty.treasurehunt.util.settings.MapSettings;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Set;
import java.util.SplittableRandom;

public final class LocationProvider {

    private static final Set<String> BLACKLISTED = Set.of(
            "water", "leaves", "stair", "wall", "slab", "wool"
    );

    private static final SplittableRandom RANDOM = new SplittableRandom();

    public static Location getRandomLocation() {
        final Location location = MapSettings.getMapStartingLocation(MapSettings.getMapWorld());

        final int y = RANDOM.nextInt(MapSettings.getMapHeightMin(), MapSettings.getMapHeightMax());
        final int x = location.getBlockX() + RANDOM.nextInt(-MapSettings.getMapRadiusX(), MapSettings.getMapRadiusX());
        final int z = location.getBlockZ() + RANDOM.nextInt(-MapSettings.getMapRadiusZ(), MapSettings.getMapRadiusZ());

        final Location result = new Location(location.getWorld(), x, MapSettings.getMapHeightMax(), z);
        for (int offsetY = y; offsetY >= MapSettings.getMapHeightMin(); offsetY--) {
            final Block block = location.getWorld().getBlockAt(x, offsetY, z);

            if (block.getType() == Material.AIR) {
                continue;
            }

            result.setY(offsetY + 1);
            break;
        }

        if (!aboveCheck(result)) {
            return getRandomLocation();
        }

        if (belowCheck(result)) {
            return getRandomLocation();
        }

        if (result.getWorld().getBlockAt(result).getType() != Material.AIR) {
            return getRandomLocation();
        }

        if (result.getWorld().getBlockAt(result.clone().add(0, -1, 0)).getType() == Material.AIR) {
            return getRandomLocation();
        }

        return result;
    }

    private static boolean aboveCheck(final Location location) {
        final Block above = location.getWorld().getBlockAt(location.clone().add(0, 1, 0));

        return above.getType() == Material.AIR;
    }

    private static boolean belowCheck(final Location location) {
        final Block below = location.getWorld().getBlockAt(location.clone().add(0, -1, 0));

        boolean result = false;
        for (final String material : BLACKLISTED) {
            if (!below.getType().name().contains(material.toUpperCase())) {
                continue;
            }

            result = true;
            break;
        }

        return result;
    }

}
