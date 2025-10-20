import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger log  = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) throws Exception {
        /*
         * El nivel de LOGS imprimira el nivel especificado en el archivo
         * logback.xml y los niveles superiores, por ejemplo el nivel de ERROR
         * solo imprimira los LOGS de ERROR mientras que los LOGS de nivel TRACE mostrara todos los LOGS
         */
        log.error("Se ha detectado un error X");
        log.warn("OJO!! Ha pasado algo");
        log.info("Este evento representa una informacion general de nuestra applicacion");
        log.debug("Detalle medio para facilitar la depuracion de la aplicacion");
        log.trace("Detalle maximo sobre el flujo del programa");
    }
}
