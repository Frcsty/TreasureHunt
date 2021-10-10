package com.github.frcsty.treasurehunt.game.task;

import com.github.frcsty.treasurehunt.TreasureHuntPlugin;
import com.github.frcsty.treasurehunt.game.GameController;
import com.github.frcsty.treasurehunt.game.GameState;
import com.github.frcsty.treasurehunt.message.MessageHandler;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public final class TaskController {

    private final GameController controller;
    private BukkitTask gameTask;
    private BukkitTask announcementTask;

    public TaskController(final GameController controller) {
        this.controller = controller;
    }

    public void setGameTask(final BukkitTask task, final boolean initializeMessageTask) {
        this.gameTask = task;

        if (initializeMessageTask)
            announcementTask = initializeMessageTask();
    }

    private BukkitTask initializeMessageTask() {
        final TreasureHuntPlugin plugin = controller.getPlugin();
        final long delay = (long) plugin.getConfig().getInt("settings.standings-announcement-delay") * 60 * 20;

        return new BukkitRunnable() {
            @Override
            public void run() {
                if (!GameState.isInMotion()) {
                    cancel();
                    return;
                }

                MessageHandler.STANDINGS_ANNOUNCEMENT.executeForPlayer(
                        null
                );
            }
        }.runTaskTimer(plugin, delay, delay);
    }

    public void cancelGameTask() {
        this.gameTask.cancel();
        this.announcementTask.cancel();
    }

}
