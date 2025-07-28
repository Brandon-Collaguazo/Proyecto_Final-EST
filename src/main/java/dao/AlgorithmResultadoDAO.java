package dao;

import models.AlgorithmResultado;

import java.util.List;

public interface AlgorithmResultadoDAO {
    void guardarResultado( AlgorithmResultado resultado );

    List<AlgorithmResultado> obtenerTodos();

    void eliminarTodos();
}
