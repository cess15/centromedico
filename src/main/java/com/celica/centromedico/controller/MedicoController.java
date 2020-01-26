package com.celica.centromedico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.celica.centromedico.model.Medico;
import com.celica.centromedico.model.Paciente;
import com.celica.centromedico.service.MedicoInterface;
import com.celica.centromedico.service.PacienteInterface;
import com.celica.centromedico.util.Response;



@RestController
@CrossOrigin(origins="*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping(value="/api")
public class MedicoController {
	@Autowired
	private MedicoInterface medicoService;
	
	@Autowired
	private PacienteInterface pacienteService;
	
	@GetMapping(value="/medicos",produces="application/json")
	public ResponseEntity<?> findAll(){
		return new ResponseEntity<>(medicoService.findAll(),HttpStatus.OK);
	}
	
	@GetMapping(value="/medico",produces="application/json")
	public ResponseEntity<?> findAll(@PageableDefault(size=10) Pageable pageable){
		return new ResponseEntity<>(medicoService.findAll(pageable),HttpStatus.OK);
	}
	
	@GetMapping(value="/medico/{id}",produces="application/json")
	public ResponseEntity<?> findById(@PathVariable("id") int id){
		Medico medico = medicoService.findById(id);
		if(medico==null) {
			return new ResponseEntity<Response>(new Response("Error: no existe el medico"),HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(medico,HttpStatus.OK);
	}
	
	@PostMapping(value="/medico",produces="application/json")
	public ResponseEntity<?> save(@RequestBody Medico m){
		Medico medico = medicoService.findByNumDocumento(m.getNumDocumento());
		Paciente paciente = pacienteService.findByNumDocumento(m.getNumDocumento());
		if(medico!=null) {
			return new ResponseEntity<>(new Response("Medico "+medico.getNombre()+" "+medico.getApellido()+" con numero documento "+m.getNumDocumento()+" ya se encuentra registrado"),HttpStatus.CONFLICT);
		}else if(paciente!=null) {
			return new ResponseEntity<>(new Response("Paciente "+paciente.getNombre()+" "+paciente.getApellido()+" con el numero de documento "+m.getNumDocumento()+" ya existe"),HttpStatus.CONFLICT);
		}
		medicoService.save(m);
		return new ResponseEntity<>(new Response("Medico "+m.getNombre()+" "+m.getApellido()+" ingresado"),HttpStatus.OK);
	}
	
	@PutMapping(value="/medico/{id}",produces="application/json")
	public ResponseEntity<Response> update(@PathVariable("id") int id,@RequestBody Medico m){
		Medico medico = medicoService.findById(id);
		if(medico==null) {
			return new ResponseEntity<Response>(new Response("Error: no existe el medico"),HttpStatus.NOT_FOUND);
		}
		m.setId(id);
		if(medico.getNumDocumento().equals(m.getNumDocumento())) {
			medicoService.save(m);
			return new ResponseEntity<Response>(new Response("Se ha modificado los datos del medico"),HttpStatus.OK);
		}
		Medico medicos = medicoService.findByNumDocumento(m.getNumDocumento());
		Paciente paciente = pacienteService.findByNumDocumento(m.getNumDocumento());
		if(medicos!=null) {
			return new ResponseEntity<>(new Response("Medico "+medicos.getNombre()+" "+medicos.getApellido()+" con numero documento "+m.getNumDocumento()+" ya se encuentra registrado"),HttpStatus.CONFLICT);
		}else if(paciente!=null) {
			return new ResponseEntity<>(new Response("Paciente "+paciente.getNombre()+" "+paciente.getApellido()+" con el numero de documento "+m.getNumDocumento()+" ya existe"),HttpStatus.CONFLICT);
		}
		medicoService.save(m);
		return new ResponseEntity<Response>(new Response("Se ha modificado los datos del medico"),HttpStatus.OK);
	}
	
	@DeleteMapping(value="/medico/{id}",produces="application/json")
	public ResponseEntity<?> delete(@PathVariable("id") int id){
		Medico medico=medicoService.findById(id);
		if(medico==null) {
			return new ResponseEntity<Response>(new Response("Error: no existe el medico"),HttpStatus.NOT_FOUND);
		}
		medicoService.delete(id);
		return new ResponseEntity<Response>(new Response("Medico "+medico.getNombre()+" "+medico.getApellido()+" eliminado"),HttpStatus.OK);
	}
}
