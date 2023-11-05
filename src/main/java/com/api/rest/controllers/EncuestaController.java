package com.api.rest.controllers;

import com.api.rest.model.Encuesta;
import com.api.rest.repositories.EncuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
public class EncuestaController {

    @Autowired
    private EncuestaRepository encuestaRepository;

    @GetMapping("/encuestas")
    public ResponseEntity<Iterable<Encuesta>> getAllEncuesta(){
        return new ResponseEntity<>(encuestaRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/encuestas")
    public ResponseEntity<?> saveEncuesta(@RequestBody Encuesta encuesta){
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
        Optional<Encuesta> encuesta = encuestaRepository.findById(idEncuesta);
        if (encuesta.isPresent()){
            return new ResponseEntity<>(encuesta,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/encuestas/{idEncuesta}")
    public ResponseEntity<?> updateEncuesta(@RequestBody Encuesta encuesta, @PathVariable Long idEncuesta){
        encuesta.setId(idEncuesta);
        Encuesta e = encuestaRepository.save(encuesta);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/encuestas/{idEncuesta}")
    public ResponseEntity<?> deleteEncuesta(@PathVariable Long idEncuesta){
         encuestaRepository.deleteById(idEncuesta);
         return new ResponseEntity<>(HttpStatus.OK);
    }
}
