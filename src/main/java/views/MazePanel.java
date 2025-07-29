package views;

import models.Cell;
import models.CellState;

import javax.swing.*;
import java.awt.*;

public class MazePanel extends JPanel {
    private JButton[][] gridButtons;
    private Cell[][] mazeCells;
    private int mazeRow;
    private int mazeCol;

    private Cell startCell;
    private Cell endCell;

    public MazePanel(int mazeRow, int mazeCol) {
        this.mazeRow = mazeRow;
        this.mazeCol = mazeCol;
        setLayout(new GridLayout(mazeRow, mazeCol));
        initGrid();
    }

    private void initGrid() {
        gridButtons = new JButton[mazeRow][mazeCol];
        mazeCells = new Cell[mazeRow][mazeCol];

        for (int i = 0; i < mazeRow; i++) {
            for (int j = 0; j < mazeCol; j++) {
                mazeCells[i][j] = new Cell(i, j, CellState.EMPTY, false);
                gridButtons[i][j] = new JButton();
                gridButtons[i][j].setOpaque(true);
                gridButtons[i][j].setBackground(mazeCells[i][j].getColor());
                gridButtons[i][j].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                add(gridButtons[i][j]);
            }
        }
    }
    public void limpiarCamino() {
        for (int i = 0; i < mazeRow; i++) {
            for (int j = 0; j < mazeCol; j++) {
                Cell cell = mazeCells[i][j];
                cell.setState(CellState.EMPTY);         // borra todo tipo de estado
                cell.setVisited(false);                 // desmarca como visitado
                gridButtons[i][j].setBackground(cell.getColor());
            }
        }
        startCell = null;
        endCell = null;
    }




    public void updateCellState(int row, int col, CellState newState) {
        if (row >= 0 && row < mazeRow && col >= 0 && col < mazeCol) {
            Cell cell = mazeCells[row][col];
            CellState oldState = cell.getState();
            if (newState == CellState.START) {
                if (startCell != null && startCell != cell) {
                    mazeCells[startCell.getRow()][startCell.getCol()].setState(CellState.EMPTY);
                    gridButtons[startCell.getRow()][startCell.getCol()].setBackground(mazeCells[startCell.getRow()][startCell.getCol()].getColor());
                }
                startCell = cell;
            } else if (newState == CellState.END) {
                if (endCell != null && endCell != cell) {
                    mazeCells[endCell.getRow()][endCell.getCol()].setState(CellState.EMPTY);
                    gridButtons[endCell.getRow()][endCell.getCol()].setBackground(mazeCells[endCell.getRow()][endCell.getCol()].getColor());
                }
                endCell = cell;
            } else if (oldState == CellState.START && newState != CellState.START) {
                startCell = null;
            } else if (oldState == CellState.END && newState != CellState.END) {
                endCell = null;
            }

            cell.setState(newState);
            gridButtons[row][col].setBackground(cell.getColor());
        }
    }

    public void reinitializeGrid(int newMazeRow, int newMazeCol) {
        this.mazeRow = newMazeRow;
        this.mazeCol = newMazeCol;
        this.removeAll();
        setLayout(new GridLayout(this.mazeRow, this.mazeCol));
        initGrid();
        startCell = null;
        endCell = null;
        revalidate(); // Valida el nuevo layout
        repaint();    // Repinta el componente
    }

    public void resetMazeColors() {
        for (int i = 0; i < mazeRow; i++) {
            for (int j = 0; j < mazeCol; j++) {
                Cell cell = mazeCells[i][j];
                gridButtons[i][j].setBackground(cell.getColor());
            }
        }
        repaint(); // Asegura el repintado de todo el panel
    }

    public Cell getCell(int row, int col) {
        if (row >= 0 && row < mazeRow && col >= 0 && col < mazeCol) {
            return mazeCells[row][col];
        }
        return null;
    }

    public Cell[][] getMaze() {
        return mazeCells;
    }

    public JButton[][] getGridButtons() {
        return gridButtons;
    }

    public Cell getStartCell() {
        return startCell;
    }

    public Cell getEndCell() {
        return endCell;
    }

    public int getMazeRow() {
        return mazeRow;
    }

    public int getMazeCol() {
        return mazeCol;
    }
}