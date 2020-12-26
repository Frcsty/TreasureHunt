package com.github.frcsty.treasurehunt.user;

import java.util.UUID;

public final class TreasurePlayerProfile {

    private final UUID uuid;
    private int treasuresFound = 0;

    public TreasurePlayerProfile(final UUID uuid) {
        this.uuid = uuid;
    }

    public void incrementTreasuresFound() {
        this.treasuresFound += 1;
    }

    public int getTreasuresFound() {
        return this.treasuresFound;
    }

    public UUID getUuid() {
        return this.uuid;
    }
}
