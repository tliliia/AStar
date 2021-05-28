package com.trainee;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        char[][] maze = {
                {'.', '.', '.', '@', '.'},
                {'.', '#', '.', '.', '#'},
                {'.', '.', '.', '.', '.'},
                {'#', '#', '#', '.', '.'},
                {'.', 'X', '.', '.', '.'}
        };
        MyRouteFinder rf = new MyRouteFinder();
        rf.findRoute(maze);
    }
}