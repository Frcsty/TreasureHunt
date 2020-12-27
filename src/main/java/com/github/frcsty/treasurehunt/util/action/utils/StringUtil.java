package com.github.frcsty.treasurehunt.util.action.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class StringUtil {

    public static void broadcast(final String message) {
        broadcast(message, null);
    }

    public static void broadcast(final String message, final String permission) {
        if (message == null || message.trim().isEmpty()) {
            return;
        }

        final boolean checkPermission = permission != null && !permission.trim().isEmpty();

        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (checkPermission && !player.hasPermission(permission)) {
                continue;
            }

            player.sendMessage(message);
        }
    }

}
