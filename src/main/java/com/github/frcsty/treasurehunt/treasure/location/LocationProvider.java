package com.github.frcsty.treasurehunt.treasure.location;

import com.github.frcsty.treasurehunt.util.settings.MapSettings;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.SplittableRandom;

public final class LocationProvider {

    private static final SplittableRandom RANDOM = new SplittableRandom();

    public static Location getRandomLocation() {
        final Location location = MapSettings.getMapStartingLocation();

        Location newLocation = new Location(
                MapSettings.getMapWorld(),
                location.getBlockX() + RANDOM.nextDouble(-MapSettings.getMapRadiusX(), MapSettings.getMapRadiusX()),
                MapSettings.getMapHeight(),
                location.getBlockZ() + RANDOM.nextDouble(-MapSettings.getMapRadiusZ(), MapSettings.getMapRadiusZ())
        );

        boolean valid = false;
        for (int y = 0; y <= (int) MapSettings.getMapHeight(); y++) {
            final Block block = location.getWorld().getBlockAt(newLocation.getBlockX(), y, newLocation.getBlockZ());

            if (block.getType() == Material.AIR) {
                newLocation.setY(y);
                valid = true;
                break;
            }
        }

        if (isValidLocation(location) && valid)
            return newLocation;

        newLocation = getRandomLocation();
        return newLocation;
    }

    private static boolean isValidLocation(final Location location) {
        boolean containsBlacklisted = false;

        for (int x = location.getBlockX() - 1; x <= location.getBlockX() + 1; x++) {
            for (int y = location.getBlockY() - 2; y <= location.getBlockY() + 2; y++) {
                for (int z = location.getBlockZ() - 1; z <= location.getBlockZ() + 1; z++) {
                    final String blockName = location.getWorld().getBlockAt(x, y, z).getType().name().toLowerCase();

                    if (!blockName.contains("leaves") || !blockName.contains("planks")
                            || !blockName.contains("stairs") || !blockName.contains("logs")) {
                        continue;
                    }

                    containsBlacklisted = true;
                    break;
                }
            }
        }

        return !containsBlacklisted;
    }

}
