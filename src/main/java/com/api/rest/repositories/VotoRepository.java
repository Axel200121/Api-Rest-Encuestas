package com.api.rest.repositories;

import com.api.rest.model.Encuesta;
import com.api.rest.model.Voto;
import org.springframework.data.repository.CrudRepository;

public interface VotoRepository extends CrudRepository<Voto,Long> {
}
