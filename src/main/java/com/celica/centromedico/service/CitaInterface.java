package com.celica.centromedico.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.celica.centromedico.model.Cita;

public interface CitaInterface {
	List<Cita> findAll();
	Page<Cita> findAll(Pageable pageable);
	void save(Cita c);
	Cita findById(int id);
}
