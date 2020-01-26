package com.celica.centromedico.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.celica.centromedico.model.Paciente;
import com.celica.centromedico.repository.PacienteRepository;



@Service
public class PacienteService implements PacienteInterface {
	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Override
	public Page<Paciente> findAll(Pageable pageable) {
		Page<Paciente> paciente = pacienteRepository.findAll(pageable);
		if(paciente!=null) {
			return paciente;
		}
		return null;
	}

	@Override
	public void save(Paciente p) {
		pacienteRepository.save(p);
		
	}
	
	@Override
	public Paciente findByNumDocumento(String numDocumento) {
		Paciente paciente=pacienteRepository.findByNumDocumento(numDocumento);
		if(paciente!=null) {
			return paciente;
		}
		return null;
	}

	@Override
	public void delete(int id) {
		pacienteRepository.deleteById(id);
	}

	@Override
	public Paciente findById(int id) {
		Optional<Paciente> paciente=pacienteRepository.findById(id);
		if(paciente.isPresent()) {
			return paciente.get();
		}
		return null;
	}

	@Override
	public void update(Paciente p) {
		pacienteRepository.save(p);
	}

	@Override
	public List<Paciente> findAll() {
		return pacienteRepository.findAll();
	}
}
