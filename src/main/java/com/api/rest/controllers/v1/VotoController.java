package com.api.rest.controllers.v1;


import com.api.rest.model.Voto;
import com.api.rest.repositories.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController("votoControllerV1")
@RequestMapping("/v1")
public class VotoController {

    @Autowired
    private VotoRepository votoRepository;


    //Crear un voto
    @PostMapping("/encuestas/{idEncuesta}/votos")
    public ResponseEntity<?> saveVoto(@PathVariable Long idEncuesta, @RequestBody Voto voto){
        voto = votoRepository.save(voto);

        //establecemos los enabezados headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/id").buildAndExpand(voto.getId()).toUri());
        return new ResponseEntity<>(null,httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/encuestas/{idEncuesta}/votos")
    public Iterable<Voto> getAllVotos(@PathVariable Long idEncuesta){
        return votoRepository.findByEncuesta(idEncuesta);
    }


}
