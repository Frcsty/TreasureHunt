package com.github.frcsty.treasurehunt.game;

public final class GameState {

    public static final boolean ENABLED = true;
    public static final boolean DISABLED = false;

    private static boolean currentState = false;

    public static boolean isInMotion() { return currentState; }

    public static void setGameStatus(final boolean state) { currentState = state; }

}
