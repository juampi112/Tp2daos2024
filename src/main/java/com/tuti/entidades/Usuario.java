package com.tuti.entidades;


import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;


@Entity
public class Usuario {

	@Id
	private Long dni;

	private String nombre;
	private String apellido;
	private String domicilio;
	@Email(message = "El e-mail ingresado no es valido")
	private String email;
	private Date fechaDeNacimiento;
	private String patente;
	private String contraseña;
	private Double saldoCuenta;

	
	//PROBAR CREAR USUARIO SIN DNI
	public Usuario() {
		super();
	}


	public Usuario(Long dni, String nombre, String apellido, String domicilio, String email, Date fechaDeNacimiento,
			String patente, String contraseña) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.domicilio = domicilio;
		this.email = email;
		this.fechaDeNacimiento = fechaDeNacimiento;
		this.patente = patente;
		this.contraseña = contraseña;
		//SAQUE SALDO
	}

	public Long getDni() {
		return dni;
	}

	public void setDni(Long dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechaDeNacimiento() {
		return fechaDeNacimiento;
	}

	public void setFechaDeNacimiento(Date fechaDeNacimiento) {
		this.fechaDeNacimiento = fechaDeNacimiento;
	}

	public String getPatente() {
		return patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	
	public Double getSaldoCuenta() {
		return saldoCuenta;
	}

	public void setSaldoCuenta(Double saldoCuenta) {
		this.saldoCuenta = saldoCuenta;
	}

}
