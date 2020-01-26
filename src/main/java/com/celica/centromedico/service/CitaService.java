package com.celica.centromedico.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.celica.centromedico.model.Cita;
import com.celica.centromedico.repository.CitaRepository;


@Service
public class CitaService implements CitaInterface {
	@Autowired
	private CitaRepository citaRepository;
	
	@Override
	public List<Cita> findAll() {
		return citaRepository.findAll();
	}

	@Override
	public void save(Cita c) {
		citaRepository.save(c);
		
	}

	@Override
	public Cita findById(int id) {
		Optional<Cita> internacion=citaRepository.findById(id);
		if(internacion.isPresent()) {
			return internacion.get();
		}
		return null;
	}

	@Override
	public Page<Cita> findAll(Pageable pageable) {
		return citaRepository.findAll(pageable);
	}
}
