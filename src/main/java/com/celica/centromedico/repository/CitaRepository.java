package com.celica.centromedico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.celica.centromedico.model.Cita;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer>{

}
