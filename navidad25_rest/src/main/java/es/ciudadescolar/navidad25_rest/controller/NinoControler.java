package es.ciudadescolar.navidad25_rest.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.ciudadescolar.navidad25_rest.model.Nino;
import es.ciudadescolar.navidad25_rest.service.NinoService;


@RestController
@RequestMapping("/api/v1/ninos")
public class NinoControler {
    private static final Logger LOG = LoggerFactory.getLogger(NinoControler.class);

    private final NinoService ninoServicio;

    public NinoControler(NinoService serv){
        this.ninoServicio = serv;
    }

    @GetMapping()
    public List<Nino> listarTodosNinos() {
        List<Nino> ninos = ninoServicio.getTodosNinos();
        LOG.debug("Peticion GET /api/v1/ninos - Recuperar todos los ninos [{}]",ninos.size());
        return ninos;
    }
    
}
