package models;

import java.util.ArrayList;
import java.util.List;

public class SolverResults {
    private List<AlgorithmResultado> resultados;

    private SolverResults(){
        resultados = new ArrayList<>();
    }

    public void agregarResultados( AlgorithmResultado resultado){
        for(int i = 0; i<resultados.size();i++){
            if(resultados.get(i).getNombreAlgoritmo().equalsIgnoreCase(resultado.getNombreAlgoritmo())){
                resultados.set(i,resultado);
                return;
            }
        }
        resultados.add(resultado);
    }
    public List<AlgorithmResultado> getResultados() {
        return resultados;
    }
}