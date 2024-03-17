package com.github.frcsty.treasurehunt.command;

import com.github.frcsty.treasurehunt.game.GameController;
import com.github.frcsty.treasurehunt.game.GameState;
import com.github.frcsty.treasurehunt.message.MessageHandler;
import com.github.frcsty.treasurehunt.user.UserController;
import com.github.frcsty.treasurehunt.util.settings.MapSettings;
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
        player.teleport(MapSettings.getMapStartingLocation(MapSettings.getMapWorld()));
        MessageHandler.SUCCESSFULLY_JOINED.executeForPlayer(
                player
        );
    }

    @SubCommand("start")
    @Permission("treasurehunt.command.start")
    public void onStartCommand(final Player player, final Integer duration) {
        if (GameState.isInMotion()) {
            MessageHandler.GAME_IS_ACTIVE.executeForPlayer(
                    player
            );
            return;
        }

        controller.startGame(player.getWorld(), duration);
    }

    @SubCommand("stop")
    @Permission("treasurehunt.command.stop")
    public void onStopCommand(final Player player) {
        if (!GameState.isInMotion()) {
            MessageHandler.NO_ACTIVE_GAME.executeForPlayer(
                    player
            );
            return;
        }

        controller.stopGame();
        controller.getTaskController().cancelGameTask();
    }

}
