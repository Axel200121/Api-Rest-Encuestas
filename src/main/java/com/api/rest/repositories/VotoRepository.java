package com.api.rest.repositories;

import com.api.rest.model.Encuesta;
import com.api.rest.model.Voto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface VotoRepository extends CrudRepository<Voto,Long> {

    @Query(value = "SELECT v.* FROM Opcion o, Voto v WHERE o.encuesta_id=?1 AND v.opcion_id= o.opcion_id", nativeQuery = true)
    public Iterable<Voto> findByEncuesta(Long idEncuesta);
}
