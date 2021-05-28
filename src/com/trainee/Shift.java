package com.trainee;

public enum Shift {
    NONE (0, 0),
    LEFT (-1, 0),
    UP (0, -1),
    RIGHT (1, 0),
    DOWN (0, 1);

    private final int x;
    private final int y;
    Shift (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public static Shift oppositeForShift(Shift shift) {
        switch (shift) {
                case UP:
                    return Shift.DOWN;
                case DOWN:
                    return Shift.UP;
                case LEFT:
                    return Shift.RIGHT;
                case RIGHT:
                    return Shift.LEFT;
                default:
                    return Shift.NONE;
        }
    }
}