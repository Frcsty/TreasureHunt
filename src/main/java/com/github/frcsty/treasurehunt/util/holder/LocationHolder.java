package com.github.frcsty.treasurehunt.util.holder;

import java.util.Objects;

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

    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        final LocationHolder that = (LocationHolder) object;
        return x == that.x && y == that.y && z == that.z;
    }

    @Override
    public int hashCode() { return Objects.hash(x, y, z); }
}
