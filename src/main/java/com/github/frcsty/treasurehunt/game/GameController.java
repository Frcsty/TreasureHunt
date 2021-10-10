package com.github.frcsty.treasurehunt.game;

import com.github.frcsty.treasurehunt.TreasureHuntPlugin;
import com.github.frcsty.treasurehunt.game.task.TaskController;
import com.github.frcsty.treasurehunt.message.MessageHandler;
import com.github.frcsty.treasurehunt.treasure.TreasureController;
import com.github.frcsty.treasurehunt.user.UserController;
import com.github.frcsty.treasurehunt.util.settings.MapSettings;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public final class GameController {

    private final UserController userController = new UserController();
    private final TreasureController treasureController = new TreasureController();
    private final TaskController taskController = new TaskController(this);

    private final TreasureHuntPlugin plugin;

    public GameController(final TreasureHuntPlugin plugin) {
        this.plugin = plugin;
    }

    public void startGame(final int duration) {
        GameState.setGameStatus(GameState.ENABLED);

        MessageHandler.GAME_STARTED.executeForPlayer(
                null
        );

        userController.getUsers().forEach((uuid, user) -> {
            final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);

            if (!offlinePlayer.isOnline()) return;
            final Player player = offlinePlayer.getPlayer();
            if (player == null) return;

            player.teleport(MapSettings.getMapStartingLocation());
        });

        treasureController.addInitialTreasures(
                plugin.getConfig().getInt("settings.treasure-amount")
        );

        taskController.setGameTask(new BukkitRunnable() {
            @Override
            public void run() {
                if (!GameState.isInMotion()) {
                    cancel();
                    return;
                }

                stopGame();
            }
        }.runTaskLater(plugin, duration * 20L), true);
    }

    public void stopGame() {
        GameState.setGameStatus(GameState.DISABLED);

        MessageHandler.GAME_FINISHED.executeForPlayer(
                null
        );

        treasureController.clearTreasures();
        userController.saveDataToFile(plugin);
    }

    public UserController getUserController() {
        return this.userController;
    }

    public TreasureController getTreasureController() {
        return this.treasureController;
    }

    public TaskController getTaskController() {
        return this.taskController;
    }

    public TreasureHuntPlugin getPlugin() {
        return this.plugin;
    }
}
