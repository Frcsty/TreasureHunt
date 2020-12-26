package com.github.frcsty.treasurehunt.game;

public final class LocationHolder {
    private final int x;
    private final int y;
    private final int z;

    public LocationHolder(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}