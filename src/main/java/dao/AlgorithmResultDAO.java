package dao;

import models.AlgorithmResult;
import java.io.IOException;
import java.util.List;

public interface AlgorithmResultDAO {

    void guardar(AlgorithmResult resultado) throws IOException;
    List<AlgorithmResult> listar() throws IOException;
    void eliminarTodos() throws IOException; // Se mantiene

}