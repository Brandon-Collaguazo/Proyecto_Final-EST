package dao.imple;

import dao.AlgorithmResultadoDAO;
import models.AlgorithmResultado;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AlgorithmResultadoDAOFile implements AlgorithmResultadoDAO {
    private String rutaArchivo;
    public AlgorithmResultadoDAOFile(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }
    @Override
    public void guardarResultado(AlgorithmResultado resultado) {
        List<AlgorithmResultado> exist = obtenerTodos();
        boolean actualizado =false;

        for( int i = 0;i<exist.size();i++){
            AlgorithmResultado resultadoAux=exist.get(i);
            if(resultadoAux.getNombreAlgoritmo().equalsIgnoreCase(resultado.getNombreAlgoritmo())){
                exist.set(i,resultado);
                actualizado= true;
                break;
            }
        }
        if(!actualizado){
            exist.add(resultado);
        }

        try(PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo))){
            for(AlgorithmResultado resultadoAux:exist){
                writer.println(resultadoAux.toString());
            }
        }catch(IOException e){
            System.err.println("Error al guardar el resultado : "+ e.getMessage());

        }

    }

    @Override
    public List<AlgorithmResultado> obtenerTodos() {
        List<AlgorithmResultado> list = new ArrayList<>();
        File archivo = new File(rutaArchivo);
        if(!archivo.exists())
            return list;

        try(BufferedReader reader = new BufferedReader(new FileReader(archivo))){
            String line;
            while ((line = reader.readLine()) != null){
                String[] parts = line.split(",");
                if(parts.length ==3){
                    String nombre = parts[0].trim();
                    int pasos = Integer.parseInt(parts[1].trim());
                    long tiempo= Long.parseLong(parts[2].trim());
                    list.add(new AlgorithmResultado(nombre,pasos,tiempo));


                }
            }
        }catch(IOException e){
            System.err.println("Error al leer el archivo : "+ e.getMessage());
        }
        return list;
    }

    @Override
    public void eliminarTodos() {
        try(PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo))){
            writer.println("");

        }catch(IOException e){
            System.err.println("Error al eliminar el resultado : "+ e.getMessage());
        }

    }
}
