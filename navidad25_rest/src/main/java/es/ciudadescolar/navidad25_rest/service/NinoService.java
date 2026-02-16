package es.ciudadescolar.navidad25_rest.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ciudadescolar.navidad25_rest.model.Carta;
import es.ciudadescolar.navidad25_rest.model.Nino;
import es.ciudadescolar.navidad25_rest.model.Nino.Comportamiento;
import es.ciudadescolar.navidad25_rest.repository.CartaRepository;
import es.ciudadescolar.navidad25_rest.repository.NinoRepository;

@Service
public class NinoService {

    private static final Logger LOG = LoggerFactory.getLogger(NinoService.class);
    
    private final NinoRepository ninoRepository;
    private final CartaRepository cartaRepository;


    // Inyección de dependencias en el constructor
    public NinoService(NinoRepository repo, CartaRepository cart)
    {
        this.ninoRepository = repo;
        this.cartaRepository = cart;
    }

    // anotación para que Spring gestione la transacción: org.springframework.transaction.annotation.Transactional
    @Transactional
    public Nino registrarNino(String nomNino, Comportamiento comp)
    {
        List<Nino> ninos = ninoRepository.findByNombreAndComportamiento(nomNino, comp);

        if (!ninos.isEmpty())
        {
            LOG.warn("El nino a registrar ya existe");
            return ninos.getFirst();
        }
        else
        {
            Nino nuevoNino = new Nino(nomNino,comp);
            LOG.info("Se ha registrado el nuevo niño");
            return ninoRepository.save(nuevoNino);
        }
    }

    // cuando se trate de una consulta, también indicar transacción pero solo en modo lectura
    @Transactional(readOnly = true)
    public List<Nino> getTodosNinos()
    {
        return ninoRepository.findAll();
    }


    @Transactional
    public void aniadirCartaANino(Long idNino, String contenido, LocalDate fecha)
    {
        Optional<Nino> ninoOptional = ninoRepository.findById(idNino);

        if (ninoOptional.isPresent())
        {
            Nino ninoEncontrado = ninoOptional.get();
            if (cartaRepository.existsByNinoIdAndContenido(idNino, contenido))
            {
                LOG.warn("La carta ya está registrada en ese niño");
            }
            else
            {
                Carta carta = new Carta();
                carta.setContenido(contenido);
                carta.setFecha(fecha);
                ninoEncontrado.aniadirCarta(carta);
            }

        }
    }
}
