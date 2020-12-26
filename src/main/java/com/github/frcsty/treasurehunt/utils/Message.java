package com.github.frcsty.treasurehunt.utils;

import me.mattstudios.mfmsg.bukkit.BukkitMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class Message {

    private static final BukkitMessage BUKKIT_MESSAGE = BukkitMessage.create();

    public static void send(final Player player, final String msg) {
        BUKKIT_MESSAGE.parse(msg).sendMessage(player);
    }

    public static void broadcast(final String msg) {
        Bukkit.getOnlinePlayers().forEach(it ->
            BUKKIT_MESSAGE.parse(msg).sendMessage(it)
        );
    }
}
