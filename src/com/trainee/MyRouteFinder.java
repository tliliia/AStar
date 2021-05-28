package com.trainee;


import java.util.*;

public class MyRouteFinder implements RouteFinder {
    private static final char START_CHAR = '@';
    private static final char FINISH_CHAR = 'X';
    private static final char WALL_CHAR = '#';
    private static final char PATH_CHAR = '+';

    private static final double NEIGHBOR_DIST = 1.0;

    private PriorityQueue<PathNode> openList = new PriorityQueue<PathNode>();//координаты со стоимостью
    private Map<Coordinate, PathNode> closedMap = new HashMap<Coordinate, PathNode>();

    private Coordinate start;
    private Coordinate finish;
    private char[][] map;

    @Override
    public char[][] findRoute(char[][] map) {
        if (map.length == 0 || (map.length > 0 && map[0].length == 0))
            throw new IllegalArgumentException("Illegal map");
        this.map = map;

        initStartAndEnd(map);
        return findPath();
    }

    private void initStartAndEnd(char[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == START_CHAR)
                    start = new Coordinate(i, j);
                else if (map[i][j] == FINISH_CHAR)
                    finish = new Coordinate(i, j);

                if (start != null && finish != null)
                    return;
            }
        }
        throw new IllegalArgumentException("Illegal start/finish point");
    }

    public char[][] findPath() {

        openList.add(new PathNode(start, Shift.NONE, 0, calculateHeuristic(start, finish)));

        while (!openList.isEmpty()) {
            PathNode current = openList.poll();
            if (map[current.location.x][current.location.y] == FINISH_CHAR) {
                 return getPath(current);
            }

            closedMap.put(current.location, current);
            addNeighbors(current);

        }
        return null;
    }

    private void addNeighbors(PathNode current) {
        for (Shift shift : Shift.values()) {
            if (shift.equals(Shift.NONE))
                continue;
            int x = current.location.x + shift.getX();
            int y = current.location.y + shift.getY();
            Coordinate  neighbor = new Coordinate(x, y);

            if (!isPointWalkable(neighbor) || closedMap.containsKey(neighbor)) {
                continue;
            }

            double neighborRouteScore = current.routeScore + NEIGHBOR_DIST;
            double estimatedScore = neighborRouteScore + calculateHeuristic(neighbor, finish);

            PathNode v = new PathNode(neighbor, Shift.oppositeForShift(shift), neighborRouteScore,  estimatedScore);

            if (!openList.contains(v)) {
                openList.add(v);
            } else {//сравнить g
                boolean modified = false;
                Iterator<PathNode> iterator = openList.iterator();
                while (iterator.hasNext()) {
                    PathNode target = iterator.next();
                    if (target.location.equals(neighbor) && target.routeScore > neighborRouteScore) {
                        iterator.remove();
                        modified = true;
                        break;
                    }
                }
                if (modified) {
                    openList.add(v);
                }
            }
        }
    }

    private char[][] getPath(PathNode current) {
        Coordinate coordinate = current.location;

        while (!coordinate.equals(start)) {
            if (!coordinate.equals(finish))
                map[coordinate.x][coordinate.y] = PATH_CHAR;

            coordinate.x += current.shiftToParent.getX();
            coordinate.y += current.shiftToParent.getY();
            current = closedMap.get(coordinate);
        }
        return map;
    }


    private boolean isPointWithinMap(Coordinate coordinate) {
        return  coordinate.x >= 0 && coordinate.x < map.length &&
                coordinate.y >= 0 && coordinate.y < map[0].length;
    }

    private boolean isPointWalkable(Coordinate coordinate) {
        return isPointWithinMap(coordinate) && map[coordinate.x][coordinate.y] != WALL_CHAR;
    }

    public double calculateHeuristic(Coordinate source, Coordinate target) {
        return 100* (Math.abs(target.x - source.x) + Math.abs(target.x - source.x));
    }
}