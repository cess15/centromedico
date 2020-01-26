package com.celica.centromedico.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.celica.centromedico.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Integer>{
	Medico findByNumDocumento(String numDocumento);
	
}
