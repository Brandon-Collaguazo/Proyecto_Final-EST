package solver.imple;

import models.Cell;
import models.CellState;
import models.SolverResults;
import solver.MazeSolver;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MazeSolverRecursivoCompletoBT implements MazeSolver {

    private List<Cell> path;
    private Set<Cell> visited;
    private Cell[][] grid;
    private Cell end;

    public MazeSolverRecursivoCompletoBT() {
        this.path = new ArrayList<>();
        this.visited = new LinkedHashSet<>();
    }

    @Override
    public SolverResults solver(Cell[][] grid, Cell start, Cell end) {
        path.clear();
        visited.clear();
        this.grid = grid;
        this.end = end;

        if (grid == null || grid.length == 0) {
            return new SolverResults(new ArrayList<>(), new LinkedHashSet<>());
        }

        if (findPath(start)) {
            return new SolverResults(path, visited);
        }

        return new SolverResults(new ArrayList<>(), new LinkedHashSet<>());
    }

    private boolean findPath(Cell current) {
        if (!isInMaze(current) || !isValid(current)) {
            return false;
        }

        visited.add(current);
        path.add(current);

        if (current.equals(end)) {
            return true;
        }

        if (findPath(grid[current.fila][current.columna + 1]) ||
                findPath(grid[current.fila + 1][current.columna]) ||
                findPath(grid[current.fila][current.columna - 1]) ||
                findPath(grid[current.fila - 1][current.columna])) {
            return true;
        }

        path.remove(path.size() - 1);
        return false;
    }

    private boolean isValid(Cell current) {
        return current != null &&
                current.getEstado() != CellState.MURO &&
                !visited.contains(current);
    }

    private boolean isInMaze(Cell current) {
        int fila = current.fila;
        int columna = current.columna;
        return fila >= 0 && fila < grid.length && columna >= 0 && columna < grid[0].length;
    }
}
