package solver.imple;

import models.Cell;
import models.CellState;
import models.SolverResults;
import solver.MazeSolver;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MazeSolverDFS implements MazeSolver {

    private Cell[][] grid;
    private Cell end;
    private List<Cell> path;
    private Set<Cell> visited;

    @Override
    public SolverResults solver(Cell[][] grid, Cell start, Cell end) {
        this.grid = grid;
        this.end = end;
        this.path = new ArrayList<>();
        this.visited = new LinkedHashSet<>();

        if (grid == null || grid.length == 0 || start == null || end == null) {
            return new SolverResults(new ArrayList<>(), visited);
        }

        if (dfs(start)) {
            return new SolverResults(path, visited);
        }

        return new SolverResults(new ArrayList<>(), visited);
    }

    private boolean dfs(Cell current) {
        if (current.row < 0 || current.row >= grid.length ||
                current.col < 0 || current.col >= grid[0].length) {
            return false;
        }

        Cell gridCell = grid[current.row][current.col];

        if (gridCell.getState() == CellState.WALL ||
                visited.contains(gridCell)) {
            return false;
        }

        visited.add(gridCell);
        path.add(gridCell);

        if (gridCell.equals(end)) {
            return true;
        }

        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (int[] dir : directions) {
            int newRow = gridCell.row + dir[0];
            int newCol = gridCell.col + dir[1];
            Cell neighbor = grid[newRow][newCol];
            if (dfs(neighbor)) {
                return true;
            }
        }

        path.remove(path.size() - 1);
        return false;
    }
}
