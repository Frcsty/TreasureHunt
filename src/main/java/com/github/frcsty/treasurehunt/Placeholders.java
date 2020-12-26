package com.github.frcsty.treasurehunt;

import com.github.frcsty.treasurehunt.game.GameManager;
import com.github.frcsty.treasurehunt.user.TreasurePlayerProfile;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public final class Placeholders extends PlaceholderExpansion {

    private final TreasureHuntPlugin plugin;

    Placeholders(final TreasureHuntPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getVersion() {
        return "1.0.0-Alpha";
    }

    @Override
    public String getAuthor() {
        return "Frcsty";
    }

    @Override
    public String getIdentifier() {
        return "treasurehunt";
    }

    @Override
    public String onPlaceholderRequest(final Player player, final String params) {
        final String[] args = params.split("_");

        if (!args[0].equalsIgnoreCase("top-players")) {
            return null;
        }

        final LinkedList<TreasurePlayerProfile> profiles = getSortedPlayers(plugin.getManager());
        final int position = Integer.valueOf(args[1]);

        TreasurePlayerProfile profile;
        try {
            profile = profiles.get(position - 1);
        } catch (final IndexOutOfBoundsException ignored) {
            return "No Player";
        }

        final Player profilePlayer = Bukkit.getPlayer(profile.getUuid());

        if (profilePlayer == null) return "User Could Not Be Found.";
        return String.format("#%s. %s - %s", position, profilePlayer.getName(), profile.getTreasuresFound());
    }

    public static LinkedList<TreasurePlayerProfile> getSortedPlayers(final GameManager manager) {
        final Map<UUID, TreasurePlayerProfile> players = manager.getJoinedPlayers();
        final Map<UUID, Integer> profiles = new HashMap<>();

        for (final UUID uuid : players.keySet()) {
            profiles.put(uuid, players.get(uuid).getTreasuresFound());
        }

        final Map<UUID, Integer> sortedMap = profiles.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        final LinkedList<TreasurePlayerProfile> resultMap = new LinkedList<>();
        for (final UUID uuid : sortedMap.keySet()) {
            resultMap.add(players.get(uuid));
        }

        return resultMap;
    }

}
