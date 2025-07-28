package controllers;

import dao.AlgorithmResultDAO;
import models.AlgorithmResult;
import models.Cell;
import models.CellState;
import models.SolverResults;
import solver.MazeSolver;
import solver.imple.*;
import views.MazePanel;
import views.ResultadosDialog;

import javax.swing.*;
import java.util.List;
import java.util.Set;


public class MazeController {

    private final MazePanel mazePanel;
    private final AlgorithmResultDAO dao;


    public MazeController(MazePanel mazePanel, AlgorithmResultDAO dao) {
        this.mazePanel = mazePanel;
        this.dao = dao;
    }


    public void solverMaze(String tipoAlgoritmo) {
        Cell[][] laberinto = mazePanel.getMaze();
        Cell inicio = mazePanel.getStartCell();
        Cell fin = mazePanel.getEndCell();

        if (inicio == null || fin == null) {
            JOptionPane.showMessageDialog(null, "Selecciona una celda de inicio y una de fin.");
            return;
        }

        MazeSolver solver = obtenerAlgoritmo(tipoAlgoritmo);
        if (solver == null) {
            JOptionPane.showMessageDialog(null, "Algoritmo no válido: " + tipoAlgoritmo);
            return;
        }

        limpiarCeldas(laberinto);

        long tiempoInicio = System.nanoTime();
        SolverResults resultados = solver.solver(laberinto, inicio, fin);
        long tiempoFin = System.nanoTime();

        if (resultados.getPath().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontró ruta entre A y B.");
            return;
        }

        marcarVisitados(resultados.getVisited());
        marcarCamino(resultados.getPath());

        long duracionMs = (tiempoFin - tiempoInicio) / 1_000_000;
        int pasos = resultados.getPath().size();

        AlgorithmResult resultado = new AlgorithmResult(tipoAlgoritmo, pasos, duracionMs);
        dao.guardarResultado(resultado);

        mazePanel.repaint();

        JOptionPane.showMessageDialog(null,
                " Algoritmo: " + tipoAlgoritmo +
                        "\n Pasos: " + pasos +
                        "\n Tiempo: " + duracionMs + " ms");
    }


    public void mostrarResultados() {
        ResultadosDialog dialog = new ResultadosDialog(null); // o puedes pasar el JFrame si lo tienes
        List<AlgorithmResult> resultados = dao.obtenerTodos();
        dialog.cargarResultados(resultados); // usa el método que creaste
        dialog.setVisible(true);
    }



    public void limpiarResultados() {
        dao.eliminarTodos();
        JOptionPane.showMessageDialog(null, "Resultados eliminados con éxito.");
    }


    public void clearMaze() {
        mazePanel.clearMaze();
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
        for (Cell[] fila : laberinto) {
            for (Cell celda : fila) {
                if (celda.getState() == CellState.VISITED || celda.getState() == CellState.PATH) {
                    celda.setState(CellState.EMPTY);
                    celda.setVisited(false);
                }
            }
        }
    }

    private void marcarVisitados(Set<Cell> visitados) {
        for (Cell celda : visitados) {
            if (celda.getState() == CellState.EMPTY) {
                celda.setState(CellState.VISITED);
            }
        }
    }

    private void marcarCamino(List<Cell> path) {
        for (Cell celda : path) {
            if (celda.getState() == CellState.EMPTY || celda.getState() == CellState.VISITED) {
                celda.setState(CellState.PATH);
            }
        }
    }
}
