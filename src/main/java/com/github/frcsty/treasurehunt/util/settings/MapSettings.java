package com.github.frcsty.treasurehunt.util.settings;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public final class MapSettings {

    private static int mapRadiusX;
    private static int mapRadiusZ;
    private static int mapHeightMin;
    private static int mapHeightMax;

    private static World world;

    private static Location mapLobbyLocation;
    private static Location mapStartingLocation;

    public static void configure(final FileConfiguration configuration) {
        mapRadiusX = configuration.getInt("settings.map-settings.map-size.x");
        mapRadiusZ = configuration.getInt("settings.map-settings.map-size.z");
        mapHeightMin = configuration.getInt("settings.map-settings.map-height-min");
        mapHeightMax = configuration.getInt("settings.map-settings.map-height-max");

        final String worldString = configuration.getString("settings.map-settings.map-world", "world");
        world = Bukkit.getWorld(worldString);

        mapLobbyLocation = new Location(
                world,
                configuration.getDouble("settings.lobby-starting-point.x"),
                configuration.getDouble("settings.lobby-starting-point.y"),
                configuration.getDouble("settings.lobby-starting-point.z")
        );

        mapStartingLocation = new Location(
                world,
                configuration.getDouble("settings.game-starting-point.x"),
                configuration.getDouble("settings.game-starting-point.y"),
                configuration.getDouble("settings.game-starting-point.z")
        );
    }

    public static int getMapRadiusX() {
        return mapRadiusX;
    }

    public static int getMapRadiusZ() {
        return mapRadiusZ;
    }

    public static int getMapHeightMin() {
        return mapHeightMin;
    }

    public static int getMapHeightMax() {
        return mapHeightMax;
    }

    public static World getMapWorld() {
        return world;
    }

    public static Location getMapLobbyLocation() {
        return mapLobbyLocation;
    }

    public static Location getMapStartingLocation(final World world) {
        return new Location(world, mapStartingLocation.getX(), mapStartingLocation.getY(), mapStartingLocation.getZ());
    }
}
