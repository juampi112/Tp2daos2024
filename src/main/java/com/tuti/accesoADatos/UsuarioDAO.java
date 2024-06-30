package com.tuti.accesoADatos;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tuti.entidades.Usuario;

public interface UsuarioDAO extends JpaRepository<Usuario, Long> {
    Usuario findByPatenteVehiculo(String patenteVehiculo);
    public Usuario findBydni(Long dni);
}

