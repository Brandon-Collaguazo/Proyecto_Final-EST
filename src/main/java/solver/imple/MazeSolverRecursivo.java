package solver.imple;

import models.Cell;
import models.CellState;
import models.SolverResults;
import solver.MazeSolver;

import java.util.*;

public class MazeSolverRecursivo implements MazeSolver {

    @Override
    public SolverResults solver(Cell[][] grid, Cell start, Cell end) {
        List<Cell> path = new ArrayList<>();
        Set<Cell> visited = new HashSet<>();
        if (grid == null || grid.length == 0) {
            return new SolverResults(new ArrayList<>(), new HashSet<>());
        }

        if (findPath(grid, start, end, path, visited)) {
            return new SolverResults(path, visited);
        }

        return new SolverResults(new ArrayList<>(), new HashSet<>());
    }

    private boolean findPath(Cell[][] grid, Cell current, Cell end, List<Cell> path, Set<Cell> visited) {
        if (current.fila < 0 || current.fila >= grid.length ||
            current.columna < 0 || current.columna >= grid[0].length ||
            grid[current.fila][current.columna].getEstado() == CellState.MURO ||
            visited.contains(current)) {
            return false;
        }

        visited.add(current);
        path.add(current);
        if (current.equals(end)) {
            return true;
        }

        if (findPath(grid, new Cell(current.fila, current.columna + 1, current.getEstado(), false), end, path, visited) ||
            findPath(grid, new Cell(current.fila + 1, current.columna, current.getEstado(), false), end, path, visited)) {
            return true;
        }

        path.remove(path.size() - 1);
        return false;
    }
}
