package com.api.rest.controllers.v2;

import com.api.rest.exception.ResourceNotFoundException;
import com.api.rest.model.Encuesta;
import com.api.rest.repositories.EncuestaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController("encuestaControllerV2")
@RequestMapping("/v2")
public class EncuestaController {

    @Autowired
    private EncuestaRepository encuestaRepository;

    @GetMapping("/encuestas")
    public ResponseEntity<Iterable<Encuesta>> getAllEncuesta(Pageable pageable){
        Page<Encuesta> encuestas = encuestaRepository.findAll(pageable);
        return new ResponseEntity<>(encuestas, HttpStatus.OK);
    }

    @PostMapping("/encuestas")
    public ResponseEntity<?> saveEncuesta(@Valid @RequestBody Encuesta encuesta){
        encuesta = encuestaRepository.save(encuesta);
        HttpHeaders httpHeaders = new HttpHeaders();

        //
        URI newEncuestaUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(encuesta.getId()).toUri();
        httpHeaders.setLocation(newEncuestaUri);

        return new ResponseEntity<>(null,httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/encuestas/{idEncuesta}")
    public ResponseEntity<?> getOneEncuesta(@PathVariable Long idEncuesta){
        verifyEncuesta(idEncuesta);
        Optional<Encuesta> encuesta = encuestaRepository.findById(idEncuesta);
        if (encuesta.isPresent()){
            return new ResponseEntity<>(encuesta,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/encuestas/{idEncuesta}")
    public ResponseEntity<?> updateEncuesta(@Valid @RequestBody Encuesta encuesta, @PathVariable Long idEncuesta){
        verifyEncuesta(idEncuesta);
        encuesta.setId(idEncuesta);
        encuestaRepository.save(encuesta);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/encuestas/{idEncuesta}")
    public ResponseEntity<?> deleteEncuesta(@PathVariable Long idEncuesta){
        verifyEncuesta(idEncuesta);
        encuestaRepository.deleteById(idEncuesta);
         return new ResponseEntity<>(HttpStatus.OK);
    }

    protected  void verifyEncuesta(Long idEncuesta){
        Optional<Encuesta> encuesta = encuestaRepository.findById(idEncuesta);
        if (!encuesta.isPresent()){
            throw new ResourceNotFoundException("Encuesta con el ID : " + idEncuesta + " no encontrada");
        }
    }
}
