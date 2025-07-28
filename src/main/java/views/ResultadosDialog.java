package views;

import models.AlgorithmResult;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ResultadosDialog extends JDialog {
    private JPanel pnlPrincipal;
    private JPanel pnlSup;
    private JPanel pnlInf;
    private JTable tblResults;
    private DefaultTableModel modelo;
    private JButton btnGraph;
    private JButton btnClean;

    public ResultadosDialog(JFrame parent) {
        super(parent, "Resultados de Ejecución", true);
        initComponents();
    }

    private void initComponents() {
        setSize(400, 500);
        setLocationRelativeTo(getParent());
        setContentPane(pnlPrincipal);
        configurarTabla();
    }

    private void configurarTabla() {
        modelo = new DefaultTableModel();
        Object[] columnas = {"Algoritmo", "# Celdas camino", "Tiempo"};
        modelo.setColumnIdentifiers(columnas);
        tblResults.setModel(modelo);
    }
    public void cargarResultados(List<AlgorithmResult> resultados) {
        if (tblResults == null || modelo == null) {
            configurarTabla(); // asegúrate de que estén listos
        }

        modelo.setRowCount(0); // limpia filas anteriores

        for (AlgorithmResult r : resultados) {
            modelo.addRow(new Object[]{
                    r.getNameAlgorithm(),
                    r.getSteps(),
                    r.getTimeMs()
            });
        }
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

    public JTable getTblResults() {
        return tblResults;
    }

    public void setTblResults(JTable tblResults) {
        this.tblResults = tblResults;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public JButton getBtnGraph() {
        return btnGraph;
    }

    public void setBtnGraph(JButton btnGraph) {
        this.btnGraph = btnGraph;
    }

    public JButton getBtnClean() {
        return btnClean;
    }

    public void setBtnClean(JButton btnClean) {
        this.btnClean = btnClean;
    }
}
