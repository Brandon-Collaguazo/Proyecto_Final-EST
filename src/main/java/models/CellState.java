package models;

import java.awt.*;

public enum CellState {
    LIBRE(Color.WHITE),
    MURO(Color.BLACK),
    INICIO(Color.YELLOW),
    FIN(Color.RED),
    VISITADO(Color.GRAY),
    CAMINO(Color.BLUE);
    private Color color;

    CellState(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
