package com.github.frcsty.treasurehunt;

import com.github.frcsty.frozenactions.wrapper.ActionHandler;
import com.github.frcsty.treasurehunt.command.GameCommand;
import com.github.frcsty.treasurehunt.game.GameController;
import com.github.frcsty.treasurehunt.game.GameState;
import com.github.frcsty.treasurehunt.listener.PlayerListener;
import com.github.frcsty.treasurehunt.listener.TreasureInteractListener;
import com.github.frcsty.treasurehunt.treasure.type.TreasureType;
import com.github.frcsty.treasurehunt.util.settings.MapSettings;
import me.mattstudios.mf.base.CommandManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class TreasureHuntPlugin extends JavaPlugin {

    private final ActionHandler actionHandler = new ActionHandler(this);
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

        this.actionHandler.loadDefaults(true);

        new Placeholders(gameController).register();
        TreasureType.init();
    }

    @Override
    public void onDisable() {
        if (GameState.isInMotion()) {
            gameController.stopGame();
        }

        gameController.getTreasureController().clearTreasures();

        reloadConfig();
    }

    private void registerListeners(final Listener... listeners) {
        Arrays.stream(listeners).forEach(it -> getServer().getPluginManager().registerEvents(it, this));
    }

    public ActionHandler getActionHandler() {
        return this.actionHandler;
    }

    public GameController getGameController() {
        return this.gameController;
    }
}
