package es.ciudadescolar.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.global.Constantes;
import es.ciudadescolar.models.Actor;
import es.ciudadescolar.models.Pelicula;

public class TXTManager {
    private final Logger log = LoggerFactory.getLogger(TXTManager.class);
    private File ficheroTxt;

    public TXTManager(File ficheroTxt) {
        this.ficheroTxt = ficheroTxt;
        log.trace("Gestor de txt inicializado con exito");
    }

    public TXTManager(String path) {
        this.ficheroTxt = new File(path);
        log.trace("Gestor de txt inicializado con exito");
    }

    public List<Pelicula> getPeliculas() {
        List<Pelicula> salida = new ArrayList<>();
        FileReader fr = null;
        BufferedReader br = null;
        String linea = null;
        String[] datos = null;
        // Guardamos la posicion de cada una de las constantes
        Integer pFilmID = null;
        Integer pTitle = null;
        Integer pReleaseYear = null;
        Integer pLanguageID = null;
        Integer pRentalDuration = null;
        Integer pRentalRate = null;
        Integer pReplacementCost = null;
        Integer pRating = null;
        Integer pActores = null;
        Integer pActorID = null;
        Integer pActorFirstName = null;
        Integer pActorLastName = null;
        try {
            fr = new FileReader(ficheroTxt);
            br = new BufferedReader(fr);
            // linea = br.readLine();
            // datos = linea.split("\\|");
            // log.trace(Arrays.toString(datos));
            while ((linea = br.readLine()) != null) {
                log.trace(linea);
                datos = linea.split("\\|");
                log.trace(Arrays.toString(datos));
                if (pFilmID == null
                        || pTitle == null
                        || pReleaseYear == null
                        || pLanguageID == null
                        || pRentalDuration == null
                        || pRentalRate == null
                        || pReplacementCost == null
                        || pRating == null
                        || pActores == null) {
                    for (int i = 0; i < datos.length; i++) {
                        if (datos[i].toLowerCase().equals(Constantes.FILM_ID)) {
                            pFilmID = i;
                        } else if (datos[i].toLowerCase().equals(Constantes.TITLE)) {
                            pTitle = i;
                        } else if (datos[i].toLowerCase().equals(Constantes.RELEASE_YEAR)) {
                            pReleaseYear = i;
                        } else if (datos[i].toLowerCase().equals(Constantes.LANGUAGE_ID)) {
                            pLanguageID = i;
                        } else if (datos[i].toLowerCase().equals(Constantes.RENTAL_DURATION)) {
                            pRentalDuration = i;
                        } else if (datos[i].toLowerCase().equals(Constantes.RENTAL_RATE)) {
                            pRentalRate = i;
                        } else if (datos[i].toLowerCase().equals(Constantes.REPLACEMENT_COST)) {
                            pReplacementCost = i;
                        } else if (datos[i].toLowerCase().equals(Constantes.RATING)) {
                            pRating = i;
                        } else if (datos[i].toLowerCase().startsWith(Constantes.ACTORES)) {
                            pActores = i;
                            int inicio = datos[i].indexOf("(");
                            int fin = datos[i].indexOf(")");
                            String[] datosActores = datos[i].substring(inicio + 1, fin).split("[\\- ]");
                            log.trace(Arrays.toString(datosActores));
                            if (pActorID == null
                                    || pActorFirstName == null
                                    || pActorLastName == null) {
                                for (int j = 0; j < datosActores.length; j++) {
                                    if (datosActores[j].toLowerCase().equals(Constantes.ACTOR_ID)) {
                                        pActorID = j;
                                    } else if (datosActores[j].toLowerCase().equals(Constantes.ACTOR_FIRST_NAME)) {
                                        pActorFirstName = j;
                                    } else if (datosActores[j].toLowerCase().equals(Constantes.ACTOR_LAST_NAME)) {
                                        pActorLastName = j;
                                    } else {
                                        log.warn("No corresponde a ninguno de los datos buscados");
                                    }
                                }
                            }
                        } else if (datos[i].toLowerCase().equals(Constantes.ACTOR_ID)) {
                            pActorID = i;
                        } else if (datos[i].toLowerCase().equals(Constantes.ACTOR_FIRST_NAME)) {
                            pActorFirstName = i;
                        } else if (datos[i].toLowerCase().equals(Constantes.ACTOR_LAST_NAME)) {
                            pActorLastName = i;
                        } else {
                            log.warn("No corresponde a ninguno de los datos buscados");
                        }

                    }
                } else {
                    Pelicula p = new Pelicula();
                    p.setFilm_id(Integer.parseInt(datos[pFilmID]));
                    p.setTitle(datos[pTitle]);
                    p.setRelease_year(Integer.parseInt(datos[pReleaseYear]));
                    p.setLanguage_id(Integer.parseInt(datos[pLanguageID]));
                    p.setRental_duration(Integer.parseInt(datos[pRentalDuration]));
                    p.setRental_rate(Double.parseDouble(datos[pRentalRate]));
                    p.setReplacement_cost(Double.parseDouble(datos[pReplacementCost]));
                    p.setRating(datos[pRating]);

                    List<Actor> actores = new ArrayList<>();
                    String[] datosActores = datos[pActores].split(",");
                    for (int i = 0; i < datosActores.length; i++) {
                        String[] datosActor = datosActores[i].trim().split("[\\-\\s+]");
                        log.trace(Arrays.toString(datosActor));
                        Actor a = new Actor();
                        a.setId(Integer.parseInt(datosActor[pActorID]));
                        a.setFirst_name(datosActor[pActorFirstName]);
                        a.setLast_name(datosActor[pActorLastName]);
                        actores.add(a);
                    }
                    p.setActores(actores);
                    salida.add(p);
                }

            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return salida;
    }

}
