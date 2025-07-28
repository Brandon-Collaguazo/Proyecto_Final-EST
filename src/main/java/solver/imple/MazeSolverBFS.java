package solver.imple;

import models.Cell;
import models.CellState;
import models.SolverResults;
import solver.MazeSolver;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class MazeSolverBFS implements MazeSolver {

    @Override
    public SolverResults solver(Cell[][] grid, Cell start, Cell end) {
        Set<Cell> visited = new LinkedHashSet<>();
        List<Cell> emptyPath = new ArrayList<>();

        if (grid == null || grid.length == 0) {
            return new SolverResults(emptyPath, visited);
        }

        Queue<Cell> queue = new ArrayDeque<>();
        Map<Cell, Cell> parentMap = new HashMap<>();

        queue.add(start);
        visited.add(start);
        parentMap.put(start, null);

        while (!queue.isEmpty()) {
            Cell current = queue.poll();

            if (current.equals(end)) {
                return new SolverResults(buildPath(parentMap, end), visited);
            }

            int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
            for (int[] dir : directions) {
                int newRow = current.fila + dir[0];
                int newCol = current.columna + dir[1];

                if (newRow < 0 || newRow >= grid.length ||
                        newCol < 0 || newCol >= grid[0].length) {
                    continue;
                }

                Cell neighbor = grid[newRow][newCol];

                if (neighbor.getEstado() != CellState.MURO && !visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        return new SolverResults(emptyPath, visited);
    }

    private List<Cell> buildPath(Map<Cell, Cell> parentMap, Cell end) {
        List<Cell> path = new ArrayList<>();
        for (Cell at = end; at != null; at = parentMap.get(at)) {
            path.add(0, at);
        }
        return path;
    }
}
