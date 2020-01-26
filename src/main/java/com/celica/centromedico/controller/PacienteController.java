package com.celica.centromedico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.celica.centromedico.model.Medico;
import com.celica.centromedico.model.Paciente;
import com.celica.centromedico.service.MedicoInterface;
import com.celica.centromedico.service.PacienteInterface;
import com.celica.centromedico.util.Response;

@RestController
@CrossOrigin(origins="*",methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RequestMapping(value="/api")
public class PacienteController {

	@Autowired
	private PacienteInterface pacienteService;
	
	@Autowired
	private MedicoInterface medicoService;
	
	@GetMapping(value="/pacientes",produces="application/json")
	public ResponseEntity<?> findAll(){
		return new ResponseEntity<>(pacienteService.findAll(),HttpStatus.OK);	
	}
	
	@GetMapping(value="/paciente",produces="application/json")
	public ResponseEntity<?> findAl(@PageableDefault(size=10) Pageable pageable){
		return new ResponseEntity<>(pacienteService.findAll(pageable),HttpStatus.OK);
	}
	
	@PostMapping(value="/paciente",produces="application/json")
	public ResponseEntity<?> save(@RequestBody Paciente p){
		Paciente paciente = pacienteService.findByNumDocumento(p.getNumDocumento());
		Medico medico = medicoService.findByNumDocumento(p.getNumDocumento());
		if(paciente!=null) {
			return new ResponseEntity<>(new Response("Paciente "+paciente.getNombre()+" "+paciente.getApellido()+" con el numero de documento "+p.getNumDocumento()+" ya existe"),HttpStatus.CONFLICT);
		}else if(medico!=null) {
			return new ResponseEntity<>(new Response("Medico "+medico.getNombre()+" "+medico.getApellido()+" con el numero de documento "+p.getNumDocumento()+" ya existe"),HttpStatus.CONFLICT);
		}
		p.setEstaRegistrado(false);
		pacienteService.save(p);
		return new ResponseEntity<>(new Response("Paciente "+p.getNombre()+" "+p.getApellido()+" ingresado"),HttpStatus.OK);			
	}
	
	@DeleteMapping(value="/paciente/{id}",produces="application/json")
	public ResponseEntity<Response> delete(@PathVariable("id") int id){
		Paciente paciente=pacienteService.findById(id);
		if(paciente==null) {
			return new ResponseEntity<Response>(new Response("Error: no existe el paciente"),HttpStatus.NOT_FOUND);
		}
		pacienteService.delete(id);
		return new ResponseEntity<Response>(new Response("Paciente "+paciente.getNombre()+" "+paciente.getApellido()+" eliminado"),HttpStatus.OK);
	}
	
	@PutMapping(value="/paciente/{id}",produces="application/json")
	public ResponseEntity<Response> update(@PathVariable("id") int id,@RequestBody Paciente p){
		Paciente paciente = pacienteService.findById(id);
		if(paciente==null) {
			return new ResponseEntity<Response>(new Response("Error: no existe el paciente"),HttpStatus.NOT_FOUND);
		}
		p.setId(id);
		if(paciente.getNumDocumento().equals(p.getNumDocumento())) {
			p.setEstaRegistrado(paciente.isEstaRegistrado());
			pacienteService.save(p);
			return new ResponseEntity<Response>(new Response("Se ha modificado los datos del paciente"),HttpStatus.OK);			
		}
		Paciente pacientes = pacienteService.findByNumDocumento(p.getNumDocumento());
		Medico medico = medicoService.findByNumDocumento(p.getNumDocumento());
		if(pacientes!=null) {
			return new ResponseEntity<Response>(new Response("Paciente "+pacientes.getNombre()+" "+pacientes.getApellido()+" con el numero de documento "+p.getNumDocumento()+" ya existe"),HttpStatus.CONFLICT);
		}else if(medico!=null) {
			return new ResponseEntity<>(new Response("Medico "+medico.getNombre()+" "+medico.getApellido()+" con el numero de documento "+p.getNumDocumento()+" ya existe"),HttpStatus.CONFLICT);
		}
		p.setEstaRegistrado(paciente.isEstaRegistrado());
		pacienteService.save(p);
		return new ResponseEntity<Response>(new Response("Se ha modificado los datos del paciente"),HttpStatus.OK);			
	}
	
	@GetMapping(value="/paciente/{id}",produces="application/json")
	public ResponseEntity<?> findById(@PathVariable("id") int id){
		Paciente paciente = pacienteService.findById(id);
		if(paciente==null) {
			return new ResponseEntity<>(new Response("Error: no existe el paciente"),HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(paciente,HttpStatus.OK);
	}
}
