package com.github.frcsty.treasurehunt.message;

import com.github.frcsty.treasurehunt.TreasureHuntPlugin;
import com.github.frcsty.treasurehunt.util.Color;
import com.github.frcsty.treasurehunt.util.Replace;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public enum MessageHandler {

    TREASURE_FOUND("message.playerFoundTreasure"),
    ALREADY_JOINED("message.alreadyJoined"),
    GAME_STARTED("message.gameStarted"),
    GAME_FINISHED("message.gameFinished"),
    SUCCESSFULLY_JOINED("message.successfullyJoined"),
    STANDINGS_ANNOUNCEMENT("message.standingsAnnouncement"),
    NO_ACTIVE_GAME("message.noActiveGame"),
    GAME_IS_ACTIVE("message.gameIsActive")
    ;

    private final TreasureHuntPlugin plugin = JavaPlugin.getPlugin(TreasureHuntPlugin.class);
    private final String messagePath;

    MessageHandler(final String messagePath) {
        this.messagePath = messagePath;
    }

    private List<String> getMessageActions() {
        return plugin.getConfig().getStringList(messagePath);
    }

    public void executeForPlayer(final Player player, final String... replacements) {
        final List<String> parsed = Color.parse(player, Replace.replaceList(getMessageActions(), replacements));
        plugin.getActionManager().execute(player, parsed);
    }

}
