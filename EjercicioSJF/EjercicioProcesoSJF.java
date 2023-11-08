import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EjercicioProcesoSJF {
    static class Proceso {
        String nombre;
        int tiempoLlegada;
        int tiempoEjecucion;
        int tiempoEspera;
        int tiempoRetorno;

        Proceso(String nombre, int tiempoLlegada, int tiempoEjecucion) {
            this.nombre = nombre;
            this.tiempoLlegada = tiempoLlegada;
            this.tiempoEjecucion = tiempoEjecucion;
            this.tiempoEspera = 0;
            this.tiempoRetorno = 0;
        }
    }

    public static void main(String[] args) {
        Proceso[] procesos = new Proceso[6];
        procesos[0] = new Proceso("P1", 2, 3);
        procesos[1] = new Proceso("P2", 1, 2);
        procesos[2] = new Proceso("P3", 3, 1);
        procesos[3] = new Proceso("P4", 4, 3);
        procesos[4] = new Proceso("P5", 0, 6);
        procesos[5] = new Proceso("P6", 3, 4);

        int tiempoActual = 0;
        int tiempoEntra = 0;
        List<Proceso> procesosCompletos = new ArrayList<>();

        while (!procesosCompletados(procesos)) {
            List<Proceso> procesosEjecutables = getProcesosEjecutables(procesos, tiempoActual);
            if (!procesosEjecutables.isEmpty()) {
                //Utilizo stream() para poder generar un flujo y hacer operaciones con el
                Proceso shortestJob = procesosEjecutables.stream()
                        //Aqui utilizo min y el comparador para coger el que menor tiempo de ejecucion tenga
                        .min(Comparator.comparingInt(p -> p.tiempoEjecucion))
                        //Y finalmente con get cojo el tiempo de ejecucion menor para almacenarlo en la variable get
                        .get();
                tiempoEntra = tiempoActual;
                tiempoActual += shortestJob.tiempoEjecucion;
                shortestJob.tiempoEjecucion = 0;
                shortestJob.tiempoRetorno = tiempoActual - shortestJob.tiempoLlegada;
                shortestJob.tiempoEspera = tiempoEntra - shortestJob.tiempoLlegada;
                System.out.println("El tiempo de retorno de " + shortestJob.nombre + " es " + tiempoActual);
                procesosCompletos.add(shortestJob);
            } else {
                tiempoActual++;
            }
        }


        System.out.println("Procesos completados en el siguiente orden:");
        for (Proceso p : procesosCompletos) {
            System.out.println(p.nombre);
            System.out.println("Tiempo de Espera: " + p.tiempoEspera);
            System.out.println("Tiempo de Retorno: " + p.tiempoRetorno);

            String fileName = p.nombre + "_tiempos.txt";
            try (PrintWriter writer = new PrintWriter(fileName)) {
                writer.println("Tiempo de Espera: " + p.tiempoEspera);
                writer.println("Tiempo de Retorno: " + p.tiempoRetorno);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ejecutarCalcularMediasDeTiempos();

    }

    static boolean procesosCompletados(Proceso[] procesos) {
        for (Proceso p : procesos) {
            if (p.tiempoEjecucion > 0) {
                return false;
            }
        }
        return true;
    }

    static List<Proceso> getProcesosEjecutables(Proceso[] procesos, int tiempoActual) {
        List<Proceso> procesosEjecutables = new ArrayList<>();
        for (Proceso p : procesos) {
            if (p.tiempoLlegada <= tiempoActual && p.tiempoEjecucion > 0) {
                procesosEjecutables.add(p);
            }
        }

        return procesosEjecutables;
    }

    static void ejecutarCalcularMediasDeTiempos() {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = "CalcularMediasDeTiempos"; // Nombre de la clase
        String[] command = new String[] { javaBin, "-cp", classpath, className };

        ProcessBuilder builder = new ProcessBuilder(command);

        // Establece el directorio de trabajo donde se encuentra la clase
        builder.directory(new File("D:\\Luismi\\GRADOSUPERIOR2\\PROGRAMACION DE SERVICIOS Y PROCESOS\\PRIMER TRIMESTRE\\CalculaMedias"));

        // Redirigir la salida estándar del proceso hijo al proceso padre
        builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);

        try {
            Process proceso = builder.start();
            proceso.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
