package cl.uach.info090.ContrerasFrancisco;

import java.util.Date;

public class CreadorBoletaCL implements CreadorBoleta {

	@Override
	public Boleta generarBoleta(String mesa, String detalle, double neto) {
		 Date fechaActual = new Date();
		 double porcentajePropina = 10.0;
		return new BoletaCL(fechaActual,detalle,neto,porcentajePropina);
	}
	
       
    
}
