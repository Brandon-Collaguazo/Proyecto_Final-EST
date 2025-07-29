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
        Set<Cell> visited = new LinkedHashSet<>();
        if (grid == null || grid.length == 0) {
            return new SolverResults(new ArrayList<>(), visited);
        }
        if (!isValidPosition(start.row, start.col, grid) || grid[start.row][start.col].getState() == CellState.WALL) {
            return new SolverResults(new ArrayList<>(), visited);
        }
        if (findPath(grid, start, end, path, visited)) {
            java.util.Collections.reverse(path);
            return new SolverResults(path, visited);
        }
        return new SolverResults(new ArrayList<>(), visited);
    }

    private boolean findPath(Cell[][] grid, Cell current, Cell end, List<Cell> path, Set<Cell> visited) {
        int row = current.row;
        int col = current.col;
        if (!isValidPosition(row, col, grid) || grid[row][col].getState() == CellState.WALL || visited.contains(current)) {
            return false;
        }

        visited.add(current);
        if (current.equals(end)) {
            path.add(current);
            return true;
        }

        int[][] directions = {
                {0, 1}, // Derecha
                {1, 0}  // Abajo
        };
        for (int[] dir : directions) {
            int nextRow = row + dir[0];
            int nextCol = col + dir[1];
            if (isValidPosition(nextRow, nextCol, grid)) {
                Cell nextCell = grid[nextRow][nextCol];
                if (findPath(grid, nextCell, end, path, visited)) {
                    path.add(current);
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isValidPosition(int row, int col, Cell[][] grid) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }
}