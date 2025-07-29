package views;

import controllers.MazeController;
import dao.imple.AlgorithmResultDAOFile;
import models.Cell;
import models.CellState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MazeFrame extends JFrame {
    private JMenuBar menuBar;
    private JMenu menuArchivo;
    private JMenu menuAyuda;
    private JMenu menuSalir;
    private JMenuItem itemLaberinto;
    private JMenuItem itemResultados;
    private JMenuItem itemInfo;
    private JMenuItem itemSalir;

    private JPanel pnlPrincipal;
    private JPanel pnlSup;
    private MazePanel mazePanel;
    private JPanel pnlInf;

    private ButtonGroup buttonGroup;
    private JToggleButton btnStart;
    private JToggleButton btnEnd;
    private JToggleButton btnWall;

    private JComboBox<String> cbxAlgoritmo;
    private JButton btnSolve;
    private JButton btnStep;
    private JToggleButton btnClean;

    private int mazeRow;
    private int mazeCol;

    private MazeController mazeController;

    public MazeFrame() {
        inputDimensions();
        initComponents();
        // Inicializar el controlador después de que los componentes estén listos
        mazeController = new MazeController(mazePanel, new AlgorithmResultDAOFile("results.txt"));
        configurarListenerControlador(); // Configurar listeners que dependen del controlador
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
        menuSalir = new JMenu("Salir del programa");

        itemLaberinto = new JMenuItem("Nuevo laberinto");
        itemResultados = new JMenuItem("Ver resultados");
        itemInfo = new JMenuItem("Acerca de");
        itemSalir = new JMenuItem("Salir");

        mazePanel = new MazePanel(mazeRow, mazeCol);
        pnlPrincipal.add(mazePanel, BorderLayout.CENTER);


        menuBar.add(menuArchivo);
        menuBar.add(menuAyuda);
        menuBar.add(menuSalir);

        menuArchivo.add(itemLaberinto);
        menuArchivo.add(itemResultados);

        menuAyuda.add(itemInfo);

        menuSalir.add(itemSalir);

        pnlPrincipal.add(pnlSup, BorderLayout.NORTH);
        pnlPrincipal.add(mazePanel, BorderLayout.CENTER);
        pnlPrincipal.add(pnlInf, BorderLayout.SOUTH);

        buttonGroup = new ButtonGroup();
        buttonGroup.add(btnStart);
        buttonGroup.add(btnEnd);
        buttonGroup.add(btnWall);
        buttonGroup.add(btnClean);

        cargarCombo();
        configuraristeners(); // Configurar listeners generales

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
                mazeController.showResults();
            }
        });

        itemInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mensaje = "Proyecto realizado por: Andrés Cajas - Brandon Collaguazo :D\nMateria: Estructura de Datos - UPS";
                JOptionPane.showMessageDialog(
                        MazeFrame.this,
                        mensaje,
                        "Acerca de",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        itemSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int respuesta = JOptionPane.showConfirmDialog(
                        MazeFrame.this,
                        "¿Está seguro que desea salir de la aplicación?",
                        "Confirmar salida",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (respuesta == JOptionPane.YES_NO_OPTION) {
                    System.exit(0);
                }

            }
        });

        itemLaberinto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MazeFrame(); // Crea una nueva instancia de MazeFrame
            }
        });

        btnClean.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mazePanel.limpiarCamino(); // Limpia el camino y los puntos de inicio/fin
                mazePanel.repaint();
            }
        });

        JButton[][] buttons = mazePanel.getGridButtons();
        for (int i = 0; i < mazeRow; i++) {
            for (int j = 0; j < mazeCol; j++) {
                JButton cellButton = buttons[i][j];
                final int row = i;
                final int col = j;
                cellButton.addActionListener(e -> {
                    Cell clickedCell = mazePanel.getCell(row, col);
                    if (btnStart.getModel().isSelected()) {
                        mazePanel.updateCellState(row, col, CellState.START);
                    } else if (btnEnd.getModel().isSelected()) {
                        mazePanel.updateCellState(row, col, CellState.END);
                    } else if (btnWall.getModel().isSelected()) {
                        if (clickedCell.getState() == CellState.WALL) {
                            mazePanel.updateCellState(row, col, CellState.EMPTY);
                        } else if (clickedCell.getState() != CellState.START && clickedCell.getState() != CellState.END) {
                            mazePanel.updateCellState(row, col, CellState.WALL);
                        }
                    } else if (btnClean.getModel().isSelected()) {
                        if (clickedCell.getState() != CellState.START && clickedCell.getState() != CellState.END) {
                            mazePanel.updateCellState(row, col, CellState.EMPTY);
                        }
                    }
                });
            }
        }
    }

    private void configurarListenerControlador() {
        btnSolve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemSelected = (String) cbxAlgoritmo.getSelectedItem();
                if (itemSelected != null) {
                    mazeController.startAutomaticSolving(itemSelected);
                }
            }
        });

        btnStep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mazeController.isSolving() && mazeController.isStepByStepMode()) {
                    mazeController.performOneStep();
                } else if (!mazeController.isSolving()) {
                    String itemSelected = (String) cbxAlgoritmo.getSelectedItem();
                    if (itemSelected != null) {
                        mazeController.startStepByStepMode(itemSelected);
                    }
                } else {
                    JOptionPane.showMessageDialog(MazeFrame.this,
                            "La resolución automática está en curso. Deténgala para usar el modo paso a paso.",
                            "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
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

    // Getters y Setters (mantener los existentes)
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

    public JToggleButton getBtnClean() {
        return btnClean;
    }

    public void setBtnClean(JToggleButton btnClean) {
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
