package solver.imple;

import models.Cell;
import models.CellState;
import models.SolverResults;
import solver.MazeSolver;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet; // Cambiado a HashSet por eficiencia para 'visited'
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class MazeSolverBFS implements MazeSolver {

    private Cell[][] maze;
    private Cell startCell;
    private Cell endCell;
    private Queue<Cell> queue;
    private Set<Cell> visited;
    private Map<Cell, Cell> parentMap;
    private SolverResults currentSolverResults;
    private boolean foundPath = false;

    private List<Cell> getNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        int r = cell.getRow();
        int c = cell.getCol();
        int[] dr = {-1, 1, 0, 0}; // Arriba, Abajo
        int[] dc = {0, 0, -1, 1}; // Izquierda, Derecha
        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            if (nr >= 0 && nr < maze.length && nc >= 0 && nc < maze[0].length &&
                    maze[nr][nc].getState() != CellState.WALL) {
                neighbors.add(maze[nr][nc]);
            }
        }
        return neighbors;
    }

    @Override
    public void initialize(Cell[][] maze, Cell start, Cell end) {
        this.maze = maze;
        this.startCell = start;
        this.endCell = end;
        this.queue = new ArrayDeque<>();
        this.visited = new HashSet<>();
        this.parentMap = new HashMap<>();
        this.currentSolverResults = new SolverResults();
        this.foundPath = false;

        queue.offer(startCell);
        visited.add(startCell);
        currentSolverResults.addVisitedCell(startCell);
        parentMap.put(startCell, null);
    }

    @Override
    public boolean step() {
        if (foundPath || queue.isEmpty()) {
            return false;
        }

        Cell current = queue.poll();

        if (current.equals(endCell)) {
            foundPath = true;
            reconstructPath();
            return false;
        }

        for (Cell neighbor : getNeighbors(current)) {
            if (!visited.contains(neighbor)) {
                visited.add(neighbor);
                parentMap.put(neighbor, current);
                queue.offer(neighbor);
                currentSolverResults.addVisitedCell(neighbor);
            }
        }
        return true;
    }

    private void reconstructPath() {
        List<Cell> path = new ArrayList<>();
        Cell current = endCell;
        while (current != null) {
            path.add(0, current);
            if (current.equals(startCell)) break;
            current = parentMap.get(current);
        }
        currentSolverResults.setPath(path);
    }

    @Override
    public SolverResults getCurrentResults() {
        return currentSolverResults;
    }

    @Override
    public void reset() {
        if (queue != null) queue.clear();
        if (visited != null) visited.clear();
        if (parentMap != null) parentMap.clear();
        this.currentSolverResults = new SolverResults();
        this.foundPath = false;
        this.maze = null;
        this.startCell = null;
        this.endCell = null;
    }

    @Override
    public SolverResults getFinalPath() {
        return currentSolverResults;
    }
}