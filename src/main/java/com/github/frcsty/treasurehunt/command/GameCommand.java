package com.github.frcsty.treasurehunt.command;

import com.github.frcsty.treasurehunt.game.GameController;
import com.github.frcsty.treasurehunt.message.MessageHandler;
import com.github.frcsty.treasurehunt.user.UserController;
import com.github.frcsty.treasurehunt.util.Color;
import com.github.frcsty.treasurehunt.util.settings.MapSettings;
import me.clip.placeholderapi.libs.JSONMessage;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.entity.Player;

@Command("hunt")
public final class GameCommand extends CommandBase {

    private final GameController controller;
    public GameCommand(final GameController controller) {
        this.controller = controller;
    }

    @SubCommand("join")
    public void onJoinCommand(final Player player) {
        final UserController userController = controller.getUserController();

        if (userController.isJoined(player)) {
            MessageHandler.ALREADY_JOINED.executeForPlayer(
                    player
            );
            return;
        }

        userController.addUser(player);
        player.teleport(MapSettings.getMapStartingLocation());
        MessageHandler.SUCCESSFULLY_JOINED.executeForPlayer(
                player
        );
    }

    @SubCommand("start")
    @Permission("treasurehunt.command.start")
    public void onStartCommand(final Player player, final Integer duration) {
        if (controller.getGameState().getGameStatus()) {
            return;
        }

        controller.startGame(duration);
    }

    @SubCommand("stop")
    @Permission("treasurehunt.command.stop")
    public void onStopCommand(final Player player) {
        if (!controller.getGameState().getGameStatus()) {
            MessageHandler.NO_ACTIVE_GAME.executeForPlayer(
                    player
            );
            return;
        }

        controller.stopGame();
        controller.getTaskController().cancelGameTask();
    }

    @SubCommand("list")
    @Permission("treasurehunt.command.list")
    public void onListCommand(final Player player) {
        controller.getTreasureController().getTreasures().forEach((loc, treasure) -> {
            final JSONMessage msg = JSONMessage.create(
                    Color.parse(String.format("&f%s&7, &f%s&7, &f%s&7, - &fRarity&7: &f%s", loc.getX(), loc.getY(), loc.getZ(), treasure.getFormattedType()))
            );

            msg.runCommand(String.format("/tp %s %s %s", loc.getX(), loc.getY(), loc.getZ()));
            msg.send(player);
        });
    }

}
