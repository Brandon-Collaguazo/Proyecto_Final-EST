package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ResultsView extends JDialog {
    private JPanel pnlPrincipal;
    private JPanel pnlSup;
    private JPanel pnlInf;
    private JTable tblResults;
    private DefaultTableModel modelo;
    private JButton btnGraph;
    private JButton btnClean;

    public ResultsView(JFrame parent) {
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
