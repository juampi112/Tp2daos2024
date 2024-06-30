package com.tuti.servicios;



import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.tuti.accesoADatos.UsuarioDAO;
import com.tuti.entidades.Usuario;
import com.tuti.exception.Excepcion;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;


@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private  Validator validator;
	
	@Autowired
	private UsuarioDAO dao;
	@Override
	public List<Usuario> getAll() {
		return dao.findAll();
	}
	@Override
	public Usuario getBydni(Long dni) {
		return  dao.findBydni(dni);
	}
	@Override
	public void update(Usuario u) {
		dao.save(u);
	}
	@Override
	public void insert(Usuario u) throws Exception {
		
		Set<ConstraintViolation<Usuario>> cv = validator.validate(u);
		if(cv.size()>0)
		{
			String err="";
			for (ConstraintViolation<Usuario> constraintViolation : cv) {
				err+=constraintViolation.getPropertyPath()+": "+constraintViolation.getMessage()+"\n";
			}
			throw new Excepcion(err,400);
		}
		else if(getBydni(u.getDni())!= null)
		{ 
			throw new Excepcion("Ya existe una persona con ese dni.",400);
		}
		else
			dao.save(u);
	}
	@Override
	public void delete(Long id) {
		dao.deleteById(id);
	}
	

	@Override
	public List<Usuario> getUsuario(String apellido, String nombre) {
		if(apellido==null && nombre==null)
			return dao.findAll();
		else
			return dao.findByApellidoOrNombre(apellido, nombre);
	}
	
	
	
	
   /* @Autowired
    private UsuarioDAO UsuarioDAO;

    @Override
    public UsuarioDAO save(Usuario usuario) {
        // Verifica que no exista un usuario con el mismo DNI o la misma patente
        if (UsuarioDAO.existsById(usuario.getDni())) {
            throw new IllegalArgumentException("Ya existe un usuario con el mismo DNI");
        }
        if (UsuarioDAO.findByPatenteVehiculo(usuario.getPatenteVehiculo()) != null) {
            throw new IllegalArgumentException("Ya existe un usuario con la misma patente de vehículo");
        }
        return UsuarioDAO.save(usuario);
    }

    @Override
    public Optional<UsuarioDAO> findById(Long dni) {
        return UsuarioDAO.findById(dni);
    }

    @Override
    public UsuarioDAO findByPatenteVehiculo(String patenteVehiculo) {
        return UsuarioDAO.findByPatenteVehiculo(patenteVehiculo);
    }

    @Override
    public void deleteById(Long dni) {
        if (!UsuarioDAO.existsById(dni)) {
            throw new IllegalArgumentException("No existe un usuario con el DNI especificado");
        }
        usuarioRepository.deleteById(dni);
    } */ 
}

