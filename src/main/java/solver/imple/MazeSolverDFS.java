package solver.imple;

import models.Cell;
import models.CellState;
import models.SolverResults;
import solver.MazeSolver;

import java.util.*;

public class MazeSolverDFS implements MazeSolver {

    private Cell[][] maze;
    private Cell startCell;
    private Cell endCell;
    private SolverResults currentResults;
    private Stack<Cell> stack;
    private Set<Cell> visited;
    private Map<Cell, Cell> parentMap;

    public MazeSolverDFS() {
        this.stack = new Stack<>();
        this.visited = new HashSet<>();
        this.parentMap = new HashMap<>();
        this.currentResults = new SolverResults();
    }

    @Override
    public void initialize(Cell[][] maze, Cell startCell, Cell endCell) {
        this.maze = maze;
        this.startCell = startCell;
        this.endCell = endCell;
        this.stack.clear();
        this.visited.clear();
        this.parentMap.clear();
        this.currentResults = new SolverResults();

        stack.push(startCell);
        visited.add(startCell);
        currentResults.addVisitedCell(startCell);
    }

    @Override
    public boolean step() {
        if (stack.isEmpty()) {
            return false;
        }

        Cell current = stack.pop();

        if (current.equals(endCell)) {
            currentResults.setPath(reconstructPath(parentMap, startCell, endCell));
            return false;
        }

        if (current != startCell && current != endCell) {
            current.setState(CellState.VISITED);
        }
        currentResults.addVisitedCell(current);

        List<Cell> neighbors = getNeighbors(current);
        Collections.shuffle(neighbors);

        for (Cell neighbor : neighbors) {
            if (!visited.contains(neighbor) && neighbor.getState() != CellState.WALL) {
                visited.add(neighbor);
                stack.push(neighbor);
                parentMap.put(neighbor, current);
            }
        }
        return true;
    }

    @Override
    public SolverResults getFinalPath() {
        return currentResults;
    }

    @Override
    public SolverResults getCurrentResults() {
        return currentResults;
    }

    @Override
    public void reset() {
        this.stack.clear();
        this.visited.clear();
        this.parentMap.clear();
        this.currentResults = new SolverResults();
    }

    private List<Cell> getNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        int row = cell.getRow();
        int col = cell.getCol();

        if (row > 0) neighbors.add(maze[row - 1][col]);
        if (row < maze.length - 1) neighbors.add(maze[row + 1][col]);
        if (col > 0) neighbors.add(maze[row][col - 1]);
        if (col < maze[0].length - 1) neighbors.add(maze[row][col + 1]);

        return neighbors;
    }

    private List<Cell> reconstructPath(Map<Cell, Cell> parentMap, Cell start, Cell end) {
        List<Cell> path = new ArrayList<>();
        Cell current = end;
        while (current != null && !current.equals(start)) {
            path.add(current);
            current = parentMap.get(current);
        }
        if (current != null && current.equals(start)) {
            path.add(start);
        }
        Collections.reverse(path);
        return path;
    }
}