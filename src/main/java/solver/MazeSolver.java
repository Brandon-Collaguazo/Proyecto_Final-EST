package solver;

import models.Cell;
import models.SolverResults;

public interface MazeSolver {
    void initialize(Cell[][] maze, Cell start, Cell end);
    boolean step();
    SolverResults getCurrentResults();
    void reset();
    SolverResults getFinalPath();
}