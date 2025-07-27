package views;

import javax.swing.*;

public class MainView extends JFrame {
    private JMenuBar menuBar;
    private JMenu menuArchivo;
    private JMenu menuAyuda;
    private JMenuItem itemLaberinto;
    private JMenuItem itemResultados;
    private JMenuItem itemInfo;

    private JPanel pnlPrincipal;
    private JPanel pnlSup;
    private JPanel pnlCen;
    private JPanel pnlInf;

    private JButton btnStart;
    private JButton btnEnd;
    private JButton btnWall;

    private JComboBox cbxAlgoritmo;
    private JButton btnSolve;
    private JButton btnStep;
    private JButton btnClean;

    public MainView() {
        initComponents();
    }

    private void initComponents() {
        menuBar = new JMenuBar();

        menuArchivo = new JMenu("Archivo");
        menuAyuda = new JMenu("Ayuda");

        itemLaberinto = new JMenuItem("Nuevo laberinto");
        itemResultados = new JMenuItem("Ver resultados");
        itemInfo = new JMenuItem("Acerca de");

        menuBar.add(menuArchivo);
        menuBar.add(menuAyuda);

        menuArchivo.add(itemLaberinto);
        menuArchivo.add(itemResultados);

        menuAyuda.add(itemInfo);

        setJMenuBar(menuBar);
        setContentPane(pnlPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Proyecto Final - EST");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void cargarCombo() {
        cbxAlgoritmo.removeAllItems();
        cbxAlgoritmo.addItem("Recursivo 2 direcciones");
        cbxAlgoritmo.addItem("Recursivo 4 direcciones");
        cbxAlgoritmo.addItem("Recursivo con backtracking");
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

    public JPanel getPnlCen() {
        return pnlCen;
    }

    public void setPnlCen(JPanel pnlCen) {
        this.pnlCen = pnlCen;
    }

    public JPanel getPnlInf() {
        return pnlInf;
    }

    public void setPnlInf(JPanel pnlInf) {
        this.pnlInf = pnlInf;
    }

    public JButton getBtnStart() {
        return btnStart;
    }

    public void setBtnStart(JButton btnStart) {
        this.btnStart = btnStart;
    }

    public JButton getBtnEnd() {
        return btnEnd;
    }

    public void setBtnEnd(JButton btnEnd) {
        this.btnEnd = btnEnd;
    }

    public JButton getBtnWall() {
        return btnWall;
    }

    public void setBtnWall(JButton btnWall) {
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
}
