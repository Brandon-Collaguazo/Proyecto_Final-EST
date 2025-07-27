package models;

public class AlgorithmResultado {

    private String nombreAlgoritmo;;
    private int pasos;
    private long tiempoMs;

    public AlgorithmResultado(String nombreAlgoritmo, int pasos, long tiempoMs) {
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
        return "AlgorithmResultado{" +
                "nombreAlgoritmo='" + nombreAlgoritmo + '\'' +
                ", pasos=" + pasos +
                ", tiempoMs=" + tiempoMs +
                '}';
    }
}
