package es.ciudadescolar;

import es.ciudadescolar.instituto.Alumno;
import es.ciudadescolar.util.FlujoBinarioFichero;

public class Programa {
    public static void main(String[] args) {

        // FlujoBinarioTeclado.lecturaTecladoDeBytes();
        // FlujoBinarioTeclado.lecturaTecladoBufferDeBytes();
        // FlujoBinarioTeclado.lecturaTecladoLineasScanner();

        Alumno[] alumnos = { 
            new Alumno("1", "Paco", "aaa"),
            new Alumno("2", "Manolo", "bbb"),
            new Alumno("3", "Fermin", "ccc") };
        String nomFichAlumnos = "alumnos.dat";

        FlujoBinarioFichero.escrituraFicheroAlumnos(alumnos, nomFichAlumnos);

        FlujoBinarioFichero.lecturaFicheroAlumnos(nomFichAlumnos);

        char[] abecedario = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        String nomFichChars = "abecedario.dat";
        FlujoBinarioFichero.escrituraFicheroChars(abecedario, nomFichChars);

        FlujoBinarioFichero.lecturaFicheroChars(nomFichChars);
    }

}
