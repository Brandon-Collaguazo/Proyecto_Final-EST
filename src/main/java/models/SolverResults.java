package models;

import java.util.List;
import java.util.Set;

public class SolverResults {

    private List<Cell> path;
    private Set<Cell> visited;

    public SolverResults(List<Cell> path, Set<Cell> visited) {
        this.path = path;
        this.visited = visited;
    }

    public List<Cell> getPath() {
        return path;
    }

    public void setPath(List<Cell> path) {
        this.path = path;
    }

    public Set<Cell> getVisited() {
        return visited;
    }

    public void setVisited(Set<Cell> visited) {
        this.visited = visited;
    }
}