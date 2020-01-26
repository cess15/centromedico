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


@Entity
@Table(name="alta")
public class Alta implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="cita_id")
	private Cita citas;
	
	@Column(name="fechasalida")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaSalida;
	
	private double abono;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cita getCitas() {
		return citas;
	}

	public void setCitas(Cita citas) {
		this.citas = citas;
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public double getAbono() {
		return abono;
	}

	public void setAbono(double abono) {
		this.abono = abono;
	}
	
	
}
