import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CalcularMediasDeTiempos {
    public static void main(String[] args) {
        File directorio = new File("D:\\Luismi\\GRADOSUPERIOR2\\PROGRAMACION DE SERVICIOS Y PROCESOS\\PRIMER TRIMESTRE\\EjercicioSJF"); // Ruta a la carpeta que contiene los archivos de texto
        File[] archivos = directorio.listFiles();
        int totalEspera = 0;
        int totalRetorno = 0;
        int totalArchivos = 0;

        for (File archivo : archivos) {
            if (archivo.isFile() && archivo.getName().endsWith("_tiempos.txt")) {
                try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                    String linea;
                    while ((linea = reader.readLine()) != null) {
                        if (linea.startsWith("Tiempo de Espera: ")) {
                            String valorEspera = linea.substring(18).trim(); // Eliminar espacios en blanco
                            if (!valorEspera.isEmpty()) {
                                totalEspera += Integer.parseInt(valorEspera);
                            }
                        } else if (linea.startsWith("Tiempo de Retorno: ")) {
                            String valorRetorno = linea.substring(18).trim(); // Eliminar espacios en blanco
                            if (!valorRetorno.isEmpty()) {
                                totalRetorno += Integer.parseInt(valorRetorno);
                            }
                        }
                    }
                    totalArchivos++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        double mediaEspera = (totalArchivos > 0) ? (double) totalEspera / totalArchivos : 0;
        double mediaRetorno = (totalArchivos > 0) ? (double) totalRetorno / totalArchivos : 0;

        System.out.println("Media de Tiempo de Espera: " + mediaEspera);
        System.out.println("Media de Tiempo de Retorno: " + mediaRetorno);
    }
}
