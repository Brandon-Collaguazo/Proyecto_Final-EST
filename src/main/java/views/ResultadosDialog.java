package views;

import dao.AlgorithmResultDAO;
import models.AlgorithmResult;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class ResultadosDialog extends JDialog {
    private JPanel pnlPrincipal;
    private JPanel pnlSup;
    private JPanel pnlInf;
    private JTable tblResults;
    private DefaultTableModel modelo;
    private JButton btnGraph;
    private JButton btnClean;
    private List<AlgorithmResult> currentResultsData;
    private AlgorithmResultDAO dao;

    public ResultadosDialog(JFrame parent, List<AlgorithmResult> results) {
        super(parent, "Resultados de Ejecución", true);
        this.currentResultsData = results;
        initComponents();
        configurarListeners();
        cargarResultados(results);
    }

    private void initComponents() {
        setSize(400, 500);
        setLocationRelativeTo(getParent());
        setContentPane(pnlPrincipal);
        configurarTabla();
    }

    private void configurarListeners() {
        btnGraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAndShowChart();
            }
        });

        btnClean.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cleanResults();
            }
        });
    }

    private void configurarTabla() {
        modelo = new DefaultTableModel();
        Object[] columnas = {"Algoritmo", "# Celdas camino", "Tiempo"};
        modelo.setColumnIdentifiers(columnas);
        tblResults.setModel(modelo);
    }

    public void cargarResultados(List<AlgorithmResult> resultados) {
        modelo.setRowCount(0);
        if (resultados != null) {
            for (AlgorithmResult r : resultados) {
                modelo.addRow(new Object[]{
                        r.getNameAlgorithm(),
                        r.getSteps(),
                        r.getTimeMs()
                });
            }
        }
    }

    private void createAndShowChart() {
        if (currentResultsData == null || currentResultsData.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay resultados para generar el gráfico.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, List<AlgorithmResult>> resultadosPorAlgoritmo = currentResultsData.stream()
                .collect(Collectors.groupingBy(AlgorithmResult::getNameAlgorithm));

        for (Map.Entry<String, List<AlgorithmResult>> entry : resultadosPorAlgoritmo.entrySet()) {
            String algoritmo = entry.getKey();
            List<AlgorithmResult> results = entry.getValue();
            double avgTime = results.stream()
                    .mapToDouble(AlgorithmResult::getTimeMs)
                    .average()
                    .orElse(0.0); // Si no hay resultados, el promedio es 0
            dataset.addValue(avgTime, "Tiempo Promedio (ms)", algoritmo);
        }

        JFreeChart lineChart = ChartFactory.createLineChart(
                "Rendimiento de Algoritmos - Tiempo Promedio", // Título del gráfico
                "Algoritmo",              // Etiqueta del eje X
                "Tiempo (ms)",            // Etiqueta del eje Y
                dataset,                  // Datos
                PlotOrientation.VERTICAL, // Orientación del gráfico
                true,                     // Mostrar leyenda
                true,                     // Usar tooltips
                false                     // No generar URLs
        );

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));

        JDialog chartDialog = new JDialog(this, "Gráfico de Rendimiento", true);
        chartDialog.setContentPane(chartPanel);
        chartDialog.pack();
        chartDialog.setLocationRelativeTo(this);
        chartDialog.setVisible(true);
    }

    private void cleanResults() {
        modelo.setRowCount(0);

        try {
            dao.eliminarTodos();
            JOptionPane.showMessageDialog(this,
                    "Resultados eliminados correctamente",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al eliminar los datos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        currentResultsData.clear();
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
