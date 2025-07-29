package controllers;

import dao.AlgorithmResultDAO;
import models.AlgorithmResult;
import models.Cell;
import models.CellState;
import models.SolverResults;
import solver.MazeSolver;
import solver.imple.*;
import views.MazePanel;
import views.ResultadosDialog; // Importar ResultadosDialog

import javax.swing.*;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

public class MazeController {

    private final MazePanel mazePanel;
    private final AlgorithmResultDAO dao;
    private MazeSolver currentSolver;
    private SwingWorker<SolverResults, Cell> currentSolveWorker;
    private boolean isStepByStepMode = false;
    private boolean isSolving = false;
    private int currentStepCount = 0;

    public MazeController(MazePanel mazePanel, AlgorithmResultDAO dao) {
        this.mazePanel = mazePanel;
        this.dao = dao;
    }

    public void startAutomaticSolving(String tipoAlgoritmo) {
        if (isSolving) {
            JOptionPane.showMessageDialog(mazePanel, "Ya hay una resolución en curso.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cell inicio = mazePanel.getStartCell();
        Cell fin = mazePanel.getEndCell();
        if (inicio == null || fin == null) {
            JOptionPane.showMessageDialog(mazePanel, "Por favor, define las celdas de inicio y fin.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (inicio.getState() == CellState.WALL || fin.getState() == CellState.WALL) {
            JOptionPane.showMessageDialog(mazePanel, "Las celdas de inicio y fin no pueden ser paredes.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        limpiarCeldas(mazePanel.getMaze());
        mazePanel.repaint();
        currentStepCount = 0;

        currentSolver = obtenerAlgoritmo(tipoAlgoritmo);
        if (currentSolver == null) {
            JOptionPane.showMessageDialog(mazePanel, "Algoritmo no válido: " + tipoAlgoritmo, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        currentSolver.initialize(mazePanel.getMaze(), inicio, fin);
        isSolving = true;
        isStepByStepMode = false;

        currentSolveWorker = new SwingWorker<SolverResults, Cell>() {
            private long startTime;
            @Override
            protected SolverResults doInBackground() throws Exception {
                startTime = System.nanoTime();
                while (!isCancelled() && currentSolver.step()) {
                    currentStepCount++;
                    SolverResults currentResults = currentSolver.getCurrentResults();
                    if (currentResults != null && currentResults.getVisitedCells() != null && !currentResults.getVisitedCells().isEmpty()) {
                        Cell lastVisited = currentResults.getVisitedCells().stream()
                                .reduce((first, second) -> second)
                                .orElse(null);
                        if (lastVisited != null && lastVisited.getState() != CellState.START && lastVisited.getState() != CellState.END) {
                            publish(new Cell(lastVisited.getRow(), lastVisited.getCol(), CellState.VISITED, false));
                        }
                    }
                    Thread.sleep(1);
                }
                return currentSolver.getFinalPath();
            }

            @Override
            protected void process(List<Cell> chunks) {
                for (Cell cell : chunks) {
                    mazePanel.updateCellState(cell.getRow(), cell.getCol(), cell.getState());
                }
            }

            @Override
            protected void done() {
                try {
                    SolverResults finalResults = get();
                    if (isCancelled()) {
                        JOptionPane.showMessageDialog(mazePanel, "La resolución del laberinto fue cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                        limpiarCeldas(mazePanel.getMaze());
                        mazePanel.repaint();
                        return;
                    }

                    if (finalResults != null && finalResults.getPath() != null && !finalResults.getPath().isEmpty()) {
                        animatePath(finalResults.getPath());
                        long durationMs = (System.nanoTime() - startTime) / 1_000_000;

                        AlgorithmResult result = new AlgorithmResult(currentSolver.getClass().getSimpleName(), currentStepCount, durationMs);

                        try {
                            dao.guardar(result);
                        } catch (IOException ioException) {
                            JOptionPane.showMessageDialog(mazePanel, "Error al guardar el resultado: " + ioException.getMessage(), "Error de E/S", JOptionPane.ERROR_MESSAGE);
                            ioException.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(mazePanel, "No se encontró un camino.", "Resultado", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    if (e instanceof CancellationException) {
                        JOptionPane.showMessageDialog(mazePanel, "Resolución cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(mazePanel, "Error durante la resolución: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } finally {
                    isSolving = false;
                    currentSolver.reset();
                    mazePanel.repaint();
                }
            }
        };
        currentSolveWorker.execute();
    }

    public void startStepByStepMode(String tipoAlgoritmo) {
        if (isSolving) {
            JOptionPane.showMessageDialog(mazePanel, "Ya hay una resolución en curso. Deténgala primero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Cell inicio = mazePanel.getStartCell();
        Cell fin = mazePanel.getEndCell();
        if (inicio == null || fin == null) {
            JOptionPane.showMessageDialog(mazePanel, "Por favor, define las celdas de inicio y fin.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (inicio.getState() == CellState.WALL || fin.getState() == CellState.WALL) {
            JOptionPane.showMessageDialog(mazePanel, "Las celdas de inicio y fin no pueden ser paredes.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        limpiarCeldas(mazePanel.getMaze());
        mazePanel.repaint();
        currentStepCount = 0;

        currentSolver = obtenerAlgoritmo(tipoAlgoritmo);
        if (currentSolver == null) {
            JOptionPane.showMessageDialog(mazePanel, "Algoritmo no válido: " + tipoAlgoritmo, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        currentSolver.initialize(mazePanel.getMaze(), inicio, fin);
        isSolving = true;
        isStepByStepMode = true;

        JOptionPane.showMessageDialog(mazePanel, "Modo 'Paso a Paso' activado. Pulsa 'Paso a Paso' para avanzar.", "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public void performOneStep() {
        if (!isSolving || !isStepByStepMode) {
            JOptionPane.showMessageDialog(mazePanel, "Inicia el modo 'Paso a Paso' primero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (currentSolver.getFinalPath() != null && !currentSolver.getFinalPath().getPath().isEmpty()) {
            JOptionPane.showMessageDialog(mazePanel, "El laberinto ya fue resuelto.", "Información", JOptionPane.INFORMATION_MESSAGE);
            isSolving = false;
            isStepByStepMode = false;
            currentSolver.reset();
            limpiarCeldas(mazePanel.getMaze());
            mazePanel.repaint();
            return;
        }

        new SwingWorker<Boolean, Cell>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                boolean moreSteps = currentSolver.step();
                currentStepCount++;
                SolverResults results = currentSolver.getCurrentResults();
                if (results != null && results.getVisitedCells() != null && !results.getVisitedCells().isEmpty()) {
                    Cell lastVisited = results.getVisitedCells().stream()
                            .reduce((first, second) -> second)
                            .orElse(null);
                    if (lastVisited != null && lastVisited.getState() != CellState.START && lastVisited.getState() != CellState.END) {
                        publish(new Cell(lastVisited.getRow(), lastVisited.getCol(), CellState.VISITED, false));
                    }
                }
                return moreSteps;
            }

            @Override
            protected void process(List<Cell> chunks) {
                for (Cell cell : chunks) {
                    mazePanel.updateCellState(cell.getRow(), cell.getCol(), cell.getState());
                }
            }

            @Override
            protected void done() {
                try {
                    boolean moreSteps = get();
                    if (!moreSteps) {
                        SolverResults finalResults = currentSolver.getFinalPath();
                        if (finalResults != null && finalResults.getPath() != null && !finalResults.getPath().isEmpty()) {
                            animatePath(finalResults.getPath());
                        } else {
                            JOptionPane.showMessageDialog(mazePanel, "No se encontró un camino o se agotaron los pasos.", "Resultado", JOptionPane.INFORMATION_MESSAGE);
                        }
                        isSolving = false;
                        isStepByStepMode = false;
                        currentSolver.reset();
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(mazePanel, "Error en el paso: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    mazePanel.repaint();
                }
            }
        }.execute();
    }

    public void stopSolving() {
        if (currentSolveWorker != null && !currentSolveWorker.isDone()) {
            currentSolveWorker.cancel(true);
        }
        isSolving = false;
        isStepByStepMode = false;
        if(currentSolver != null) currentSolver.reset();
        limpiarCeldas(mazePanel.getMaze());
        mazePanel.repaint();
    }

    public void newMaze(int rows, int cols) {
        stopSolving();
        mazePanel.reinitializeGrid(rows, cols);
        mazePanel.repaint();
    }

    public void showResults() {
        List<AlgorithmResult> results = null;
        try {
            results = dao.listar();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mazePanel, "Error al cargar los resultados: " + e.getMessage(), "Error de E/S", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            results = new java.util.ArrayList<>(); // Asegurarse de que no sea null
        }

        // Crear y mostrar el diálogo de resultados, pasando los resultados
        ResultadosDialog dialog = new ResultadosDialog(null, results);
        dialog.setVisible(true);
    }

    public void limpiarResultados() {
        try {
            dao.eliminarTodos();
            JOptionPane.showMessageDialog(null, "Resultados eliminados con éxito.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar resultados: " + e.getMessage(), "Error de E/S", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private MazeSolver obtenerAlgoritmo(String tipoAlgoritmo) {
        return switch (tipoAlgoritmo) {
            case "Recursivo 2 direcciones" -> new MazeSolverRecursivo();
            case "Recursivo completo" -> new MazeSolverRecursivoCompleto();
            case "Recursivo completo BT" -> new MazeSolverRecursivoCompletoBT();
            case "BFS (Breadth-First Search)" -> new MazeSolverBFS();
            case "DFS (Depth-First Search)" -> new MazeSolverDFS();
            default -> null;
        };
    }

    private void limpiarCeldas(Cell[][] laberinto) {
        if (laberinto == null) return;
        for (Cell[] fila : laberinto) {
            for (Cell celda : fila) {
                if (celda.getState() == CellState.VISITED || celda.getState() == CellState.PATH) {
                    celda.setState(CellState.EMPTY);
                }
                // Solo resetear el estado visitado si no es START, END o WALL
                if (celda.getState() != CellState.START && celda.getState() != CellState.END && celda.getState() != CellState.WALL) {
                    celda.setVisited(false);
                    mazePanel.updateCellState(celda.getRow(), celda.getCol(), celda.getState());
                }
            }
        }
        mazePanel.resetMazeColors(); // Asegura que los colores se actualicen en la UI
    }

    private void animatePath(List<Cell> path) {
        new SwingWorker<Void, Cell>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (Cell cell : path) {
                    if (isCancelled()) break;
                    // No cambiar el color de inicio y fin
                    if (cell.getState() != CellState.START && cell.getState() != CellState.END) {
                        publish(new Cell(cell.getRow(), cell.getCol(), CellState.PATH, false));
                    }
                    Thread.sleep(50); // Pequeña pausa para la animación
                }
                return null;
            }
            @Override
            protected void process(List<Cell> chunks) {
                for (Cell cell : chunks) {
                    mazePanel.updateCellState(cell.getRow(), cell.getCol(), cell.getState());
                }
            }
        }.execute();
    }

    public boolean isSolving() {
        return isSolving;
    }
    public boolean isStepByStepMode() {
        return isStepByStepMode;
    }
}
