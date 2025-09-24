package es.ciudadescolar;

import java.io.File;

public class Programa {
    public static void main(String[] args) throws Exception {

        if(args.length != 1){
            System.out.println("Se esperaba un parametro con el nombre fichero");
            System.out.println("Ejamplo de ejecucion correcta: java Programa fichero.txt");
            System.exit(1);
        }

        String nombrefichero = args[0];

        File fichero = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + nombrefichero);

        System.out.println("Ruta absoluta: " + fichero.getAbsolutePath());

        // VAMOS A TRATAR DE VISUALIZAR LAS PROPIEDADES DEL FICHERO

        String permisos = new String();
        permisos += fichero.canRead() ? "R" : "-";
        permisos += fichero.canWrite() ? "W" : "-";
        permisos += fichero.canExecute() ? "X" : "-";
        permisos += fichero.isHidden() ? "H" : "V";
        System.out.println(fichero.getName() + "->" + permisos);

        if (fichero.exists()) {
            fichero.delete();
            System.out.println("Borrando fichero con el mismo nombre.");
        }

        if (!fichero.createNewFile()) {
            System.out.println("No se ha podido crear el fichero: " + fichero.getAbsolutePath());
            System.exit(1);
        }

        File directorio = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "carpeta");

        if (!directorio.exists()) {
            if (!directorio.mkdir()) {
                System.out.println("Error al crear la carpeta " + directorio.getPath());
                System.exit(1);
            }
        }

        if (directorio.isDirectory() && directorio.list().length > 0) {
            System.out.println("El contenido del directorio " + directorio.getName() + " es:");
            for (String item : directorio.list()) {
                System.out.println(item);
            }
        }

        // El sighuiente metodo ademas de renombrar tambien permitira moverlo en elñ
        // mismo filesystem

        File copiaFile = new File(directorio, fichero.getName());

        if (fichero.renameTo(copiaFile)) {
            System.out.println("Se ha movido el fichero: " + fichero.getAbsolutePath());
        } else{
            System.out.println("Error durante el renombrado/moivimiento del fichero");
        }

        // borrar el directorio: Necesitamos un dir (y no un fich) y que ademas tenga
        // contenido

        if (directorio.isDirectory() && directorio.list().length > 0) {
            System.out.println("El contenido del directorio " + directorio.getName() + " es:");
            for (File item : directorio.listFiles()) {
                System.out.println("Borrando fichero interno: " + item.getName());
                item.delete();

            }
        } else {
            if (!directorio.delete()) {
                System.out.println("No se ha podido borrar el directorio " + directorio.getName());
            } else {
                System.out.println("Se ha borrado el directorio");
            }
        }

    }
}
