package com.api.rest.repositories;

import com.api.rest.model.Encuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface EncuestaRepository extends JpaRepository<Encuesta,Long> {
}
