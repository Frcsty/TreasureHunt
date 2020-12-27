package com.github.frcsty.treasurehunt.user.impl;

import com.github.frcsty.treasurehunt.treasure.type.TreasureType;

import java.util.UUID;

public final class User {

    private final UUID uuid;
    private int points = 0;

    public User(final UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUniqueId() {
        return this.uuid;
    }

    public void incrementPoints(final TreasureType type) {
        this.points += type.getPoints();
    }

    public int getPoints() {
        return this.points;
    }
}
