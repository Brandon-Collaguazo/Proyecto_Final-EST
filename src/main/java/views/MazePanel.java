package views;

import models.Cell;
import models.CellState;

import javax.swing.*;
import java.awt.*;

public class MazePanel extends JPanel {

    private Cell[][] maze;
    private int rows;
    private int cols;
    private Cell startCell;
    private Cell endCell;

    public MazePanel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.maze = new Cell[rows][cols];
        setLayout(new GridLayout(rows, cols));
        inicializarMaze();
    }

    private void inicializarMaze() {
        removeAll();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j] = new Cell(i, j, CellState.EMPTY, false);
                JButton button = crearBotonCelda(i, j);
                add(button);
            }
        }
        revalidate();
        repaint();
    }

    private JButton crearBotonCelda(int row, int col) {
        JButton button = new JButton();
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        button.setBackground(obtenerColorEstado(maze[row][col].getState()));
        button.addActionListener(e -> manejarClickCelda(row, col));
        return button;
    }

    private void manejarClickCelda(int row, int col) {
        Cell cell = maze[row][col];
        CellState currentState = cell.getState();

        if (currentState == CellState.EMPTY) {
            if (startCell == null) {
                cell.setState(CellState.START);
                startCell = cell;
            } else if (endCell == null) {
                cell.setState(CellState.END);
                endCell = cell;
            } else {
                cell.setState(CellState.WALL);
            }
        } else {
            if (cell.equals(startCell)) {
                startCell = null;
            } else if (cell.equals(endCell)) {
                endCell = null;
            }
            cell.setState(CellState.EMPTY);
        }

        actualizarVista();
    }

    public void actualizarVista() {
        Component[] components = getComponents();
        for (int i = 0; i < components.length; i++) {
            int row = i / cols;
            int col = i % cols;
            CellState state = maze[row][col].getState();
            components[i].setBackground(obtenerColorEstado(state));
        }
        repaint();
    }

    private Color obtenerColorEstado(CellState state) {
        return switch (state) {
            case EMPTY -> Color.WHITE;
            case WALL -> Color.BLACK;
            case START -> Color.GREEN;
            case END -> Color.RED;
            case VISITED -> Color.YELLOW;
            case PATH -> Color.CYAN;
        };
    }

    public void limpiarCamino() {
        for (Cell[] fila : maze) {
            for (Cell celda : fila) {
                if (celda.getState() == CellState.PATH || celda.getState() == CellState.VISITED) {
                    celda.setState(CellState.EMPTY);
                    celda.setVisited(false);
                }
            }
        }
        actualizarVista();
    }

    public void reiniciarMaze() {
        this.startCell = null;
        this.endCell = null;
        inicializarMaze();
    }

    public void clearMaze() {
        for (Cell[] fila : maze) {
            for (Cell celda : fila) {
                celda.setState(CellState.EMPTY);
                celda.setVisited(false);
            }
        }
        startCell = null;
        endCell = null;
        actualizarVista();
    }


    public Cell[][] getMaze() {
        return maze;
    }

    public Cell getStartCell() {
        return startCell;
    }

    public Cell getEndCell() {
        return endCell;
    }
}
