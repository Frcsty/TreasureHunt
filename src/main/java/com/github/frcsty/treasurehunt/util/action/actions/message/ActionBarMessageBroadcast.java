package com.github.frcsty.treasurehunt.util.action.actions.message;

import com.github.frcsty.treasurehunt.TreasureHuntPlugin;
import com.github.frcsty.treasurehunt.util.action.Action;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class ActionBarMessageBroadcast implements Action {

    private final TreasureHuntPlugin plugin;
    public ActionBarMessageBroadcast(final TreasureHuntPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getID() {
        return "actionbarbroadcast";
    }

    @Override
    public void run(final Player player, final String data) {
        plugin.getGameController().getUserController().getUsers().forEach((uuid, user) -> {
            final Player plr = Bukkit.getPlayer(uuid);

            if (plr == null) return;
            plr.sendActionBar(data);
        });
    }
}
