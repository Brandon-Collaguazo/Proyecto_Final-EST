package models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SolverResults {

    private List<Cell> path;
    private Set<Cell> visited;

    public SolverResults(List<Cell> path, Set<Cell> visited) {
        this.path = path;
        this.visited = visited != null ? visited : new HashSet<>();
    }

    public SolverResults() {
        this.visited = new HashSet<>();
        this.path = new ArrayList<>();
    }

    public List<Cell> getPath() {
        return path;
    }

    public void setPath(List<Cell> path) {
        this.path = path;
    }

    public Set<Cell> getVisitedCells() {
        return visited;
    }

    public void setVisitedCells(Set<Cell> visited) {
        this.visited = visited;
    }

    public void addVisitedCell(Cell cell) {
        if (this.visited == null) {
            this.visited = new HashSet<>();
        }
        this.visited.add(cell);
    }
}