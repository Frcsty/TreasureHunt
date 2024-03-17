package com.github.frcsty.treasurehunt;

import com.github.frcsty.treasurehunt.game.GameController;
import com.github.frcsty.treasurehunt.user.impl.User;
import com.github.frcsty.treasurehunt.util.Sort;
import com.github.frcsty.treasurehunt.util.time.TimeAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.SplittableRandom;

final class Placeholders extends PlaceholderExpansion {

    private static final TimeAPI TIME_API = new TimeAPI();
    private static final SplittableRandom RANDOM = new SplittableRandom();
    private final GameController controller;

    Placeholders(final GameController controller) {
        this.controller = controller;
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0-Alpha";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Frcsty";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "treasurehunt";
    }

    @Override
    public String onPlaceholderRequest(final Player player, final String params) {
        final String[] args = params.split("_");

        if (args[0].equalsIgnoreCase("countdown")) {
            if (controller.getCountdown().get() == 0) {
                return "Event se bo kmalu začel!";
            }
            return TIME_API.formatTime((controller.getCountdown().get() - System.currentTimeMillis()) / 1000);
        }

        if (args[0].equalsIgnoreCase("selfscore")) {
            return String.valueOf(controller.getUserController().getPointsForUser(player));
        }

        if (!args[0].equalsIgnoreCase("top-players")) {
            return null;
        }

        final LinkedList<User> sortedUsers = Sort.getSortedPlayers(controller.getUserController());
        final int position = Integer.parseInt(args[1]);

        User user;
        try {
            user = sortedUsers.get(position - 1);
        } catch (final IndexOutOfBoundsException ex) {
            return String.format("%s", getRandomSpookyName());
        }

        final OfflinePlayer userPlayer = Bukkit.getOfflinePlayer(user.getUniqueId());
        if (user.getPoints() <= 0 || userPlayer.getName() == null) {
            return getRandomSpookyName();
        }

        return String.format("%s - %s", userPlayer.getName(), user.getPoints());
    }

    private String getRandomSpookyName() {
        return NAMES.get(RANDOM.nextInt(0, NAMES.size()));
    }

    private static final List<String> NAMES = List.of(
            "Duhec Casper", "Spooky Skeleton", "krusic21", "Scarecrow", "Harry Peter", "Dave"
    );

}
