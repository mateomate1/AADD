package es.ciudadescolar;

import java.util.Scanner;

/**
 * Clase principal que pone a prueba las utilidades de las clases GestorTxt y GestorBin
 *
 * @author Mateo Ayarra
 * @version 1.0
 */
public class Programa {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        String ficheroBin = "modulos.dat";
        String ficheroTxt = "modulos.txt";
        Integer opc = 0;
        GestorTxt gt = new GestorTxt(ficheroTxt);
        GestorBin gb = new GestorBin(ficheroBin);

        do {
            opc = pedirEntero("Introduzca la instruccion(-1. Salir\n1. Introducir modulo en fichero de texto\n2. Introducir modulos en fichero binario\n3. Recuperar modulos del fichero de texto\n4. Recuperar modulos del fichero binario)");
            switch (opc) {
                case -1 -> System.out.println("Saliendo del programa. Adios");
                case 1 -> gt.setModulo(generarModulo());
                case 2 -> gb.setModulo(generarModulo());
                case 3 -> gt.getModulos();
                case 4 -> gb.getModulos();
                default -> System.out.println("Valor no valido");
            }
        } while (opc != -1);
    }

    /**
     * Metodo que gestiona la inicializacion de un objeto Modulo a traves de consola
     *
     * @return
     */
    public static Modulo generarModulo() {
        Modulo salida;

        String modulo;
        Double horas;
        String profesor;
        Integer curso;
        String contrasena;

        System.out.print("Introduce el nombre del modulo profesional: ");
        modulo = sc.nextLine();
        horas = pedirDouble("Introduce el numero de horas: ");
        System.out.print("Introduce el profesor: ");
        profesor = sc.nextLine();
        curso = pedirEntero("Introduce el curso: ");
        System.out.print("Introduce la contraseña: ");
        contrasena = sc.nextLine();

        salida = new Modulo(modulo, horas, profesor, curso, contrasena);

        return salida;
    }

    /**
     * Metodo que genera un objeto Integer repitiendo un mensaje pasado por parametro hasta que la salida sea valida
     *
     * @param mensaje
     * @return
     */
    public static Integer pedirEntero(String mensaje) {
        while (true) {
            Integer salida;
            String texto;
            System.out.print(mensaje);
            texto = sc.nextLine();
            try {
                salida = Integer.parseInt(texto);
                return salida;
            } catch (NumberFormatException e) {
                System.out.println("No has introducido un numero...");
            }
        }
    }

    /**
     * Metodo que genera un objeto Double repitiendo un mensaje pasado por parametro hasta que la salida sea valida
     *
     * @param mensaje
     * @return
     */
    public static Double pedirDouble(String mensaje) {
        while (true) {
            Double salida;
            String texto;
            System.out.print(mensaje);
            texto = sc.nextLine();
            try {
                salida = Double.parseDouble(texto);
                return salida;
            } catch (NumberFormatException e) {
                System.out.println("No has introducido un numero...");
            }
        }
    }
}
