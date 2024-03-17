package com.github.frcsty.treasurehunt.game;

import com.github.frcsty.treasurehunt.TreasureHuntPlugin;
import com.github.frcsty.treasurehunt.game.task.TaskController;
import com.github.frcsty.treasurehunt.message.MessageHandler;
import com.github.frcsty.treasurehunt.treasure.TreasureController;
import com.github.frcsty.treasurehunt.user.UserController;
import com.github.frcsty.treasurehunt.util.settings.MapSettings;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.atomic.AtomicLong;

public final class GameController {

    private final UserController userController = new UserController();
    private final TreasureController treasureController = new TreasureController();
    private final TaskController taskController = new TaskController(this);

    private final AtomicLong countdown = new AtomicLong(0L);

    private final TreasureHuntPlugin plugin;

    public GameController(final TreasureHuntPlugin plugin) {
        this.plugin = plugin;
    }

    public void startGame(final World world, final int duration) {
        GameState.setGameStatus(GameState.ENABLED);
        this.countdown.set(System.currentTimeMillis() + (duration * 1000L));

        MessageHandler.GAME_STARTED.executeForPlayer(
                null
        );

        userController.getUsers().forEach((uuid, user) -> {
            final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);

            if (!offlinePlayer.isOnline()) return;
            final Player player = offlinePlayer.getPlayer();
            if (player == null) return;

            player.teleport(MapSettings.getMapStartingLocation(world));
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

        treasureController.initializeNotification(plugin);
    }

    public void stopGame() {
        GameState.setGameStatus(GameState.DISABLED);

        MessageHandler.GAME_FINISHED.executeForPlayer(
                null
        );

        treasureController.clearTreasures();
        userController.saveDataToFile(plugin);
        this.countdown.set(0L);
    }

    public AtomicLong getCountdown() {
        return this.countdown;
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
