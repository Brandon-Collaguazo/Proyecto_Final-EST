package models;

public class Cell {
    private int fila;
    private int columna;
    private CellSate estado;
    private boolean visitado;

    public Cell(int fila, int columna, CellSate estado, boolean visitado) {

        this.fila = fila;
        this.columna = columna;
        this.estado = estado;
        this.visitado = visitado;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public CellSate getEstado() {
        return estado;
    }

    public void setEstado(CellSate estado) {
        this.estado = estado;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "fila=" + fila +
                ", columna=" + columna +
                ", estado=" + estado +
                ", visitado=" + visitado +
                '}';
    }
}
