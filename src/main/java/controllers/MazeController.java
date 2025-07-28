package controllers;

import dao.AlgorithmResultDAO;
import models.AlgorithmResult;
import models.Cell;
import models.CellState;
import solver.MazeSolver;
import solver.imple.*;
import views.MazePanel;
import views.ResultadosDialog;

import javax.swing.*;
import java.util.List;


public class MazeController {

    private final MazePanel mazePanel;
    private final AlgorithmResultDAO dao;

    public MazeController(MazePanel mazePanel, AlgorithmResultDAO dao) {
        this.mazePanel = mazePanel;
        this.dao = dao;
    }

    public void solverMaze(String tipoAlgoritmo) {
        Cell[][] laberinto = mazePanel.getMaze();
        Cell inicio = mazePanel.getStartCell();  // Corregido: getStartCell()
        Cell fin = mazePanel.getEndCell();

        if (inicio == null || fin == null) {
            JOptionPane.showMessageDialog(null, "Selecciona una celda de inicio y una de fin.");
            return;
        }

        MazeSolver solver = obtenerAlgoritmo(tipoAlgoritmo);
        if (solver == null) {
            JOptionPane.showMessageDialog(null, " Algoritmo no válido: " + tipoAlgoritmo);
            return;
        }

        limpiarCeldas(laberinto);

        long tiempoInicio = System.nanoTime();
        boolean exito = solver.solve(laberinto, inicio, fin);
        long tiempoFin = System.nanoTime();

        if (!exito) {
            JOptionPane.showMessageDialog(null, " No se encontró ruta entre A y B.");
            return;
        }

        long duracionMs = (tiempoFin - tiempoInicio) / 1_000_000;
        int pasos = solver.getSteps();

        AlgorithmResult resultado = new AlgorithmResult(tipoAlgoritmo, pasos, duracionMs);
        dao.guardarResultado(resultado);
        mazePanel.repaint();

        JOptionPane.showMessageDialog(null,
                " Algoritmo: " + tipoAlgoritmo +
                        "\n Pasos: " + pasos +
                        "\n Tiempo: " + duracionMs + " ms"
        );
    }

    public void mostrarResultados() {
        List<AlgorithmResult> list = dao.obtenerTodos();
        ResultadosDialog dialog = new ResultadosDialog(list);
        dialog.setVisible(true);
    }

    public void limpiarResultados() {
        dao.eliminarTodos();
        JOptionPane.showMessageDialog(null, " Resultados eliminados con éxito.");
    }

    private MazeSolver obtenerAlgoritmo(String tipoAlgoritmo) {
        return switch (tipoAlgoritmo) {
            case "Recursivo 2D" -> new MazeSolverRecursivo();
            case "Recursivo 4D" -> new MazeSolverRecursivoCompleto();
            case "Recursivo BT" -> new MazeSolverRecursivoCompletoBT();
            case "BFS" -> new MazeSolverBFS();
            case "DFS" -> new MazeSolverDFS();
            default -> null;
        };
    }

    private void limpiarCeldas(Cell[][] laberinto) {
        for (Cell[] fila : laberinto) {
            for (Cell celda : fila) {
                if (celda.getEstado() == CellState.VISITED || celda.getEstado() == CellState.PATH) {
                    celda.setEstado(CellState.FREE);
                    celda.setVisitado(false);
                }
            }
        }
    }
}