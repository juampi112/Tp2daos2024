package com.tuti.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tuti.dto.CotizacionDolarDTO;
import com.tuti.dto.TipoCambioDolarDTO;
@Service
public class DolarProxyImpl implements DolarProxy {

	@Override
	public List<CotizacionDolarDTO> getCotizaciones() {
		
		List<CotizacionDolarDTO> lista=new ArrayList<CotizacionDolarDTO>();
		
		ResponseEntity<TipoCambioDolarDTO[]> resp= new RestTemplate().getForEntity("https://www.dolarsi.com/api/api.php?type=valoresprincipales", TipoCambioDolarDTO[].class);
		for (TipoCambioDolarDTO tipoCambio : resp.getBody()) {
			lista.add(tipoCambio.getCasa());
		}
		return lista;
	}
	

}
