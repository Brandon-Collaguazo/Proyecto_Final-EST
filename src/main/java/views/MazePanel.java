package views;

import javax.swing.*;
import java.awt.*;

public class MazePanel extends JPanel {
    private JButton[][] gridCells;
    private int mazeRow;
    private int mazeCol;

    public MazePanel(int mazeRow, int mazeCol) {
        this.mazeRow = mazeRow;
        this.mazeCol = mazeCol;
        setLayout(new GridLayout(mazeRow, mazeCol));
        initGrid();
    }

    private void initGrid() {
        gridCells = new JButton[mazeRow][mazeCol];
        for (int i = 0; i < mazeRow; i++) {
            for (int j = 0; j < mazeCol; j++) {
                gridCells[i][j] = new JButton();
                gridCells[i][j].setOpaque(true);
                gridCells[i][j].setBackground(Color.WHITE);
                gridCells[i][j].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                add(gridCells[i][j]);
            }
        }
    }

    public JButton[][] getGridCells() {
        return gridCells;
    }

    public int getMazeRow() {
        return mazeRow;
    }

    public int getMazeCol() {
        return mazeCol;
    }

}
