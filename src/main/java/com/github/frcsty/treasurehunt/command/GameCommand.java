package com.github.frcsty.treasurehunt.command;

import com.github.frcsty.treasurehunt.TreasureHuntPlugin;
import com.github.frcsty.treasurehunt.utils.Message;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@Command("hunt")
public final class GameCommand extends CommandBase {

    private final TreasureHuntPlugin plugin;
    private final FileConfiguration config;

    public GameCommand(final TreasureHuntPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @SubCommand("start")
    @Permission("treasurehunt.command.start")
    public void onCommand(final Player player) {
        plugin.getManager().startGame();

        Message.broadcast(
                config.getString("message.gameStarted")
        );
    }

    @SubCommand("stop")
    @Permission("treasurehunt.command.stop")
    public void onStopCommand(final Player player) {
        plugin.getManager().stopGame();
    }

    @SubCommand("list")
    @Permission("treasurehunt.command.list")
    public void onListCommand(final Player player) {
        plugin.getManager().getTreasureLocations().forEach((loc, block) -> Message.send(player, loc.getX() + ", " + loc.getY() + ", " + loc.getZ()));
    }

    @SubCommand("join")
    public void onJoinCommand(final Player player) {
        if (plugin.getManager().isJoined(player)) {
            Message.send(
                    player,
                    config.getString("message.alreadyJoined")
            );
            return;
        }

        plugin.getManager().addPlayer(player);
        Message.send(
                player,
                config.getString("message.successfullyJoined")
        );
    }
}
