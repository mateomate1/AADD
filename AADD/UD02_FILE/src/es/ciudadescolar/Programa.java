package es.ciudadescolar;

import java.io.File;

public class Programa {
    public static void main(String[] args) throws Exception {
        File fichero = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "fichero.txt");

        if (fichero.exists()) {
            fichero.delete();
            System.out.println("Borrando fichero con el mismo nombre.");
        }

        if (!fichero.createNewFile()){
            System.out.println("No se ha podido crear el fichero: " + fichero.getAbsolutePath());
            System.exit(1);
        }

        File directorio = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "carpeta");

        if (!directorio.exists()){
            if (!directorio.mkdir()) {
                System.out.println("Error al crear la carpeta " + directorio.getPath());
                System.exit(1);
            }
        }

        if (directorio.isDirectory() && directorio.list().length > 0) {
            System.out.println("El contenido del directorio " + directorio.getName() + " es:");
            for (String item : directorio.list()){
                System.out.println(item);
            }
        }

        //borrar el directorio: Necesitamos un dir (y no un fich) y que ademas tenga contenido

        if (directorio.isDirectory() && directorio.list().length > 0) {
            System.out.println("El contenido del directorio " + directorio.getName() + " es:");
            for (File item : directorio.listFiles()){
                System.out.println("Borrando fichero interno: "+item.getName());
                item.delete();
                
            }
        } else {
            if (!directorio.delete()){
                System.out.println("No se ha podido borrar el directorio "+ directorio.getName());
            } else {
                System.out.println("Se ha borrado el directorio");
            }
        }

    }
}
