package solver.imple;

import models.Cell;
import models.CellState;
import models.SolverResults;
import solver.MazeSolver;

import java.util.*;

public class MazeSolverRecursivo implements MazeSolver {

    private Cell[][] maze;
    private Cell startCell;
    private Cell endCell;
    private Stack<Cell> stack;
    private Set<Cell> visited;
    private Map<Cell, Cell> parentMap;
    private SolverResults currentSolverResults;
    private boolean foundPath = false;

    public MazeSolverRecursivo() {
        this.stack = new Stack<>();
        this.visited = new HashSet<>();
        this.parentMap = new HashMap<>();
        this.currentSolverResults = new SolverResults();
    }

    private List<Cell> getNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        int r = cell.getRow();
        int c = cell.getCol();

        int[][] directions = {
                {1, 0}, // Abajo
                {0, 1}  // Derecha
        };

        for (int[] dir : directions) {
            int nr = r + dir[0];
            int nc = c + dir[1];

            if (isValidPosition(nr, nc, maze) && maze[nr][nc].getState() != CellState.WALL) {
                neighbors.add(maze[nr][nc]);
            }
        }
        return neighbors;
    }

    private boolean isValidPosition(int row, int col, Cell[][] grid) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    @Override
    public void initialize(Cell[][] maze, Cell start, Cell end) {
        this.maze = maze;
        this.startCell = start;
        this.endCell = end;

        this.stack.clear();
        this.visited.clear();
        this.parentMap.clear();
        this.currentSolverResults = new SolverResults();
        this.foundPath = false;

        stack.push(start);
        visited.add(start);
        currentSolverResults.addVisitedCell(start);
    }

    @Override
    public boolean step() {
        if (foundPath || stack.isEmpty()) {
            return false;
        }

        Cell current = stack.pop();
        if (current.equals(endCell)) {
            foundPath = true;
            reconstructPath();
            return false;
        }

        List<Cell> neighbors = getNeighbors(current);
        Collections.reverse(neighbors);

        for (Cell neighbor : neighbors) {
            if (!visited.contains(neighbor) && neighbor.getState() != CellState.WALL) {
                visited.add(neighbor);
                parentMap.put(neighbor, current);
                stack.push(neighbor);
                currentSolverResults.addVisitedCell(neighbor);
                System.out.println("Reportando celda visitada: (" + neighbor.getRow() + ", " + neighbor.getCol() + ") - Total de celdas visitadas en results: " + currentSolverResults.getVisitedCells().size());
            }
        }

        return true;
    }

    private void reconstructPath() {
        List<Cell> path = new ArrayList<>();
        Cell current = endCell;
        while (current != null) {
            path.add(current);
            if (current.equals(startCell)) break;
            current = parentMap.get(current);
        }
        Collections.reverse(path);
        currentSolverResults.setPath(path);
    }

    @Override
    public SolverResults getCurrentResults() {
        return currentSolverResults;
    }

    @Override
    public void reset() {
        if (stack != null) stack.clear();
        if (visited != null) visited.clear();
        if (parentMap != null) parentMap.clear();
        this.currentSolverResults = new SolverResults();
        this.foundPath = false;
    }

    @Override
    public SolverResults getFinalPath() {
        return currentSolverResults;
    }
}