package com.api.rest.repositories;

import com.api.rest.model.Encuesta;
import com.api.rest.model.Opcion;
import org.springframework.data.repository.CrudRepository;

public interface OpcionRepository extends CrudRepository<Opcion,Long> {
}
