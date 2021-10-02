package com.github.frcsty.treasurehunt;

import com.github.frcsty.treasurehunt.game.GameController;
import com.github.frcsty.treasurehunt.user.impl.User;
import com.github.frcsty.treasurehunt.util.Sort;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.LinkedList;

final class Placeholders extends PlaceholderExpansion {

    private final GameController controller;
    Placeholders(final GameController controller) {
        this.controller = controller;
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

        final LinkedList<User> sortedUsers = Sort.getSortedPlayers(controller.getUserController());
        final int position = Integer.parseInt(args[1]);

        User user;
        try {
            user = sortedUsers.get(position - 1);
        } catch (final IndexOutOfBoundsException ex) {
            return String.format("#%s. No Data For This Position.", position);
        }

        final Player userPlayer = Bukkit.getPlayer(user.getUniqueId());
        if (userPlayer == null) return "User Could Not Be Found.";
        return String.format("#%s. %s - %s", position, userPlayer.getName(), user.getPoints());
    }

}
