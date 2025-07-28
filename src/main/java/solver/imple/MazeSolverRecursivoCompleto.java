package solver.imple;

import models.Cell;
import models.CellState;
import models.SolverResults;
import solver.MazeSolver;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MazeSolverRecursivoCompleto implements MazeSolver {

    @Override
    public SolverResults solver(Cell[][] grid, Cell start, Cell end) {
        List<Cell> path = new ArrayList<>();
        Set<Cell> visited = new LinkedHashSet<>();

        if (grid == null || grid.length == 0) {
            return new SolverResults(new ArrayList<>(), visited);
        }

        if (findPath(grid, start, end, path, visited)) {
            return new SolverResults(path, visited);
        }

        return new SolverResults(new ArrayList<>(), visited);
    }

    private boolean  findPath(Cell[][] grid, Cell current, Cell end, List<Cell> path, Set<Cell> visited) {
        int row = current.fila;
        int col = current.columna;

        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length ||
            grid[row][col].getEstado() == CellState.MURO || visited.contains(current)) {
            return false;
        }

        visited.add(current);
        if (current.equals(end)) {
            path.add(current);
            return true;
        }

        if (findPath(grid, grid[row + 1][col], end, path, visited) ||
            findPath(grid, grid[row][col + 1], end, path, visited) ||
            findPath(grid, grid[row][col - 1], end, path, visited) ||
            findPath(grid, grid[row - 1][col], end, path, visited)) {
            path.add(current);
            return true;
        }

        visited.remove(current);
        return false;
    }
}
