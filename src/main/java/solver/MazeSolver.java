package solver;

import models.Cell;
import models.SolverResults;

public interface MazeSolver {

    SolverResults solver(Cell[][] grid, Cell start, Cell end);

}
