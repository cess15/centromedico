package com.celica.centromedico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.celica.centromedico.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Integer>{
	Paciente findByNumDocumento(String numDocumento);
}
