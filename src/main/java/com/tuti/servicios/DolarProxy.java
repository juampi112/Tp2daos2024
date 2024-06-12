package com.tuti.servicios;

import java.util.List;

import com.tuti.dto.CotizacionDolarDTO;


public interface DolarProxy {
	
	/**
	 * Devuelve la lista de cotizaciones del día
	 * @return Lista de cotizaciones
	 */
	public List<CotizacionDolarDTO> getCotizaciones();

	

}
