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

import com.celica.centromedico.model.Alta;
import com.celica.centromedico.model.Cita;
import com.celica.centromedico.model.EstadoCita;
import com.celica.centromedico.model.Paciente;
import com.celica.centromedico.service.AltaInterface;
import com.celica.centromedico.service.CitaInterface;
import com.celica.centromedico.service.PacienteInterface;
import com.celica.centromedico.util.Response;

@RestController
@CrossOrigin(origins="*", methods= {RequestMethod.GET, RequestMethod.POST})
@RequestMapping(value="/api")
public class AltaController {

	@Autowired
	private AltaInterface altaService;
	
	@Autowired
	private CitaInterface citaService;
	
	@Autowired
	private PacienteInterface pacienteService;
	
	@GetMapping(value="/altas", produces="application/json")
	public ResponseEntity<?> findAll(){
		return new ResponseEntity<>(altaService.findAll(),HttpStatus.OK);
	}
	
	@GetMapping(value="/alta", produces="application/json")
	public ResponseEntity<?> findAll(@PageableDefault(size=10) Pageable pageable){
		return new ResponseEntity<>(altaService.findAll(pageable),HttpStatus.OK);
	}
	
	@PostMapping(value="/alta", produces="application/json")
	public ResponseEntity<?> save(@RequestBody Alta alta){
		Cita cita=citaService.findById(alta.getCitas().getId());
		if(cita==null) {
			return new ResponseEntity<>(new Response("Error datos no encontrados"),HttpStatus.NOT_FOUND);
		}
		
		Paciente paciente = pacienteService.findById(cita.getPaciente().getId());
		if(paciente.isEstaRegistrado()==false) {
			return new ResponseEntity<>(new Response("La cita del paciente ya fue atendida"),HttpStatus.CONFLICT);
		}
		altaService.save(alta);
		
		paciente.setEstaRegistrado(false);
		pacienteService.update(paciente);
		
		EstadoCita estadoC=new EstadoCita();
		estadoC.setId(2);
		cita.setEstadoCita(estadoC);
		citaService.save(cita);
		
		return new ResponseEntity<>(new Response("La cita ha sido atendida del paciente: "+cita.getPaciente().getNombre()+" "+cita.getPaciente().getApellido()),HttpStatus.CREATED);
	}
}
