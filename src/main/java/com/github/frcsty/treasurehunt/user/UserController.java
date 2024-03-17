package com.github.frcsty.treasurehunt.user;

import com.github.frcsty.treasurehunt.TreasureHuntPlugin;
import com.github.frcsty.treasurehunt.treasure.type.TreasureType;
import com.github.frcsty.treasurehunt.user.impl.User;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class UserController {

    private final Map<UUID, User> users = new HashMap<>();

    public void addUser(final Player player) {
        if (this.users.get(player.getUniqueId()) != null) return;

        this.users.put(player.getUniqueId(), new User(player.getUniqueId()));
    }

    private User getUser(final Player player) {
        return this.users.getOrDefault(player.getUniqueId(), new User(player.getUniqueId()));
    }

    public boolean isJoined(final Player player) {
        return this.users.get(player.getUniqueId()) != null;
    }

    public void incrementTreasureCountForUser(final Player player, final TreasureType.TreasureSubType type) {
        final User user = getUser(player);

        user.incrementPoints(type);
        this.users.put(player.getUniqueId(), user);
    }

    public Map<UUID, User> getUsers() {
        return this.users;
    }

    public long getPointsForUser(final Player player) {
        return getUser(player).getPoints();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void saveDataToFile(final TreasureHuntPlugin plugin) {
        try {
            final File file = new File(plugin.getDataFolder(), "hunt-results-" + new Date(System.currentTimeMillis()) + ".yml");
            if (!file.exists())
                file.createNewFile();

            final FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            this.users.forEach((uuid, user) -> {
                configuration.set("results." + uuid.toString() + ".points", user.getPoints());
                configuration.set("results." + uuid.toString() + ".name", user.getName());
            });

            configuration.save(file);
        } catch (final IOException ex) {
            ex.printStackTrace();
        }

        this.users.clear();
    }

}
