package models;

public class AlgorithmResult {
    private String nombreAlgoritmo;;
    private int pasos;
    private long tiempoMs;

    public AlgorithmResult(String nombreAlgoritmo, int pasos, long tiempoMs) {
        this.nombreAlgoritmo = nombreAlgoritmo;
        this.pasos = pasos;
        this.tiempoMs = tiempoMs;
    }

    public String getNombreAlgoritmo() {
        return nombreAlgoritmo;
    }

    public void setNombreAlgoritmo(String nombreAlgoritmo) {
        this.nombreAlgoritmo = nombreAlgoritmo;
    }

    public int getPasos() {
        return pasos;
    }

    public void setPasos(int pasos) {
        this.pasos = pasos;
    }

    public long getTiempoMs() {
        return tiempoMs;
    }

    public void setTiempoMs(long tiempoMs) {
        this.tiempoMs = tiempoMs;

    }

    @Override
    public String toString() {
        return nombreAlgoritmo + "," + pasos + "," + tiempoMs;
    }
}
