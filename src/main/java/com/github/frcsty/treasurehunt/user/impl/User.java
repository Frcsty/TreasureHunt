package com.github.frcsty.treasurehunt.user.impl;

import com.github.frcsty.treasurehunt.treasure.type.TreasureType;
import org.bukkit.Bukkit;

import java.util.UUID;

public final class User {

    private final UUID uuid;
    private final String name;
    private int points = 0;

    public User(final UUID uuid) {
        this.uuid = uuid;

        this.name = Bukkit.getOfflinePlayer(uuid).getName();
    }

    public UUID getUniqueId() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public void incrementPoints(final TreasureType.TreasureSubType type) {
        this.points += type.getPoints();
    }

    public int getPoints() {
        return this.points;
    }
}
