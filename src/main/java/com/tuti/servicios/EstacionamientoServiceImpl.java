package com.tuti.servicios;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.tuti.accesoADatos.EstacionamientoDAO;
import com.tuti.entidades.Estacionamiento;
import com.tuti.exception.Excepcion;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;


@Service
public class EstacionamientoServiceImpl implements EstacionamientoService {

	@Autowired
	private  Validator validator;
	@Autowired
	private EstacionamientoDAO dao;
	@Override
	public Estacionamiento getPatente(String patente) {
		return  dao.findByPatente(patente);
	}
	@Override
	public void update(Estacionamiento e) {
		dao.save(e);
	}
	@Override
	public void insert(Estacionamiento e) throws Exception {
		
		Set<ConstraintViolation<Estacionamiento>> cv = validator.validate(e);
		if(cv.size()>0)
		{
			String err="";
			for (ConstraintViolation<Estacionamiento> constraintViolation : cv) {
				err+=constraintViolation.getPropertyPath()+": "+constraintViolation.getMessage()+"\n";
			}
			throw new Excepcion(err,400);
		}
		else
			dao.save(e);
	}
	
}

