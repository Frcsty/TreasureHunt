package com.github.frcsty.treasurehunt.util.action.actions.message;

import com.github.frcsty.treasurehunt.util.action.Action;
import com.google.gson.JsonSyntaxException;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class JsonMessage implements Action {
    private final Plugin plugin;

    public JsonMessage(final Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getID() {
        return "json";
    }

    @Override
    public void run(final Player player, final String json) {
        try {
            final BaseComponent[] components = ComponentSerializer.parse(json);
            player.sendMessage(components);
        } catch (JsonSyntaxException ignored) {
            plugin.getLogger().warning("[ActionUtil] Invalid JSON: '" + json + "'");
        }
    }
}
