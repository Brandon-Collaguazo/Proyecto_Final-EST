package models;

import java.awt.*;

public enum CellState {
    EMPTY, //Vacío
    WALL, //Pared
    START, //Inicio
    END, //Final
    VISITED, //Visitado
    PATH; //Camino
}
