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
    private List<Cell> pasosActuales;
    private Set<Cell> visitadosActuales;
    private int pasoIndex;


    public MazeController(MazePanel mazePanel, AlgorithmResultDAO dao) {
        this.mazePanel = mazePanel;
        this.dao = dao;
    }

    public AlgorithmResult solverMaze(String tipoAlgoritmo) {
        Cell[][] laberinto = mazePanel.getMaze();
        Cell inicio = mazePanel.getStartCell();
        Cell fin = mazePanel.getEndCell();

        if (inicio == null || fin == null) {
            JOptionPane.showMessageDialog(null, "Selecciona una celda de inicio y una de fin.");
            return null;
        }

        if (inicio.getState() == CellState.WALL || fin.getState() == CellState.WALL) {
            JOptionPane.showMessageDialog(null, "Las celdas de inicio y fin no pueden ser paredes. Seleccione celdas transitables.");
            return null;
        }

        MazeSolver solver = obtenerAlgoritmo(tipoAlgoritmo);
        if (solver == null) {
            JOptionPane.showMessageDialog(null, "Algoritmo no válido: " + tipoAlgoritmo);
            return null;
        }

        limpiarCeldas(laberinto);
        mazePanel.repaint();

        long tiempoInicio = System.nanoTime();
        SolverResults exito = solver.solver(laberinto, inicio, fin);
        long tiempoFin = System.nanoTime();

        if (exito.getPath() == null || exito.getPath().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontró ruta.", "Sin Ruta", JOptionPane.INFORMATION_MESSAGE);
            mazePanel.repaint();
            return null;
        }


        marcarVisitados(exito.getVisited());
        marcarCamino(exito.getPath());

        long duracionMs = (tiempoFin - tiempoInicio) / 1_000_000;
        int pasos = exito.getPath().size();

        AlgorithmResult finalResult = new AlgorithmResult(tipoAlgoritmo, pasos, duracionMs);
        dao.guardarResultado(finalResult);
        mazePanel.repaint();
        return finalResult;
    }

    public void showResults() {
        List<AlgorithmResult> results = dao.obtenerTodos();
        ResultadosDialog dialog = new ResultadosDialog(null);
        dialog.cargarResultados(results);
        dialog.setVisible(true);
    }

    public void limpiarResultados() {
        dao.eliminarTodos();
        JOptionPane.showMessageDialog(null, "Resultados eliminados con éxito.");
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
                // Solo limpiar celdas que fueron visitadas o parte de un camino
                if (celda.getState() == CellState.VISITED || celda.getState() == CellState.PATH) {
                    celda.setState(CellState.EMPTY);
                }
                celda.setVisited(false);
            }
        }
    }

    private void marcarVisitados(Set<Cell> visitados) {
        if (visitados == null) return;
        for (Cell celda : visitados) {
            if (celda.getState() != CellState.START && celda.getState() != CellState.END) {
                mazePanel.updateCellState(celda.getRow(), celda.getCol(), CellState.VISITED);
            }
        }
    }
    public void prepararPasoAPaso(String tipoAlgoritmo) {
        Cell[][] laberinto = mazePanel.getMaze();
        Cell inicio = mazePanel.getStartCell();
        Cell fin = mazePanel.getEndCell();

        if (inicio == null || fin == null) {
            JOptionPane.showMessageDialog(null, "Selecciona inicio y fin.");
            return;
        }

        MazeSolver solver = obtenerAlgoritmo(tipoAlgoritmo);
        if (solver == null) return;

        limpiarCeldas(laberinto);
        mazePanel.repaint();

        SolverResults resultado = solver.solver(laberinto, inicio, fin);

        this.pasosActuales = resultado.getPath();
        this.visitadosActuales = resultado.getVisited();
        this.pasoIndex = 0;
    }

    public void siguientePaso() {
        if (pasosActuales == null || pasoIndex >= pasosActuales.size()) {
            JOptionPane.showMessageDialog(null, "No hay más pasos.");
            return;
        }

        Cell paso = pasosActuales.get(pasoIndex);
        if (paso.getState() == CellState.EMPTY || paso.getState() == CellState.VISITED) {
            mazePanel.updateCellState(paso.getRow(), paso.getCol(), CellState.PATH);
        }
        pasoIndex++;
        mazePanel.repaint();
    }



    private void marcarCamino(List<Cell> path) {
        if (path == null) return;
        for (Cell celda : path) {
            if (celda.getState() == CellState.EMPTY || celda.getState() == CellState.VISITED) {
                mazePanel.updateCellState(celda.getRow(), celda.getCol(), CellState.PATH);
            }
        }
        Cell inicio = mazePanel.getStartCell();
        if (inicio != null) {
            mazePanel.updateCellState(inicio.getRow(), inicio.getCol(), CellState.START);
        }
        Cell fin = mazePanel.getEndCell();
        if (fin != null) {
            mazePanel.updateCellState(fin.getRow(), fin.getCol(), CellState.END);
        }
    }
    public int getPasoIndex() {
        return pasoIndex;
    }

}
