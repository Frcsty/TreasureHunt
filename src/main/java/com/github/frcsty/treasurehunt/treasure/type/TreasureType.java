package com.github.frcsty.treasurehunt.treasure.type;

import java.util.SplittableRandom;

public enum TreasureType {

    COMMON(1, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTAzYmQwMDQyMTcyOWNkNjM1Y2QzYjQ4MjQzNDMwYWQ0N2NmNzA3MDE4YTU5MTZmZjU5NTQ5ZDVlY2Q2Zjg3OSJ9fX0="),
    UNCOMMON(2, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjNiYTdiYzhlM2MwOTNiZDQ4YzFmNzdiZjQ4ZTM1YmZhMGVhYzlhYjQ4ZDBhZDEzZWJkOWUzYzIyZjcxYWZhIn19fQ=="),
    RARE(3, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmU2ZTY1MmFkYzBhNGY1YmY0MmE3ZjhkOGM3Njk4YjFlYWI4YTZiYzA5YWUzZjJkNzM5NDZiN2UzNTU1MDQ3In19fQ=="),
    LEGENDARY(5, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjM5YjRmYzU5ODZjMDI1ZGY0ZTQxMTc4MmJhZDg4ZTVkYTBkNzUwMWZmMmQ5OWNkZmM1MjIyNmZlYmU5MmQifX19");

    private static final SplittableRandom RANDOM = new SplittableRandom();

    private final int points;
    private final String texture;

    TreasureType(final int points, final String texture) {
        this.points = points;
        this.texture = texture;
    }

    public static TreasureType getRandomTreasureType() {
        return values()[RANDOM.nextInt(0, values().length)];
    }

    public int getPoints() {
        return this.points;
    }

    public String getTexture() {
        return this.texture;
    }

}
