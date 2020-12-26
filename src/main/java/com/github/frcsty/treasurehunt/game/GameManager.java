package com.github.frcsty.treasurehunt.game;

import com.github.frcsty.treasurehunt.Placeholders;
import com.github.frcsty.treasurehunt.TreasureHuntPlugin;
import com.github.frcsty.treasurehunt.user.TreasurePlayerProfile;
import com.github.frcsty.treasurehunt.utils.Message;
import com.github.frcsty.treasurehunt.utils.Replace;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.util.*;

public final class GameManager {

    private static final SplittableRandom RANDOM = new SplittableRandom();

    private final Map<LocationHolder, Block> treasureLocations = new HashMap<>();
    private final Map<UUID, TreasurePlayerProfile> joinedPlayers = new HashMap<>();

    private final TreasureHuntPlugin plugin;
    private final FileConfiguration config;
    private final int mapX;
    private final int mapZ;
    private Location lobbyLocation;
    private boolean started = false;
    private Location startingLocation;

    public GameManager(final TreasureHuntPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();

        this.mapX = config.getInt("settings.map-settings.map-size.x");
        this.mapZ = config.getInt("settings.map-settings.map-size.z");
    }

    public void teleportToLobby(final Player player) {
        player.teleport(this.lobbyLocation);
    }

    public void addPlayer(final Player player) {
        if (this.lobbyLocation == null) {
            this.lobbyLocation = new Location(
                    Bukkit.getWorld(config.getString("settings.lobby-starting-point.world")),
                    config.getInt("settings.lobby-starting-point.x"),
                    config.getInt("settings.lobby-starting-point.y"),
                    config.getInt("settings.lobby-starting-point.z")
            );
        }

        this.joinedPlayers.put(player.getUniqueId(), new TreasurePlayerProfile(player.getUniqueId()));

        player.teleport(lobbyLocation);
    }

    private BukkitTask task;

    public void startGame() {
        this.started = true;
        this.startingLocation = new Location(
                Bukkit.getWorld(config.getString("settings.game-starting-point.world")),
                config.getInt("settings.game-starting-point.x"),
                config.getInt("settings.game-starting-point.y"),
                config.getInt("settings.game-starting-point.z")
        );

        for (final UUID uuid : joinedPlayers.keySet()) {
            final OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);

            if (!player.isOnline()) continue;

            final Player onlinePlayer = player.getPlayer();
            if (onlinePlayer == null) continue;
            onlinePlayer.teleport(startingLocation);
        }

        for (int i = 1; i <= 5; i++) {
            spawnRandomTreasure();
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!started) return;

                stopGame();
            }
        }.runTaskLater(plugin, 72000);

        initializeHintRunnable();
    }

    private void initializeHintRunnable() {
        this.task = new BukkitRunnable() {
            @Override
            public void run() {
                treasureLocations.forEach((loc, block) -> {
                    final Location location = lobbyLocation.clone();

                    location.setX(loc.getX());
                    location.setY(loc.getY());
                    location.setZ(loc.getZ());

                    final Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
                    final FireworkMeta meta = firework.getFireworkMeta();

                    meta.addEffect(FireworkEffect.builder().withColor(Color.AQUA).trail(true).build());
                    meta.setPower(1);
                    firework.setFireworkMeta(meta);
                });
            }
        }.runTaskTimer(plugin, 1200, 1200);
    }

    public void removeTreasure(final LocationHolder holder) {
        LocationHolder removal = null;

        for (final LocationHolder location : treasureLocations.keySet()) {
            if (location.getX() == holder.getX() && location.getY() == holder.getY() && location.getZ() == holder.getZ()) {
                removal = location;
                break;
            }
        }

        treasureLocations.remove(removal);
    }

    public void stopGame() {
        try {
            final File file = new File(plugin.getDataFolder(), "hunt-results.yml");
            if (!file.exists())
                file.createNewFile();

            final FileConfiguration resultsConfig = YamlConfiguration.loadConfiguration(file);
            joinedPlayers.forEach((uuid, profile) ->
                    resultsConfig.set("result." + uuid.toString(), profile.getTreasuresFound())
            );

            resultsConfig.save(file);
        } catch (final IOException ex) {
            ex.printStackTrace();
        }

        treasureLocations.forEach((location, block) -> block.setType(Material.AIR));
        final LinkedList<TreasurePlayerProfile> profiles = Placeholders.getSortedPlayers(plugin.getManager());
        Message.broadcast(
                config.getString("message.gameFinished")
        );
        for (int i = 0; i < 10; i++) {
            TreasurePlayerProfile profile;
            try {
                profile = profiles.get(i);
            } catch (final IndexOutOfBoundsException ex) {
                profile = null;
            }

            final String playerName = profile == null ? "No Data" : Bukkit.getPlayer(profile.getUuid()) == null ? "Invalid Player" : Bukkit.getPlayer(profile.getUuid()).getName();
            final String treasuresFound = profile == null ? "No Data" : String.valueOf(profile.getTreasuresFound());

            Message.broadcast(Replace.replaceString(
                    config.getString("message.positionPlayer"),
                    "{position}", String.valueOf(i + 1),
                    "{player-name}", playerName,
                    "{treasures-found}", treasuresFound
            ));
        }

        if (task != null) task.cancel();
        joinedPlayers.clear();
        treasureLocations.clear();
        started = false;
    }

    public Map<LocationHolder, Block> getTreasureLocations() {
        return this.treasureLocations;
    }

    public void spawnRandomTreasure() {
        final Location treasureLocation = lobbyLocation.clone();
        treasureLocation.setX(startingLocation.getBlockX() + RANDOM.nextDouble(-mapX, mapX));
        treasureLocation.setZ(startingLocation.getBlockZ() + RANDOM.nextDouble(-mapZ, mapZ));

        final World world = treasureLocation.getWorld();
        boolean valid = false;
        for (int y = 90; y > 0; y--) {
            final Block block = world.getBlockAt(treasureLocation.getBlockX(), y, treasureLocation.getBlockZ());
            if (block.getType() != Material.AIR) {
                treasureLocation.setY(y + 1);
                world.getBlockAt(treasureLocation).setType(Material.CHEST);

                treasureLocations.put(
                        new LocationHolder(
                                treasureLocation.getBlockX(),
                                treasureLocation.getBlockY(),
                                treasureLocation.getBlockZ()),
                        block
                );
                valid = true;
                break;
            }
        }

        if (!valid) {
            spawnRandomTreasure();
        }
    }

    public Block getTreasureByLocation(final LocationHolder holder) {
        Block treasure = null;

        for (final LocationHolder location : treasureLocations.keySet()) {
            if (location.getX() == holder.getX() && location.getY() == holder.getY() && location.getZ() == holder.getZ()) {
                treasure = treasureLocations.get(location);
                break;
            }
        }

        return treasure;
    }

    public Map<UUID, TreasurePlayerProfile> getJoinedPlayers() {
        return this.joinedPlayers;
    }

    public TreasurePlayerProfile getProfile(final Player player) {
        return this.joinedPlayers.getOrDefault(player.getUniqueId(), new TreasurePlayerProfile(player.getUniqueId()));
    }

    public boolean isGameStarted() {
        return this.started;
    }

    public boolean isJoined(final Player player) {
        return this.joinedPlayers.get(player.getUniqueId()) != null;
    }

}
