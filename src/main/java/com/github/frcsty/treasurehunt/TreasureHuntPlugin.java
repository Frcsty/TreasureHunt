package com.github.frcsty.treasurehunt;

import com.github.frcsty.treasurehunt.command.GameCommand;
import com.github.frcsty.treasurehunt.game.GameController;
import com.github.frcsty.treasurehunt.listener.PlayerListener;
import com.github.frcsty.treasurehunt.listener.TreasureInteractListener;
import com.github.frcsty.treasurehunt.util.action.ActionManager;
import com.github.frcsty.treasurehunt.util.settings.MapSettings;
import me.mattstudios.mf.base.CommandManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class TreasureHuntPlugin extends JavaPlugin {

    private final ActionManager actionManager = new ActionManager(this);
    private final GameController gameController = new GameController(this);

    @Override
    public void onEnable() {
        saveDefaultConfig();

        MapSettings.configure(getConfig());

        registerListeners(
                new PlayerListener(gameController),
                new TreasureInteractListener(gameController)
        );

        final CommandManager commandManager = new CommandManager(this);
        commandManager.register(
                new GameCommand(gameController)
        );

        new Placeholders(gameController).register();
    }

    @Override
    public void onDisable() {
        if (gameController.getGameState().getGameStatus()) {
            gameController.stopGame();
        }

        reloadConfig();
    }

    private void registerListeners(final Listener... listeners) {
        Arrays.stream(listeners).forEach(it -> getServer().getPluginManager().registerEvents(it, this));
    }

    public ActionManager getActionManager() {
        return this.actionManager;
    }

    public GameController getGameController() {
        return this.gameController;
    }
}
