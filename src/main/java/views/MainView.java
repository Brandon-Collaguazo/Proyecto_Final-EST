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
        setVisible(true);
    }
}
