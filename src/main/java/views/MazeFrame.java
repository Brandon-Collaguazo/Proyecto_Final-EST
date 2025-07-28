package views;

import models.CellState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MazeFrame extends JFrame {
    private JMenuBar menuBar;
    private JMenu menuArchivo;
    private JMenu menuAyuda;
    private JMenuItem itemLaberinto;
    private JMenuItem itemResultados;
    private JMenuItem itemInfo;

    private JPanel pnlPrincipal;
    private JPanel pnlSup;
    private MazePanel mazePanel;
    private JPanel pnlInf;

    private ButtonGroup buttonGroup;
    private JToggleButton btnStart;
    private JToggleButton btnEnd;
    private JToggleButton btnWall;

    private JComboBox cbxAlgoritmo;
    private JButton btnSolve;
    private JButton btnStep;
    private JButton btnClean;

    private int mazeRow;
    private int mazeCol;

    public MazeFrame() {
        inputDimensions();
        initComponents();
    }

    private void inputDimensions() {
        String rowInput = JOptionPane.showInputDialog(
                this,
                "Ingrese el número de filas:",
                "Configuración del Laberinto :D",
                JOptionPane.QUESTION_MESSAGE
        );

        String colInput = JOptionPane.showInputDialog(
                this,
                "Ingrese el número de columnas:",
                "Configuración del Laberinto :D",
                JOptionPane.QUESTION_MESSAGE
        );

        try {
            mazeRow = Integer.parseInt(rowInput);
            mazeCol = Integer.parseInt(colInput);

            if (mazeRow < 5 || mazeCol < 5) {
                JOptionPane.showMessageDialog(
                        this,
                        "El laberinto debe ser de al menos 5x5",
                        "Tamaño insuficiente",
                        JOptionPane.WARNING_MESSAGE
                );
                inputDimensions();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar números válidos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            inputDimensions();
        }
    }

    private void initComponents() {
        menuBar = new JMenuBar();

        menuArchivo = new JMenu("Archivo");
        menuAyuda = new JMenu("Ayuda");

        itemLaberinto = new JMenuItem("Nuevo laberinto");
        itemResultados = new JMenuItem("Ver resultados");
        itemInfo = new JMenuItem("Acerca de");

        mazePanel = new MazePanel(mazeRow, mazeCol);
        pnlPrincipal.add(mazePanel, BorderLayout.CENTER);

        menuBar.add(menuArchivo);
        menuBar.add(menuAyuda);

        menuArchivo.add(itemLaberinto);
        menuArchivo.add(itemResultados);

        menuAyuda.add(itemInfo);

        pnlPrincipal.add(pnlSup, BorderLayout.NORTH);
        pnlPrincipal.add(mazePanel, BorderLayout.CENTER);
        pnlPrincipal.add(pnlInf, BorderLayout.SOUTH);

        buttonGroup = new ButtonGroup();
        buttonGroup.add(btnStart);
        buttonGroup.add(btnEnd);
        buttonGroup.add(btnWall);

        cargarCombo();
        configuraristeners();

        setJMenuBar(menuBar);
        setContentPane(pnlPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Proyecto Final - EST");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void configuraristeners() {
        itemResultados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultadosDialog resultsView = new ResultadosDialog(MazeFrame.this);
                resultsView.setVisible(true);
            }
        });

        JButton[][] cells = mazePanel.getGridCells();
        for (int i = 0; i < mazeRow; i++) {
            for (int j = 0; j < mazeCol; j++) {
                JButton cell = cells[i][j];

                cell.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!cell.getBackground().equals(CellState.MURO.getColor()) &&
                        !cell.getBackground().equals(CellState.INICIO.getColor()) &&
                        !cell.getBackground().equals(CellState.FIN.getColor()) &&
                        !cell.getBackground().equals(CellState.LIBRE.getColor())) {
                        }

                        if (btnStart.getModel().isSelected()) {
                            cell.setBackground(CellState.INICIO.getColor());
                        } else if (btnEnd.getModel().isSelected()) {
                            cell.setBackground(CellState.FIN.getColor());
                        } else if (btnWall.getModel().isSelected()) {
                            cell.setBackground(CellState.MURO.getColor());
                        }
                    }
                });
            }
        }
    }

    private void cargarCombo() {
        cbxAlgoritmo.removeAllItems();
        cbxAlgoritmo.addItem("Recursivo 2 direcciones");
        cbxAlgoritmo.addItem("Recursivo completo");
        cbxAlgoritmo.addItem("Recursivo completo BT");
        cbxAlgoritmo.addItem("BFS (Breadth-First Search)");
        cbxAlgoritmo.addItem("DFS (Depth-First Search)");
        cbxAlgoritmo.setSelectedIndex(0);
    }

    public JMenu getMenuArchivo() {
        return menuArchivo;
    }

    public void setMenuArchivo(JMenu menuArchivo) {
        this.menuArchivo = menuArchivo;
    }

    public JMenu getMenuAyuda() {
        return menuAyuda;
    }

    public void setMenuAyuda(JMenu menuAyuda) {
        this.menuAyuda = menuAyuda;
    }

    public JMenuItem getItemLaberinto() {
        return itemLaberinto;
    }

    public void setItemLaberinto(JMenuItem itemLaberinto) {
        this.itemLaberinto = itemLaberinto;
    }

    public JMenuItem getItemResultados() {
        return itemResultados;
    }

    public void setItemResultados(JMenuItem itemResultados) {
        this.itemResultados = itemResultados;
    }

    public JMenuItem getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(JMenuItem itemInfo) {
        this.itemInfo = itemInfo;
    }

    public JPanel getPnlPrincipal() {
        return pnlPrincipal;
    }

    public void setPnlPrincipal(JPanel pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    public JPanel getPnlSup() {
        return pnlSup;
    }

    public void setPnlSup(JPanel pnlSup) {
        this.pnlSup = pnlSup;
    }

    public JPanel getPnlInf() {
        return pnlInf;
    }

    public void setPnlInf(JPanel pnlInf) {
        this.pnlInf = pnlInf;
    }

    public ButtonGroup getButtonGroup() {
        return buttonGroup;
    }

    public void setButtonGroup(ButtonGroup buttonGroup) {
        this.buttonGroup = buttonGroup;
    }

    public JToggleButton getBtnStart() {
        return btnStart;
    }

    public void setBtnStart(JToggleButton btnStart) {
        this.btnStart = btnStart;
    }

    public JToggleButton getBtnEnd() {
        return btnEnd;
    }

    public void setBtnEnd(JToggleButton btnEnd) {
        this.btnEnd = btnEnd;
    }

    public JToggleButton getBtnWall() {
        return btnWall;
    }

    public void setBtnWall(JToggleButton btnWall) {
        this.btnWall = btnWall;
    }

    public JComboBox getCbxAlgoritmo() {
        return cbxAlgoritmo;
    }

    public void setCbxAlgoritmo(JComboBox cbxAlgoritmo) {
        this.cbxAlgoritmo = cbxAlgoritmo;
    }

    public JButton getBtnSolve() {
        return btnSolve;
    }

    public void setBtnSolve(JButton btnSolve) {
        this.btnSolve = btnSolve;
    }

    public JButton getBtnStep() {
        return btnStep;
    }

    public void setBtnStep(JButton btnStep) {
        this.btnStep = btnStep;
    }

    public JButton getBtnClean() {
        return btnClean;
    }

    public void setBtnClean(JButton btnClean) {
        this.btnClean = btnClean;
    }

    public int getMazeRow() {
        return mazeRow;
    }

    public void setMazeRow(int mazeRow) {
        this.mazeRow = mazeRow;
    }

    public int getMazeCol() {
        return mazeCol;
    }

    public void setMazeCol(int mazeCol) {
        this.mazeCol = mazeCol;
    }
}
