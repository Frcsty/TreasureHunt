package com.github.frcsty.treasurehunt.util;

import me.clip.placeholderapi.PlaceholderAPI;
import me.mattstudios.mfmsg.bukkit.BukkitMessage;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public final class Color {

    private static final BukkitMessage MESSAGE = BukkitMessage.create();

    public static String parse(final String message) {
        return MESSAGE.parse(message).toString();
    }

    public static List<String> parse(final Player player, final List<String> message) {
        return message.stream()
                .map(it -> PlaceholderAPI.setPlaceholders(player, it))
                .map(Color::parse)
                .collect(Collectors.toList());
    }

    public static List<String> parse(final List<String> message) {
        return message.stream()
                .map(it -> MESSAGE.parse(it).toString())
                .collect(Collectors.toList());
    }

}
