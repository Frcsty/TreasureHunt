package com.github.frcsty.treasurehunt.game;

public enum GameState {

    ENABLED,
    DISABLED
    ;

    private static GameState currentState = GameState.DISABLED;

    public static boolean isInMotion() { return currentState == ENABLED; }

    public static void setGameStatus(final GameState state) { currentState = state; }

}
