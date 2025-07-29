package dao.imple;

import dao.AlgorithmResultDAO;
import models.AlgorithmResult;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AlgorithmResultDAOFile implements AlgorithmResultDAO {

    private final String rutaArchivo;

    public AlgorithmResultDAOFile(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        System.out.println("DAO File inicializado. Ruta: " + new File(rutaArchivo).getAbsolutePath());
        inicializarArchivo();
    }

    private void inicializarArchivo() {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo, false))) {
                writer.println("Algoritmo;Pasos;Tiempo(ms)"); // Encabezado de 3 campos
                System.out.println("Archivo de resultados creado con encabezado en: " + archivo.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error al inicializar el archivo: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void guardar(AlgorithmResult resultado) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo, true))) {
            writer.println(resultado.toString());
            System.out.println("Resultado guardado: " + resultado.getNameAlgorithm() + " en: " + new File(rutaArchivo).getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error al guardar el resultado: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<AlgorithmResult> listar() throws IOException {
        List<AlgorithmResult> resultados = new ArrayList<>();
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            System.out.println("El archivo de resultados no existe en: " + archivo.getAbsolutePath() + ". Se devuelve lista vacía.");
            return resultados;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            reader.readLine();
            System.out.println("Leyendo resultados desde: " + archivo.getAbsolutePath());
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 3) {
                    try {
                        String nombre = partes[0].trim();
                        int pasos = Integer.parseInt(partes[1].trim());
                        long tiempo = Long.parseLong(partes[2].trim());
                        // Ya no se intenta parsear la fecha
                        resultados.add(new AlgorithmResult(nombre, pasos, tiempo));
                    } catch (NumberFormatException e) {
                        System.err.println("Error de formato numérico en línea: '" + linea + "': " + e.getMessage());
                    }
                } else {
                    System.err.println("Línea con formato inesperado (se esperaban 3 partes separadas por ';'): '" + linea + "'");
                }
            }
            System.out.println("Se leyeron " + resultados.size() + " resultados.");
        } catch (IOException e) {
            System.err.println("Error al listar resultados: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return resultados;
    }

    @Override
    public void eliminarTodos() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo))) {
            writer.println("Algoritmo;Pasos;Tiempo(ms)");
            System.out.println("Resultados eliminados del archivo");
        }
    }
}