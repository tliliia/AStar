package com.trainee;

public class PathNode implements Comparable {
    public Coordinate location;
    public Shift shiftToParent;
    public double routeScore;//g
    public double estimatedScore;//h

    PathNode(Coordinate current, Shift parent, double routeScore, double estimatedScore) {
        this.location = current;
        this.shiftToParent = parent;
        this.routeScore = routeScore;
        this.estimatedScore = estimatedScore;
    }

    @Override
    public int compareTo(Object o) {
        PathNode other = (PathNode)o;
        return Double.compare(this.estimatedScore, other.estimatedScore);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathNode that = (PathNode) o;
        return this.location.equals(that.location);
    }

    @Override
    public int hashCode() {
        return this.location.hashCode();
    }

    @Override
    public String toString() {
        return "("+ location +")"+ shiftToParent +":"+routeScore+"/"+estimatedScore;
    }
}