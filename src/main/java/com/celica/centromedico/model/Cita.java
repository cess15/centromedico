package com.celica.centromedico.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="cita")
public class Cita implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="paciente_id")
	@JsonIgnoreProperties(value={"citas","medico"},allowSetters=true)
	private Paciente paciente;
	
	@ManyToOne
	@JoinColumn(name="medico_id")
	@JsonIgnoreProperties({"citas"})
	private Medico medico;
	
	@ManyToOne
	@JoinColumn(name="estado_id")
	private EstadoCita estadoCita;
	
	private String motivo;
	
	@Column(name="fecha_cita")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCita;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public EstadoCita getEstadoCita() {
		return estadoCita;
	}

	public void setEstadoCita(EstadoCita estadoCita) {
		this.estadoCita = estadoCita;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Date getFechaCita() {
		return fechaCita;
	}

	public void setFechaCita(Date fechaCita) {
		this.fechaCita = fechaCita;
	}

	
	
	
}
