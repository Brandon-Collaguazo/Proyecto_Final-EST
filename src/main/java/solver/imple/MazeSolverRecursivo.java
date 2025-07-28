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
        if (current.row < 0 || current.row >= grid.length ||
            current.col < 0 || current.col >= grid[0].length ||
            grid[current.row][current.col].getState() == CellState.WALL ||
            visited.contains(current)) {
            return false;
        }

        visited.add(current);
        path.add(current);
        if (current.equals(end)) {
            return true;
        }

        if (findPath(grid, new Cell(current.row, current.col + 1, current.getState(), false), end, path, visited) ||
            findPath(grid, new Cell(current.row + 1, current.col, current.getState(), false), end, path, visited)) {
            return true;
        }

        path.remove(path.size() - 1);
        return false;
    }
}
