package com.github.frcsty.treasurehunt;

import com.github.frcsty.treasurehunt.command.GameCommand;
import com.github.frcsty.treasurehunt.game.GameManager;
import com.github.frcsty.treasurehunt.listener.PlayerListener;
import com.github.frcsty.treasurehunt.listener.TreasureOpenListener;
import me.mattstudios.mf.base.CommandManager;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class TreasureHuntPlugin extends JavaPlugin {

    private final GameManager manager = new GameManager(this);

    @Override
    public void onEnable() {
        saveDefaultConfig();

        registerListeners(
                new PlayerListener(this),
                new TreasureOpenListener(this)
        );

        final CommandManager commandManager = new CommandManager(this);
        commandManager.register(
                new GameCommand(this)
        );

        new Placeholders(this).register();
    }

    @Override
    public void onDisable() {
        manager.getTreasureLocations().forEach((loc, block) -> block.setType(Material.AIR));
    }

    private void registerListeners(final Listener... listeners) {
        Arrays.stream(listeners).forEach(it -> getServer().getPluginManager().registerEvents(it, this));
    }

    public GameManager getManager() {
        return this.manager;
    }
}
