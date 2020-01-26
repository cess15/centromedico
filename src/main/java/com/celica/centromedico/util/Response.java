package com.celica.centromedico.util;

public class Response {
	private String mensaje;
	
	public Response(String mensaje) {
		this.mensaje=mensaje;
	}
	
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
