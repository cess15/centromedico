package com.celica.centromedico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.celica.centromedico.model.Cita;
import com.celica.centromedico.model.EstadoCita;
import com.celica.centromedico.model.Medico;
import com.celica.centromedico.model.Paciente;
import com.celica.centromedico.service.CitaInterface;
import com.celica.centromedico.service.MedicoInterface;
import com.celica.centromedico.service.PacienteInterface;
import com.celica.centromedico.util.Response;


@RestController
@CrossOrigin(origins="*", methods= {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/api")
public class CitaController {

	@Autowired
	private CitaInterface citaService;
	
	@Autowired
	private MedicoInterface medicoService;
	
	@Autowired
	private PacienteInterface pacienteService;
	
	@GetMapping(value="/citas", produces="application/json")
	public ResponseEntity<?> findAll(){
		return new ResponseEntity<>(citaService.findAll(),HttpStatus.OK);
	}
	
	@GetMapping(value="/cita", produces="application/json")
	public ResponseEntity<?> findAll(@PageableDefault(size=10) Pageable pageable){
		return new ResponseEntity<>(citaService.findAll(pageable),HttpStatus.OK);
	}
	
	@PostMapping(value="/cita", produces="application/json")
	public ResponseEntity<?> save(@RequestBody Cita cita){
		Medico medico = medicoService.findById(cita.getMedico().getId());
		Paciente paciente = pacienteService.findById(cita.getPaciente().getId());
		
		if(paciente==null) {
			return new ResponseEntity<>(new Response("No se encontro al paciente"),HttpStatus.NOT_FOUND);
		}else if(medico==null) {
			return new ResponseEntity<>(new Response("No se encontro al médico"),HttpStatus.NOT_FOUND);
		}
		
		if(paciente.isEstaRegistrado()==true) {
			return new ResponseEntity<>(new Response("El paciente ya tiene una cita en progreso"),HttpStatus.CONFLICT);
		}
		
		EstadoCita estadoC = new EstadoCita();
		estadoC.setId(1);
		cita.setEstadoCita(estadoC);
		citaService.save(cita);
		
		paciente.setEstaRegistrado(true);
		pacienteService.update(paciente);
		
		return new ResponseEntity<>(new Response("Se le ha asignado una cita al Paciente!"),HttpStatus.CREATED);
	}
	
	
}
