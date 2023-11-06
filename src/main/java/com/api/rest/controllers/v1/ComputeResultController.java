package com.api.rest.controllers.v1;


import com.api.rest.dto.OpcionCountDTO;
import com.api.rest.dto.VotoResultDTO;
import com.api.rest.model.Voto;
import com.api.rest.repositories.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("computeResultControllerV1")
@RequestMapping("/v1")
public class ComputeResultController {

    @Autowired
    private VotoRepository votoRepository;


    @GetMapping("/calcularResultados")
    public ResponseEntity<?> calcularResultado(@RequestParam Long idEncuesta){
        VotoResultDTO votoResultDTO = new VotoResultDTO();
        Iterable<Voto> votos = votoRepository.findByEncuesta(idEncuesta);

        //Algoritmo para contar los votos
        int totalVotos =0;
        Map<Long, OpcionCountDTO> tempMap =  new HashMap<>();
        for (Voto v:votos){
            totalVotos ++;
            //obtenemos la opcion count correspodiente a esta opcion
            OpcionCountDTO opcionCountDTO = tempMap.get(v.getOpcion().getId());
            if (opcionCountDTO == null){
                opcionCountDTO = new OpcionCountDTO();
                opcionCountDTO.setIdOpcion(v.getOpcion().getId());
                tempMap.put(v.getOpcion().getId(),opcionCountDTO);
            }
            opcionCountDTO.setCount(opcionCountDTO.getCount()+1);
        }
        votoResultDTO.setTotalVotos(totalVotos);
        votoResultDTO.setResults(tempMap.values());

        return  new ResponseEntity<>(votoResultDTO, HttpStatus.OK);

    }
}
