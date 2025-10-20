package es.ciudadescolar.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class FlujoBinarioTeclado {

    /**
     * Lectura binaria "ineficiente": de byte en byte
     */
    public static void lecturaTecladoDeBytes() {
        int byteLeido;
        try {
            System.out.print("Introduzca secuencia de caracteres (Ctrl+Z para terminar.): ");
            // al pulsar la tecla Intro, no llega un solo byte, sino una secuencia de bytes:
            // salto de línea.
            // en Windows ascii 10 + 13 heredado de las máquinas de escribir: avance a
            // siguiente linea y mover carro al inicio de la nueva linea.

            byteLeido = System.in.read();
            while (byteLeido != -1) {
                System.out.println("Byte leído: " + byteLeido + " que corresponde con char: " + (char) byteLeido);
                byteLeido = System.in.read();
            }
            System.out.println("Fin de la lectura.");

        } catch (IOException e) {
            // TODO Este tratamiento de la excepción no es definitivo. A futuro lo
            // mandaremos a un log.
            System.out.println("Problemas al leer de un flujo de entrada binario:" + e.getMessage());
        } finally {
            try {
                System.in.close();
            } catch (IOException e) {
                // TODO Este tratamiento de la excepción no es definitivo. A futuro lo
                // mandaremos a un log.
                System.out.println("Problemas durante el cierre de flujo binario de entrada:" + e.getMessage());
            }
        }
    }

    /**
     * Lectura binaria "eficiente": en bloques de bytes en lugar de byte a byte
     * El BufferedInputStream aporta un buffer interno leyendo en bloque (aunque
     * nosotros leamos byte a byte)
     * podemos dejar el tamaño por defecto o establecer el número de bytes nosotros
     * durante la instanciación
     */
    public static void lecturaTecladoBufferDeBytes() {
        int byteLeido;
        BufferedInputStream bis = new BufferedInputStream(System.in);
        try {
            System.out.print("Introduzca secuencia de caracteres (Ctrl+Z para terminar.): ");
            // al pulsar la tecla Intro, no llega un solo byte, sino una secuencia de bytes:
            // salto de línea.
            // en Windows ascii 10 + 13 heredado de las máquinas de escribir: avance a
            // siguiente linea y mover carro al inicio de la nueva linea.

            byteLeido = bis.read();
            while (byteLeido != -1) {
                System.out.println("Byte leído: " + byteLeido + " que corresponde con char: " + (char) byteLeido);
                byteLeido = bis.read();
            }
            System.out.println("Fin de la lectura.");

        } catch (IOException e) {
            // TODO Este tratamiento de la excepción no es definitivo. A futuro lo
            // mandaremos a un log.
            System.out.println("Problemas al leer de un flujo de entrada binario:" + e.getMessage());
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
                // TODO Este tratamiento de la excepción no es definitivo. A futuro lo
                // mandaremos a un log.
                System.out.println("Problemas durante el cierre de flujo binario de entrada:" + e.getMessage());
            }
        }
    }

    /**
     * Lectura de texto por teclado usando la clase Scanner.
     * Se para cuando lea un determinado texto por ejemplo, salir
     *
     */
    public static void lecturaTecladoLineasScanner() {
        String linea = null;
        Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8);
        System.out.println("Escriba cadenas de texto ('salir' para terminar):");

        while (true) {
            linea = sc.nextLine();
            if (linea.equalsIgnoreCase("salir")) {
                break;
            }
            System.out.println("Línea leida: " + linea);
        }

        sc.close();
        System.out.println("Fin de la lectura de cadenas de texto por teclado.");
    }

}
