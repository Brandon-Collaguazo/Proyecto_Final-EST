package models;

public class Cell {
    private int fila;
    private int columna;
    private CellState estado;
    private boolean visitado;

    public Cell(int fila, int columna, CellState estado, boolean visitado) {

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

    public CellState getEstado() {
        return estado;
    }

    public void setEstado(CellState estado) {
        this.estado = estado;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cell other = (Cell) obj;
        return fila == other.fila && columna == other.columna;
    }

    @Override
    public int hashCode() {
        return 31 * fila + columna;
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
