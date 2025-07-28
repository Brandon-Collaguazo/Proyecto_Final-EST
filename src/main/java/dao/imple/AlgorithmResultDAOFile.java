package dao.imple;

import dao.AlgorithmResultDAO;
import models.AlgorithmResult;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AlgorithmResultDAOFile implements AlgorithmResultDAO {

    private String rutaArchivo;

    public AlgorithmResultDAOFile(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    @Override
    public void guardarResultado(AlgorithmResult resultado) {
        List<AlgorithmResult> exist = obtenerTodos();
        boolean actualizado =false;

        for( int i = 0;i<exist.size();i++){
            AlgorithmResult resultadoAux=exist.get(i);
            if(resultadoAux.getNameAlgorithm().equalsIgnoreCase(resultado.getNameAlgorithm())){
                exist.set(i,resultado);
                actualizado= true;
                break;
            }
        }
        if(!actualizado){
            exist.add(resultado);
        }

        try(PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo))){
            for(AlgorithmResult resultadoAux:exist){
                writer.println(resultadoAux.toString());
            }
        }catch(IOException e){
            System.err.println("Error al guardar el resultado : "+ e.getMessage());

        }

    }

    @Override
    public List<AlgorithmResult> obtenerTodos() {
        List<AlgorithmResult> list = new ArrayList<>();
        File archivo = new File(rutaArchivo);
        if (!archivo.exists())
            return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String nombre = parts[0].trim();
                    String pasosStr = parts[1].trim();
                    int pasos = 0;
                    if (pasosStr.startsWith("pasos=")) {
                        try {
                            pasos = Integer.parseInt(pasosStr.substring("pasos=".length()));
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato en pasos: " + pasosStr);
                        }
                    } else {
                        try {
                            pasos = Integer.parseInt(pasosStr);
                        } catch (NumberFormatException e) {
                            System.err.println("Formato de pasos inesperado: " + pasosStr);
                        }
                    }

                    String tiempoStr = parts[2].trim();
                    long tiempo = 0;
                    if (tiempoStr.startsWith("tiempo=")) {
                        try {
                            tiempo = Long.parseLong(tiempoStr.substring("tiempo=".length()));
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato en tiempo: " + tiempoStr);
                        }
                    } else {
                        try {
                            tiempo = Long.parseLong(tiempoStr);
                        } catch (NumberFormatException e) {
                            System.err.println("Formato de tiempo inesperado: " + tiempoStr);
                        }
                    }

                    list.add(new AlgorithmResult(nombre, pasos, tiempo));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo : " + e.getMessage());
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
