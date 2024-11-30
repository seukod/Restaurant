package cl.uach.info090.ContrerasFrancisco;

import java.util.Date;
/**
 * Clase que implementa la interfaz CreadorBoleta para generar boletas.
 * Esta clase crea una boleta con un porcentaje fijo de propina del 10%.
 * 
 * @author Francisco Contreras
 */
public class CreadorBoletaCL implements CreadorBoleta {
	/**
     * Genera una boleta para un consumo en una mesa específica.
     *
     * @param mesa El identificador de la mesa para la cual se genera la boleta.
     * @param detalle El detalle del consumo realizado en la mesa.
     * @param neto El valor neto del consumo.
     * @return Una instancia de Boleta que representa el consumo en la mesa.
     */
	@Override
	public Boleta generarBoleta(String mesa, String detalle, double neto) {
		 Date fechaActual = new Date();
		 double porcentajePropina = 10.0;
		return new BoletaCL(fechaActual,detalle,neto,porcentajePropina);
	}
	
       
    
}
